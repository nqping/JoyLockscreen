package pages;

import bases.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.CacheLookup;

/**
 * Created by qingping.niu on 2017/11/9.
 */
public class FeedbackPage extends BasePage {

    public FeedbackPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "text(\"Feedback\")")
    public AndroidElement feedback;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/submit")
    public AndroidElement submit;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/feedback_content")
    public AndroidElement content;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/contact_information")
    public AndroidElement email;

}
