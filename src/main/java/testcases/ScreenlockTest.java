package testcases;

import bases.BaseTestCase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import listener.AppiumEventListener;
import opeartion.FeedbackOperate;
import opeartion.ScreenlockOperate;
import opeartion.WallpaperOperate;
import org.jsoup.Connection;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

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

   @BeforeClass
    public void setUp() throws MalformedURLException {
        //实例化操作类
       wallpaperOperate = new WallpaperOperate(driver);
       screenlockOperate = new ScreenlockOperate(driver);
       feedbackOperate = new FeedbackOperate(driver);
   }

   @Test
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
   @Test
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
}
