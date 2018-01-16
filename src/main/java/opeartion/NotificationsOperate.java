package opeartion;

import bases.OperateBase;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import javafx.scene.input.KeyCode;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.NotificationsPage;
import utils.Constant;

import java.time.Duration;
import java.util.*;

/**
 * Created by qingping.niu on 2017/11/30.
 */
public class NotificationsOperate extends OperateBase {

    Logger logger = LoggerFactory.getLogger(NotificationsOperate.class);

    AndroidDriver driver;
    NotificationsPage notificationsPage;
    WebElement notificationView;
    List<WebElement> notificationList;
    int count = 0;
    String cleanBut = "com.tcl.joylockscreen:id/lock_notification_clean_button";//一键清除按钮
    String  notifiactionContainer = "com.tcl.joylockscreen:id/views_shared_infocardbaseview_container";//通知控件
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

//    /**
//     * 熄屏亮屏中间休眠2000
//     */
//    public void powerEvent(){
//        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
//        goSleep(2000);
//        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
//        goSleep(2000);
//    }

    /**
     * 获取通知相关重要控件数据

     */
    public void init(){
        //检查应用通知全选按钮是否"全选"
        if(driver.getPageSource().contains(cleanBut)){
            notificationView = driver.findElementById("com.tcl.joylockscreen:id/lock_notification_recycler_view");
            notificationList = notificationView.findElements(By.id(notifiactionContainer));
            count = notificationList.size();
            logger.info("notificationList.size初始大小:"+count);
        }
    }

    /**
     * 模块:应用通知
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
     * 模块:应用通知
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
     * 模块:应用通知
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
     * 模块:应用通知
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
     * 模块:应用通知
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
            swipe(width / 2, height * 3 / 4, width / 2, height / 4, 500);
            appList.clear();
        }while (!pageSource.contains(endstr));

        System.out.println(appMap.size());
//        logger.debug("appMap.size()=="+appMap.size());
//        for (String key : appMap.keySet()) {
//            System.out.println(key+"----"+appMap.get(key));
//        }
        return appMap;
    }

    /**
     * 清除锁屏页上所有通知
     * 1.判断锁屏页有没有 " 清除按钮",有继续执行用例,无表示没有通知
     * 2.清除通知
     * @return
     */
    public boolean cleanAllNotifications(){
        //锁屏页执行清理所有删除
       powerEvent();
        if(driver.getPageSource().contains(cleanBut)){
            logger.debug("clean button 存在有通知");
            notificationsPage.cleanButton.click();
            if(!driver.getPageSource().contains(cleanBut)){
                return true;
            }
        }else{
            return true;
        }
        return false;
    }


    /**
     * 锁屏页: 向左滑除通知
     */
    public boolean leftSlideDelete(){
        if(notificationList != null && notificationList.size() > 0){
            WebElement notification = notificationList.get(0);
            //获取控件自身width和height
            int width = notification.getSize().getWidth();
            int heigth = notification.getSize().getHeight();
            //获取控件坐标
            int x = notification.getLocation().getX();
            int y = notification.getLocation().getY();
            //计算滑动的起使位置和结束位置
            int startX = width/2+x;
            int endY = heigth/2 +y;
            int endX =width/4+startX;
            //滑除通知
            getTouch().press(notificationView,startX,endY).moveTo(notificationView,endX,endY).release().perform();
            //重新获取通知列表
            notificationList = notificationView.findElements(By.id(notifiactionContainer));
            System.out.println("--------------"+notificationList.size());
            if(notificationList.size() != count){
                count = notificationList.size();
                logger.info("右滑删除后notificationList大小:"+notificationList.size());
                logger.info("通知count计数器大小:"+count);
                return true;
            }

        }
        return false;
    }

