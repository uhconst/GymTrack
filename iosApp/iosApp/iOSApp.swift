import SwiftUI
import shared

@main
struct iOSApp: App {
    
    private let databaseModule = DatabaseModule()
    
	var body: some Scene {
		WindowGroup {
            NavigationView {
                ExerciseListScreen(exerciseDataSource: databaseModule.exerciseDataSource)
            }.accentColor(.black)
		}
	}
}
