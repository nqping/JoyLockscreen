package testcases;

import bases.BaseTestCase;
import com.beust.jcommander.Parameter;
import io.appium.java_client.android.AndroidElement;
import opeartion.FeedbackOperate;
import opeartion.NotificationsOperate;
import opeartion.ScreenlockOperate;
import opeartion.WallpaperOperate;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


/**
 * Created by qingping.niu on 2017/6/14.
 */
public class ScreenlockTest extends BaseTestCase{
   // private AndroidDriver driver;
    public ScreenlockOperate screenlockOperate = null;
    public WallpaperOperate wallpaperOperate = null;
    public FeedbackOperate feedbackOperate = null;
    public NotificationsOperate notificationsOperate = null;

   @BeforeClass
    public void setUp() throws MalformedURLException {
        //实例化操作类
       wallpaperOperate = new WallpaperOperate(driver);
       screenlockOperate = new ScreenlockOperate(driver);
       feedbackOperate = new FeedbackOperate(driver);
       notificationsOperate = new NotificationsOperate(driver);
   }

   @Test(enabled = false)
    public void testModifyPassword() throws Exception {
       screenlockOperate.testSwipe();
      // Assert.assertEquals(flag,true,"success");
   }

    /**
     * 从swipe 切换到 Patthern
     */
   @Test
   public void testChangePattern(){
       boolean flag = screenlockOperate.setPattern();
       assertTrue(flag,"Pattern setting fail");
   }

    /**
     * 从Pattern切换到Pin
     */
   @Test
   public void testChangePin(){
       //从Pattern切换到Pin, 需要验证之前密码
       boolean flag = screenlockOperate.setPin();
       assertTrue(flag,"PIN setting fail");
   }
    /**
     * 默认壁纸
     */
   @Test(enabled  = false)
   public void testDefaultWallpaper(){
       boolean flag = wallpaperOperate.setDefaultWallpaper();
       assertTrue(flag,"setting default wallpaper fail");
   }

   @Test
   public void testCustomWallpaper(){
       boolean flag = wallpaperOperate.setCustomWallpaper();
       assertTrue(flag,"setting default customWallpaper fail");
   }

   @Test
    public void testFeedback(){
       AndroidElement element = feedbackOperate.feedbackInfo();
       assertNotNull(element,"submit feedback info fail");
   }

   @Test
   public void testAbout(){
        boolean flag = feedbackOperate.swipeAbout();
       assertTrue(flag,"checked about fail");
   }

    @Test
    public void testAppNotifiactions(){
        boolean flag = notificationsOperate.closeAppNotifications();
        assertTrue(flag,"close and open app notifications fail");
    }
   @Test(parameters = {"endstr"})
   public void testCheckDefaultStatus(String endstr){
       Reporter.log("Get the parameters: "+endstr);
       boolean flag = notificationsOperate.getDefaultStatus(endstr); //默认是全选
       assertTrue(flag,"selected all fail");
   }

    @Test(dependsOnMethods = {"testCheckDefaultStatus"})
    public void testCancelSelectApp(){
        boolean flag = notificationsOperate.cancelSelectStatus(); //取消单个应用
        assertTrue(flag,"cancel one app select status fail ");
    }

    @Test(parameters = {"endstr"},dependsOnMethods = {"testCancelSelectApp"},alwaysRun = true)
    public void testSelectAll(String endstr){
        Reporter.log("Get the parameters: "+endstr);
        boolean flag = notificationsOperate.selectAll(endstr); //全选应用
        assertTrue(flag,"selected all fail");
    }

    @Test(parameters = {"endstr"},dependsOnMethods = {"testSelectAll"},alwaysRun = true)
    public void testReverseSelectApp(String endstr){
        Reporter.log("Get the parameters: "+endstr);
        boolean flag = notificationsOperate.reverseSelectApp(endstr); //取消全选应用
        assertTrue(flag,"reverse selected all fail");
    }

















    //@Test(dependsOnMethods ={"testChooseAppSelectAll"})


}
