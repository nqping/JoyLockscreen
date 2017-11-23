package bases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import listener.AppiumEventListener;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by qingping.niu on 2017/11/17.
 */
public class BaseTestCase {
    public AndroidDriver driver;
    //public Logger logger = LoggerFactory.getLogger(BaseTestCase.class);

    @BeforeSuite
    public void beforeSuite() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName","a96cd66d"); //4LQGHAGI5HOBZLT4 40a29086   a96cd66d
        capabilities.setCapability("noReset", "true"); //不重启
        //设置安卓系统版本
        capabilities.setCapability("platformVersion", "6.0.1");
        //capabilities.setCapability("automationName","uiautomator2");//调用uiautomator2,获取toas
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability("unicodeKeyboard", "True"); //使用unicode 输入法
        //capabilities.setCapability("resetKeyboard", "True"); //重置输入法原有状态
        // capabilities.setCapability("newCommandTimeout", 30);
        capabilities.setCapability("appPackage", "com.tcl.joylockscreen");

        capabilities.setCapability("appActivity", "com.tcl.joylockscreen.settings.activity.SplashActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        //注册监听事件
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver,new AppiumEventListener(driver));
    }

    @AfterSuite
    public void afterSuite(){

    }

    @AfterTest
    public void afterTest() {
        //driver.quit();
    }

    @AfterClass
    public void afterClass(){
        //每一个用例完毕结束这次测试
        //driver.quit();
    }




}
