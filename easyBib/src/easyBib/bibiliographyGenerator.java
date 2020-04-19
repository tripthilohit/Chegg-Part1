package easyBib;

import org.testng.Assert;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		File file = new File("configs//Configurations.properties");
		  
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
		Assert.assertEquals(title, "EasyBib: Free Bibliography Generator - MLA, APA, Chicago citation styles");
		
		testCase1_verifyIfButtonsExists();
		testCase2_verifyIfExportModalOpensUp();
		testCase3_verifyCitationActionsMenu();
		testCase4_verifyCopyCitationButton();
		
		endSession();
	}
	
	public static void testCase1_verifyIfButtonsExists()
	{
		//Verify Copy All, Export & Save buttons exist
		try {
			driver.findElement(By.cssSelector("button[data-test-id='copy-all-btn']")).isDisplayed();
			driver.findElement(By.cssSelector("button[data-test-id='export-btn']")).isDisplayed();
			driver.findElement(By.cssSelector("a[data-test-id='save-btn']")).isDisplayed();
		}catch(AssertionError e) {
			System.out.println("TestCase1 Verify If Buttons Exists - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase1 Verify If Buttons Exists - Test case Passed.");
	}
	
	public static void testCase2_verifyIfExportModalOpensUp()
	{
		//Verify clicking on the Export button will open up the export modal
		try {
			driver.findElement(By.cssSelector("button[data-test-id='export-btn']")).click();
			driver.findElement(By.cssSelector("button[data-test-id='modal-export-btn']")).isDisplayed();
			driver.findElement(By.cssSelector("button[data-test-id='close-modal-x']")).click();
		}catch(AssertionError e) {
			System.out.println("TestCase2 Verify If Export Modal Opens Up - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase2 Verify If Export Modal Opens Up - Test case Passed.");
	}
	
	public static void testCase3_verifyCitationActionsMenu()
	{
		//Verify clicking on the citation menu button (the button next to the citation with 3 dots) will open Citation Actions Menu
		try {
			driver.findElement(By.cssSelector("button[data-test-id='citations-more-menu']")).click();
			List<WebElement> menuElementcount = driver.findElements(By.cssSelector("div[data-test-id='dropdown-copy-citation']"));
			Assert.assertEquals(menuElementcount.size(), 1);
		}catch(AssertionError e) {
			System.out.println("TestCase3 Verify If Export Modal Opens Up - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase3 Verify If Export Modal Opens Up - Test case Passed.");
	}
	
	public static void testCase4_verifyCopyCitationButton()
	{
		//Verify clicking on the Copy Citation button in the Citation Actions Menu will open the copy success model
		try {
			driver.findElement(By.cssSelector("button[data-test-id='citations-more-menu']")).click();
			driver.findElement(By.cssSelector("div[data-test-id='dropdown-copy-citation']")).click();
			driver.findElement(By.cssSelector("div[class='sc-jtRlXQ ideHPR']")).isDisplayed();
			
		}catch(AssertionError e) {
			System.out.println("TestCase4 Verify Copy Citation Button - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase4 Verify Copy Citation Button - Test case Passed.");
	}
	
	public static void endSession() {
		driver.close();
		driver.quit();
	}
}