package Scraper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.text.html.HTML;

import  java.io.*;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.logging.Log;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException; 


public class Tester {
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		try (final WebClient webClient = new WebClient()) {
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
			 webClient.waitForBackgroundJavaScriptStartingBefore(5000);
			 webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		     final HtmlPage page1 = webClient.getPage("https://factfinder.census.gov/faces/nav/jsf/pages/community_facts.xhtml");
		     HtmlForm form = (HtmlForm) page1.getElementById("cfsearchform");
		     HtmlTextInput textField = form.getInputByName("cfsearchtextbox");
		     List<HtmlAnchor> anchors = page1.getAnchors();
		     textField.type("01991");
		     HtmlAnchor button = null;
	         List<HtmlAnchor> stuff = new ArrayList<HtmlAnchor>();
		     for (HtmlAnchor anchor : anchors) {
		        if (anchor.asText().equals("GO")) {
		           stuff.add(anchor);
		        }
		     }
		     button = stuff.get(0);
		     //System.out.println(button);
		    // webClient.waitForBackgroundJavaScriptStartingBefore(5000);
		     
		     HtmlPage page2 = button.click(false, false, false, true, true, false);
		     if(page2.getByXPath("/html/body/div[7]/div[1]/div/div/alert-modal/div[1]/span") == null) {
		     //HtmlDivision n = (HtmlDivision) page2.getByXPath("/html/body/comm-facts/div/div[3]/div[1]/div[1]/div[3]").get(0);
			 webClient.waitForBackgroundJavaScriptStartingBefore(5000);
			 List<HtmlAnchor> anch = page2.getAnchors();
			 stuff = new ArrayList<HtmlAnchor>();
			 HtmlAnchor income = null;
		     for (HtmlAnchor anchor : anch) {
			        if (anchor.asText().equals("Income")) {
			           stuff.add(anchor);
			        }
			 }
		     income = stuff.get(0);
		     HtmlPage page3 = income.click();
			 webClient.waitForBackgroundJavaScriptStartingBefore(5000);
			 HtmlDivision n = (HtmlDivision) page3.getByXPath("/html/body/comm-facts/div/div[3]/div[1]/div[1]/div[3]").get(0);
			 System.out.println(n.asText());
		     }
		     else {
		    	 
		    	 System.out.println("zip not real");
		     }
		}

	}
	
}
