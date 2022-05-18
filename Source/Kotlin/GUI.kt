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
    private lateinit var resultTA: TextArea
    var keyword   = ""
    var directory = ""

    init 
    {
        createUI()
    }

    companion object 
    {
        private fun defaultFont(size: Int): Font = Font("Comic Sans", Font.BOLD, size)
    }

    private fun createUI() 
    {
        BasicConfigurator.configure()
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(600, 800)

        val jPanel = JPanel().also 
        {
            it.background = Color.getHSBColor(25F, 130F, 95F)
            it.layout = null
        }

        val directoryTF = JTextField().also 
        {
            it.setBounds(120, 90, 100, 20)
            jPanel.add(it)
        }

        JLabel("Directory: ").also 
        {
            it.setBounds(10, 90, 400, 20)
            it.font = defaultFont(18)
            jPanel.add(it)
        }

        val keywordTF = JTextField().also 
        {
            it.setBounds(120, 60, 100, 20)
            jPanel.add(it)
        }

        JLabel("Keywords: ").also 
        {
            it.setBounds(10, 60, 400, 20)
            it.font = defaultFont(18)
            jPanel.add(it)
        }

        resultTA = TextArea().also 
        {
            it.setBounds(10, 160, 550, 600)
            jPanel.add(it)
        }

        JButton("Display Results!").also 
        {
            it.setBounds(10, 120, 210, 30)
            it.font = defaultFont(18)
            it.addActionListener 
            {
                keyword = keywordTF.text
                directory = directoryTF.text
                onClickCallback()
            }
            jPanel.add(it)
        }
        contentPane.add(jPanel)
    }

    fun updateUI(input: String) 
    {
        resultTA.append("$input\n")
    }    
}