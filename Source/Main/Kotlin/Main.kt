import ui.fileMenu
import ui.MainScreen
import repos.Repository
import utils.ResumeScanner
import theme.CustomMaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.compose.ui.Alignment

fun main() = application {
    val repo = Repository()
    val windowState = rememberWindowState(position = WindowPosition(Alignment.Center), width = 650.dp, height = 1000.dp)
    Window(onCloseRequest = ::exitApplication, title = "Resume Scanner", resizable = true, state = windowState)
    {
        val textArea by repo.textArea.collectAsState()        
        val keywords by repo.keywords.collectAsState()
        val directory by repo.directory.collectAsState()
        val isDarkTheme by repo.isDarkTheme.collectAsState()        
        val outputDirectory by repo.outputDirectory.collectAsState()

        MenuBar( 
        { 
            fileMenu(selectedTheme = isDarkTheme, changeTheme = { repo.changeTheme(it) }) 
        })
        
        CustomMaterialTheme(darkTheme = isDarkTheme)
        {
            MainScreen(keywords = keywords, updateKeywords = repo::updateKeywords, directory = directory, updateDirectory = repo::updateDirectory,
            outputDirectory = outputDirectory, updateOutputDirectory = repo::updateOutputDirectory, onButtonClick =
            {
                ResumeScanner(keyword = keywords, directory = directory, outputDirectory = outputDirectory, updateUI = repo::updateTextArea)
            }, textAreaText = textArea)
        }
    }
}