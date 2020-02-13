package Scraper;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.logging.Level;
import  java.io.*;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

public class HTMLScraper 
{
	
	public static HashMap<String, String> map;
	public static WebClient webClient;
	public static String url = "https://factfinder.census.gov/bkmk/cf/1.0/en/zip/";
	public static int sample = 1000;
	public static String xpath = "/html/body/comm-facts/div/div[3]/div[1]/div[1]/div[2]";
	public static File file = new File("C:\\Users\\mnkha\\Documents\\ZipIncome.csv");
	public static FileWriter outWriter;
	public static BufferedWriter writer;
	
	//File writing fields handling IOexception
	/*static 
	{
		try 
		{
		FileWriter outWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(outWriter);
		}
		catch (final IOException e) 
		{
		FileWriter outWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(outWriter);
        throw new ExceptionInInitializerError(e.getMessage());
		}
	}*/
	
	
	//Calls scraper function to populate hashmap, then calls output_content to place in csv
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		//creates WebClient obj, sets number of zipcodes needed 
		map = initialize(sample);
	}
	
	//Inits objects for HtmlIter to use, sets console warnings off
	public static HashMap<String, String> initialize(int sample) throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		//Start webclient, keep console from overloading with warnings
		webClient = new WebClient();
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		//Number of iterations over HtmlIter for loop for map<zip, income> pairs
		int numZips = sample;
		//Capacity = numZips
		map = new HashMap<String, String>(numZips);		
		return HtmlIter(numZips);
	}
	
	
	
	
	
	//loops through as many zips as needed. Collects XPath of displayed 
	//median income into zip/income key/value map
	public static HashMap<String, String> HtmlIter(int numZips) throws IOException 
	{
		outWriter = new FileWriter(file);
		writer = new BufferedWriter(outWriter);
		HtmlPage zipPage;
		//median income as displayed on HtmlPage
		HtmlDivision result;
		int i;
		//Final string value to store zipcode into map as formatted String
		String iZip;
		
		//Represents income string as placed in map
		String str = "";
		int z = numZips + 600;
				
		//Zipcodes start at 501
		for (i = 600; i < z; i++) 
		{
			//add necessary zeroes to i 
			iZip = "0000" + i;
			//take last 5 chars-- all zips have 5 digits
			iZip = iZip.substring(iZip.length() -5);
			//webClient.waitForBackgroundJavaScriptStartingBefore(5000);
			
			
			//get page
			try {
				zipPage = webClient.getPage(url + iZip + "/INCOME");
				
				
				//get XPath to where median income value is displayed
				try {
					//"try" should catch nonexistent zips
					result = (HtmlDivision) zipPage.getByXPath(xpath).get(0);
					//catch "no data" about zip
					if(!result.asText().equals("Data are not available for this topic and the selected geography"))
					{
						str = result.asText();
						map.put(iZip, str);
						//Add new zip/income pair to csv
						output_content(iZip, str);
					}
				} 
				catch(Exception IndexOutOfBoundsException) 
				{
					//do nothing
				}
			} catch (FailingHttpStatusCodeException | IOException e) 
			{
				//do nothing
			}
			
		}
		writer.close();
		return map;
	}
	
	//Posts zip/income pair each time one from HtmlIter is found
	public static void output_content(String zip, String income) throws IOException 
	{
		//If you need to create output file
		//makeFile();

		String zipIncomeCs = zip + "," + income.replace(",", "");
		System.out.println(zipIncomeCs);
	    writer.write(zipIncomeCs);
	    writer.newLine();
	}
	
	//Make key/value pair in HashMap comma-separated string
	public static String makeCs(String zip)
	{
		String income = map.get(zip).replace(",", "");
		return zip + "," + income;
	}
	
	
	
	public static void makeFile() throws IOException 
	{
		if(!file.exists()) 
		{
			file.createNewFile();
		}
	}
	
}
