package testing_experiment;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PictureIO {
    //读取图片
    public static BufferedImage getGoalImage(String imagePath) throws Exception{
        File file = new File(imagePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        }
        catch (Exception e){
            image = null;
        }
        return image;
    }
    //写入图片
    public static boolean writeGoalImage(BufferedImage newImage, String Path, String newPictureName)
            throws Exception{
        boolean result = false;
        File outputfile = new File(Path + newPictureName + ".jpg" );
        try {
            ImageIO.write(newImage, "jpg", outputfile);
            result = true;
        }
        catch (Exception e) {
            result = false;
        }
        return result;
    }
}
