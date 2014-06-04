package gatech.cs6300.project1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Main {
	public static String OPT_WORD_DELIM="-d";
	public static String OPT_SENT_DELIM="-l";
	public static String OPT_WORD_SIZE="-w";

	HashSet<Character> c = new HashSet<Character>();
	
	//TODO filename at the end or beginning or anywhere
	public static String USAGE = "Usage: java Main [-w <word size>] [-l <sentence delimiters>] [-d <word delimiters>] <filename>";
	
    public static void main(String[] args) {
 
    	List<String> argsArray =  Arrays.asList(args);
    	Iterator<String> iter = argsArray.iterator();
    	HashMap<String, String> argsMap = new HashMap<String,String>();
    	String filename = null;
    	boolean insufficientArgs = false;
    	while(iter.hasNext()) {
    		String arg = iter.next();
    		System.out.println("ARg: "+arg);
    		if(arg.matches("^\\-[dlw]$")) {
    			if(!iter.hasNext()) {
    				insufficientArgs = true;
    			} else {
    				argsMap.put(arg, (String)iter.next());
    			}
    		} else {
    			filename = arg;
    			//TODO filename at the end or beginning  or anywhere remove break
    			break;
    		}
    	}
    	
    	if(insufficientArgs || filename == null) {
    		System.out.println("Error: Insufficient arguments");
    		System.out.println	(USAGE);
    		System.exit(1);    				
    	}
    		

    	if(!(new File(filename).exists())) {
    		//TODO filepath has to be included in quotes if it has spaces 
    		System.out.println("Error: File "+filename+" does not exist");
    		System.exit(1);
    	}
    	
    	AverageSentenceLength mr = new AverageSentenceLength(filename);
    	if(argsMap.get(OPT_WORD_SIZE) != null) {
    		//TODO catch exception
    		mr.setMinWordSize(Integer.valueOf(argsMap.get(OPT_WORD_SIZE)));
    	}
    	if(argsMap.get(OPT_SENT_DELIM) != null) {
    		//TODO what to do with * and &
    		mr.setSentenceDelimiters("["+argsMap.get(OPT_SENT_DELIM).replaceAll("([\\W])", "\\\\$1")+"]+");
    	}
    	if(argsMap.get(OPT_WORD_DELIM) != null) {
    		mr.setWordDelimiters("["+argsMap.get(OPT_WORD_DELIM).replaceAll("([\\W])", "\\\\$1")+"]");
    	}


        try{
            mr.sizeIt();
        }
        catch(FileNotFoundException e){
        	//TODO improve feedback to user
            System.out.println("Oops!");
        }
 
    }
}
