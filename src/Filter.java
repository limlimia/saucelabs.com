import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Utilities.Constants;
import Utilities.ExcelUtils;

public class Filter {
    
    static ExcelUtils excelUtils = new ExcelUtils();
    static String excelFilePath =Constants.Path_TestData+Constants.File_TestData;

    public static void main(String[] args) throws Exception{
        WebDriver driver = new ChromeDriver();

        long start = System.currentTimeMillis();

        driver.get("https://www.saucedemo.com/");

        //calling the ExcelUtils class method to initialise the workbook and sheet
        excelUtils.setExcelFile(excelFilePath,"Filter");

        for(int i=1;i<=excelUtils.getRowCountInSheet();i++)
        {
            Thread.sleep(7);
            WebElement username = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/div[1]/input"));
            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/div[2]/input"));
            WebElement loginButton = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/input"));
        	//Enter the values read from Excel
        	
            username.sendKeys("standard_user");
            password.sendKeys("secret_sauce");
        	loginButton.click();

            Thread.sleep(500);

            Boolean confirm = driver.findElements(By.xpath("/html/body/div/div/div/div[1]/div[2]/span")).size() == 0;

            //check if confirmation message is displayed
            if (confirm==false) {
            	// if the message is displayed , write PASS in the excel sheet using the method of ExcelUtils
            	excelUtils.setCellValue(i,2,"PASS",excelFilePath);

                WebElement burgerButton = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[1]/div[1]/div/div[1]/div/button"));
                burgerButton.click();
                Thread.sleep(500);
                
                WebElement logout = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[1]/div[1]/div/div[2]/div[1]/nav/a[3]"));
                logout.click();

                Thread.sleep(500);

            }else {
                //if the message is not displayed , write FAIL in the excel sheet using the method of ExcelUtils
                excelUtils.setCellValue(i,2,"FAIL",excelFilePath);
                Thread.sleep(500);

                WebElement username2 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/div[1]/input"));
                WebElement password2 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/div[2]/input"));
                
                if(driver.findElement(By.xpath("/html/body/div/div/div[2]/div[1]/div/div/form/div[1]/input")).isDisplayed()){
                    String s = Keys.chord(Keys.CONTROL, "a");
                    username2.sendKeys(s);
                    username2.sendKeys(Keys.DELETE);
                    password2.sendKeys(s);
                    password2.sendKeys(Keys.DELETE);
                }else{
                    System.out.println("Ga muncul");
                }
                
                
            }
        }

        long finish = System.currentTimeMillis();
        long estimation = finish - start; 

        long timeConvert = TimeUnit.MILLISECONDS.toSeconds(estimation);

        System.out.println("Execution time - "+timeConvert +" seconds"); 
        driver.quit();
    }

}
