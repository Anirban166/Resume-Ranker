import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// .doc file handling dependencies:
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

// .pdf file handling dependencies:
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

// Including log4j configurator to remove corresponding log4j warnings:
import org.apache.log4j.BasicConfigurator;

public class ResumeScanner 
{	
	public static TextArea OutputTextArea;
    public static JLabel LabelOne, LabelTwo, LabelThree;
	public static JTextField InputTextFieldOne, InputTextFieldTwo; // Keywords, Directory.

	public static void main(String[] args) throws IOException
    {   
		  BasicConfigurator.configure();
          // Constructing a new window (600 x 800) with a dull background:
		  JFrame jf = new JFrame("Keyword-based Resume Scanner (V1)");
		  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      jf.setSize(600, 800);
	      JPanel jpanel = new JPanel();
	      jpanel.setBackground(Color.getHSBColor(25, 130, 95));
	      jpanel.setLayout(null);

          // Adding labels for the GUI:
	      LabelOne = new JLabel("Keyword-based Resume Scanner");
	      LabelOne.setFont(new Font("Comic Sans", Font.BOLD, 20));
	      LabelOne.setForeground(Color.white);
	      LabelTwo = new JLabel("Keywords: ");
	      LabelTwo.setFont(new Font("Comic Sans", Font.BOLD, 18));
	      LabelTwo.setBounds(10, 60, 400, 20);
	      LabelThree = new JLabel("Directory: ");
	      LabelThree.setBounds(10, 90, 400, 20);
	      LabelThree.setFont(new Font("Comic Sans", Font.BOLD, 18));

          // Adding and set coordinates for test fields (input) and one text area (output):
	      InputTextFieldOne = new JTextField();
	      InputTextFieldOne.setBounds(120, 60, 100, 20); 
	      InputTextFieldTwo = new JTextField();
	      InputTextFieldTwo.setBounds(120, 90, 100, 20);
	      OutputTextArea = new TextArea();
	      OutputTextArea.setBounds(10, 160, 550, 600);

	      // Adding button to display results on click:
	      JButton ButtonToDisplay = new JButton("Display Results!");		  
	      ButtonToDisplay.setBounds(10, 120, 210, 30);
	      ButtonToDisplay.setFont(new Font("Comic Sans", Font.BOLD, 15));
          
		  // Adding all the highestScoreonents onto the Jframe window:
	      jpanel.add(LabelOne); 
	      jpanel.add(LabelTwo);
	      jpanel.add(LabelThree);
	      jpanel.add(InputTextFieldOne);
	      jpanel.add(InputTextFieldTwo);
	      jpanel.add(OutputTextArea);
	      jpanel.add(ButtonToDisplay);
	      jf.getContentPane().add(jpanel);
	      jf.setVisible(true); 

	      ButtonToDisplay.addActionListener(new ActionListener()
	      {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {	
				// Implementing this solely for the button's actions.
			   	try
	        	{	
				  	// Taking whitespace-separated keywords as input from my first text field and storing them as a string:
	        	  	String keywordString = InputTextFieldOne.getText();
				  	// Dividing (splits based on a blank space) the input string of keywords into a string array composed of keywords:
				  	String[] keywords = keywordString.split(" ");
			      	int keywordCount = 0, fileCount = 0, highestScore = 0;
				  
				  	// Sanity check for the entered keywords: (printed to console)
				  	for(String d: keywords)
				  	{   
						System.out.print(d);
						keywordCount++;
				  	}

					String directory = InputTextFieldTwo.getText();		
					File dir = new File(directory);
					File[] fileslist = dir.listFiles(new FileFilterer());
					FileWriter foutput = new FileWriter("./ResumeRanker-RunInformation.doc"); // Emplacing log in current directory.
					foutput.write("Total number of keywords: " + keywordCount);
					OutputTextArea.append("Total number of keywords: " + keywordCount);
					
					for(File f : fileslist)
				    {
				   		// .doc(<-x) resumes
				      	if(f.getName().endsWith(".doc")) 
				      	{   
							fileCount++;
				          	WordExtractor extractor = null;
				          	try
				          	{   
								System.out.println("Opening the resume '" + f.getName() + "' (word format)\n");
				              	foutput.write("\nOpening the resume '" + f.getName() + "' (word format)\n");
				              	OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (word format)\n");
				              	int count = 0;
				          
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
					         	        	if(word.equals(k)) // Keyword in search is a match against the current iteration's string.
					         	            {
					         	        	    System.out.print("Keyword '" + k + "' was found!\n");
					         	        	    foutput.write("Keyword '" + k + "' was found!\n");
					         	        	    OutputTextArea.append("Keyword '" + k + "' was found!\n");
					         	                count++;
					         	            }
					         	        } // End of word highestScorearison
					         	    }    // End of word checking
					         	}       // End of line checks   
				                 
								System.out.println("Total keywords found (resume strength): " + count + "\n");
								foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
								OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
								highestScore = (count > highestScore) ? count : highestScore;
				              	if(!count)
				              	{	
									foutput.write("No keywords were found in this resume!\n");
				              		OutputTextArea.append("No keywords were found in this resume!\n");
								}
				                // System.out.println(keyword.length);
				        	}  // End of external try block
				          	catch(Exception e)
				          	{
				            	e.printStackTrace();
				          	}  
				      	}	 

				     	// .pdf resumes
				      	else if(f.getName().endsWith(".pdf"))
				      	{   
							fileCount++;
				    	  	try(PDDocument document = PDDocument.load(f)) 
				          	{   
								System.out.println("Opening the resume '" + f.getName() + "' (pdf format)\n");
				              	foutput.write("\nOpening the resume '" + f.getName() + "' (pdf format)\n");
				              	OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (pdf format)\n");
				              	int count = 0;
				          	
				              	document.getClass();

				              	if(!document.isEncrypted()) 
				              	{
				                	PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
				                	stripper.setSortByPosition(true);
				                	PDFTextStripper tStripper = new PDFTextStripper();
				                	String pdfFileInText = tStripper.getText(document);
				  					// Splitting lines in the pdf:
				                	String lines[] = pdfFileInText.split("\\r?\\n");
				                	for(String line : lines) 
				                	{      
				         		    	String check[] = {};
				         	        	check = line.split(" ");
				         	        	for(String word : check) 
				         	        	{
				         	                for(String k : keywords)
				         	                {	 
				         	        	    	if(word.equals(k))
				         	                    {
													System.out.print("Keyword '" + k + "' was found!\n");
													foutput.write("Keyword '" + k + "' was found!\n");
													OutputTextArea.append("Keyword '" + k + "' was found!\n");
				         	                      	count++;
				         	                    }
				         	                }
				         	          	}
				         	        }       	

				                }

								System.out.println("Total keywords found (resume strength): " + count + "\n");
								foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
								OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
				              	highestScore = (count > highestScore) ? count : highestScore;
				              	if(!count)
				              	{ 
									foutput.write("No keywords were found in this resume!\n");
				              		OutputTextArea.append("No keywords were found in this resume!\n");
				              	}
							}
				        }
				        else System.out.print("No .doc/.pdf files were found in the specified directory!");	
				    }	
					foutput.write("\nTotal number of files scanned: " + fileCount);
					OutputTextArea.append("\nTotal number of files scanned: " + fileCount);
					foutput.write("\nHighest number of keywords found in a resume: " + highestScore);
					OutputTextArea.append("\nHighest number of keywords found in a resume: " + highestScore);
					/* int big = 0;
					for(int x = 0; x < fileCount ; x++)
					{
						big = (highestScore[x] > big) ? highestScore[x] : big;	
					}
					foutput.write("\n Resume with the highest strength or maximum number of keywords is : " + Resume); */
					foutput.close();
	            }
	          	catch(Exception e) {}
	        }
		}); // End of Action Listener (ButtonToDisplay)
		
		// Taking the actual input (using a Scanner object) and repeating the process:
		System.out.print("Please enter the keywords you want to search (seperated by a space each) and the directory where you would want to scan the resumes (on the next line, or after pressing Enter).");
		Scanner sin = new Scanner(System.in);
		String keywordString = sin.nextLine();
		String[] keywords = keywordString.split(" ");
        
		String directory = sin.nextLine();
		File dir = new File(directory);
		File[] fileslist = dir.listFiles(new FileFilterer());
		FileWriter foutput = new FileWriter("./ResumeRanker-RunInformation.doc");
		foutput.write("Total number of keywords: " + keywordCount);
		OutputTextArea.append("Total number of keywords: " + keywordCount);	
		sin.close(); // Closing the Scanner after both inputs have been taken (using only of them to avoid stream issues) and processed.
		
		for(File f : fileslist)
		{
			  // Docs
			  if(f.getName().endsWith(".doc")) 
			  {   
				  fileCount++;
				  WordExtractor extractor = null;
				  try
				  {   
					System.out.println("Opening the resume '" + f.getName() + "' (word format)\n");
					foutput.write("\nOpening the resume '" + f.getName() + "' (word format)\n");
					OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (word format)\n");
					int count = 0;
			  
					FileInputStream fis = new FileInputStream(f.getAbsolutePath());
					HWPFDocument document = new HWPFDocument(fis); 
					extractor = new WordExtractor(document);
					String[] fileData = extractor.getParagraphText();
	 
					String lines[] = fileData;
					for(String line : lines) 
					{      
						 String check[] = {};
						 check = line.split(" ");  
						 for(String word : check) 
						 {
							 for(String k : keywords)
							 {	 
								 if(word.equals(k))
								 {
									 System.out.print("Keyword '" + k + "' was found!\n");
									 foutput.write("Keyword '" + k + "' was found!\n");
									 OutputTextArea.append("Keyword '" + k + "' was found!\n");
									 count++;
								 }
							 }
						 }
					 }  
					System.out.println("Total keywords found (resume strength): " + count + "\n");
					foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
					OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
					highestScore = (count > highestScore) ? count : highestScore;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}  
			  } 

			  // Pdfs
			  else if(f.getName().endsWith(".pdf"))
			  {   
				  fileCount++;
				  try(PDDocument document = PDDocument.load(f)) 
				  {   
					  System.out.println("Opening the resume '" + f.getName() + "' (pdf format)\n");
					  foutput.write("\nOpening the resume '" + f.getName() + "' (pdf format)\n");
					  OutputTextArea.append("\nOpening the resume '" + f.getName() + "' (pdf format)\n");
					  int count = 0;
					  document.getClass();

					  if(!document.isEncrypted()) 
					  {
						PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
						stripper.setSortByPosition(true);
						PDFTextStripper tStripper = new PDFTextStripper();
						String pdfFileInText = tStripper.getText(document);
						String lines[] = pdfFileInText.split("\\r?\\n");
						for(String line : lines) 
						{      
							 String check[] = {};
							 check = line.split(" ");
							 for(String word : check) 
							 {
								 for(String k : keywords)
								 {	 
									 if(word.equals(k))
									 {
										System.out.print("Keyword '" + k + "' was found!\n");
										foutput.write("Keyword '" + k + "' was found!\n");
										OutputTextArea.append("Keyword '" + k + "' was found!\n");
										count++;
									 }
								 }
							 }
						 }       	

					}

					System.out.println("Total keywords found (resume strength): " + count + "\n");
					foutput.write("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
					OutputTextArea.append("Total number of keywords found in '" + f.getName() + "': " + count + "\n");
					highestScore = (count > highestScore) ? count : highestScore;
				  }
			  }
			  else System.out.print("No .doc/.pdf files were found in the specified directory!");	
		}	
		foutput.write("\nTotal number of files scanned: " + fileCount);
		OutputTextArea.append("\nTotal number of files scanned: " + fileCount);
		foutput.write("\nHighest number of keywords found in a resume: " + highestScore);
		OutputTextArea.append("\nHighest number of keywords found in a resume: " + highestScore);		
		foutput.close();
		
		// Final iteration just to find the name of the resume with the highest score (since only the scores were collected, comparing against all the scores in this run) - gonna remove this soon (redundant after all)
		String bestResumeName = null;
		for(File t : fileslist)
	    {
	      // Docs
	      if(t.getName().endsWith(".doc")) 
	      {   
			  fileCount++;
	          WordExtractor extractor = null;
	          try
	          {   
	              int count = 0;
	              FileInputStream fis = new FileInputStream(t.getAbsolutePath());
	              HWPFDocument document = new HWPFDocument(fis); 
	              extractor = new WordExtractor(document);
	              String[] fileData = extractor.getParagraphText();
	              String lines[] = fileData;
		          for(String line : lines) 
		          {      
		        	String check[] = {}; 
		         	check = line.split(" ");  
		         	for(String word : check) 
		         	{
		         	    for(String k : keywords)
		         	    {	 
		         	        if(word.equals(k)) count++;     
		         	    }
		         	}
		         	if(highestScore == count) bestResumeName = t.getName(); // Obtaining and storing the name of the highest scoring resume.
		          }     
		          OutputTextArea.append("Highest ranked resume (based on having the maximum number of keywords) filtered among all candidates is the one called '" + bestResumeName + "'.");
			  	  foutput.write("Highest ranked resume (based on having the maximum number of keywords) filtered among all candidates is the one called '" + bestResumeName + "'.");
	          }
	          catch(Exception e)
	          {
	            e.printStackTrace();
	          }  
	      }	 
	      // Pdfs
	      else if(t.getName().endsWith(".pdf"))
	      {   
			  fileCount++;
	    	  try(PDDocument document = PDDocument.load(t)) 
	          {   
	            int count = 0;
	            document.getClass();

	            if(!document.isEncrypted()) 
	            {
	                PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
	                stripper.setSortByPosition(true);
	                PDFTextStripper tStripper = new PDFTextStripper();
	                String pdfFileInText = tStripper.getText(document);
	                String lines[] = pdfFileInText.split("\\r?\\n");
	                for(String line : lines) 
	                {      
	         		    String check[] = {};
	         	        check = line.split(" ");
	         	        for(String word : check) 
	         	        {
	         	            for(String k : keywords)
	         	            {	 
	         	        		if(word.equals(k)) count++;
	         	            }
	         	        }
	         	        if(highestScore == count) bestResumeName = t.getName();
	         	    }       	
	            }
				OutputTextArea.append("Highest ranked resume (based on having the maximum number of keywords) filtered among all candidates is the one called '" + bestResumeName + "'.");
				foutput.write("Highest ranked resume (based on having the maximum number of keywords) filtered among all candidates is the one called '" + bestResumeName + "'.");
	          }
	      }	
	    }	
		foutput.close();	
    }	
}