    /**
     * 锁屏页: 向右滑除通知
     * @return
     */
    public boolean rightSildeDelete(){
        if(notificationList != null && notificationList.size()>0){
            WebElement notification = notificationList.get(0);
            //获取控件自身width和height
            int width = notification.getSize().getWidth();
            int heigth = notification.getSize().getHeight();
            //获取控件坐标
            int x = notification.getLocation().getX();
            int y = notification.getLocation().getY();
            //计算滑动的起使位置和结束位置
            int startX = width/2+x;
            int endY = heigth/2 +y;
            int endX =startX-width/4;
            //滑除通知
            getTouch().press(notificationView,startX,endY).moveTo(notificationView,-endX,endY).release().perform();
            //重新获取通知列表
            notificationList = notificationView.findElements(By.id(notifiactionContainer));
            if(notificationList.size() != count){
                count = notificationList.size();
                logger.info("右滑删除后notificationList大小:"+notificationList.size());
                logger.info("通知count计数器大小:"+count);
                return true;
            }
        }
        return false;
    }

//    public boolean testNotification(){
//        //锁屏页执行清理所有删除
//        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
//        goSleep(2000);
//        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
//        goSleep(2000);
//       WebElement cleanBut1 = driver.findElementById(cleanBut);
////       int y = cleanBut1.getLocation().getY();
////       int x = cleanBut1.getLocation().getX();
////       int height = cleanBut1.getSize().getHeight();
////       int width = cleanBut1.getSize().getWidth();
////       System.out.println(x+"x--y"+y);
//       //System.out.println(width+"width -- height:"+height);
//
//        WebElement notificationView = driver.findElementById("com.tcl.joylockscreen:id/lock_notification_recycler_view");
//        List<WebElement> notificationList = notificationView.findElements(By.id(notifiactionContainer));
//
//        WebElement notifiction = notificationList.get(0);
//
//        int width = notifiction.getSize().getWidth();
//        int heigth = notifiction.getSize().getHeight();
//        int x = notifiction.getLocation().getX();
//        int y = notifiction.getLocation().getY();
//        System.out.println(width+"width -- height:"+heigth);
//        System.out.println(x+"x--y"+y);
//
//        int startX = width/2+x; //控件的中间位置
//
//        int endY = heigth/2 +y;  //控件的中间部分
//        //getTouch().tap(notifiction).perform();
//        // getTouch().tap(notifiction,startX,endY).perform();  //指定点击位置
//        //getTouch().press(notifiction,startX,endY).release().perform(); //指定点击位置
//        System.out.println(startX +"startx = endY"+endY);
//        getTouch().press(notificationView,startX,endY).moveTo(notificationView,startX+100,endY).release().perform();
//
//
//
//        //new TouchAction(driver).longPress(notifiction).waitAction(Duration.ofSeconds(3)).perform();
//
//       // notifiction.click();
//       return true;
//    }

    /**
     * 锁屏页:长按通知 hide 操作
     * 1: 获取第一个通知并长按通知展开
     * 2: 点击 hide 按钮
     * 3: 重新获取通知列表,如果数量!=初始数量(count) 表示 hide成功
     * 4: 到应用通知功能中去确认, 全选按钮 未勾选表示用例成功,反之失败
     * @return
     */
    public boolean longPressHide(){
        if(notificationList != null && count>0){
            WebElement notification = notificationList.get(0);
            getTouch().longPress(notification).waitAction(Duration.ofSeconds(3)).perform();
            notificationsPage.hideButton.click();
            //重新获取通知列表
            notificationList = notificationView.findElements(By.className("android.widget.RelativeLayout"));
            if(notificationList.size() != count){
                count = notificationList.size();
                logger.info("hide后notificationList大小:"+notificationList.size());
                logger.info("通知count计数器大小:"+count);
                return true;
            }
        }
        return false;
    }

    /**
     *  锁屏页 通知左滑 操作 hide 功能
     *  1.从左向右滑动通知
     *  2.点击hide,展开选择项,选择 hide
     *  3.重新获取list大小,小于原计算器(count)表示成功
     * @return
     */
    public boolean leftSildeHide(){
        if(notificationList !=null && notificationList.size()>0){
            WebElement notification = notificationList.get(0);
            //获取控件自身width和height
            int width = notification.getSize().getWidth();
            int heigth = notification.getSize().getHeight();
            //获取控件坐标
            int x = notification.getLocation().getX();
            int y = notification.getLocation().getY();
            //计算滑动的起使位置和结束位置
            int startX = width/2+x;
            int endY = heigth/2 +y;
            getTouch().press(notificationView,startX,endY).moveTo(notificationView,startX-100,endY).release().perform();
            notificationsPage.hideDismissButton.click(); //右边按钮
            if(isElementExist(By.id("com.tcl.joylockscreen:id/lock_notification_shield"))){
                return true;
            }
        }
        return false;
    }

    /**
     * 锁屏页 通知右滑 hide 功能
     * 1.从右向左滑动通知
     * 2.点击hide,展开选择项,选择 hide
     * 3.重新获取list大小,小于原计算器(count)表示成功
     * @return
     */
    public boolean rightSildeHide(){
        if(notificationList !=null && notificationList.size()>0){
            WebElement notification = notificationList.get(0);
            //获取控件自身width和height
            int width = notification.getSize().getWidth();
            int heigth = notification.getSize().getHeight();
            //获取控件坐标
            int x = notification.getLocation().getX();
            int y = notification.getLocation().getY();
            //计算滑动的起使位置和结束位置
            int startX = width/2+x;
            int endY = heigth/2 +y;
            getTouch().press(notificationView,startX,endY).moveTo(notificationView,startX+100,endY).release().perform();
            notificationsPage.hideDetailButton.click(); //左边按钮
            if(isElementExist(By.id("com.tcl.joylockscreen:id/lock_notification_shield"))){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加通知
     */
    public void addNotification(){
        driver.startActivity(new Activity("com.slw.notification.demo","com.slw.notification.demo.MainActivity"));
        WebElement sendEl = driver.findElementById("com.slw.notification.demo:id/send");
        for(int i=0; i<5; i ++){
            sendEl.click();
        }
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
    }

    public void reopenNotificationSwitch(){
        notificationsPage.appNotifications.click();
        notificationsPage.appAllSelect.click();
        notificationsPage.backButton.click();
    }








}
