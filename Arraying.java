package Scraper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import  java.io.*;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.EncryptedDocumentException;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.common.collect.Table.Cell;
import java.util.Iterator;

public class Arraying {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		ArrayList<Object> zips = new ArrayList<>();
		File filename = new File("C:\\Users\\mnkha\\Documents\\zip_code_database.csv");		
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
	    HSSFSheet myExcelSheet = workbook.getSheet("Birthdays");
		//InputStream input = new FileInputStream(filename);
		Sheet sheet = workbook.getSheet("zip_code_database");
        Iterator<Row> rowIterator = sheet.rowIterator();
        Row row = sheet.getRow(2);
        while (rowIterator.hasNext()) {
        	
        	 zips.add(row.getCell(0));
             row = rowIterator.next();
            
        }
        System.out.println(zips);
	}

}
