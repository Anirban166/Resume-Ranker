package repos
import java.io.File
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.StateFlow
import javax.swing.filechooser.FileSystemView
import kotlinx.coroutines.flow.MutableStateFlow

class Repository
{
    private val _keywords = MutableStateFlow("")
    val keywords: StateFlow<String> = _keywords

    private val _directory = MutableStateFlow("")
    val directory: StateFlow<String> = _directory

    private val _outputDirectory = MutableStateFlow("")
    val outputDirectory: StateFlow<String> = _outputDirectory

    private val _textArea: MutableStateFlow<String> = MutableStateFlow("")
    val textArea: MutableStateFlow<String> = _textArea

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun updateKeywords(keywords: String) 
    {
        _keywords.value = keywords
    }

    fun updateDirectory(file: File?) 
    {
        _directory.value = file?.absolutePath ?: ""
    }

    fun updateOutputDirectory(file: File?) 
    {
        val strValue: String? = file?.absolutePath
        if(!strValue.isNullOrBlank())
            _outputDirectory.value = "${file.absolutePath}/output.doc"
        else
            _outputDirectory.value = ""
    }

    fun updateTextArea(text: String) 
    {
        _textArea.value = "${_textArea.value}$text\n"
    }

    fun changeTheme(bool: Boolean)
    {
        _isDarkTheme.value = bool
    }    
}