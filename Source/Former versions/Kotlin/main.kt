// Set this to wherever you desire a run's record to be kept at:
const val LOG_DIRECTORY = "./logFilePath"

fun main() 
{
    var gui: GUI? = null

    gui = GUI(
    {
        gui?.let { ResumeScanner(it) }
    })    .apply { isVisible = true  } 
    
}