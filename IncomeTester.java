package Scraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import org.junit.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import junit.framework.TestCase;

public class IncomeTester extends TestCase 
	{

	final WebClient webClient = new WebClient();
	
	@Test
	public void incomeComparer() throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		//Get array of ints 
		int[] zips = zipGetter();
		HtmlPage page = pageGetter(1);
	}
	
	
	public HtmlPage pageGetter(int i) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		
		webClient.waitForBackgroundJavaScriptStartingBefore(5000);
		HtmlPage page = webClient.getPage("https://factfinder.census.gov/faces/nav/jsf/pages/index.xhtml");
		if(i < 1000) 
		{
		try {
			page = webClient.getPage("https://factfinder.census.gov/bkmk/cf/1.0/en/zip/" + "00" + i + "/INCOME");
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		}
		else {
			try {
				page = webClient.getPage("https://factfinder.census.gov/bkmk/cf/1.0/en/zip/" + "0" + i + "/INCOME");
			} catch (FailingHttpStatusCodeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}
		}
		return page;
	}

	//
	public int[] zipGetter() throws FileNotFoundException {
		
		File file = new File("C:\\\\Users\\\\mnkha\\\\Documents\\\\zip_code_database.csv");
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		int[] zips = new int[42633];
		return zips;
	}
}
