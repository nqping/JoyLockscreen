package opeartion;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.HomePage;
import utils.Constant;

/**
 * Created by qingping.niu on 2017/12/8.
 */
public class GeneralSettingsOperate extends OperateBase {

    Logger logger = LoggerFactory.getLogger(GeneralSettingsOperate.class);
    private AndroidDriver driver;
    private HomePage homePage;

    /**
     * 构造函数
     * @param driver
     */
    public GeneralSettingsOperate(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        homePage = new HomePage(driver);
    }

    /**
     * 检查 Call Without lock 默认状态 (默认关闭状态)
     * 步骤一:进入joy首页验证 Call Without Lock 初始状态是否为 false,反之失败
     * 步骤二:在锁屏页滑动电话图标,出现解锁页用例失败,反之失败
     * 步骤三:正常解锁后,是否显示拨号页面,反之失败
     * 步骤四:点击系统返回键退出页面,回到Joy首页
     * @return
     */
    public boolean defaultCallWidthLockStatus(){
        WebElement callSwitch = homePage.callWithoutLock.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        String checked = callSwitch.getAttribute("checked");
        if("false".equals(checked)){
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(1000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(2000);
            swipe(20, 1164, 20, 600, 1000); //滑动电话图标
            if(driver.getPageSource().contains("com.tcl.joylockscreen:id/lockview_content")){
                logger.info("Call Withou Lock 功能默认状态是关闭状态(正确)");
                drawLockPin(Constant.PIN_NUMBER);
                if(driver.findElementById("com.android.dialer:id/floating_action_button").isDisplayed()){
                    driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     *
     * 打开 Call Without Lock
     * 前置条件:必须是密码锁,且是 关闭 状态
     * 步骤一:打开Call Without Lock,checked==true表示打开,反之用例失败
     * 步骤二:锁屏页滑动电话图标不需要解锁出现拨号界面表示成功,反之失败
     * 步骤三:最后按系统返回键回到锁屏页,执行正常解锁操作(该步骤主要还原环境,为其它用例提供正常环境)
     * @return
     */
    public boolean openCallWithoutLock(){
        WebElement callSwitch = homePage.callWithoutLock.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        callSwitch.click();
        String checked = callSwitch.getAttribute("checked");
        if("true".equals(checked)) {//true表示打开
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(1000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(2000);
            swipe(20, 1164, 20, 600, 1000); //滑动锁屏页电话图标
            if(driver.getPageSource().contains("com.android.dialer:id/floating_action_button")){
                logger.info("Call Without Lock功能打开成功");
                driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
                goSleep(2000);
                //以下操作是还原环境
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
                drawLockPin(Constant.PIN_NUMBER);
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭 Call Withou Lock功能
     * 前置条件: 打开 状态
     * 步骤一:点击关闭 Call Withou Lock功能
     * 步骤二:再锁屏页验证滑动电话图标是否出现解锁页,出现解锁页表示用例成功,反之失败
     * 步骤三:解锁后验证电话界面是否被成功调用,出现电话图标表示调用成功,反之失败
     * 步骤四:最后按系统返回键回到Joy首页(还原成初始环境)
     * @return
     */
    public boolean closeCallWithouLock(){
        WebElement callSwitch = homePage.callWithoutLock.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        callSwitch.click();
        String checked = callSwitch.getAttribute("checked");
        if("false".equals(checked)){ //关闭状态
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(1000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(2000);
            int width = driver.manage().window().getSize().width;
            int height = driver.manage().window().getSize().height;
            swipe(20, height-20, 20, width * 3/4, 1000); //滑动电话图标
            //swipe(20, 1164, 20, 600, 1000); //滑动电话图标
            if(driver.getPageSource().contains("com.tcl.joylockscreen:id/lockview_content")){ //出现解锁页表示正确
                logger.info("Call Without Lock 功能关闭成功");
                drawLockPin(Constant.PIN_NUMBER); //解锁
                if(driver.findElementById("com.android.dialer:id/floating_action_button").isDisplayed()){ //检查拨号图标是否存在
                    driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查挂件开关初始状态 (默认是关闭状态)
     * 1: 获取挂件开关初始状态, checked==false表示成功,反之失败
     * 2: 锁屏页验证挂件是否出现,否表示成功,反之失败
     * 3: 正常解锁(作用:执行下个用例)
     * @return
     */
    public boolean defaultWindmillRopeStauts(){
        WebElement windmillRepoEl = homePage.windmillRope.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        String checked = windmillRepoEl.getAttribute("checked");
        if("false".equals(checked)){
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(2000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(2000);
            if(!driver.getPageSource().contains("com.tcl.joylockscreen:id/ropeview")){
                logger.debug("挂件初始状态正确");
                //以下操作是还原环境
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
                drawLockPin(Constant.PIN_NUMBER);
                return true;
            }
        }
        return false;
    }

    /**
     * 打开挂件
     * 前置条件: 挂件开关是 "关闭"状态
     * 1: 打开挂件开关
     * 2: 在锁屏页上检查挂件是否出现,出现用例成功,反之失败
     * 3: 正常解锁
     * @return
     */
    public boolean openWindmillRope(){
        WebElement windmillRepoEl = homePage.windmillRope.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        windmillRepoEl.click(); //打开挂件
        String checked = windmillRepoEl.getAttribute("checked"); //获取挂件当前状态
        if("true".equals(checked)){
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(2000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(30000);
            if(driver.getPageSource().contains("com.tcl.joylockscreen:id/ropeview")){
                logger.info("挂件打开成功!");
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
                drawLockPin(Constant.PIN_NUMBER);
               return true;
            }
        }
        return false;
    }

    /**
     * 关闭挂件
     * 前置条件: 挂件开关已 "打开"状态
     * 1: 关闭挂件
     * 2: 在锁屏页上检查挂件是否显示, 不显示用例成功,反之失败
     * 3: 正常解锁
     * @return
     */
    public boolean closeWindmillRope(){
        WebElement windmillRepoEl = homePage.windmillRope.findElementById("com.tcl.joylockscreen:id/rl_root").
                findElementByClassName("android.widget.Switch");
        windmillRepoEl.click(); //打开挂件
        String checked = windmillRepoEl.getAttribute("checked"); //获取挂件状态
        if("false".equals(checked)){
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,熄屏
            goSleep(2000);
            driver.pressKeyCode(AndroidKeyCode.KEYCODE_POWER); //电源事件,亮屏
            goSleep(2000);
            if(!driver.getPageSource().contains("com.tcl.joylockscreen:id/ropeview")){
                logger.info("挂件关闭成功");
                //以下操作是还原环境
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                swipe(width / 2, height * 7 / 8, width / 2, height / 8, 500);
                drawLockPin(Constant.PIN_NUMBER);
                return true;
            }
        }
        return false;
    }
}
