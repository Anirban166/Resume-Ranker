const val FILE_OUTPUT = "./filePath"

fun main() 
{
    var gui: GUI? = null
    gui = GUI 
    {
        gui?.let { KeywordScanner(it) }
    }.apply { isVisible = true }
}