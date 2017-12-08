package pages;

import bases.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

/**
 * Created by qingping.niu on 2017/11/8.
 */
public class WallpaperPage extends BasePage{

    public WallpaperPage(AndroidDriver driver){
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "text(\"Set wallpaper\")")
    @CacheLookup
    public AndroidElement wallpager;

    @AndroidFindBy(uiAutomator="text(\"Default wallpaper\")")
    @CacheLookup
    public AndroidElement defaultWallpaper;

    @AndroidFindBy(uiAutomator="text(\"Custom wallpaper\")")
    @CacheLookup
    public AndroidElement customWallpaper;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/iv_back")
    @CacheLookup
    public AndroidElement backButton;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/save")
    @CacheLookup
    public AndroidElement saveButton;

}
