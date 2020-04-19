package easyBib;

import org.testng.Assert;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class bibiliographyGenerator {

	public static WebDriver driver;
	public static Properties prop=new Properties();
	
	public static void initWebDriver(String URL) throws InterruptedException {

		// Setting up Chrome driver path.
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		// Launching Chrome browser.
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(URL);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) throws InterruptedException {
		File file = new File("configs//Configuration.properties");
		  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initWebDriver(prop.getProperty("url"));
		String title = driver.getTitle();
		Assert.assertEquals(title, "Chegg QA Challenge");
		
		testCase1_verifyUrlRedirection();
	
		
		endSession();
	}
	
	public static void testCase1_verifyUrlRedirection()
	{
		String redirectUrl = driver.getCurrentUrl();
		try {
		Assert.assertEquals(redirectUrl, prop.getProperty("expectedUrl"));
		}catch(AssertionError e) {
			System.out.println("TestCase1 VerifyUrlRedirection - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase1 Verify Url Redirection - Test case Passed.");
	}
	
	
	
	
	
	public static void endSession() {
		driver.close();
		driver.quit();
	}
}