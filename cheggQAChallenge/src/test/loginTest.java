package test;

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

public class loginTest {

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
		testCase2_loginWithInCorrectCredentials();
		testCase3_loginWithCorrectCredentials();
		testCase4_pageEditButtonShouldNotBeVisible();
		
		endSession();
	}
	
	public static void testCase1_verifyUrlRedirection()
	{
		//This test verifies if the redirected url is correct.
		String redirectUrl = driver.getCurrentUrl();
		try {
		Assert.assertEquals(redirectUrl, prop.getProperty("expectedUrl"));
		}catch(AssertionError e) {
			System.out.println("TestCase1 VerifyUrlRedirection - Test case Failed.");
		    throw e;
		}
		System.out.println("TestCase1 Verify Url Redirection - Test case Passed.");
	}
	
	public static void testCase2_loginWithInCorrectCredentials()
	{
		//This test makes sure a user cannot login using incorrect credentials.
		try {
			driver.findElement(By.id("login-user")).sendKeys(prop.getProperty("inCorrectUsername"));
			driver.findElement(By.id("login-pass")).sendKeys(prop.getProperty("inCorrectPassword"));
			driver.findElement(By.cssSelector("button[type='submit']")).click();
			driver.findElement(By.cssSelector("i[class='icon-warning-outline']")).isDisplayed();
			}catch(AssertionError e) {
				System.out.println("TestCase2 Login With InCorrectCredentials - Test case Failed.");
			    throw e;
			}
			System.out.println("TestCase2 Login With InCorrectCredentials - Test case Passed.");
	}

	public static void testCase3_loginWithCorrectCredentials()
	{
		//This test verifies login functionality with correct credentials.
		try {
			driver.findElement(By.id("login-user")).sendKeys(prop.getProperty("username"));
			driver.findElement(By.id("login-pass")).sendKeys(prop.getProperty("password"));
			driver.findElement(By.cssSelector("button[type='submit']")).click();
			driver.findElement(By.id("home-page")).isDisplayed();
			}catch(AssertionError e) {
				System.out.println("TestCase3 Login With CorrectCredentials - Test case Failed.");
			    throw e;
			}
			System.out.println("TestCase3 Login With CorrectCredentials - Test case Passed.");
	}
	
	public static void testCase4_pageEditButtonShouldNotBeVisible()
	{
		//P.S here I am assuming the locator of the edit button. This test makes sure edit button is not visible.
		try {
		Assert.assertEquals(0, driver.findElements(By.id("page-edit")).size());
		}catch(AssertionError e) {
			System.out.println("TestCase4 Page Edit Button Should Not Be Visible - Test case Failed.");
		    throw e;
		}System.out.println("TestCase4 Page Edit Button Should Not Be Visible  - Test case Passed.");
	}
	
	public static void endSession() {
		driver.close();
		driver.quit();
	}
}
