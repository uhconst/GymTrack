import Foundation
import shared

extension ExerciseListScreen {
    @MainActor class ExerciseListViewModel: ObservableObject {
        private var exerciseDataSource: ExerciseDataSource? = nil
        
        private let searchExercises = SearchExercises()
        
        private var exercises = [Exercise]()
        @Published private(set) var filteredExercises = [Exercise]()
        @Published var searchText = "" {
            didSet {
                self.filteredExercises = searchExercises.execute(exercises: self.exercises, query: searchText, muscleIds: nil) //muscleIdsFilter
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(exerciseDataSource: ExerciseDataSource? = nil) {
            self.exerciseDataSource = exerciseDataSource
        }
        
        func loadExercises() {
            exerciseDataSource?.getAllExercises(completionHandler: { exercises, error in
                self.exercises = exercises ?? []
                self.filteredExercises = self.exercises
            })
        }
        
        func deleteExerciseById(id: Int64?) {
            if id != nil {
                exerciseDataSource?.deleteExerciseById(id: id!, completionHandler: { error in
                    self.loadExercises()
                })
            }
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setExerciseDataSource(exerciseDataSource: ExerciseDataSource) {
            self.exerciseDataSource = exerciseDataSource
        }
    }
}
