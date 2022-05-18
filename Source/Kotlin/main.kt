const val FILE_OUTPUT = "./filePath"

fun main() 
{
    var gui: GUI? = null
    gui = GUI 
    {
        gui?.let { ResumeScanner(it) }
    }.apply { isVisible = true }
}