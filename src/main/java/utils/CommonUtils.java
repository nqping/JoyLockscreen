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

    /**
     *  截图并保存到指定路径
     * @param driver
     * @param fileName  文件保存的路径
     * @return
     */
    public static String getScreen(AndroidDriver driver ,String fileName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        //String picName = fileName + sdf.format(new Date()).toString()+".png";
        String picName = fileName+".png";
        File screen = driver.getScreenshotAs(OutputType.FILE);
        //File screenFile = new File(picName);
        try {
            FileUtils.copyFile(screen,new File(Constant.IMG_PATH+picName));
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

//    public static void unLockScreen(AndroidDriver driver, AndroidElement element,int ...num) throws Exception {
//        if(num.length < 4){
//            throw new Exception("The length of the array must be greater than 4");
//        }
//
//       // Point point = element.getLocation();
//        Rectangle rect = element.getRect();
//        int x0 = rect.x + (int)((double)138/816*rect.getWidth());
//        int y0 = rect.y + (int)((double)138/816*rect.getHeight());
//        int offset = (rect.getWidth()-2*(int)((double)138/816*rect.getWidth()))/2;//D 点与点之间距离
//        Point[] p =new Point[9];
//        for(int i=0; i<9; i++){
//            p[i] = new Point();
//            p[i].x = x0+(i+3)%3*offset;
//            int dy = i-3>0 ? ((i-3)<3 ? 1:2):0;
//            p[i].y = y0+dy*offset;
//            // System.out.println("i="+i+" x="+p[i].x+" y="+p[i].y);
//        }
//
//        Point[] segments = new Point[num.length];
//        for(int i=0;i<num.length; i++){
//            segments[i] = p[num[i]-1];
//        }
//        driver.swipe();
//
//
//    }
}
