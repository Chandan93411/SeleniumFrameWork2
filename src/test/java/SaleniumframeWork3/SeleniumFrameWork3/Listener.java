package SaleniumframeWork3.SeleniumFrameWork3;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import SeleniumFrameWork2.Resources.ExtentReporterNg;
import SeleniumFrameWork2.TestComponent.BaseTest;

public class Listener extends BaseTest implements ITestListener {
	ExtentTest test;
	ExtentReports extent = ExtentReporterNg.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		
		test= extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); //Assign uniue thread to each test 
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		extentTest.get().log(Status.PASS, "Test Pass");
		
	}
		
	
	@Override
	public void onTestFailure(ITestResult result) {
		
		
		extentTest.get().fail(result.getThrowable());
		try {
		driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
				.get(result.getInstance());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	String filepath = null;
	try {
		filepath = getScreenshot(result.getMethod().getMethodName(), driver);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	extentTest.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
		//ScreenShot 
		
	}
	
	@Override
	public void onFinish(ITestContext contex) {
		
		extent.flush();		
	}

}
