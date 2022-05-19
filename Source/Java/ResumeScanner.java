import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import org.apache.pdfbox.*;
import org.apache.poi.hwpf.*;
import org.apache.log4j.BasicConfigurator;

public class ResumeScanner 
{	
    public static TextArea OutputTextArea;
    public static JLabel LabelOne, LabelTwo, LabelThree;
    public static JTextField InputTextFieldOne, InputTextFieldTwo;
    public static void main(String[] args) throws IOException
    {   
	BasicConfigurator.configure();
        // Constructing a new window (600 x 800) with a dull background:
        JFrame jf = new JFrame("Keyword-based Resume Scanner (V1)"); 
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); jf.setSize(600, 800);
	JPanel jpanel = new JPanel(); 
	jpanel.setBackground(Color.getHSBColor(25, 130, 95)); jpanel.setLayout(null);
  
        // Adding labels for the GUI:
	LabelOne = new JLabel("Resume Scanner"); LabelOne.setFont(new Font("Comic Sans", Font.BOLD, 20));   LabelOne.setForeground(Color.white);
	LabelTwo = new JLabel("Keywords: ");     LabelTwo.setFont(new Font("Comic Sans", Font.BOLD, 18));   LabelTwo.setBounds(10, 60, 400, 20);
	LabelThree = new JLabel("Directory: ");  LabelThree.setFont(new Font("Comic Sans", Font.BOLD, 18)); LabelThree.setBounds(10, 90, 400, 20);

        // Adding and setting coordinates for test fields (input) and one text area (output):
	InputTextFieldOne = new JTextField(); InputTextFieldOne.setBounds(120, 60, 100, 20); 
	InputTextFieldTwo = new JTextField(); InputTextFieldTwo.setBounds(120, 90, 100, 20);
	OutputTextArea = new TextArea();      OutputTextArea.setBounds(10, 160, 550, 600);

	// Adding a button to display results on click:
	JButton ButtonToDisplay = new JButton("Display Results!"); ButtonToDisplay.setBounds(10, 120, 210, 30); ButtonToDisplay.setFont(new Font("Comic Sans", Font.BOLD, 15));
	
	// Adding all the components onto the Jframe window:
	jpanel.add(LabelOne);          jpanel.add(LabelTwo);            jpanel.add(LabelThree);
	jpanel.add(InputTextFieldOne); jpanel.add(InputTextFieldTwo);   jpanel.add(OutputTextArea);
	jpanel.add(ButtonToDisplay);   jf.getContentPane().add(jpanel); jf.setVisible(true); 

	ButtonToDisplay.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {	
		try
	        {   // Taking whitespace-separated keywords as input (from the first text field; second one's for the directory) and thereafter dividing the input string (splits based on blank space) into an array of keywords:
	            String keywordString = InputTextFieldOne.getText(), directory = InputTextFieldTwo.getText();
		    String[] keywords = keywordString.split(" "); 
		    File dir = new File(directory); 
		    File[] fileslist = dir.listFiles(new FileFilterer());			
		    int keywordCount = 0, fileCount = 0, highestScore = 0;
					
		    System.out.print("Please enter the filepath of the directory (inside this console) where you would want to store the logs.");
		    Scanner sin = new Scanner(System.in); String logDirectory = sin.nextLine(); FileWriter foutput = new FileWriter(logDirectory); sin.close();
		    foutput.write("Total number of keywords: " + keywordCount); OutputTextArea.append("Total number of keywords: " + keywordCount);
					
		    for(File f : fileslist)
		    {   // .doc(<-x) resumes
			if(f.getName().endsWith(".doc")) 
			{   
			    fileCount++;
			    WordExtractor extractor = null;
			    try
			    {   
				int count = 0;    
				foutput.write("\nOpening the resume '" + f.getName() + "' (word format)\n"); OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (word format)\n");        
				FileInputStream fis = new FileInputStream(f.getAbsolutePath()); 
				HWPFDocument document = new HWPFDocument(fis); 
				extractor = new WordExtractor(document);
				String[] fileData = extractor.getParagraphText(); // Extract all text within the doc file.
				String lines[] = fileData;
				for(String line : lines) 
				{      
				    String check[] = {}; // String array to store all the words from the file.
				    check = line.split(" ");  
				    for(String word : check) 
				     {   
					 for(String k : keywords)
					 {	 
					     if(word.equals(k)) // Keyword in search is a match against the current iteration's string/word.
					     {
					         foutput.write("Keyword '" + k + "' was found!\n"); OutputTextArea.append("Keyword '" + k + "' was found!\n");
					         count++;
					     }
					 }   // End of keyword comparisons for a word
				     }      // End of the word checking process for a line
				}          // End of all the line checks (i.e., the entire process)  
				foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n"); OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
				highestScore = (count > highestScore) ? count : highestScore;
				if(count == 0)
				{	
				    foutput.write("No keywords were found in this resume!\n"); OutputTextArea.append("No keywords were found in this resume!\n");
				}
			    }   // End of internal try block (61)
			    catch(Exception e) { e.printStackTrace(); }  
			}	 

			// .pdf resumes
			else if(f.getName().endsWith(".pdf"))
			{   
			    fileCount++;
			    try(PDDocument document = PDDocument.load(f)) 
			    {   
				foutput.write("\nOpening the resume '" + f.getName() + "' (pdf format)\n"); OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (pdf format)\n");
				int count = 0;
				document.getClass();

				if(!document.isEncrypted()) 
				{
				    PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
				    stripper.setSortByPosition(true); 
			            PDFTextStripper tStripper = new PDFTextStripper();
				    String pdfFileInText = tStripper.getText(document);
				    String lines[] = pdfFileInText.split("\\r?\\n"); // Splitting lines in the pdf.
				    for(String line : lines) 
				    {      
				        String check[] = {}; check = line.split(" ");
				        for(String word : check) 
				        {
				            for(String k : keywords)
				            {	 
				         	if(word.equals(k))
				         	{
							foutput.write("Keyword '" + k + "' was found!\n"); OutputTextArea.append("Keyword '" + k + "' was found!\n");
				         	        count++;
				         	}
				            }
				        }
				    }       	
				}
				foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n"); OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
				highestScore = (count > highestScore) ? count : highestScore;
				if(count == 0)
				{ 
				     foutput.write("No keywords were found in this resume!\n"); OutputTextArea.append("No keywords were found in this resume!\n");
				}
			    }
			}
			else System.out.print("No .doc/.pdf files were found in the specified directory!");	
		    }  // End of all iterations of the for loop (done with all the concerned files)
		    foutput.write("\nTotal number of files scanned: " + fileCount); OutputTextArea.append("\nTotal number of files scanned: " + fileCount);
		    foutput.write("\nHighest number of keywords found in a resume: " + highestScore); OutputTextArea.append("\nHighest number of keywords found in a resume: " + highestScore);
		    foutput.close();
	        }
		catch(Exception ex) { ex.printStackTrace(); } 
	    }
	}); // End of Action Listener (38)	
    }
}