package opeartion;

import bases.OperateBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.WallpaperPage;

/**
 * Created by qingping.niu on 2017/11/8.
 */
public class WallpaperOperate extends OperateBase {
    public Logger logger = LoggerFactory.getLogger(WallpaperOperate.class);
    AndroidDriver driver;
    WallpaperPage wallpaperPage;
    public boolean flag = false;


    /**
     * 构造函数
     *
     * @param driver
     */
    public WallpaperOperate(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        wallpaperPage = new WallpaperPage(driver);
    }

    /**
     * 设置默认壁纸
     * @return
     */
    public boolean setDefaultWallpaper(){
        wallpaperPage.wallpager.click();//进入壁纸设置页
        wallpaperPage.defaultWallpaper.click(); //进入默认壁纸设置
        swipeToUp();
        AndroidElement element = findElementBy(By.xpath("//android.support.v7.widget.RecyclerView/android.widget.RelativeLayout[5]/android.widget.ImageView[1]"));
        element.click();
        wallpaperPage.saveButton.click();
        driver.pressKeyCode(AndroidKeyCode.KEYCODE_BACK);
        flag = wallpaperPage.defaultWallpaper.isDisplayed();
        return flag;
    }

    /**
     * 自定义壁纸
     * @return
     */
    public boolean setCustomWallpaper(){
        wallpaperPage.wallpager.click();
        wallpaperPage.customWallpaper.click();
        AndroidElement element = findElementBy(By.id("com.tct.gallery3d:id/gl_root_view"));
        element.click();
        wallpaperPage.saveButton.click();
        flag = wallpaperPage.defaultWallpaper.isDisplayed();
        return flag;
    }
}
