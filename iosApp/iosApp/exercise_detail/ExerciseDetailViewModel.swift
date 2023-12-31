import Foundation
import shared

extension ExerciseDetailScreen {
    @MainActor class ExerciseDetailViewModel: ObservableObject {
        private var exerciseDataSource: ExerciseDataSource?
        private var muscleDataSource: MuscleDataSource?
        
        private var exerciseId: Int64? = nil
        @Published var exerciseName = ""
        @Published var exerciseWeight = ""
        @Published private(set) var exerciseColor = Exercise.Companion().generateRandomColor()
        @Published var exerciseMuscleId: Int64? = nil
        @Published var musclesList: [Muscle] = []
        
        init(exerciseDataSource: ExerciseDataSource? = nil, muscleDataSource: MuscleDataSource? = nil) {
            self.exerciseDataSource = exerciseDataSource
            self.muscleDataSource = muscleDataSource
        }
        
        func loadExerciseIfExists(id: Int64?) {
            if id != nil {
                self.exerciseId = id
                exerciseDataSource?.getExerciseById(id: id!, completionHandler: { exercise, error in
                    self.exerciseName = exercise?.name ?? ""
                    self.exerciseWeight = String(format: "%.1f", exercise?.weight.weight ?? "")
                    self.exerciseColor = exercise?.muscle?.colorHex ?? Exercise.Companion().generateRandomColor()
                    self.exerciseMuscleId = exercise?.muscle?.id?.int64Value ?? 0
                })
            }
            
            muscleDataSource?.getAllMuscles(completionHandler: { muscles, error in
                if let muscles = muscles {
                    self.musclesList.append(contentsOf: muscles)
                }
            })
        }
        
        func saveExercise(onSaved: @escaping () -> Void) {
            let exerciseMuscle: Muscle? = self.musclesList.first(where: { $0.id?.int64Value == self.exerciseMuscleId })
            
            let weight = Weight(id: nil, weight: Double(self.exerciseWeight) ?? 0.0, unit: Weight.companion.UNIT_KG, created: DateTimeUtil().now())
            
            exerciseDataSource?.insertExercise( //todo created
                exercise: Exercise(id: exerciseId == nil ? nil : KotlinLong(value: exerciseId!), name: self.exerciseName, weight: weight, created: DateTimeUtil().now(), modified: DateTimeUtil().now(), muscle: exerciseMuscle), completionHandler: { error in
                    onSaved()
                })
        }
        
        func setParamsAndLoadExercise(exerciseDataSource: ExerciseDataSource, muscleDataSource: MuscleDataSource, exerciseId: Int64?) {
            self.exerciseDataSource = exerciseDataSource
            self.muscleDataSource = muscleDataSource
            loadExerciseIfExists(id: exerciseId)
        }
    }
}
