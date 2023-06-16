import SwiftUI
import shared

struct ExerciseItem: View {
    var exercise: Exercise
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(exercise.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, 3)
            
            Text(exercise.content)
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
        .background(Color(hex: exercise.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

struct ExerciseItem_Previews: PreviewProvider {
    static var previews: some View {
        ExerciseItem(
            exercise: Exercise(id: nil, title: "My exercise", content: "Exercise weight", colorHex: 0xFF2341, created: DateTimeUtil().now()),
            onDeleteClick: {}
        )
    }
}
