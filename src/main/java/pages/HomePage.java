package pages;

import bases.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Joy 首页所有UI元素
 * Created by qingping.niu on 2017/7/5.
 */
public class HomePage extends BasePage{

    /**
     * 构造方法中实例化类
     * @param driver
     */
    public HomePage(AndroidDriver driver){
        super(driver);

//        super(driver);
        //PageFactory.initElements(driver,this);

       // PageFactory.initElements(new AppiumFieldDecorator(driver),this);

       // PageFactory.initElements(new AppiumFieldDecorator(driver,10,TimeUnit.SECONDS),this);

    }

    @AndroidFindBy(uiAutomator="text(\"Screen lock\")")
    //@WithTimeout(time = 2,unit = TimeUnit.MINUTES)
    @CacheLookup
    public AndroidElement screenLock;

    @AndroidFindBy(uiAutomator="text(\"Swipe\")")
    @CacheLookup
    public AndroidElement swipe;

    @AndroidFindBy(uiAutomator="text(\"Pattern\")")
    @CacheLookup
    public AndroidElement pattern;

    @AndroidFindBy(uiAutomator="text(\"PIN\")")
    @CacheLookup
    public AndroidElement pin;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/enable_phone_item_view")
    public AndroidElement callWithoutLock;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/enable_curtain_item_view")
    public AndroidElement windmillRope;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/iv_back")
    public AndroidElement backButton; //应用通知页面返回按钮







//    @FindBy(name="Select common apps")
//   // @CacheLookup
//    public AndroidElement selectCommonApps;
//
//    @FindBy(name="App notifications")
//    //@CacheLookup
//    public WebElement appNotifications;

//    @FindBy(name="Feedback")
//    //@CacheLookup
//    public WebElement feedback;
//
//    @FindBy(name = "About")
//   // @AndroidFindBy(uiAutomator="new UiSelector().scrollable(true).text(\"About\")")
//   // @CacheLookup
//    public WebElement about;

//    @FindBy(xpath = "//android.widget.Switch[1]")
//    //@CacheLookup
//    public WebElement callWithoutLock;
//
//    @FindBy(xpath = "//android.widget.Switch[2]")
//    //@CacheLookup
//    public WebElement  privacyProtection;
//
//    @FindBy(xpath = "//android.widget.Switch[3]")
//    //@CacheLookup
//    public WebElement enableJoyLockscreen;





}
