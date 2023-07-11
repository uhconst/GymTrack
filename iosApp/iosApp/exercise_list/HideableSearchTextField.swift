import SwiftUI

struct HideableSearchTextField<Destination1: View, Destination2: View>: View {
    
    var onSearchToggled: () -> Void
    var destinationOneProvider: () -> Destination1
    var destinationTwoProvider: () -> Destination2
    var isSearchActive: Bool
    @Binding var searchText: String
    
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
//            Menu {
//                Button("Add Exercise", action: { destinationOneProvider() })
//                Button("Add Muscle", action: destinationTwo)
//            } label: {
//                Image(systemName: "plus")
//            }
            
            NavigationLink(destination: destinationOneProvider()) {
                Image(systemName: "plus")
                    .foregroundColor(.black)
            }
            NavigationLink(destination: destinationTwoProvider()) {
                Image(systemName: "plus")
                    .foregroundColor(.red)
            }
        }
    }
}

struct HideableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchTextField(
            onSearchToggled: {},
            destinationOneProvider: { EmptyView() },
            destinationTwoProvider: { EmptyView() },
            isSearchActive: true,
            searchText: .constant("YouTube")
        )
    }
}
