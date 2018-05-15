package com.salesstock.reza.sales_stock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class Point2 
{
    public static void main( String[] args ) throws IOException
    {
    	Point2 point2 = new Point2();
    	List<String> list = point2.processInputFile(".\\sources\\age.txt",
    			".\\sources\\ageresult.txt");
       
    }
    
    private List<String> processInputFile(String inputPathFile,String outputPathFile){
    
    	List<String> inputList = new ArrayList<String>();
    	try {
			File inputfile = new File(inputPathFile);
			InputStream inputStream = new FileInputStream(inputfile);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			inputList = bufferedReader.lines().skip(0).map(mapToItem).collect(Collectors.toList());
			inputList.sort(Comparator.naturalOrder());
			savingFile(outputPathFile, inputList);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return inputList;
    }
    

    private Function<String, String> mapToItem = (line) -> {
    	String[] p = line.split(System.getProperty("line.separator"));
    	
    	String item = new String();
    	for (int i = 0; i < p.length; i++) {
			item = p[i];
		}
		return item;
    
    };
    
    private void savingFile(String outputPathFile,List<String> list) throws IOException {
    	Path path = Paths.get(outputPathFile);
    	try(BufferedWriter writer = Files.newBufferedWriter(path)){
    		for (String data  : list) {
        		writer.write(data);
    			writer.newLine();
			}
			writer.close();
    		    		
    	}
    }
    
}
