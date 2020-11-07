package testing_experiment;

import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

public class MocPicGet extends PictureGet{

    public MocPicGet(String FilePath, String PictureName, String format){
        super(FilePath, PictureName, format);
    }
    //判断输入的图片是否存在
    public boolean judgeExistence(){
        //此时判断是否存在的函数尚未实现，单纯根据图片尾缀判断
        if(this.getFilePath().equals("C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture")){
            return true;
        }
        else {
            return false;
        }
    }
    public void printExif(final ImageMetadata metadata){
        //具体方法未实现，输出固定信息
        if(metadata instanceof JpegImageMetadata){
            System.out.println("以下为图片的EXIF信息");
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            System.out.println("XResolution: Not found");
            System.out.println("DateTime: Not found");
            System.out.println("DateTimeOriginal: Not found");
            System.out.println("DateTimeDigitized: Not found");
            System.out.println("PhotographicSensitivity: Not found");
            System.out.println("ShutterSpeedValue: Not found");
            System.out.println("ApertureValue: Not found");
        }
        else {
            System.out.println("不存在EXIF信息");
        }
    }
}
