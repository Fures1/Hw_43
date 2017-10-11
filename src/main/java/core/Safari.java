package core;

import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import java.math.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.regex.*;

public class Safari {

	public static void main(String[] args) throws InterruptedException {
		Logger.getLogger("").setLevel(Level.OFF);
		int optionsUrl=1; //1; 2;3;4; 5;
		String url = null;
		String browser="Browser Safari Version 11.0";
		switch (optionsUrl) {
			case 1: url="http://alex.academy/exe/payment_tax/index.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/index.html\"");
				break;
			case 2: url="http://alex.academy/exe/payment_tax/index2.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/index2.html\"");
				break;
			case 3: url="http://alex.academy/exe/payment_tax/index3.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/index3.html\"");
				break;
			case 4: url="http://alex.academy/exe/payment_tax/index4.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/index4.html\"");
				break;
			case 5: url="http://alex.academy/exe/payment_tax/index5.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/index5.html\"");
				break;
			case 6: url="http://alex.academy/exe/payment_tax/indexE.html";
				System.out.println(browser+"    output for url=\"http://alex.academy/exe/payment_tax/indexE.html\"");
				break;
		}
		if (!System.getProperty("os.name").contains("Mac")) throw new IllegalArgumentException("Safari is available only on Mac");

		WebDriver driver = new SafariDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get(url);
		String string_monthly_payment_and_tax = driver.findElement(By.id("id_monthly_payment_and_tax")).getText();

	    String regex = ""
							+ "(?:\\D*(\\d+\\.\\d{0,})),*"
							+ "(?:\\D+(\\d+\\.\\d{0,})%*)"
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
	    		           + ""
				       + "";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string_monthly_payment_and_tax); m.find();

		double monthly_payment = Double.parseDouble(m.group(1));
		double tax = Double.parseDouble(m.group(2));
		// (91.21 * 8.25) / 100 = 7.524825
		double monthly_and_tax_amount = new BigDecimal((monthly_payment * tax) / 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
		// 91.21 + 7.52 = 98.72999999999999
		double monthly_payment_with_tax = new BigDecimal(monthly_payment + monthly_and_tax_amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double annual_payment_with_tax = new BigDecimal(monthly_payment_with_tax * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		driver.findElement(By.id("id_annual_payment_with_tax")).sendKeys(String.valueOf(annual_payment_with_tax));
		
		WebElement settings = driver.findElement(By.id("id_validate_button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", settings);
		
		String actual_result = driver.findElement(By.id("id_result")).getText();
		System.out.println("String: \t" + string_monthly_payment_and_tax);
		System.out.println("Annual Payment with Tax: " + annual_payment_with_tax);
		System.out.println("Result: \t" + actual_result);
		driver.quit();
	}
}