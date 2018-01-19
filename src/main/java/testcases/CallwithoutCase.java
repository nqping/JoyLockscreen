package testcases;

import bases.BaseTestCase;
import opeartion.GeneralSettingsOperate;
import org.jsoup.Connection;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.Assert.assertTrue;

/**
 * Created by qingping.niu on 2018/1/16.
 */
public class CallwithoutCase{
    GeneralSettingsOperate generalSettingsOperate = null;

    @BeforeClass
    public void setUp(){
        generalSettingsOperate = new GeneralSettingsOperate(BaseTestCase.driver);
    }

    @Test
    public void testDefaultCallWidthLockStatus(){
        boolean flag = generalSettingsOperate.defaultCallWidthLockStatus();
        assertTrue(flag,"Call width lock default status fail");
    }

    @Test(dependsOnMethods = {"testDefaultCallWidthLockStatus"},alwaysRun=true)
    public void testOpenCallWithoutLock(){
        boolean flag = generalSettingsOperate.openCallWithoutLock();
        assertTrue(flag,"Open telephone page fail ");
    }

    @Test(dependsOnMethods = {"testOpenCallWithoutLock"},alwaysRun = true)
    public void testCloseCallWithouLock(){
        boolean flag = generalSettingsOperate.closeCallWithouLock();
        assertTrue(flag,"Close telephone page fail ");
    }
}
