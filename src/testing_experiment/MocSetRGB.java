package testing_experiment;

import java.awt.image.BufferedImage;

public class MocSetRGB extends SetRGB{
    public static BufferedImage setRGB(BufferedImage image, int xiangsu_x, int xiangsu_y,
                                       int new_red, int new_green, int new_blue, int new_ar) throws Exception{
        //未实现设置指定RGB颜色，那么设定成满足一定条件返回参数image
        BufferedImage newImage = null;
        if(xiangsu_x < image.getWidth() && xiangsu_y < image.getHeight()) {
            newImage = image;
        }
        else newImage = null;
        return newImage;
    }
}
