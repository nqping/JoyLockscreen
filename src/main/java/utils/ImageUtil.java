package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by qingping.niu on 2017/12/14.
 */
public class ImageUtil {

    /**
     * 获取本地已保存文件
     * @param f
     * @return
     */
    public static BufferedImage getImageFromFile(File f){
        BufferedImage img = null;
        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return img;
    }

    /**
     * 图片对比
     * @param myImage
     * @param otherImage
     * @param percent
     * @return
     */
    public static boolean sameAs(BufferedImage myImage, BufferedImage otherImage, double percent){
        if(otherImage.getWidth() != myImage.getWidth()){
            return false;
        }
        if(otherImage.getHeight() != myImage.getHeight()){
            return false;
        }
        int width = myImage.getWidth();
        int height = myImage.getHeight();
        int numDiffPixels = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x <width; x++){
                if(myImage.getRGB(x,y) != otherImage.getRGB(x,y)){
                    numDiffPixels++;
                }
            }
        }
        double numberPixels = height * width;
        double diffPercent = numDiffPixels / numberPixels;
        return percent <= 1.0D - diffPercent;

    }

    public static BufferedImage getSubImage(BufferedImage image,int x, int y, int w, int h)
    {
        return image.getSubimage(x, y, w, h);
    }

    /**
     * 比对结果
     * @param define_name
     * @param screen_shot_name
     * @return
     */
    public static boolean compare(String define_name,String screen_shot_name){
        File f1 = new File("imag/"+ define_name+".png");
        BufferedImage img1 = ImageUtil.getImageFromFile(f1);
        File f2 = new File("target/screenshots/"+screen_shot_name+".png\"");
        BufferedImage img2 = ImageUtil.getImageFromFile(f2);
        boolean same = ImageUtil.sameAs(img1,img2,0.98);
        return same;
    }

}
