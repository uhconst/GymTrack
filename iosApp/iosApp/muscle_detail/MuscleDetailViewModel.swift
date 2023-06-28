import Foundation
import shared

extension MuscleDetailScreen {
    @MainActor class MuscleDetailViewModel: ObservableObject {
        private var muscleDataSource: MuscleDataSource?
        
        private var muscleId: Int64? = nil
        @Published var muscleName = ""
        @Published var muscleDescription = ""
        
        init(muscleDataSource: MuscleDataSource? = nil) {
            self.muscleDataSource = muscleDataSource
        }
        
        func loadMuscleIfExists(id: Int64?) {
            if id != nil {
                self.muscleId = id
                muscleDataSource?.getMuscleById(id: id!, completionHandler: { muscle, error in
                    self.muscleName = muscle?.name ?? ""
                    self.muscleDescription = muscle?.description ?? ""
                })
            }
        }
        
        func saveMuscle(onSaved: @escaping () -> Void) {
            muscleDataSource?.insertMuscle( //todo created
                muscle: Muscle(id: muscleId == nil ? nil : KotlinLong(value: muscleId!), name: self.muscleName, description: self.muscleDescription, created: DateTimeUtil().now(), modified: DateTimeUtil().now()), completionHandler: { error in
                    onSaved()
                })
        }
        
        func setParamsAndLoadMuscle(muscleDataSource: MuscleDataSource, muscleId: Int64?) {
            self.muscleDataSource = muscleDataSource
            loadMuscleIfExists(id: muscleId)
        }
    }
}
