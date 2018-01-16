package testcases;

import bases.BaseTestCase;
import opeartion.GeneralSettingsOperate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by qingping.niu on 2018/1/16.
 */
public class WindmillRopeCase extends BaseTestCase {
    GeneralSettingsOperate generalSettingsOperate = null;

    @BeforeClass
    public void setUp(){
        generalSettingsOperate = new GeneralSettingsOperate(driver);
    }

    @Test
    public void testDefaultWindmillRope(){
        boolean flag = generalSettingsOperate.defaultWindmillRopeStauts();
        assertTrue(flag,"挂件初始状态不正确");
    }

    @Test(dependsOnMethods = {"testDefaultWindmillRope"},alwaysRun=true)
    public void testOpenWindmillRope(){
        boolean flag = generalSettingsOperate.openWindmillRope();
        assertTrue(flag,"挂件打开失败");
    }

    @Test(dependsOnMethods = {"testOpenWindmillRope"},alwaysRun=true)
    public void testCloseWindmillRope(){
        boolean flag = generalSettingsOperate.closeWindmillRope();
        assertTrue(flag,"挂件关闭失败 ");
    }
}
