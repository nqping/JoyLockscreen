package testcases;

import bases.BaseTestCase;
import com.beust.jcommander.Parameter;
import io.appium.java_client.android.AndroidElement;
import opeartion.*;
import org.testng.Reporter;
import org.testng.annotations.*;
import utils.Constant;

import java.net.MalformedURLException;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


/**
 * Created by qingping.niu on 2017/6/14.
 */
public class ScreenlockTest extends BaseTestCase{
    public ScreenlockOperate screenlockOperate = null;

   @BeforeClass
    public void setUp() throws MalformedURLException {
        //实例化操作类
       screenlockOperate = new ScreenlockOperate(driver);
   }

    /**
     * 从swipe 切换到 Patthern
     */
   @Test
   public void testChangePattern(){
       boolean flag = screenlockOperate.setPattern(Constant.PATTERN_NUMBER);
       assertTrue(flag,"Pattern setting fail");
   }

    /**
     * 从Pattern切换到Pin
     */
   @Test
   public void testChangePin(){
       //从Pattern切换到Pin, 需要验证之前密码
       boolean flag = screenlockOperate.setPin(Constant.PATTERN_NUMBER,Constant.PIN_NUMBER);
       assertTrue(flag,"PIN setting fail");
   }


























    //@Test(dependsOnMethods ={"testChooseAppSelectAll"})


}
