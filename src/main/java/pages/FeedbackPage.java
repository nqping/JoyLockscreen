package pages;

import bases.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by qingping.niu on 2017/11/9.
 */
public class FeedbackPage extends BasePage{

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
    public MobileElement email;

    @AndroidFindBy(uiAutomator = "text(\"About\")")
    public MobileElement about;

    @AndroidFindBy(uiAutomator = "text(\"Terms of Service\")")
    public MobileElement termsOfService;

    @AndroidFindBy(uiAutomator = "text(\"Privacy Policy\")")
    public MobileElement privacyPolicy;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/iv_back")
    public MobileElement backButton;

}
