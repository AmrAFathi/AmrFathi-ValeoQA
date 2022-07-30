package com.valeo.task.utilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static String platform;
    public static String reportFileName =
            "API_ExecutionReport" + "_" + new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-ms").format(new Date())
                    + ".html";
    private static String macPath = System.getProperty("user.dir") + "/TestReport";
    private static String linuxPath = System.getProperty("user.dir") + "/TestReport";
    private static String windowsPath = System.getProperty("user.dir") + "\\TestReport";
    private static String macReportFileLoc = macPath + "/" + reportFileName;
    private static String linuxReportFileLoc = linuxPath + "/" + reportFileName;
    private static String winReportFileLoc = windowsPath + "\\" + reportFileName;

    public static String filePathAndName;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    // Create an extent report instance
    public static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        filePathAndName = getReportFileLocation(platform);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filePathAndName);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("API Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config()
                .setReportName("API Execution Report");
        htmlReporter.config().setTimelineEnabled(true);

        extent = new ExtentReports();
        extent.setSystemInfo("OS", getCurrentPlatform());

        extent.attachReporter(htmlReporter);


        return extent;
    }

    // Select the extent report file location based on platform
    private static String getReportFileLocation(String platform) {
        String reportFileLocation = null;
        switch (platform) {
            case "MAC":
                reportFileLocation = macReportFileLoc;
                createDirectoryPath(macPath);
                break;
            case "WINDOWS":
                reportFileLocation = winReportFileLoc;
                createDirectoryPath(windowsPath);
                break;
            case "LINUX":
                reportFileLocation = linuxReportFileLoc;
                createDirectoryPath(linuxPath);
                break;
            default:
                break;
        }
        return reportFileLocation;
    }

    // Create the report path if it does not exist
    private static void createDirectoryPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            testDirectory.mkdir();
        }
    }

    // Get current platform
    public static String getCurrentPlatform() {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                platform = "WINDOWS";
            } else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
                platform = "LINUX";
            } else if (operSys.contains("mac")) {
                platform = "MAC";
            }
        }
        return platform;
    }
}
