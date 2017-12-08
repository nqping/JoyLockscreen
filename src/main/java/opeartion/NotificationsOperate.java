package opeartion;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.NotificationsPage;

import java.util.*;

/**
 * Created by qingping.niu on 2017/11/30.
 */
public class NotificationsOperate extends OperateBase {

    Logger logger = LoggerFactory.getLogger(NotificationsOperate.class);

    AndroidDriver driver;
    NotificationsPage notificationsPage;
    /**
     * 构造函数
     *
     * @param driver
     */
    public NotificationsOperate(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        notificationsPage = new NotificationsPage(driver);
    }

    /**
     * 打开和关闭应用通知总开关
     * @return
     */
    public boolean closeAppNotifications(){
        notificationsPage.appNotifications.click();

        String status = notificationsPage.notificationSwitch.getAttribute("text");
        if(status.equals("OFF")){//是OFF状态就打开应用总开关
            notificationsPage.notificationSwitch.click();
            if(notificationsPage.appContainer.isDisplayed()){
                return true;
            }
        }else{ //是ON 状态就关闭通知
            notificationsPage.notificationSwitch.click();
            if(!isElementExist(By.id("com.tcl.joylockscreen:id/fl_apppick_container"))){
                return true;
            }
        }
        return false;
    }

    /**
     * Choose apps 功能全选操作
     * @return
     */
    public boolean selectAllApp(){
        notificationsPage.appNotifications.click();
        String status = notificationsPage.notificationSwitch.getAttribute("text");
        if(status.equals("OFF")){//是OFF状态就打开应用总开关
            notificationsPage.notificationSwitch.click();
        }
        notificationsPage.appAllSelect.click(); //不管全选还是反选都点击一次
        if(isElementExist(By.id("com.tcl.joylockscreen:id/img_selected"))){
            return true;
        }
        return false;
    }

    /**
     * Choose apps 功能返选操作
     * @return
     */
    public boolean reverseSelectApp(String endstr){
        notificationsPage.appAllSelect.click(); //取消全选
        Map<String,Boolean> rsMap= getAppSelectStatus(endstr);
        for (String key : rsMap.keySet()) {
            if(rsMap.get(key) == true){ //map值有 true 时,表示 取消全选操作失败
                return false;
            }
        }
        notificationsPage.appAllSelect.click();
        notificationsPage.backButton.click();
        return true;
    }

    /**
     * 选中单个
     */
    public boolean cancelSelectStatus(){
        WebElement appContent = driver.findElementById("com.tcl.joylockscreen:id/allapp_recyleview");
        List<WebElement> appList = appContent.findElements(By.className("android.widget.LinearLayout"));
        WebElement li = appList.get(0);
        li.findElement(By.id("com.tcl.joylockscreen:id/img_grid_item_icon")).click();
        try{
            li.findElement(By.id("com.tcl.joylockscreen:id/img_selected"));
            return false;
        }catch(org.openqa.selenium.NoSuchElementException e){
            return true;
        }
    }

    /**
     * 全选/反选/单选某应用显示通知
     * @return
     */
    public boolean getDefaultStatus(String endstr){
        notificationsPage.appNotifications.click();
        String status = notificationsPage.notificationSwitch.getAttribute("text");
        if(status.equals("OFF")){//是OFF状态就打开应用通知总开关
            notificationsPage.notificationSwitch.click();
        }
        Map<String,Boolean> rsMap = getAppSelectStatus(endstr);
        for (String key : rsMap.keySet()) {
            if(rsMap.get(key) == false){ //map值有 false 时,表示 非全选
                return false;
            }
        }
        return true;
    }

    /**
     * 全选操作
     * @param endstr
     * @return
     */
    public boolean selectAll(String endstr){
        notificationsPage.appAllSelect.click();
        Map<String,Boolean> rsMap = getAppSelectStatus(endstr);
        for (String key : rsMap.keySet()) {
            if(rsMap.get(key) == false){ //map值有 false 时,表示 非全选
                return false;
            }
        }
        return true;
    }

    public Map getAppSelectStatus(String endstr){
        List<WebElement> appList = new ArrayList<WebElement>();
        HashMap<String,Boolean> appMap = new HashMap<String,Boolean>();
        WebElement appContent = driver.findElementById("com.tcl.joylockscreen:id/allapp_recyleview");
        int width = appContent.getSize().width;
        int height = appContent.getSize().height;
        String pageSource;
        boolean flag = false;
        String appname = null;
        do {
            pageSource = driver.getPageSource();
            appList = appContent.findElements(By.className("android.widget.LinearLayout"));
            for (int i = 0; i <appList.size() ; i++) {
                WebElement li = appList.get(i);
                try {
                    appname= li.findElement(By.id("com.tcl.joylockscreen:id/tv_grid_item_name")).getAttribute("text");
                    li.findElement(By.id("com.tcl.joylockscreen:id/img_selected"));
                    flag = true;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    flag =false;
                }
                if(!appMap.containsKey(appname)){
                    if(flag){
                        appMap.put(appname,true); //已选中
                    }else{
                        appMap.put(appname,false);
                    }
                }
            }
            swipeToUp(width / 2, height * 3 / 4, width / 2, height / 4, 500);
            appList.clear();
        }while (!pageSource.contains(endstr));

        System.out.println(appMap.size());
//        logger.debug("appMap.size()=="+appMap.size());
//        for (String key : appMap.keySet()) {
//            System.out.println(key+"----"+appMap.get(key));
//        }
        return appMap;
    }


}
