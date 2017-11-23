package opeartion;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import utils.Constant;

/**
 * Created by qingping.niu on 2017/7/4.
 */
public class ScreenlockOperate extends OperateBase {

    private AndroidDriver driver;
    private HomePage homePage;
    public boolean flag = false;
    Logger logger = LoggerFactory.getLogger(ScreenlockOperate.class);


    /**
     * 构造方法
     * @param driver
     */
    public ScreenlockOperate(AndroidDriver driver){
        super(driver);
        this.driver = driver;
        homePage = new HomePage(driver);
    }

    public void testSwipe() throws InterruptedException {
       // homePage.screenLock.click();

        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
        goSleep(2000);
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER);//电源事件,亮屏
        goSleep(2000);
        swipeToUp(2,7,2,8);
    }

    /**
     * 从swipe设置Pattern 锁
     * @return
     */
    public boolean setPattern(){
        try {
            homePage.screenLock.click();//进入锁设置页面
            homePage.pattern.click();//进入Pattern绘制图案页
            drawLockPattern(0,1,2,5,8);//绘制图案
            drawLockPattern(0,1,2,5,8);//确认图案
            goSleep(1000);
            flag = validationLock(Constant.LOCK_PATTERN,0,1,2,5,8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *从Pattern切换到Pin
     */
    public boolean setPin(){
        try {
            homePage.screenLock.click();
            drawLockPattern(0,1,2,5,8);//绘制确认图案
            homePage.pin.click(); //进入Pin码设置
            drawLockPin(1,2,3,6,9); //输入Pin码
            drawLockPin(1,2,3,6,9);//确认Pin码
            goSleep(1000);
            flag = validationLock(Constant.LOCK_PIN,1,2,3,6,9); //验证密码使用效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 验证密码效果
     * @param lockType
     * @param mnuber
     */
    public boolean validationLock(int lockType,int...mnuber){
        try {
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(1000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER);//电源事件,亮屏
            goSleep(1000);
            swipeToUp(2,7,2,8,500);
            if(lockType == Constant.LOCK_PATTERN){
                drawLockPattern(0,1,2,5,8);//绘制图案
            }else if(lockType == Constant.LOCK_PIN){
                drawLockPin(1,2,3,6,9);//确认密码
            }
            goSleep(2000);
            if(driver.getPageSource().contains("com.tcl.joylockscreen:id/unlockview")){//是否包含该id
                return false; //表示解锁失败
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
