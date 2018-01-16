package bases;


import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.Connection;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonUtils;
import utils.Constant;
import utils.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

/**
 * 操作处理父类
 * Created by qingping.niu on 2017/6/14.
 */
public class OperateBase{
    public static AndroidDriver driver;
    Logger logger = LoggerFactory.getLogger(OperateBase.class);

    //单个触摸操作

    TouchAction touchAction;

    //多个触摸操作
    MultiTouchAction multiTouchAction;

    /**
     * 获取触摸实例
     *
     * @return
     */
    public TouchAction getTouch() {
        if (driver == null) {
            print("单点触摸时候driver为空");
            return null;
        } else {
            if (touchAction == null) {
                return new TouchAction(driver);
            } else {
                return touchAction;
            }
        }
    }

    /**
     * 获取多点触摸实例
     *
     * @return
     */
    public MultiTouchAction getMultiTouch() {
        if (driver == null) {
            print("多点触摸时候driver为空");
            return null;
        } else {
            if (multiTouchAction == null) {
                return new MultiTouchAction(driver);
            } else {
                return multiTouchAction;
            }

        }
    }

    /**
     * 构造函数
     * @param driver
     */

    public OperateBase(AndroidDriver driver){
        this.driver = driver;
    }


    public boolean imgCompare(String define_name,String screen_shot_name){
        File f1 = new File("img/"+define_name+".png");
        BufferedImage img1 = ImageUtil.getImageFromFile(f1);
        File f2 = driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f2, new File("target/reports/screenshots/"+screen_shot_name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage img2 = ImageUtil.getImageFromFile(f2);
        Boolean same =ImageUtil.sameAs(img1, img2, 0.98);
        return same ;
    }

    /**
     * 图案解锁
     * @param num  图案锁数字1-9
     * @throws Exception
     * 适用于
     */
    public void drawLockPattern(int...num){
        try {
            if(num.length <4){
                throw new Exception("The length of the array must be greater than 4");
            }
            List<AndroidElement> pic = driver.findElements(By.xpath("//android.view.ViewGroup/android.widget.ImageView"));
            touchAction = new TouchAction(driver);
            touchAction.press(pic.get(num[0]));
            for(int i=1 ;i <num.length;i++){
                touchAction.moveTo(pic.get(num[i]));
            }
            touchAction.release().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * L 图案
     */
    public void drawLockPattern(){
        if(driver.getPageSource().contains("android.view.View")){
            WebElement element = driver.findElementById("com.android.systemui:id/lockPatternView");
            int startx = element.getLocation().getX();  //获取控件的左上角坐标
            int starty = element.getLocation().getY();
            int height = element.getSize().getHeight(); //获取控件的height、width
            int width = element.getSize().getWidth();
            int beginx = startx + width/6;
            int beginy = starty + height/6;  //起始点

            int xstep = width/3;  //每次移动x,y的相对距离
            int ystep = height/3;

            TouchAction action = new TouchAction(driver);
            action.press(beginx, beginy).moveTo(0, ystep).moveTo(0, ystep).moveTo(xstep, 0).moveTo(xstep, 0)
                    .release().perform();

        }
    }



    /**
     * Pin码解锁
     * @param num  密码
     * @throws Exception
     */
    public void drawLockPin(int...num){
        try {
            if(num.length <4){
                throw new Exception("The length of the array must be greater than 4");
            }
            List<AndroidElement> pin = driver.findElements(By.xpath("//android.view.ViewGroup/android.widget.ImageView"));
            System.out.println(pin.size());
            HashMap<Integer,AndroidElement> pinMap = new HashMap<Integer,AndroidElement>();
            pinMap.put(1,pin.get(0));
            pinMap.put(2,pin.get(2));
            pinMap.put(3,pin.get(3));
            pinMap.put(4,pin.get(1));
            pinMap.put(5,pin.get(4));
            pinMap.put(6,pin.get(7));
            pinMap.put(7,pin.get(5));
            pinMap.put(8,pin.get(8));
            pinMap.put(9,pin.get(9));
            pinMap.put(0,pin.get(6));

            for(int i = 0;i < num.length; i++){
                for (int pinKey: pinMap.keySet()) {
                    if(num[i] == pinKey){
                        pinMap.get(pinKey).click();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 name 查找某个元素
     * @param elementName
     * @return
     */
    public AndroidElement findElementByName(String elementName){
        try {
            if(driver != null){
                return (AndroidElement) driver.findElementByName(elementName);
            }
        } catch (NoSuchElementException e) {
            print("找不到控件:" +elementName+" 异常信息:"+ e.getMessage());
        }
        return null;
    }


    /**
     * 检查网络状态
     * @return
     */
    public boolean checkNetWorkStatus(){
        String network = driver.getConnection().toString().toUpperCase();
        return !network.contains(Connection.NONE.toString());
    }

    /**
     * 获取屏幕宽和高
     * @return 屏幕宽高数组
     * @param driver
     * @param s
     */
    public int[] getScreen(AndroidDriver driver, String s){
        int width = OperateBase.driver.manage().window().getSize().getWidth();
        int height = OperateBase.driver.manage().window().getSize().getHeight();
        return new int []{width,height};
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public int getScreenWidth(){
        return driver.manage().window().getSize().getWidth();
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public int getScreenHeight(){
        return driver.manage().window().getSize().getHeight();
    }
    /**
     * 打印消息
     * @param str  要打印的字符
     * @param <T>
     */
    public <T> void print(T str){
        if(!TextUtils.isEmpty(String.valueOf(str))){
            System.out.print(str);
        }else{
            System.out.println("Empty characters are output!");
        }
    }
    /**
     * 往控件中输入内容
     * @param element 目标控件element
     * @param str 要输入的内容
     */
    public void input(WebElement element,String str){
        if (element == null) {
            print("控件为空,输入内容失败:" + str);
        } else {
            element.sendKeys(str);
        }
    }

    /**
     * 逐字删除编辑框中的文字
     * @param driver
     * @param element 文本框架控件
     */
    public void clearText(AndroidDriver driver,WebElement element){
        int length = element.getText().length();
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END); //光标移到最后
        for (int i = 0; i < length ; i++) {
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_DEL);//追个删除(还可以使用 BACKSPACE)
        }
    }

    /**
     * 点击控件
     * @param element 控件
     * @param str 控件的文字描述，供错误时候输出
     * @return 返回是否存在控件
     */
    public boolean clickView(WebElement element, String str){
        if(element != null){
            element.click();
            return true;
        }else {
            print(str + " is null ,click error!");
        }
        return false;
    }

    /**
     * 由上向下滑动
     */
    public void swipeToDown(){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        doSwipe(width / 2, height / 4, width / 2, height * 3 / 4, 500);
    }

    /**
     *由下向上滑动
     */
    public void swipeToUp() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        doSwipe(width / 2, height * 3 / 4, width / 2, height / 4, 500);
    }

    /**
     * 从指定距离向上滑动
     */
    public void swipe(int startX,int startY,int endX,int endY,int duration) {
        doSwipe(startX, startY, endX, endY, duration);
    }


    /**
     * 从左向右滑动
     */
    public void swipeToRight(){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        doSwipe(width/4, height/2, width*3/4, height/2, 500);
    }

    /**
     * 从右向左滑动
     * @param duration
     */
    public void swipeToLeft(int duration){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        doSwipe(width*3/4, height/2, width/4, height/2, duration);
    }

    public void doSwipe(int startx, int starty, int endx, int endy, int duration){
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startx,starty).waitAction(Duration.ofMillis(duration)).moveTo(endx,endy).release();
        touchAction.perform();
    }

    public boolean swipeToDown(String str){
        boolean isSwipe = true;
        while (isSwipe){
            swipeToDown();
            goSleep(1000);
            String pageSource = driver.getPageSource();
            if(pageSource.contains(str)){
                isSwipe = false;
            }
        }
        return isSwipe;
    }

    /**
     *  休眠
     * @param seconds
     * @throws InterruptedException
     */
    public void goSleep(int seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 长按
     * @param id
     */
    public void longClick(String id){
        WebElement element = driver.findElementById(id);
        new TouchAction(driver).longPress(element).waitAction(Duration.ofSeconds(3)).perform();
    }

    public AndroidElement findElementBy(By by){
        AndroidElement element = null;

        try {
            element = (AndroidElement) driver.findElement(by);
            return element;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
           ex.printStackTrace();
        }
        return element;
    }

    /**
     * 判断控件是否存在并且是可用状态
     * @param uiautomator
     * @return
     */
    public static boolean isElementExistAndEnabled(String uiautomator){
        try {
            WebElement el = driver.findElementByAndroidUIAutomator(uiautomator);
            if(el != null && el.isEnabled())
                return true;
            return false;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    public static boolean isElementExist(String uiautomator){
        try {
            driver.findElementByAndroidUIAutomator(uiautomator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }
//    public static boolean isElementExist(final String uiautomator){
//        try {
//            new WebDriverWait(driver,10).until(new ExpectedCondition<WebElement>() {
//                @Override
//                public WebElement apply(WebDriver webDriver) {
//                    return driver.findElementByAndroidUIAutomator(uiautomator);
//                }
//            });
//            return true;
//        } catch (org.openqa.selenium.NoSuchElementException ex) {
//            return false;
//        }
//    }


    public static boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    public static void screenSrc(String name){
        CommonUtils.getScreen(driver,name);
    }

    /**
     * 查找Toast 消息
     * @param by
     * @param time 超时时间
     * @return
     */
    public AndroidElement findElementToast(By by,int time){
        AndroidElement element = (AndroidElement) new WebDriverWait(driver,time).
                until((ExpectedCondition)ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }

    /**
     *  显示等待
     * @param by
     * @param time
     */
    public AndroidElement autoWait(final By by,int time){
        AndroidElement element = null;
        try {
            element = new WebDriverWait(driver,time).until(new ExpectedCondition<AndroidElement>() {
                @Override
                public AndroidElement apply(WebDriver webDriver) {
                    return (AndroidElement) driver.findElement(by);
                }
            });
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return element;
    }

    /**
     * 长按控件并移动到目标地
     * @param origin_el 起点控件
     * @param destination_el 目标控件
     */
    public void moveTo(WebElement origin_el, WebElement destination_el)
    {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(origin_el).moveTo(destination_el).release().perform();
    }

    /**
     * 熄屏亮屏中间休眠2000
     */
    public void powerEvent(){
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
        goSleep(2000);
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
        goSleep(2000);
    }

    public void unLock(int lockType){
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
       switch (lockType){
           case 2:
               drawLockPin(Constant.PIN_NUMBER);
               break;
           case 3:
               drawLockPattern(Constant.LOCK_PATTERN);
               break;
           default:
               break;
       }
    }
}
