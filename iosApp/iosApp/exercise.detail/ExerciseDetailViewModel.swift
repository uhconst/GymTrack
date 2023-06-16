import Foundation
import shared

extension ExerciseDetailScreen {
    @MainActor class ExerciseDetailViewModel: ObservableObject {
        private var exerciseDataSource: ExerciseDataSource?
        
        private var exerciseId: Int64? = nil
        @Published var exerciseName = ""
        @Published var exerciseWeight = ""
        @Published private(set) var exerciseColor = Exercise.Companion().generateRandomColor()
        
        init(exerciseDataSource: ExerciseDataSource? = nil) {
            self.exerciseDataSource = exerciseDataSource
        }
        
        func loadExerciseIfExists(id: Int64?) {
            if id != nil {
                self.exerciseId = id
                exerciseDataSource?.getExerciseById(id: id!, completionHandler: { exercise, error in
                    self.exerciseName = exercise?.title ?? ""
                    self.exerciseWeight = exercise?.content ?? ""
                    self.exerciseColor = exercise?.colorHex ?? Exercise.Companion().generateRandomColor()
                })
            }
        }
        
        func saveExercise(onSaved: @escaping () -> Void) {
            exerciseDataSource?.insertExercise(
                exercise: Exercise(id: exerciseId == nil ? nil : KotlinLong(value: exerciseId!), title: self.exerciseName, content: self.exerciseWeight, colorHex: self.exerciseColor, created: DateTimeUtil().now()), completionHandler: { error in
                    onSaved()
                })
        }
        
        func setParamsAndLoadExercise(exerciseDataSource: ExerciseDataSource, exerciseId: Int64?) {
            self.exerciseDataSource = exerciseDataSource
            loadExerciseIfExists(id: exerciseId)
        }
    }
}
