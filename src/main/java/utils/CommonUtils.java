package utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by qingping.niu on 2017/7/8.
 */
public class CommonUtils {


    public static void main(String []args){
        testDemo(1,2,3,4,5);
    }

    public static void testDemo(int...num){
        for(int i = 0; i< num.length;i++){
            System.out.print("**"+num[i]);
        }

    }
    public static void getScreen(AndroidDriver driver){
        try {
            File directory = new File(Constant.IMG_PATH);
            String screenPath = directory.getCanonicalPath()+"/";
            File file = new File(screenPath);
            if (!file.exists()){
                file.mkdirs();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String picName = screenPath + sdf.format(new Date()).toString()+".png";
            File screen = driver.getScreenshotAs(OutputType.FILE);
           // File screenFile = new File(picName);
            FileUtils.copyFile(screen,new File(picName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  截图并保存到指定路径
     * @param driver
     * @param fileName  文件保存的路径
     * @return
     */
    public static String getScreen(AndroidDriver driver ,String fileName){
        String picName = fileName+".png";
        try {
            File directory = new File(Constant.IMG_PATH);
            String screenPath = directory.getCanonicalPath()+"/";
            File file = new File(screenPath);
            if (!file.exists()){
                file.mkdirs();
            }
            File screen = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screen,new File(screenPath+picName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picName;
    }

    /**
     * 截图
     * @param driver
     * @param filename
     */
    public static void getScreen(TakesScreenshot driver, String filename){
        String cyrPatn=System.getProperty("user.dir");
        File scrfile=driver.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrfile, new File(cyrPatn+"\\img\\"+filename+".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("GetScreenshot Fail");
        }finally{
            System.out.println("GetScreenshot Successful"+cyrPatn+"\\img\\"+filename+".png");
        }

    }

}
