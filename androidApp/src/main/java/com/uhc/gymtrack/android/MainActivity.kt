package com.uhc.gymtrack.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uhc.gymtrack.android.exercise.detail.ExerciseDetailScreen
import com.uhc.gymtrack.android.exercise.list.ExerciseListScreen
import com.uhc.gymtrack.android.muscle.MuscleDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3),
            primaryContainer = Color(0xFFFFFFFF),
            onPrimaryContainer = Color(0xFF000000)
//            surface = Color(0xFF000000)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3),
            primaryContainer = Color(0xFF000000),
            onPrimaryContainer = Color(0xFFFFFFFF)
//            surface = Color(0xFFFFFFFF)
        )
    }
    val typography = Typography(
/*        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )*/
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "exercise_list") {
                    composable(route = "exercise_list") {
                        ExerciseListScreen(navController = navController)
                    }
                    composable(
                        route = "exercise_detail/{exerciseId}",
                        arguments = listOf(
                            navArgument(name = "exerciseId") {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) { backStackEntry ->
                        val exerciseId = backStackEntry.arguments?.getLong("exerciseId") ?: -1L
                        ExerciseDetailScreen(exerciseId = exerciseId, navController = navController)
                    }
                    composable(
                        route = "muscle_detail/{muscleId}",
                        arguments = listOf(
                            navArgument(name = "muscleId") {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) { backStackEntry ->
                    // todo
                        val exerciseId = backStackEntry.arguments?.getLong("muscleId") ?: -1L
                        MuscleDetailScreen(/*muscleId = muscleId, */navController = navController)
                    }
                }
            }
        }
    }
}
