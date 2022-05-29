package ui
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

fun fileChooser(): File?
{
    val jfc = JFileChooser(FileSystemView.getFileSystemView().defaultDirectory)
    jfc.dialogTitle = "Choose a directory"
    jfc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    jfc.approveButtonText = "Select directory"
    val returnValue = jfc.showOpenDialog(null)
    if(returnValue == JFileChooser.APPROVE_OPTION)
    {
        if(jfc.selectedFile.isDirectory)
            return jfc.selectedFile
        else 
            return null
    }
    return null
}