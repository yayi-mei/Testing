package testing_experiment;

import org.junit.Test;

import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class JunitImag {
    //测试获取图片的EXIF信息
    @Test
    public void testGet() throws Exception{
        //第一个测试用例，正确的图片路径，图片名与格式，图片含EXIF信息，但是每个EXIF信息都没有内容
        String testPath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture";
        String testName = "苍鹰";
        String testFormat = ".jpeg";
        String imgFileName = testPath + "\\" + testName + testFormat;
        PictureGet test_exp = new PictureGet(testPath, testName, testFormat);
        //判断图片是否存在函数是否正确执行
        assertTrue(test_exp.judgeExistence());
        //判断EXIF信息是否存在，以下只判断4个
        ImageMetadata metadata = test_exp.getExif(imgFileName);
        JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        TiffField field = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_XRESOLUTION);
        assertNull(field);
        field = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_DATE_TIME);
        assertNull(field);
        field = jpegMetadata.findEXIFValueWithExactMatch(
                ExifTagConstants.EXIF_TAG_ISO);
        assertNull(field);
        field = jpegMetadata.findEXIFValueWithExactMatch(
                ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
        assertNull(field);

        //第二个测试用例，正确的图片路径，图片名与格式，图片含EXIF信息
        testName = "南京眼";
        testFormat = ".jpg";
        imgFileName = testPath + "\\" + testName + testFormat;
        //构建获取EXIF图片信息类
        test_exp = new PictureGet(testPath, testName, testFormat);
        assertTrue(test_exp.judgeExistence());
        //获取EXIF信息类
        metadata = test_exp.getExif(imgFileName);
        jpegMetadata = (JpegImageMetadata) metadata;
        field = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_XRESOLUTION);
        assertEquals("350", field.getValueDescription());
        field = jpegMetadata.findEXIFValueWithExactMatch(
                TiffTagConstants.TIFF_TAG_DATE_TIME);
        assertEquals("'2019:10:06 20:21:57'", field.getValueDescription());
        field = jpegMetadata.findEXIFValueWithExactMatch(
                ExifTagConstants.EXIF_TAG_ISO);
        assertEquals("800", field.getValueDescription());
        field = jpegMetadata.findEXIFValueWithExactMatch(
                ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
        assertEquals("-6816/2560 (-2.663)", field.getValueDescription());

        //第三个测试用例，正确的图片路径，图片名与格式，图片不含EXIF信息
        testName = "四谎";
        testFormat = ".jpg";
        imgFileName = testPath + "\\" + testName + testFormat;
        //构建获取EXIF图片信息类
        test_exp = new PictureGet(testPath, testName, testFormat);
        assertTrue( test_exp.judgeExistence());
        //获取EXIF信息类
        metadata = test_exp.getExif(imgFileName);
        assertFalse(metadata instanceof JpegImageMetadata);

        //第四个测试用例，除图片名均正确
        testPath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验";
        testName = "没有";
        testFormat = ".jpg";
        imgFileName = testPath + "\\" + testName + testFormat;
        //构建获取EXIF图片信息类PictureGet
        test_exp = new PictureGet(testPath, testName, testFormat);
        assertFalse( test_exp.judgeExistence());

        //第四个测试用例，除格式均正确
        testName = "南京眼";
        testFormat = ".png";
        imgFileName = testPath + "\\" + testName + testFormat;
        //构建获取EXIF图片信息类PictureGet
        test_exp = new PictureGet(testPath, testName, testFormat);
        assertFalse( test_exp.judgeExistence());

        //第五个测试用例，除路径均正确
        testName = "南京眼";
        testFormat = ".jpg";
        imgFileName = testPath + "\\" + testName + testFormat;
        //构建获取EXIF图片信息类PictureGet
        test_exp = new PictureGet(testPath, testName, testFormat);
        assertEquals(false , test_exp.judgeExistence());
    }
    //测试读取与写入图片
    @Test
    public void testIO() throws Exception{
        //测试读取图片IO
        //第一个测试用例，完全正确的图片路径，判断读取正确的方式是用ImageIO.read读取出统一图片判断是否相同
        String imagePath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture\\四谎.jpg";
        File file = new File(imagePath);
        BufferedImage correct_image = ImageIO.read(file);
        BufferedImage image = PictureIO.getGoalImage(imagePath);
        for (int i = 0; i < correct_image.getWidth(); i++) {
            for (int t = 0; t < correct_image.getHeight(); t++){
                assertEquals(correct_image.getRGB(i, t), image.getRGB(i, t));
            }
        }
        //第二个测试用例，不存在的图片路径，返回null代表未读取到
        imagePath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\不存在.jpg";
        image = PictureIO.getGoalImage(imagePath);
        assertNull(image);
        //第三个测试用例，写图片返回True代表写图片成功
        imagePath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture\\四谎.jpg";
        String Path = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture";
        String Name = "test";
        file = new File(imagePath);
        correct_image = ImageIO.read(file);
        assertTrue(PictureIO.writeGoalImage(correct_image, Path, Name));
    }
    //测试设置颜色代码
    @Test
    public void testSet() throws Exception{
        //第一个测试用例，正确
        int xiangsu_x = 16;
        int xiangsu_y = 2014;
        int new_red = 255;
        int new_green = 255;
        int new_blue = 0;
        int new_ar = 255;
        int goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        int orgArgb = 0;
        String imagePath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture\\星夜.jpeg";
        BufferedImage orgImage = PictureIO.getGoalImage(imagePath);
        orgArgb = orgImage.getRGB(xiangsu_x, xiangsu_y);
        BufferedImage newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        //逐个像素点扫描，查看是否符合要求
        for (int i = 0; i < orgImage.getWidth(); i++) {
            for (int t = 0; t < orgImage.getHeight(); t++){
                if(orgImage.getRGB(i,t) == orgArgb)
                    assertEquals(newImage.getRGB(i, t), goalArgb);
                else assertEquals(orgImage.getRGB(i,t), newImage.getRGB(i,t));
            }
        }

        //第二个测试用例，采集的像素点横轴越界
        xiangsu_x = 5068;
        xiangsu_y = 2014;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        orgArgb = 0;
        //因为访问像素点越界，此时返回的应该是空指针
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第三个测试用例，采集的像素点纵轴越界
        xiangsu_x = 25;
        xiangsu_y = 9066;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第四个测试用例，rgb颜色值中red值越上界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 308;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第五个测试用例，rgb中green值越上界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = 256;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第六个测试用例，rgb中blue值越上界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = 255;
        new_blue = 286;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第七个测试用例，rgb中blue值越下界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = 255;
        new_blue = -521;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第八个测试用例，rgb中green值越下界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = -1;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第九个测试用例，rgb颜色值中red值越下界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = -308;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第十个测试用例，不透明度越上界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = 256;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第十一个测试用例，不透明度越下界
        xiangsu_x = 25;
        xiangsu_y = 2018;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = -256;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第十二个测试用例，采集的像素点横轴越下界
        xiangsu_x = -6;
        xiangsu_y = 2014;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        orgArgb = 0;
        //因为访问像素点越界，此时返回的应该是空指针
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);

        //第十三个测试用例，采集的像素点纵轴越下界
        xiangsu_x = 36;
        xiangsu_y = -2014;
        new_red = 255;
        new_green = 255;
        new_blue = 0;
        new_ar = 255;
        goalArgb = (new_ar << 24) | (new_red << 16) | (new_green << 8) | new_blue;
        orgArgb = 0;
        //因为访问像素点越界，此时返回的应该是空指针
        newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                xiangsu_y, new_red, new_green, new_blue, new_ar);
        assertNull(newImage);
    }
}
