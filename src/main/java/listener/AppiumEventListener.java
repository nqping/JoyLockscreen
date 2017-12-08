package listener;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qingping.niu on 2017/11/8.
 */
public class AppiumEventListener implements AppiumWebDriverEventListener{
    Logger logger = LoggerFactory.getLogger(AppiumEventListener.class);
    AndroidDriver driver;

    public AppiumEventListener(AndroidDriver driver){
        this.driver = driver;
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void beforeAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeNavigateTo(String s, WebDriver webDriver) {

    }

    @Override
    public void afterNavigateTo(String s, WebDriver webDriver) {

    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {

    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        while (true){
            if(OperateBase.isElementExistAndEnabled("text(\"Allow\")")){
                driver.findElementByAndroidUIAutomator("text(\"Allow\")").click();
                logger.info("Allow click");

            }else if(OperateBase.isElementExistAndEnabled("text(\"Always\")") ){
                driver.findElementByAndroidUIAutomator(".text(\"Always\")").click();
                logger.info("Allow click");

            }else if(OperateBase.isElementExist("text(\"Photos\")") &&
                    OperateBase.isElementExist("text(\"Always\")")){
                driver.findElementByAndroidUIAutomator("text(\"Photos\")").click();
                driver.findElementByAndroidUIAutomator(".text(\"Always\")").click();
                logger.info("Photos==>Allow click");
            }else{
                break;
            }
        }
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    @Override
    public void beforeScript(String s, WebDriver webDriver) {

    }

    @Override
    public void afterScript(String s, WebDriver webDriver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver webDriver) {

    }
}
