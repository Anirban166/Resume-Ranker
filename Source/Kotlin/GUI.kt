import java.awt.Font
import java.awt.Color
import java.awt.TextArea
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JButton
import javax.swing.JTextField
import org.apache.log4j.BasicConfigurator

class GUI(val onClickCallback: () -> Unit) : JFrame("Keyword-based Resume Scanner (V2)") 
{
    // Avoiding initialization of the output text box early on, waiting for the dependency injection of the inputs (keywords, directory):
    private lateinit var resultTA: TextArea

    // Initializing inputs to empty strings for the start:
    var keyword   = ""
    var directory = ""
    
    // Secondary constructor:
    init 
    {
        createUI()
    }

    // Since I want the font to be accessible outside, without creation of an instance of this class:
    companion object 
    {
        private fun defaultFont(size: Int): Font = Font("Comic Sans", Font.BOLD, size)
    }

    // Function to create the UI components: (box dimensions taken from my Java version)
    private fun createUI() 
    {
        BasicConfigurator.configure() // Log4j shit
        defaultCloseOperation = EXIT_ON_CLOSE

        // Setting the window size: (width x height)
        setSize(600, 800)

        val jPanel = JPanel().also( 
        {
            it.background = Color.getHSBColor(25F, 130F, 95F)
            it.layout = null
        })

        // Text field for the keyword-string input:
        val keywordTF = JTextField().also( 
        {
            it.setBounds(120, 60, 100, 20)
            jPanel.add(it)
        })

        JLabel("Keywords: ").also( 
        {
            it.setBounds(10, 60, 400, 20)
            it.font = defaultFont(18)
            jPanel.add(it)
        })

        // Text field for the directory input:
        val directoryTF = JTextField().also( 
        {
            it.setBounds(120, 90, 100, 20)
            jPanel.add(it)
        })

        JLabel("Directory: ").also( 
        {
            it.setBounds(10, 90, 400, 20)
            it.font = defaultFont(18)
            jPanel.add(it)
        })

        // Text box for displaying the output:
        resultTA = TextArea().also( 
        {
            it.setBounds(10, 160, 550, 600)
            jPanel.add(it)
        })

        // Button to launch a homing missile:
        JButton("Display Results!").also( 
        {
            it.setBounds(10, 120, 210, 30)
            it.font = defaultFont(18)

            it.addActionListener( 
            {
                keyword = keywordTF.text
                directory = directoryTF.text
                onClickCallback()
            })

            jPanel.add(it)
        })

        contentPane.add(jPanel)
    }

    // Function to append strings to the UI: (calling this as in when required to update stuff to the UI only, and not to the log file)
    fun updateUI(input: String) 
    {
        resultTA.append("$input\n")
    }  

}