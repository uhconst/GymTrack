import SwiftUI
import shared

struct ExerciseDetailScreen: View {
    private var exerciseDataSource: ExerciseDataSource
    private var muscleDataSource: MuscleDataSource
    private var exerciseId: Int64? = nil
    
    @StateObject var viewModel = ExerciseDetailViewModel(exerciseDataSource: nil)
    
    @Environment(\.presentationMode) var presentation
    
    init(exerciseDataSource: ExerciseDataSource, muscleDataSource: MuscleDataSource, exerciseId: Int64? = nil) {
        self.exerciseDataSource = exerciseDataSource
        self.muscleDataSource = muscleDataSource
        self.exerciseId = exerciseId
    }
    
    var enteredNumberFormatted: Double {
        return (Double(viewModel.exerciseWeight) ?? 0) / 100
    }

    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter a name for the exercise...", text: $viewModel.exerciseName)
                .font(.title)
            Picker("Select a muscle", selection: $viewModel.exerciseMuscleId) {
                ForEach(viewModel.musclesList, id: \.id) {
                    Text($0.name)
                }
            }.pickerStyle(.menu)
            ZStack(alignment: .leading) {
                        TextField(
                            "",
                            text: $viewModel.exerciseWeight
                        )
                            .keyboardType(.numberPad).foregroundColor(.clear)
                            .textFieldStyle(PlainTextFieldStyle())
                            .disableAutocorrection(true)
                            .accentColor(.clear)
                        Text("\(enteredNumberFormatted, specifier: "%.2f")")
                    }
            
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
            viewModel.setParamsAndLoadExercise(
                exerciseDataSource: exerciseDataSource,
                muscleDataSource: muscleDataSource,
                exerciseId: exerciseId
            )
        }
    }
}

struct ExerciseDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
