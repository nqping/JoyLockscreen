package bases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by qingping.niu on 2017/8/25.
 */
public class BasePage {

    public AndroidDriver driver;
    private final  int TIMEOUT = 10;

//    public BasePage(){
//
//    }

    public BasePage(AndroidDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver,5,TimeUnit.SECONDS),this);
    }
}
