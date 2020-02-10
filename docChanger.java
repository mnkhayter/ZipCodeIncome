package Scraper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Take text doc of all zips in U.S. with calculated median income given by census bureau
public class docChanger 
{

	public static void main(String[] args) throws IOException 
	{

		//Read Zip Income file and make csv formatted Strings from each "zip: income" line
		output_content();
	}

	//Take one line "zip: income" and makes it comma separated
	public static String parse_content(String line) throws IOException
	{
		//BufferedReader br = new BufferedReader(new FileReader(file)); 
		Scanner scanner = new Scanner(line);
		String next = "";
		String ln = "";
		String[] splits;
		
		//Go to zip code: add to first cell in .csv
		next = scanner.next().substring(0, 5);
		ln = ln + next + ",";
		//Go to median income
		next = scanner.next();
		splits = next.split(",");
		next = splits[0] + splits[1];
		ln = ln + next + "\n";

		return ln;	
	}

	public static void output_content() throws IOException
	{
		File file = new File("C:\\Users\\mnkha\\Documents\\ZipIncome.csv");
		File fl = new File("C:\\Users\\mnkha\\Documents\\Zipcode Median Income.txt");
		if(!file.exists()) 
		{
			file.createNewFile();
		}
		Scanner scanner = new Scanner(fl);
		FileWriter outWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(outWriter);
		//PrintWriter printwrite = new PrintWriter(file);
	    String line = scanner.nextLine();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(line.getBytes())));


	    
	    while ((line = scanner.nextLine()) != null) 
	    {
	      System.out.println(line);
	      line = parse_content(line);
	      
	      writer.write(line.trim());
	      writer.newLine();
	    }
		
		writer.close();
		
	}
	
}
