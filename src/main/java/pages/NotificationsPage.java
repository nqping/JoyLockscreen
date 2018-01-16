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
    public AndroidElement notificationSwitch; //应用通知开关

    @AndroidFindBy(id="com.tcl.joylockscreen:id/btn_app_select")
    public AndroidElement appAllSelect;  //应用通知页面的 全选框

    @AndroidFindBy(id="com.tcl.joylockscreen:id/fl_apppick_container")
    public AndroidElement appContainer; //应用通知页面的应用列表

    @AndroidFindBy(id="com.tcl.joylockscreen:id/iv_back")
    public AndroidElement backButton; //应用通知页面返回按钮

    @AndroidFindBy(id="com.tcl.joylockscreen:id/lock_notification_clean_button")
    public AndroidElement cleanButton; //锁屏页一键清除通知按钮

    @AndroidFindBy(id="com.tcl.joylockscreen:id/lock_notification_shield")
    public AndroidElement hideButton; //长按通知出现的 隐藏通知按钮

    @AndroidFindBy(id="com.tcl.joylockscreen:id/lock_notification_cancel")
    public AndroidElement cancelButton; //长按通知出现的 取消按钮

    @AndroidFindBy(id="com.tcl.joylockscreen:id/views_shared_infocardbaseview_detail_text")
    public AndroidElement hideDetailButton; //左边 隐藏按钮

    @AndroidFindBy(id="com.tcl.joylockscreen:id/views_shared_infocardbaseview_dismiss_text")
    public AndroidElement hideDismissButton; //右边隐藏按钮
}
