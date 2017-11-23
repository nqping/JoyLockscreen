package listener;

import bases.OperateBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by qingping.niu on 2017/11/13.
 */
public class TestngListener extends TestListenerAdapter{
    private Logger logger = LoggerFactory.getLogger(TestngListener.class);

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info("【" + tr.getName() + " Start】");
        //    extent=InitDriverCase.getextent();
        //    test= extent.startTest(tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        logger.info("【" + tr.getName() + " Failure】");
        takeScreenShot(tr);
        //    test.log(LogStatus.INFO,"TakesScreenshot ",test.addScreenCapture("../img/"+tr.getName()+".png"));
        //    test.log(LogStatus.FAIL, tr.getThrowable());
        //    extent.endTest(test);

    }

    public void takeScreenShot(ITestResult tr){
        OperateBase.screenSrc(tr.getName());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
       // takeScreenShot(tr);
        logger.info("【" + tr.getName() + " Skipped】");
        //    test.log(LogStatus.SKIP, "SKIP");
        //    extent.endTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        logger.info("【" + tr.getName() + " Success】");
        //    test.log(LogStatus.PASS, "Pass");
        //    extent.endTest(test);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }

}
