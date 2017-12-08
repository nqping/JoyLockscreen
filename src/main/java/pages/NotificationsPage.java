package pages;

import bases.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Created by qingping.niu on 2017/11/30.
 */
public class NotificationsPage extends BasePage{

    public NotificationsPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(uiAutomator = "text(\"App notifications\")")
    public AndroidElement appNotifications;
//OFF ON
    @AndroidFindBy(id="com.tcl.joylockscreen:id/switch_open_notification")
    public AndroidElement notificationSwitch;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/btn_app_select")
    public AndroidElement appAllSelect;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/fl_apppick_container")
    public AndroidElement appContainer;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/iv_back")
    public AndroidElement backButton;

    @AndroidFindBy(id="com.tcl.joylockscreen:id/img_selected")
    public AndroidElement imgSelected;
}
