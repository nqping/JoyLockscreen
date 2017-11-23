package opeartion;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.FeedbackPage;

/**
 * Created by qingping.niu on 2017/11/9.
 */
public class FeedbackOperate extends OperateBase {
    public Logger logger = LoggerFactory.getLogger(WallpaperOperate.class);
    AndroidDriver driver;
    AndroidElement element = null;
    FeedbackPage feedbackPage;

    public boolean flag = false;
    /**
     * 构造函数
     *
     * @param driver
     */
    public FeedbackOperate(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        feedbackPage = new FeedbackPage(driver);
    }

    /**
     * 提交反馈
     */
    public AndroidElement feedbackInfo(){
        try {
            swipeToUp();
            feedbackPage.feedback.click();
            feedbackPage.content.sendKeys("test 123456 AAAD 测试使用!");
            goSleep(1000);
            feedbackPage.submit.click();
           element = findElementToast(By.xpath(".//*[contains(@text,\"Thanks for your feedback\")]"),1000);
            return element;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return element;
    }
}
