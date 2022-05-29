package theme
import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme

private val DarkColorPalette = darkColors(primary = Purple200, primaryVariant = Purple700, secondary = Teal200, background = Color.White,
        surface = Color.Black, onPrimary = Color.White, onSecondary = Color.White, onBackground = Color.White, onSurface = Color.LightGray)

private val LightColorPalette = lightColors(primary = Purple500, primaryVariant = Purple700, secondary = Teal200, background = Color.White,
        surface = Color.White, onPrimary = Color.White, onSecondary = Color.White, onBackground = Color.Black, onSurface = Color.Black)

@Composable
fun CustomMaterialTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit)
{
    val colors: Colors = when
    {
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }
    MaterialTheme(colors = colors, content = content)
}