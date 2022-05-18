package ani;

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

public class KeywordScannerForResumeByAnirban 
{	
	public static TextArea OutputTextArea;
    public static JLabel LabelOne, LabelTwo, LabelThree;
	public static JTextField InputTextFieldOne, InputTextFieldTwo; // Keywords, Directory.

	public static void main(String[] args) throws IOException
    {   
		  BasicConfigurator.configure();
          // Constructing a new window (600 x 800) with a dull background:
		  JFrame jf = new JFrame("Keyword Scanner Menu");
		  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      jf.setSize(600, 800);
	      JPanel jpanel = new JPanel();
	      jpanel.setBackground(Color.getHSBColor(25, 130, 95));
	      jpanel.setLayout(null);

          // Adding labels for the GUI:
	      LabelOne = new JLabel("Resume Scanner :");
	      LabelOne.setFont(new Font("Comic Sans", Font.BOLD, 20));
	      LabelOne.setForeground(Color.white);
	      LabelTwo = new JLabel("Keywords:");
	      LabelTwo.setFont(new Font("Comic Sans", Font.BOLD, 18));
	      LabelTwo.setBounds(10, 60, 400, 20);
	      LabelThree = new JLabel("Directory :");
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
	      JButton ButtonToDisplay = new JButton("Display Results :");		  
	      ButtonToDisplay.setBounds(10, 120, 210, 30);
	      ButtonToDisplay.setFont(new Font("Comic Sans", Font.BOLD, 15));
          
		  // Adding all the components onto the Jframe window:
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
			   	try
	        	{	
				  	// Taking whitespace-separated keywords as input from my first text field and storing it as a string:
	        	  	String key = InputTextFieldOne.getText();
				  	// Dividing (splits based on whitespace) input string of keywords into a string array composed of keywords:
				  	String[] keywords = key.split(" ");
			      	int keywordCount = 0, filecount = 0, comp = 0;
				  
				  	// Sanity check (console):
				  	for(String d: keywords)
				  	{   
						System.out.print(d);
						keywordCount++;
				  	}

					String directory = InputTextFieldTwo.getText();		
					File dir = new File(directory);
					File[] fileslist = dir.listFiles(new FileFilterer());
					FileWriter foutput = new FileWriter("./ResumeRanker-RunInformation.doc"); // Emplace log in current directory.
					foutput.write("Total number of keywords: " + keywordCount);
					OutputTextArea.append("Total number of keywords: " + keywordCount);
					
					for(File f : fileslist)
				    {
				   		// .doc(x) resumes
				      	if(f.getName().endsWith(".doc")) 
				      	{   
							filecount++;
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
					         		String check[] = {}; 
					         	    check = line.split(" ");  
					         	    for(String word : check) 
					         	    {
					         	    	for(String k : keywords) // k < total no. of keywords -> check this.
					         	        {	 
					         	        	if(word.equals(k)) // Keyword in search is a match against the current iteration's string.
					         	            {
					         	        	    System.out.print("Keyword '" + k + "' is found!\n");
					         	        	    foutput.write("Keyword '" + k + "' is found!\n");
					         	        	    OutputTextArea.append("Keyword '" + k + "' is found!\n");
					         	                count++;
					         	            }
					         	        } // End of word comparison
					         	    }   // End of word checking
					         	}     // End of line checks   
				                 
				              	System.out.println("Total keywords found (resume strength): " + count + "\n");
				              	foutput.write("Total number of keywords found in " + f.getName() + " : " + count + "\n");
				              	OutputTextArea.append("Total number of keywords found in " + f.getName() + " : " + count + "\n");
								comp = (count > comp) ? count : comp;
				              	if(!count)
				              	{	
									foutput.write("No keywords found in this resume.\n");
				              		OutputTextArea.append("No keywords found in this resume.\n");
								}
				                // System.out.println(keyword.length);
				        	}  // End of external try block
				          	catch(Exception exep)
				          	{
				            	exep.printStackTrace();
				          	}  
				      	}	 
				     	//------------------------------------else for pdf---------------------------------------//
				      	else if(f.getName().endsWith(".pdf"))
				      	{   filecount++;
				    	  	try(PDDocument document = PDDocument.load(f)) 
				          	{   
								System.out.println("\nOpening Resume : "+f.getName()+"(pdf format)\n");
				              	foutput.write("\nOpening Resume : "+f.getName()+"(pdf format)\n");
				              	OutputTextArea.append("\nOpening Resume : "+f.getName()+"(pdf format)\n");
				              	int count = 0;
				          	
				              	document.getClass();

				              	if(!document.isEncrypted()) 
				              	{
				                	PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
				                	stripper.setSortByPosition(true);

				                	PDFTextStripper tStripper = new PDFTextStripper();

				                	String pdfFileInText = tStripper.getText(document);

				  				// split by whitespace
				                	String lines[] = pdfFileInText.split("\\r?\\n");
				                	for(String line : lines) 
				                	{      
				         		    	String check[] = {}; //String array to put all words from file, finally done (^ ^) 
				         	        	check = line.split(" ");  //check=words now, split line by blankspace 
				         	        	for(String word : check) 
				         	        	{
				         	                for(String k : keywords) // k < total no. of keywords -> check this.
				         	                {	 
				         	        	    	if(word.equals(k))   //Search for the given word
				         	                    {
				         	        	    	  System.out.print("Keyword '"+k+"' is found!\n");  
				         	        	    	  foutput.write("Keyword '"+k+"' is found!\n");
				         	        	    	  OutputTextArea.append("Keyword '"+k+"' is found!\n");
				         	                      count++;     //If Present increase the count by one (considering similar weightage)
				         	                     // comp[compinc++]=count;
				         	                    }
				         	                }
				         	          	}
				         	        }       	

				                } // doc encryption block end
				              	System.out.println("Total keywords found / resume strength = "+count+"\n");
				              	foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              	OutputTextArea.append("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              	comp = (count > comp) ? count : comp;
				              	if(!count)
				              	{ 
									foutput.write("No keywords found in this resume.\n");
				              		OutputTextArea.append("No keywords found in this resume.\n"); }
				              	}   // pdf try end
				      }
				      else
				      System.out.print("No doc/pdf files found in the specified directory");	
				    }	
					foutput.write("\nTotal number of files scanned : "+filecount);
					OutputTextArea.append("\nTotal number of files scanned : "+filecount);
					foutput.write("\nHighest number of keywords found in a resume : "+comp);
					OutputTextArea.append("\nHighest number of keywords found in a resume : "+comp);
					/* int big = 0;
					for(int x = 0; x < filecount ; x++)
					{
						big = (comp[x] > big) ? comp[x] : big;	
					}
					foutput.write("\n Resume with the highest strength or maximum number of keywords is : " + Resume); */
					foutput.close();
	            }
	          catch(Exception ae){}
	        }
		});
		
		// Second loop:  
		System.out.print("Enter keywords you want to search, seperated by a space each.");
		Scanner sin = new Scanner(System.in);
		String x = sin.nextLine();
		String[] keywords = x.split(" ");
		sin.close();
		
		int keywordCount, filecount = 0, comp = 0;
		for(String d: keywords)
		{   
			System.out.print(d); // Debugging on console
			keywordCount++;
		}

		//----------------------------------Define Keywords above----------------------------------//
		String directory = "./Resumes";
		File dir = new File(directory);
		File[] fileslist = dir.listFiles(new FileFilterer());
	    
		FileWriter foutput = new FileWriter("F:/Resume_Ranker.doc"); // Change to location of output text file
		
		foutput.write("Total no. of keywords : " + keywordCount);
		OutputTextArea.append("Total no. of keywords : " + keywordCount);
		
		for (File f : fileslist)
	    {
	   //------------------------------------------doc---------------------------------------------//
	      if(f.getName().endsWith(".doc")) 
	      {   
			  filecount++;
	          WordExtractor extractor = null;
	          try
	          {   System.out.println("Opening Resume : " + f.getName() + "(word format)\n");
	              foutput.write("\nOpening Resume : " + f.getName() + "(word format)\n");
	              int count=0;
	          
	              FileInputStream fis = new FileInputStream(f.getAbsolutePath());
	              HWPFDocument document = new HWPFDocument(fis); 
	              extractor = new WordExtractor(document);
	              String[] fileData = extractor.getParagraphText(); //Get all text from doc
	 
	                  	  String lines[] = fileData;
		                  for (String line : lines) 
		                  {      
		         		     String check[] = {}; 
		         	         check = line.split(" ");  
		         	          for(String word : check) 
		         	          {
		         	                 for(String k : keywords) // k < total no. of keywords -> check this.
		         	                 {	 
		         	        	        if(word.equals(k))   //Search for the given word
		         	                    {
		         	        	    	  System.out.print("Keyword '"+k+"' is found!\n");
		         	        	    	  foutput.write("Keyword '"+k+"' is found!\n");
		         	                      count++;     //If Present increase the count by one (considering similar weightage)
		         	                     // comp[compinc++]=count;
		         	                    }
		         	                 }//word comparer for end
		         	          }//word checker for end
		         	         }//line checker for end    
	                
	              OutputTextArea.append("Total number of keywords found in "+f.getName()+" : "+count+"\n");
	              foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
	              if(count>comp) comp=count;
	              // System.out.println(keyword.length); //Debug Statement - No. of keywords
	                  
	          }//external try end, catch next
	          catch(Exception exep)
	          {
	              exep.printStackTrace();
	          }  
	      }	 
	     //------------------------------------else for pdf---------------------------------------//
	      else if(f.getName().endsWith(".pdf"))
	      {   filecount++;
	    	  try (PDDocument document = PDDocument.load(f)) 
	          {   System.out.println("\nOpening Resume : "+f.getName()+"(pdf format)\n");
	              foutput.write("\nOpening Resume : "+f.getName()+"(pdf format)\n");
	            	int count=0;
	          	
	              document.getClass();

	              if (!document.isEncrypted()) 
	              {
	                  PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
	                  stripper.setSortByPosition(true);

	                  PDFTextStripper tStripper = new PDFTextStripper();

	                  String pdfFileInText = tStripper.getText(document);

	  				// split by whitespace
	                  String lines[] = pdfFileInText.split("\\r?\\n");
	                  for (String line : lines) 
	                  {      
	         		     String check[] = {}; //String array to put all words from file, finally done (^ ^) 
	         	         check = line.split(" ");  //check=words now, split line by blankspace 
	         	          for(String word : check) 
	         	          {
	         	                 for(String k : keywords) // k < total no. of keywords -> check this.
	         	                 {	 
	         	        	        if (word.equals(k))   //Search for the given word
	         	                    {
	         	        	    	  System.out.print("Keyword '"+k+"' is found!\n");  
	         	        	    	  foutput.write("Keyword '"+k+"' is found!\n");
	         	                      count++;     //If Present increase the count by one (considering similar weightage)
	         	                     // comp[compinc++]=count;
	         	                    }
	         	                 }//word comparer for end
	         	          }//word checker for end
	         	         }//line checker for end        	
	                 }//encryptdoc end
	              System.out.println("Total keywords found / resume strength = "+count+"\n");
	              foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
	              if(count>comp) 
	              comp=count; 

	              }//pdf try end
	      }
	      else
	      System.out.print("No doc/pdf files found in the specified directory");	
	    }	
		foutput.write("\nTotal number of files scanned : "+filecount);
		foutput.write("\nHighest number of keywords found in a resume : "+comp);
	
		/*int x,big=0;
		for(x=0; x < filecount ; x++)
		{ if(comp[x]>big) 
			big=comp[x];
		}
		
		foutput.write("\n Resume with the highest strength / maximum no. of keywords found : "+Resume);*/
		foutput.close();
		
	
		
