package testcases;

import bases.BaseTestCase;
import opeartion.WallpaperOperate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by qingping.niu on 2018/1/16.
 */
public class WallpaperCase extends BaseTestCase {
    WallpaperOperate wallpaperOperate = null;

    @BeforeClass
    public void setUp(){
        wallpaperOperate = new WallpaperOperate(driver);
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
}
