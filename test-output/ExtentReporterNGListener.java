package listener;

import com.relevantcodes.extentreports.*;
import org.springframework.ui.context.Theme;
import org.testng.*;
import org.testng.IReporter;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qingping.niu on 2017/11/10.
 */
public class ExtentReporterNGListener implements IReporter {
    private ExtentReports extent;
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "Extent.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // true为覆盖已经生成的报告
        extent = new ExtentReports(outputDirectory + File.separator + "Extent.html", true  // true为覆盖已经生成的报告，false 在已有的报告上面生成，不会覆盖旧的结果
                ,DisplayOrder.NEWEST_FIRST ,// 最新运行的用例结果在第一个
                NetworkMode.OFFLINE
        );
        extent.startReporter(ReporterType.DB, outputDirectory + File.separator + "Extent.html"); //生成本地的DB数据文件
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }

        extent.flush();
        extent.close();
    }

//    private void init() {
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
//        htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
//        htmlReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
//        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
//        htmlReporter.config().setTheme(Theme.STANDARD);
//
//        extent = new ExtentReports();
//        extent.attachReporter(htmlReporter);
//        extent.setReportUsesManualConfiguration(true);
//    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName());

                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    test.log(status, test.addScreenCapture("../"+result.getMethod().getMethodName()+".png"));
                    test.log(status, result.getThrowable());

                }
                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                extent.endTest(test);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }


}
