package com.valeo.task.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener implements ITestListener
{

    // Extent Report Declarations
    private static ExtentReports extent = com.valeo.task.utilities.ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private String testReportFilePath;

    @Override
    public synchronized void onStart(ITestContext context)
    {
        System.out.println("Extent Reports Version 5 Test Suite started!");
    }

    @Override
    public synchronized void onFinish(ITestContext context)
    {
        System.out.println(("Extent Reports Version 5  Test Suite is ending!"));
        extent.flush();

    }

    @Override
    public synchronized void onTestStart(ITestResult result)
    {
        System.out.println((result.getMethod().getMethodName() + " started!"));

        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result)
    {
        System.out.println((result.getMethod().getMethodName() + " Passed!"));
        test.get().pass("Test Passed");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result)
    {
        System.out.println((result.getMethod().getMethodName() + " Failed!"));

        if (result.getThrowable() != null)
        {
            result.getThrowable().printStackTrace();
        }
        test.get().fail(result.getThrowable());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result)
    {
        System.out.println((result.getMethod().getMethodName() + " Skipped!"));
        if (result.getThrowable() != null)
        {
            result.getThrowable().printStackTrace();
        }
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result)
    {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

}