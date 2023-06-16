import SwiftUI
import shared

struct ExerciseDetailScreen: View {
    private var exerciseDataSource: ExerciseDataSource
    private var exerciseId: Int64? = nil
    
    @StateObject var viewModel = ExerciseDetailViewModel(exerciseDataSource: nil)
    
    @Environment(\.presentationMode) var presentation
    
    init(exerciseDataSource: ExerciseDataSource, exerciseId: Int64? = nil) {
        self.exerciseDataSource = exerciseDataSource
        self.exerciseId = exerciseId
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter a name for the exercise...", text: $viewModel.exerciseName)
                .font(.title)
            TextField("Enter exercise weight...", text: $viewModel.exerciseWeight)
            Spacer()
        }.toolbar(content: {
            Button(action: {
                viewModel.saveExercise {
                    self.presentation.wrappedValue.dismiss()
                }
            }) {
                Image(systemName: "checkmark")
            }
        })
        .padding()
        .background(Color(hex: viewModel.exerciseColor))
        .onAppear {
            viewModel.setParamsAndLoadExercise(exerciseDataSource: exerciseDataSource, exerciseId: exerciseId)
        }
    }
}

struct ExerciseDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}