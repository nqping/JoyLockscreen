package testcases;

import bases.BaseTestCase;
import opeartion.NotificationsOperate;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Constant;

import java.net.MalformedURLException;

import static org.testng.Assert.assertTrue;

/**
 * Created by qingping.niu on 2018/1/11.
 */
public class NotificationCase extends BaseTestCase{
    public NotificationsOperate notificationsOperate = null;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        notificationsOperate = new NotificationsOperate(driver);
    }

    //***************锁屏页 删除操作**************************************************//

    @Test
    public void testCleanAllNotifications(){
        boolean flag = notificationsOperate.cleanAllNotifications();
        notificationsOperate.unLock(Constant.LOCK_PIN);
        notificationsOperate.addNotification();
        assertTrue(flag,"清除所有通知失败 ");
    }

    @Test
    public void testLeftSlideDelete(){
        notificationsOperate.powerEvent();
        notificationsOperate.init();
        boolean flag = notificationsOperate.leftSlideDelete();
        assertTrue(flag,"左滑删除通知失败 ");
    }

    @Test
    public void testRigthSlideDelete(){
        boolean flag = notificationsOperate.rightSildeDelete();
        assertTrue(flag,"右滑删除通知失败 ");
    }

    //***************锁屏页 隐藏通知**************************************************//


    @Test
    public void testLeftSildeHide(){
        boolean flag = notificationsOperate.leftSildeHide();
        assertTrue(flag,"左滑隐藏通知失败 ");
    }

    @Test
    public void testRightSildeHide(){
        boolean flag = notificationsOperate.rightSildeHide();
        assertTrue(flag,"右滑隐藏通知失败 ");
    }

    @Test
    public void testLongPressHide(){
        boolean flag = notificationsOperate.longPressHide();
        notificationsOperate.unLock(Constant.LOCK_PIN);
        notificationsOperate.reopenNotificationSwitch();
        notificationsOperate.addNotification();
        assertTrue(flag,"长按隐藏通知失败 ");
    }

    //***************应用通知*********************************************************//
    @Test
    public void testAppNotifiactions(){
        boolean flag = notificationsOperate.closeAppNotifications();
        assertTrue(flag,"关闭和打开通知开关失败");
    }
    @Test(parameters = {"endstr"})
    public void testCheckDefaultStatus(String endstr){
        Reporter.log("Get the parameters: "+endstr);
        boolean flag = notificationsOperate.getDefaultStatus(endstr); //默认是全选
        assertTrue(flag,"应用通知->检查默认全选状态失败");
    }

    @Test(dependsOnMethods = {"testCheckDefaultStatus"})
    public void testCancelSelectApp(){
        boolean flag = notificationsOperate.cancelSelectStatus(); //取消单个应用
        assertTrue(flag,"应用通知->取消单个应用勾选状态失败 ");
    }

    @Test(parameters = {"endstr"},dependsOnMethods = {"testCancelSelectApp"},alwaysRun = true)
    public void testSelectAll(String endstr){
        Reporter.log("Get the parameters: "+endstr);
        boolean flag = notificationsOperate.selectAll(endstr); //全选应用
        assertTrue(flag,"应用通知->全选应用失败");
    }

    @Test(parameters = {"endstr"},dependsOnMethods = {"testSelectAll"},alwaysRun = true)
    public void testReverseSelectApp(String endstr){
        Reporter.log("Get the parameters: "+endstr);
        boolean flag = notificationsOperate.reverseSelectApp(endstr); //取消全选应用
        assertTrue(flag,"应用通知->返选失败");
    }
}
