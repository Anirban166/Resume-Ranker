package utils
import java.util.*
import java.io.File
import java.io.FileFilter
import java.io.FileWriter
import java.io.FileInputStream
import java.util.regex.Pattern
import org.apache.poi.hwpf.HWPFDocument
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.poi.hwpf.extractor.WordExtractor
 
class ResumeScanner(keyword: String, directory: String, outputDirectory: String, val updateUI: (String) -> Unit)
{
    // Curating the lists of keywords (extracted from the input string) and desired files:
    private var keywordList = keyword.split(" ")
    private val filesList   = File(directory).listFiles(CustomFileFilter())

    // Storing the keyword and file counts for future use:
    private var totalKeywords = keywordList.count()
    private var fileCount     = filesList?.count()

    // Variables for the output file (will write to this) and scores corresponding to each resume: (filename, score)
    private val fileOutput  = FileWriter(LOG_DIRECTORY)    
    private var scoreList   = listOf<Pair<String, Int>>()

    init 
    {
        if(fileCount == 0 || filesList.isNullOrEmpty()) 
        {
            println("No .doc/.pdf files found in the specified directory!")
        } 
        else 
        {
            writeToFileAndUI("Total number of keywords: $totalKeywords")            
            scoreList = keywordFinder(filesList)
            val mostKeywordsFound = scoreList.maxOf { it.second }
            val bestResume = scoreList.find( 
            {
                it.second == mostKeywordsFound
            })
            writeToFileAndUI("Post filtering, the highest ranked resume among the lot is: ${bestResume?.first}")
            fileOutput.close()
        }
    }

    // Function to find keywords in a file:
    private fun keywordFinder(inputFileList: Array<out File>?): MutableList<Pair<String, Int>> 
    {
        val fileScoreList: MutableList<Pair<String, Int>> = mutableListOf()
        inputFileList?.forEach( 
        {   file ->
            when 
            {
                // .doc(<-x) resumes
                file.name.endsWith(".doc") -> 
                {
                    writeToFileAndUI("Opening the resume '${file.name}' (word format)")
                    val fileInputStream = FileInputStream(file.absolutePath)
                    val extractor = WordExtractor(HWPFDocument(fileInputStream))
                    val dataList = extractor.paragraphText.toList()
                    val score = calculateScore(dataList)
                    fileScoreList.add(Pair(file.name, score))
                    writeToFileAndUI("Total number of keywords found in the resume '${file.name}': $score\n")
                }
                // .pdf resumes
                file.name.endsWith(".pdf") -> 
                {
                    val pdfDoc: PDDocument = PDDocument.load(file)
                    writeToFileAndUI("Opening the resume '${file.name}' (pdf format)")
                    val rawData: String = PDFTextStripper().getText(pdfDoc)
                    val dataList = Pattern.compile("\\s+").split(rawData.trim()).toList()
                    val score = calculateScore(dataList)
                    fileScoreList.add(Pair(file.name, score))
                    pdfDoc.close()
                    writeToFileAndUI("Total number of keywords found in the resume '${file.name}': $score\n")
                }
            }
        })
        return fileScoreList
    }

    // Function to compute the scores for each resume:
    private fun calculateScore(inputList: List<String>): Int 
    {
        val score = inputList.count( 
        { 
            word ->
            if (keywordList.contains(word))
                writeToFileAndUI("Keyword '$word' was found!")
            keywordList.contains(word)
        })

        if(score == 0)
            writeToFileAndUI("No keywords were found in this resume!")

        return score
    }

    // Function to write to both an output file and the GUI: (takes the string to be written as input)
    private fun writeToFileAndUI(str : String) 
    {
        fileOutput.write(str)
        updateUI(str)
    }

    // Function to filter out files in a given directory that have 'doc' and 'pdf' extensions:
    inner class CustomFileFilter: FileFilter 
    {
        override fun accept(pathname: File?): Boolean 
        {
            listOf("doc", "pdf").forEach( 
            {
                if(pathname != null) 
                {
                    if(pathname.name.lowercase(Locale.getDefault()).endsWith(it))
                        return true
                }
            })
            return false
        }
    }
}   