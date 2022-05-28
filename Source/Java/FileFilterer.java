import java.io.*;

public class FileFilterer implements FileFilter
{
    public final String[] AcceptedFileExtensions = new String[] {"doc", "pdf"};
    public boolean accept(File file)
    {
    	for(String extension : AcceptedFileExtensions)
        { 
            if(file.getName().toLowerCase().endsWith(extension)) 
                return true;
        }        
        return false;
    }
}