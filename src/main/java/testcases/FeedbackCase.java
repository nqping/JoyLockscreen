package testcases;

import bases.BaseTestCase;
import io.appium.java_client.android.AndroidElement;
import opeartion.FeedbackOperate;
import opeartion.NotificationsOperate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by qingping.niu on 2018/1/16.
 */
public class FeedbackCase extends BaseTestCase {
    FeedbackOperate feedbackOperate = null;
    @BeforeClass
    public void setUp() throws MalformedURLException {
        feedbackOperate = new FeedbackOperate(driver);
    }

    @Test
    public void testFeedback(){
        AndroidElement element = feedbackOperate.feedbackInfo();
        assertNotNull(element,"submit feedback info fail");
    }

    @Test
    public void testAbout(){
        boolean flag = feedbackOperate.swipeAbout();
        assertTrue(flag,"checked about fail");
    }
}
