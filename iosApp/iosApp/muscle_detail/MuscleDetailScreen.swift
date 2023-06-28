import SwiftUI
import shared

struct MuscleDetailScreen: View {
    private var muscleDataSource: MuscleDataSource
    private var muscleId: Int64? = nil
    
    @StateObject var viewModel = MuscleDetailViewModel(muscleDataSource: nil)
    
    @Environment(\.presentationMode) var presentation
    
    init(muscleDataSource: MuscleDataSource, muscleId: Int64? = nil) {
        self.muscleDataSource = muscleDataSource
        self.muscleId = muscleId
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter the muscle name...", text: $viewModel.muscleName)
                .font(.title)
            TextField("Enter the muscle description...", text: $viewModel.muscleDescription)
            Spacer()
        }.toolbar(content: {
            Button(action: {
                viewModel.saveMuscle {
                    self.presentation.wrappedValue.dismiss()
                }
            }) {
                Image(systemName: "checkmark")
            }
        })
        .padding()
        .onAppear {
            viewModel.setParamsAndLoadMuscle(muscleDataSource: muscleDataSource, muscleId: muscleId)
        }
    }
}

struct MuscleDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
