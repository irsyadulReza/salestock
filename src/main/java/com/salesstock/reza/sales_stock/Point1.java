package com.salesstock.reza.sales_stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class Point1 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		processFileProduct("C:\\Users\\emerio\\Documents\\workspace-sts-3.9.2.RELEASE\\sales_stock\\sources\\product_score.txt",
				"C:\\Users\\emerio\\Documents\\workspace-sts-3.9.2.RELEASE\\sales_stock\\sources\\user_preference.txt");
		
//		long epoch = epoch("1508733366");
//		System.out.println(epoch);
    	
	}
	
	
	private static void processFileProduct(String file1, String file2) throws FileNotFoundException{
	   	double result = 0;    		
		Scanner productscore = new Scanner(new File(file1));
	    Scanner userpreference = new Scanner(new File(file2));

	    /*
	     * product_score
	     */
	    ArrayList<String> id = new ArrayList<String>();
	    ArrayList<Double> score = new ArrayList<Double>();

	    while(productscore.hasNext()){
	        String curLine = productscore.nextLine();
	        String[] splitted = curLine.split("\t");
	        String id_ = splitted[0].trim();
	        String score_ = splitted[1].trim();
	        if(!"id".equals(id_)){
	            id.add(id_);
	        }
	        if(!"score".equals(score_)){
	        	result = parse(score_);
	        	score.add(result);
	        }
	    }
	    /*
	     * user_preference
	     */
	    
	    ArrayList<String> user = new ArrayList<String>();
	    ArrayList<String> ids = new ArrayList<String>();
	    ArrayList<Double> scores = new ArrayList<Double>();
	    ArrayList<Long> time = new ArrayList<Long>();
	    long epoch = 0;
	    while(userpreference.hasNext()){
	        String curLine = userpreference.nextLine();
	        String[] splitted = curLine.split("\t");
	        String user_ = splitted[0].trim();
	        String ids_ = splitted[1].trim();
	        String scores_ = splitted[2].trim();
	        String time_ = splitted[3].trim();
	        if(!"user".equals(user_)){
	        	user.add(user_);
	        }
	        if(!"id".equals(ids_)){
	        	ids.add(ids_);
	        }
	        if(!"score".equals(scores_)){
	        	result = parse(scores_);
	        	scores.add(result);
	        }
	        if(!"timestamp".equals(time_)){
	        	epoch = epoch(time_);
	        	time.add(epoch);
	        }
	    }
	    
	    System.out.println("product_score");
	    System.out.print(id);
	    System.out.print(score);
	    System.out.println();
	    System.out.println("user_preference");
	    System.out.print(user);
	    System.out.print(ids);
	    System.out.print(scores);
	    System.out.print(time);
	    System.out.println();
	    
	    /*
	     * recommendation Calculation 
	     */
	    List<Double> list = recommendationCalculate(scores, time);
	    
	    /*
	     * recommendation Result 
	     */
	    
	    List<Double> list2 = recommendationResult(id, ids, list,score);
	    productscore.close();
	    userpreference.close();
	}
	
	 
	private static long epoch(String unix) {
		long unix_seconds = Long.parseLong(unix);
		Date date = new Date(unix_seconds*1000L);
		   Date date2 = new Date();
		   // format of the date
		   SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
		   jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		   String java_date = jdf.format(date);
		   String current_date = jdf.format(date2);
		   
	        LocalDate startDate = LocalDate.parse(java_date);
	        LocalDate endtDate = LocalDate.parse(current_date);
//	        // Range = End date - Start date
	        long range = ChronoUnit.DAYS.between(startDate, endtDate);
		   
		return range;
		
	}
	
	private static double parse(String data) {
		double result = Double.parseDouble(data);
		return result;
		
	}
	
	private static List<Double> recommendationCalculate(List<Double> a,List<Long> b){
		double cal = 0.95;
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < a.size(); i++) {
			double resulta = a.get(i);
				if(resulta == 0.8) {
					System.out.println("score "+a.get(i)+"*"+cal+"*"+2+" = "+a.get(i)*cal*b.get(i));
					result.add(a.get(i)*cal*2);
				}else {
					System.out.println("score "+a.get(i)+"*"+cal+"*"+b.get(i)+" = "+a.get(i)*cal*b.get(i));
					result.add(a.get(i)*cal*b.get(i));					
				}
		}
		return result;
		
	}
	   
	private static List<Double> recommendationResult(List<String> a,List<String> b, List<Double> c,List<Double> d){
		List<Double> list = new ArrayList<Double>();
		a.sort(Comparator.naturalOrder());
		b.sort(Comparator.naturalOrder());
		for(int i = 0; i < a.size(); i++) {
			if (!a.get(i).equalsIgnoreCase(b.get(i))) {
				b.remove(i);
			}
		}
		double result = 0;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).equalsIgnoreCase(b.get(i))) {
				System.out.println("result "+d.get(i)+"*"+c.get(i)+"+"+d.get(i)+" = "+d.get(i)*c.get(i)+d.get(i));
				result = d.get(i)*c.get(i)+d.get(i);
				list.add(result);
			}
		}
		System.out.println(" recommendationResult "+result);
		return list;
		
	}

}
