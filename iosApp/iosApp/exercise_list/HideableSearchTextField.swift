import SwiftUI

struct HideableSearchTextField: View {
    
    var onSearchToggled: () -> Void
    var destinationOneProvider: () -> Void
    var destinationTwoProvider: () -> Void
    var isSearchActive: Bool
    @Binding var searchText: String

    @State private var addExerciseClicked: Bool = false
    @State private var addMuscleClicked: Bool = false
    
    var body: some View {
        HStack {
            TextField("Search...", text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                Spacer()
            }
            Button(action: onSearchToggled) {
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
                    .foregroundColor(.black)
            }
            Menu {
                Button("Add Exercise", action: { destinationOneProvider() })
                Button("Add Muscle", action: { destinationTwoProvider() })
            } label: {
                Image(systemName: "plus")
            }
        }
    }
}

struct HideableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchTextField(
            onSearchToggled: {},
            destinationOneProvider: {},
            destinationTwoProvider: { },
            isSearchActive: true,
            searchText: .constant("YouTube")
        )
    }
}
