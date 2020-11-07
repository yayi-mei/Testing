package testing_experiment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class SetRGB {
    public static BufferedImage setRGB(BufferedImage image, int xiangsu_x, int xiangsu_y,
                                       int new_red, int new_green, int new_blue, int new_ar) throws Exception{
        //输入要修改颜色代表的像素点，以及新制定颜色的RGB值
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        //获取目标像素点的rgb值
        int background = image.getRGB(xiangsu_x, xiangsu_y);
        int new_argb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        for(int i = 0; i < image.getWidth(); ++i)//把原图片的内容复制到新的图片，同时把背景设为透明
        {
            for(int j = 0; j < image.getHeight(); ++j)
            {
                if(image.getRGB(i, j) == background)
                    newImage.setRGB(i, j, new_argb);//这里设置成指定的颜色
                else
                    newImage.setRGB(i, j, image.getRGB(i, j));
            }
        }
        return newImage;
    }
}
