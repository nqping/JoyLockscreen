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
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);

    }

    /**
     * 从swipe设置Pattern 锁
     * @return
     */
    public boolean setPattern(int ...number){
        try {
            homePage.screenLock.click();//进入锁设置页面
            homePage.pattern.click();//进入Pattern绘制图案页
            drawLockPattern(number);//绘制图案
            drawLockPattern(number);//确认图案 0,1,2,5,8
            goSleep(1000);
            flag = validationLock(Constant.LOCK_PATTERN,number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *从Pattern切换到Pin
     */
    public boolean setPin(int [] patternNumber,int [] pinNumber){

        homePage.screenLock.click();
        drawLockPattern(patternNumber);//绘制确认图案
        homePage.pin.click(); //进入Pin码设置
        drawLockPin(pinNumber); //输入Pin码
        drawLockPin(pinNumber);//确认Pin码
        goSleep(1000);
        flag = validationLock(Constant.LOCK_PIN,pinNumber); //验证密码使用效果
        return flag;
    }

    /**
     * 验证密码效果
     * @param lockType
     * @param number
     */
    public boolean validationLock(int lockType,int...number){
        powerEvent();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        //上滑
        swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
        if(lockType == Constant.LOCK_PATTERN){
            drawLockPattern(number);//绘制图案
        }else if(lockType == Constant.LOCK_PIN){
            drawLockPin(number);//确认密码
        }
        goSleep(1000);
        if(driver.getPageSource().contains("com.tcl.joylockscreen:id/lockview_content")){//是否包含该id
            return false; //表示解锁失败
        }
        return true;
    }


}
