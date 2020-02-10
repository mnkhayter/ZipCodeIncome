package Scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import  java.io.*;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

public class HTTPScraper 
{
	
	public static ArrayList<String> map;
	public static WebClient webClient;
	
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		//Start webclient, keep console from overloading with warnings
		webClient = new WebClient();
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		//creates array of data pts
		ArrayList<String> finish = scrape();
	}
	
	public static ArrayList<String> scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		map = new ArrayList<String>();
		int i;
		String str = "";
		HtmlDivision result = null;
		HtmlPage zipPage = webClient.getPage("https://factfinder.census.gov/");
		
		for (i = 500; i < 10000; i++) 
		{
			
			if(i <= 1000) 
			{
				try {
					zipPage = webClient.getPage("https://factfinder.census.gov/bkmk/cf/1.0/en/zip/" + "00" + i + "/INCOME");
				} catch (FailingHttpStatusCodeException | IOException e) {
					//do nothing
				}
			}
			else {
				try {
					zipPage = webClient.getPage("https://factfinder.census.gov/bkmk/cf/1.0/en/zip/" + "0" + i + "/INCOME");
				} catch (FailingHttpStatusCodeException | IOException e) {
					//do nothing
				}
			}
			webClient.waitForBackgroundJavaScriptStartingBefore(5000);

			try 
			{
				//"try" should catch nonexistent zips, if statement catches no data zips
				result = (HtmlDivision) zipPage.getByXPath("/html/body/comm-facts/div/div[3]/div[1]/div[1]/div[2]").get(0);
				if(!result.asText().equals("Data are not available for this topic and the selected geography")){
				
					if(i <=1000) 
					{
						str = "00" + i + ": " + result.asText();
						System.out.println(str);
						map.add(str);
					}
					else 
					{
						str = "0" + i + ": " +  result.asText();
						System.out.println(str);
						map.add(str);
					}
				}
			}	 
			catch(Exception IndexOutOfBoundsException) 
			{
				//do nothing	
			}
		  }
		  
		  
		  //For zips that don't need to add 0s
		  for (i = 10000; i < 99999; i++) 
		  {
			webClient.waitForBackgroundJavaScriptStartingBefore(5000);
			try {
				zipPage = webClient.getPage("https://factfinder.census.gov/bkmk/cf/1.0/en/zip/"+ i + "/INCOME");
			} catch (FailingHttpStatusCodeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//"try" should catch nonexistent zips, if statement catches no data zips
				result = (HtmlDivision) zipPage.getByXPath("/html/body/comm-facts/div/div[3]/div[1]/div[1]/div[2]").get(0);
				if(!result.asText().equals("Data are not available for this topic and the selected geography"))
				{
					str = i + ": " +  result.asText();
					System.out.println(str);
					map.add(str);
				}
			} 
			catch(Exception IndexOutOfBoundsException) 
			{
				//do nothing
			}
			
		  }
		
		return map;
	}
}
