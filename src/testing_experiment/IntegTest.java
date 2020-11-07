package testing_experiment;

import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import java.awt.image.BufferedImage;
import java.io.File;

import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import javax.imageio.ImageIO;

/*
* PictureGet、setRGB、PictureIO还未完成，设计两个子类继承PictureGet和setRGB，并重载未实现的方法
* PictureIO写功能先通过Imageio实现，读功能先通过ImageIO.read读取出图片，直接使用
* */
@RunWith(MockitoJUnitRunner.class)
public class IntegTest{
    private static JpegImageMetadata stubinfo;
    private static BufferedImage stubImage;
    private static BufferedImage stubImageNull;
    private static String stubPath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture";
    private static String stubName = "wangtian";
    private static String stubFormat = ".jpg";
    private static String stubPicPath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹" +
            "\\软件测试\\软件测试实验\\testpicture\\wangtian.jpg";
    private static String TrueNewPath = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture\\副本.jpg";
    private static String  stubPathNull = "E:\\腾讯会议";
    private static String stubExifNull = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹" +
            "\\软件测试\\软件测试实验\\testpicture\\南京眼.jpg";

    @BeforeClass
    public static void prepare() throws Exception{
        //预先读取一张图片，充当修改颜色的目标图片
        File file = new File(stubPicPath);
        stubImage = ImageIO.read(file);
        stubImageNull = null;
    }
    @Test
    //此测试测试的是正常执行
    public void testIntegTrue() throws Exception{
        MocPicGet stubGet = new MocPicGet(stubPath, stubName, stubFormat);
        boolean existence = stubGet.judgeExistence();
        assertTrue(existence);
        if(existence){
            stubGet.printExif(stubGet.getExif(stubPicPath));
            if(stubImage != null) {
                BufferedImage newImg = MocSetRGB.setRGB(stubImage, 1, 1,
                        1, 1, 1, 1);
                assertNotNull(newImg);
                if(newImg != null) {
                    ImageIO.write(newImg, "jpg", new File(TrueNewPath));
                }
                else {
                    System.out.println("输入像素范围有误");
                }
            }
            else {
                System.out.println("读取图片过程图片被删除");
            }
        }
        else {
            System.out.println("读取文件不存在");
        }
    }
    @Test
    //此测试测试的是输入了无效的图片路径
    public void testIntegFlase() throws Exception{
        //更换了目录地址，现在差找不到对应的图片
        MocPicGet stubGet = new MocPicGet(stubPathNull, stubName, stubFormat);
        boolean existence = stubGet.judgeExistence();
        assertFalse(existence);
        if(existence){
            stubGet.printExif(stubGet.getExif(stubPicPath));
            //此时像素点超过范围，newImg应该返回null
            if(stubImage != null){
                BufferedImage newImg = MocSetRGB.setRGB(stubImage, 1, 1,
                        1, 1, 1, 1);
                assertNull(newImg);
                if(newImg != null) {
                    ImageIO.write(newImg, "jpg", new File(TrueNewPath));
                }
                else {
                    System.out.println("输入像素范围有误");
                }
            }
            else {
                System.out.println("读取图片过程图片被删除");
            }
        }
        else {
            //此测试执行这里
            System.out.println("读取文件不存在");
        }
    }
    @Test
    //此测试测试的是将图中指定颜色替换成其他颜色时，用于指定颜色所在像素点时，像素点输入越界，且图片无EXIF信息
    public void TestIntegFalse2() throws Exception{
        MocPicGet stubGet = new MocPicGet(stubPath, stubName, stubFormat);
        boolean existence = stubGet.judgeExistence();
        assertTrue(existence);
        if(existence){
            stubGet.printExif(stubGet.getExif(stubExifNull));
            if(stubImage != null){
                BufferedImage newImg = MocSetRGB.setRGB(stubImage, 8069, 1,
                        1, 1, 1, 1);
                assertNull(newImg);
                if(newImg != null) {
                    ImageIO.write(newImg, "jpg", new File(TrueNewPath));
                }
                else{
                    System.out.println("输入像素范围有误");
                }
            }
            else {
                System.out.println("读取图片过程中图片被删除");
            }
        }
        else {
            System.out.println("读取文件不存在");
        }
    }
    @Test
    //此测试测试的是第一次读取EXIF信息时图片存在，第二次重新读取图片来修改像素点时，图片被删除，看能否正确执行
    public void TestIntegFalse3() throws Exception{
        MocPicGet stubGet = new MocPicGet(stubPath, stubName, stubFormat);
        boolean existence = stubGet.judgeExistence();
        assertTrue(existence);
        if(existence){
            stubGet.printExif(stubGet.getExif(stubPicPath));
            if(stubImageNull != null){
                BufferedImage newImg = MocSetRGB.setRGB(stubImage, 8069, 1,
                        1, 1, 1, 1);
                assertNull(newImg);
                if(newImg != null) {
                    ImageIO.write(newImg, "jpg", new File(TrueNewPath));
                }
                else{
                    System.out.println("输入像素范围有误");
                }
            }
            else {
                System.out.println("读取图片过程中图片被删除");
            }
        }
        else {
            System.out.println("读取文件不存在");
        }
    }

}