//------------------------------------------------------------------------------------------------------------		
		String bestresumename = null;
		
		//2nd round to find the best resume among all based on keywords.
		for (File t : fileslist)
	    {
	   //------------------------------------------doc---------------------------------------------//
	      if(t.getName().endsWith(".doc")) 
	      {   filecount++;
	          WordExtractor extractor = null;
	          try
	          {   
	              int count = 0;
	          
	              FileInputStream fis = new FileInputStream(t.getAbsolutePath());
	              HWPFDocument document = new HWPFDocument(fis); 
	              extractor = new WordExtractor(document);
	              String[] fileData = extractor.getParagraphText(); //Get all text from doc
	 
	                  	  String lines[] = fileData;
		                  for (String line : lines) 
		                  {      
		         		     String check[] = {}; 
		         	         check = line.split(" ");  
		         	          for(String word : check) 
		         	          {
		         	                 for(String k:keywords) // k < total no. of keywords -> check this.
		         	                 {	 
		         	        	        if(word.equals(k))   //Search for the given word
		         	                    {
		         	                      count++;     
		         	                    }
		         	                 }//word comparer for end
		         	          }//word checker for end
		         	         if(comp==count) 
				             bestresumename=t.getName(); 
		         	         }//line checker for end    
	                  
		                  OutputTextArea.append("Highest ranked resume filtered among all based on maximum keywords : "+bestresumename);
			  	  		  foutput.write("\nBest/Highest Ranked Resume filtered among selected resumes : "+bestresumename);
	          }//external try end, catch next
	          catch (Exception exep)
	          {
	              exep.printStackTrace();
	          }  
	          
	      }	 
	     //------------------------------------else for pdf---------------------------------------//
	      else if(t.getName().endsWith(".pdf"))
	      {   filecount++;
	    	  try (PDDocument document = PDDocument.load(t)) 
	          {   
	            	int count=0;
	          	
	              document.getClass();

	              if (!document.isEncrypted()) 
	              {
	                  PDFTextStripperByArea stripper = new PDFTextStripperByArea(); 
	                  stripper.setSortByPosition(true);

	                  PDFTextStripper tStripper = new PDFTextStripper();

	                  String pdfFileInText = tStripper.getText(document);

	  				// split by whitespace
	                  String lines[] = pdfFileInText.split("\\r?\\n");
	                  for (String line : lines) 
	                  {      
	         		     String check[] = {}; //String array to put all words from file, finally done (^ ^) 
	         	         check = line.split(" ");  //check=words now, split line by blankspace 
	         	          for(String word : check) 
	         	          {
	         	                 for(String k : keywords) // k < total no. of keywords -> check this.
	         	                 {	 
	         	        	        if(word.equals(k))   //Search for the given word
	         	                    {
	         	                      count++;     //If Present increase the count by one (considering similar weightage)
	         	                     // comp[compinc++]=count;
	         	                    }
	         	                 }//word comparer for end
	         	          }//word checker for end
	         	         if(comp==count) 
	                         bestresumename=t.getName();
	         	         }//line checker for end        	
	                 }//encryptdoc end
	              
	              OutputTextArea.append("Highest ranked resume filtered among all based on maximum keywords : "+bestresumename);
	  	  		  foutput.write("\nBest/Highest Ranked Resume filtered among selected resumes : "+bestresumename);
	              }//pdf try end
	      }	
	    }	
		foutput.close();	
    }
		
}

