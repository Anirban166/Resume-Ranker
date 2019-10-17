package anirban;

import java.util.*;
import java.io.*;
/*import java.nio.file.Files;
  import java.nio.file.Path; Unused (~ ~) */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// .doc dependencies:
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

//.pdf dependencies:
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

//include log4j configurator to remove log4j warnings:
import org.apache.log4j.BasicConfigurator;

public class KeywordScannerForResumeByAnirban 
{	
    public static JTextField t1,t2;
    public static TextArea t3;
    public static JLabel l1,l2,l3;
	
	public static void main(String[] args) throws IOException
    {   
		  BasicConfigurator.configure(); //pdf scan'ot hei run koribo, so space add koribo ase console disp. karone
		  JFrame jf = new JFrame("Keyword Scanner Menu");
		  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      jf.setSize(600,800);
	      JButton b1 = new JButton("Display Results :");
	      JPanel jpanel = new JPanel();
	      jpanel.setBackground(Color.getHSBColor(25, 130, 95));
	      jpanel.setLayout(null);
	      l3 = new JLabel("Directory :");
	      t1=new JTextField(); //Keywords
	      t1.setBounds(120,60,100,20); 
	      t2=new JTextField(); //Directory
	      t2.setBounds(120,90,100,20);
	      t3=new TextArea(); //Result
	      t3.setBounds(10,160,550,600);
	      l1 = new JLabel("Resume Scanner :");
	      l1.setFont(new Font("Comic Sans", Font.BOLD, 20));
	      l1.setForeground(Color.white);
	      l2 = new JLabel("Keywords:");
	      l2.setFont(new Font("Comic Sans", Font.BOLD, 18));
	      l2.setBounds(10,60,400,20);
	      l3 = new JLabel("Directory :");
	      l3.setBounds(10,90,400,20);
	      l3.setFont(new Font("Comic Sans", Font.BOLD, 18));
	      //Results button
	      b1.setBounds(10,120,210,30);
	      b1.setFont(new Font("Comic Sans", Font.BOLD, 15));
	      jpanel.add(l1); 
	      jpanel.add(l2);
	      jpanel.add(l3);
	      jpanel.add(t1);
	      jpanel.add(t2);
	      jpanel.add(t3);
	      jpanel.add(b1);
	      jf.getContentPane().add(jpanel);
	      jf.setVisible(true); 
	      b1.addActionListener(new ActionListener()
	      {
	          @Override
	           public void actionPerformed(ActionEvent e) 
	          {   try
	          {
	        	  String key=t1.getText();
				  String[] keyword=key.split(" "); //keyword line divided into keywords
			      int totalkeywords=0; int filecount=0; int comp=0;
					for(String d: keyword)
					{   System.out.print(d);
						totalkeywords++;
					}
					String directory=t2.getText();		
					File dir = new File(directory);
					File[] fileslist = dir.listFiles(new FileFilterer());
					FileWriter foutput=new FileWriter("F:/Resume_Ranker.doc");
					foutput.write("Total no. of keywords : "+totalkeywords);
					t3.append("Total no. of keywords : "+totalkeywords);
					
					for (File f : fileslist)
				    {
				   //------------------------------------------doc---------------------------------------------//
				      if(f.getName().endsWith(".doc")) 
				      {   filecount++;
				          WordExtractor extractor = null;
				          try
				          {   System.out.println("Opening Resume : "+f.getName()+"(word format)\n");
				              foutput.write("\nOpening Resume : "+f.getName()+"(word format)\n");
				              t3.append("\nOpening Resume : "+f.getName()+"(word format)\n");
				              int count=0;
				          
				              FileInputStream fis = new FileInputStream(f.getAbsolutePath());
				              HWPFDocument document = new HWPFDocument(fis); 
				              extractor = new WordExtractor(document);
				              String[] fileData = extractor.getParagraphText(); //Get all text from doc
				 
				                  	  String lines[] = fileData;
					                  for (String line : lines) 
					                  {      
					         		     String check[] = {}; 
					         	         check=line.split(" ");  
					         	          for (String word : check) 
					         	          {
					         	                 for(String k:keyword) // k < total no. of keywords -> check this.
					         	                 {	 
					         	        	        if (word.equals(k))   //Search for the given word
					         	                    {
					         	        	    	  System.out.print("Keyword '"+k+"' is found!\n");
					         	        	    	  foutput.write("Keyword '"+k+"' is found!\n");
					         	        	    	  t3.append("Keyword '"+k+"' is found!\n");
					         	                      count++;     //If Present increase the count by one (considering similar weightage)
					         	                     // comp[compinc++]=count;
					         	                    }
					         	                 }//word comparer for end
					         	          }//word checker for end
					         	         }//line checker for end    
				                 
				              System.out.println("Total keywords found / resume strength = "+count+"\n");
				              foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              t3.append("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              if(count>comp) comp=count;
				              if(count==0)
				              {foutput.write("No keywords found in this resume.\n");
				              t3.append("No keywords found in this resume.\n");}
				              // System.out.println(keyword.length); //Debug Statement - No. of keywords
				                  
				          }//external try end, catch next
				          catch (Exception exep)
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
				              t3.append("\nOpening Resume : "+f.getName()+"(pdf format)\n");
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
				         	         check=line.split(" ");  //check=words now, split line by blankspace 
				         	          for (String word : check) 
				         	          {
				         	                 for(String k:keyword) // k < total no. of keywords -> check this.
				         	                 {	 
				         	        	        if (word.equals(k))   //Search for the given word
				         	                    {
				         	        	    	  System.out.print("Keyword '"+k+"' is found!\n");  
				         	        	    	  foutput.write("Keyword '"+k+"' is found!\n");
				         	        	    	  t3.append("Keyword '"+k+"' is found!\n");
				         	                      count++;     //If Present increase the count by one (considering similar weightage)
				         	                     // comp[compinc++]=count;
				         	                    }
				         	                 }//word comparer for end
				         	          }//word checker for end
				         	         }//line checker for end        	

				                 }//encryptdoc end
				              System.out.println("Total keywords found / resume strength = "+count+"\n");
				              foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              t3.append("Total number of keywords found in "+f.getName()+" : "+count+"\n");
				              if(count>comp) comp=count;
				              if(count==0)
				              { foutput.write("No keywords found in this resume.\n");
				              t3.append("No keywords found in this resume.\n"); }
				              }//pdf try end
				      }
				      else
				      System.out.print("No doc/pdf files found in the specified directory");	
				    }	
					foutput.write("\nTotal number of files scanned : "+filecount);
					t3.append("\nTotal number of files scanned : "+filecount);
					foutput.write("\nHighest number of keywords found in a resume : "+comp);
					t3.append("\nHighest number of keywords found in a resume : "+comp);
					/*int x,big=0;
					for(x=0; x < filecount ; x++)
					{ if(comp[x]>big) 
						big=comp[x];
					}
					foutput.write("\n Resume with the highest strength / maximum no. of keywords found : "+Resume);*/
					
					foutput.close();
	          }
	          catch(Exception ae){}
	          }});
		// define second loop below:
	      
		System.out.print("Enter keywords you want to search, seperated by a space each.");
		Scanner sin=new Scanner(System.in);
		String x= sin.nextLine();
		String[] keyword=x.split(" ");
		sin.close();
		
		int totalkeywords=0; int filecount=0; int comp=0;
		for(String d: keyword)
		{   System.out.print(d); //for debugging
			totalkeywords++;
		}
		
		//----------------------------------Define Keywords above----------------------------------//
		
		String directory="F:/Resumes";
		File dir = new File(directory);
		File[] fileslist = dir.listFiles(new FileFilterer());
	    
		FileWriter foutput=new FileWriter("F:/Resume_Ranker.doc");
		//output text file
		
		foutput.write("Total no. of keywords : "+totalkeywords);
		t3.append("Total no. of keywords : "+totalkeywords);
		
		for (File f : fileslist)
	    {
	   //------------------------------------------doc---------------------------------------------//
	      if(f.getName().endsWith(".doc")) 
	      {   filecount++;
	          WordExtractor extractor = null;
	          try
	          {   System.out.println("Opening Resume : "+f.getName()+"(word format)\n");
	              foutput.write("\nOpening Resume : "+f.getName()+"(word format)\n");
	              int count=0;
	          
	              FileInputStream fis = new FileInputStream(f.getAbsolutePath());
	              HWPFDocument document = new HWPFDocument(fis); 
	              extractor = new WordExtractor(document);
	              String[] fileData = extractor.getParagraphText(); //Get all text from doc
	 
	                  	  String lines[] = fileData;
		                  for (String line : lines) 
		                  {      
		         		     String check[] = {}; 
		         	         check=line.split(" ");  
		         	          for (String word : check) 
		         	          {
		         	                 for(String k:keyword) // k < total no. of keywords -> check this.
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
	                
	              t3.append("Total number of keywords found in "+f.getName()+" : "+count+"\n");
	              foutput.write("Total number of keywords found in "+f.getName()+" : "+count+"\n");
	              if(count>comp) comp=count;
	              // System.out.println(keyword.length); //Debug Statement - No. of keywords
	                  
	          }//external try end, catch next
	          catch (Exception exep)
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
	         	         check=line.split(" ");  //check=words now, split line by blankspace 
	         	          for (String word : check) 
	         	          {
	         	                 for(String k:keyword) // k < total no. of keywords -> check this.
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
	              int count=0;
	          
	              FileInputStream fis = new FileInputStream(t.getAbsolutePath());
	              HWPFDocument document = new HWPFDocument(fis); 
	              extractor = new WordExtractor(document);
	              String[] fileData = extractor.getParagraphText(); //Get all text from doc
	 
	                  	  String lines[] = fileData;
		                  for (String line : lines) 
		                  {      
		         		     String check[] = {}; 
		         	         check=line.split(" ");  
		         	          for (String word : check) 
		         	          {
		         	                 for(String k:keyword) // k < total no. of keywords -> check this.
		         	                 {	 
		         	        	        if (word.equals(k))   //Search for the given word
		         	                    {
		         	                      count++;     
		         	                    }
		         	                 }//word comparer for end
		         	          }//word checker for end
		         	         if(comp==count) 
				             bestresumename=t.getName(); 
		         	         }//line checker for end    
	                  
		                  t3.append("Highest ranked resume filtered among all based on maximum keywords : "+bestresumename);
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
	         	         check=line.split(" ");  //check=words now, split line by blankspace 
	         	          for (String word : check) 
	         	          {
	         	                 for(String k:keyword) // k < total no. of keywords -> check this.
	         	                 {	 
	         	        	        if (word.equals(k))   //Search for the given word
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
	              
	              t3.append("Highest ranked resume filtered among all based on maximum keywords : "+bestresumename);
	  	  		  foutput.write("\nBest/Highest Ranked Resume filtered among selected resumes : "+bestresumename);
	              }//pdf try end
	      }	
	    }	
		
		foutput.close();	
    }
		
    }//main end, prints go above
    
//Finally Khotom.

