package driverFactory;

import org.testng.annotations.Test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commenFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil{
String inputpath=".//DDF_Framework/FileInput.xlsx"; //FileInput>propertie>copypath
String outputpath="./FileOutput/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;

@Test
public void startTest() throws Throwable
{ 
	//define path for html
	report=new ExtentReports("./target/Reports/DataDriven.html");
	//create obj for excell util class
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	int rc =xl.rowCount("Login");
	Reporter.log("no of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger=report.startTest("validateLogin");
		
	String user=xl.getCellData("Login",i,0);
	String pass=xl.getCellData("Login",i,1);
	
	//call admineloginmethod from functionality class
	boolean res=FunctionLibrary.adminLogin(user, pass);
	if(res)
{
		    //write login success into result cell
		xl.setCellData("Login", i, 2, "Login success", outputpath);
		//write as pass into status cell
		xl.setCellData("Login", i, 3, pass, outputpath);
		logger.log(LogStatus.PASS,"valid username and password");
}
	else
	{// take screenshot and store
		File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//copy screenshot into local system
		FileUtils.copyFile(screen,new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
		//write as login fail into results cell
		xl.setCellData("Login", i, 2, "Login fail", outputpath);
		//write as fail into status cell
				xl.setCellData("Login", i, 3,"fail", outputpath);
	logger.log(LogStatus.FAIL,"invalid username and password");
	}
	report.endTest(logger);
	report.flush();
	}
}
}
