package com.screen.testscripts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



//NOTE: Path of report : ProjectFOLDER\test-output

public class ExtentScreenShot {
	
	WebDriver driver;
	ExtentReports extent;//Taken from already added ExtentListenerTestNG.class file
	ExtentTest extentTest;//Taken from already added ExtentListenerTestNG.class file
	
	@BeforeTest
	public void ExtentReportSS() 
	{
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html",true); //path of the screenshot TRUE to replace taken SS
		//To display the info in report 																//user.dir-path of the project
			//extent.addSystemInfo("HOST NAME", " DELL");
			extent.addSystemInfo("OS", "WINDOWS 10");
			//extent.addSystemInfo("EVERONMENT","QA"); 
	}
	
	@AfterTest
	public void aftertest()
	{
		extent.flush();
		extent.close(); //close the connection with EXTREPORT
	}
	
	
	public static String getScreenshot(WebDriver driver, String screenshotname) throws IOException
	
	{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// under src folder
		String destination = System.getProperty("user.dir") + "FailedTestsScreenshots" + screenshotname + dateName
				+ ".png";  // FailedTestsScreenshots : foldername
		File finalDestination = new File(destination);
		//FileUtils.copyFile(source, finalDestination);
		FileHandler.copy(source, finalDestination);
		
		return destination;
	}
	
	  @BeforeMethod
	  public void chrome()
	  {
		  System.setProperty("webdriver.chrome.driver","E:\\Softwares\\Eclipse\\selenium component\\New Automation Jars\\Chrome driver\\chromedriver.exe");
			 
			 driver = new ChromeDriver();
			 driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			 driver.manage().window().maximize();	 
			 
			 driver.get("https://Facebook.com/");

	  }

	  @Test
	  public void Facebook()
	  {
		 extentTest = extent.startTest("ExtentScreenShot"); //entry manditory of method
		 String url = driver.getTitle();
		 System.out.println(url);
		 Assert.assertEquals(url, "Facebook – log in or sign up123");
		
	  }
	  
	  @Test
	  public void Facebooklogo()
	  {
		  extentTest = extent.startTest("Facebooklogo"); //entery manditory of method
		  boolean b = driver.findElement(By.xpath("//*[@id=\"blueBarDOMInspector\"]/div/div/div/div[1]/h1/a/i")).isDisplayed();
		  Assert.assertTrue(b);
	  }
	  

	  @AfterMethod
	  public void teardown(ITestResult result) throws IOException //ITestResult builtin print the errors
	  {
		  if(result.getStatus()==ITestResult.FAILURE)
		  {
			  extentTest.log(LogStatus.FAIL, "TEST FAILED IS : "+result.getName()); //to add name
			  extentTest.log(LogStatus.FAIL, "TEST FAILED IS : "+result.getThrowable()); // add error and exception
			  String screenshotPath = ExtentScreenShot.getScreenshot(driver, result.getName()); 
			  extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));// to add screenshot in extent report //video
			  //addScreencast method
			  extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //IMAGE ScSh
		  }
		  
		  else if(result.getStatus()==ITestResult.SKIP)
		  {
			  extentTest.log(LogStatus.SKIP, "Testcases skipped is :" +result.getName() );
		  }
		  
		  else if(result.getStatus()==ITestResult.SUCCESS)
		  {
			  extentTest.log(LogStatus.PASS, "Test cases Success : "+result.getName());
		  }
		  
		  extent.endTest(extentTest); //ending test and ends the current test and prepares the HTML report.
		  
		 
		  driver.quit();  
	  }
  
  
}
