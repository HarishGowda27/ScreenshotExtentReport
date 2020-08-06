package com.screen.testscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class NewTest3 {
	
  @Test
  public void chrome() {
	  System.setProperty("webdriver.chrome.driver","E:\\Softwares\\Eclipse\\selenium component\\New Automation Jars\\Chrome driver\\chromedriver.exe");
		 
		 WebDriver driver = new ChromeDriver();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 driver.manage().window().maximize();
		 driver.get("https://Google.com/");
		 String url = driver.getCurrentUrl();
	
		 if(url.contains("Google"))
		 {
			 System.out.println(url);
		 }
		 else {
			 System.out.println("NO match");
		 }
		 
		 driver.quit();
  }
}
