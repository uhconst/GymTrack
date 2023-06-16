import SwiftUI
import shared

struct ExerciseListScreen: View {
    private var exerciseDataSource: ExerciseDataSource
    @StateObject var viewModel = ExerciseListViewModel(exerciseDataSource: nil)
    
    @State private var isExerciseSelected = false
    @State private var selectedExerciseId: Int64? = nil
    
    init(exerciseDataSource: ExerciseDataSource) {
        self.exerciseDataSource = exerciseDataSource
    }
    
    var body: some View {
        VStack {
            ZStack {
                NavigationLink(destination: ExerciseDetailScreen(exerciseDataSource: self.exerciseDataSource, exerciseId: selectedExerciseId), isActive: $isExerciseSelected) {
                    EmptyView()
                }.hidden()
                HideableSearchTextField<ExerciseDetailScreen>(onSearchToggled: {
                    viewModel.toggleIsSearchActive()
                }, destinationProvider: {
                    ExerciseDetailScreen(
                        exerciseDataSource: exerciseDataSource,
                        exerciseId: selectedExerciseId
                    )
                }, isSearchActive: viewModel.isSearchActive, searchText: $viewModel.searchText)
                .frame(maxWidth: .infinity, minHeight: 40)
                .padding()
                
                if !viewModel.isSearchActive {
                    Text("All exercises")
                        .font(.title2)
                }
            }
            
            List {
                ForEach(viewModel.filteredExercises, id: \.self.id) { exercise in
                    Button(action: {
                        isExerciseSelected = true
                        selectedExerciseId = exercise.id?.int64Value
                    }) {
                        ExerciseItem(exercise: exercise, onDeleteClick: {
                            viewModel.deleteExerciseById(id: exercise.id?.int64Value)
                        })
                    }
                }
            }
            .onAppear {
                viewModel.loadExercises()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }
        .onAppear {
            viewModel.setExerciseDataSource(exerciseDataSource: exerciseDataSource)
        }
    }
}

struct ExerciseListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
