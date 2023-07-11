import SwiftUI
import shared

struct ExerciseItem: View {
    var exercise: Exercise
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(exercise.name)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, 3)
            
            Text(exercise.weight)
                .fontWeight(.light)
                .padding(.bottom, 3)
            
            HStack {
                Spacer()
                Text(DateTimeUtil().formatExerciseDate(dateTime: exercise.created))
                    .font(.footnote)
                    .fontWeight(.light)
            }
        }
        .padding()
        .background(Color(hex: exercise.muscle?.colorHex ?? 0xFF2341))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

struct ExerciseItem_Previews: PreviewProvider {
    static var previews: some View {
        ExerciseItem(
            exercise: Exercise(id: nil, name: "My exercise", weight: "Exercise weight", created: DateTimeUtil().now(), modified: DateTimeUtil().now(), muscle: nil),
            onDeleteClick: {}
        )
    }
}
