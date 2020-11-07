package testing_experiment;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import org.apache.commons.imaging.common.ImageMetadata;

public class LearnApacheImaging {
    public static void main(String[] args) throws Exception{
        //设置默认的图片存储路径
        String filePathHead = "C:\\Users\\Administrator\\Desktop\\学校课程用文件夹\\软件测试\\软件测试实验\\testpicture";
        String pictureName = "";
        Scanner scanner = new Scanner(System.in);
        //询问用户是否修改地址
        System.out.println("当前文件路径是:" + filePathHead);
        System.out.print("是否更改文件路径？(是请输入Y，否请输入N)");
        String choice = scanner.nextLine();
        if(choice.equals("Y") || choice.equals("y")){
            System.out.print("请输入文件路径:");
            filePathHead = scanner.nextLine();
        }
        //输入图片的基础信息
        System.out.print("请输入图片名:");
        pictureName = scanner.nextLine();
        System.out.println("请选择图片格式:");
        System.out.println("1 .jpg");
        System.out.println("2 .jpeg");
        System.out.println("3 .png");
        System.out.print("请输入你的选择:");
        int formatChoice = scanner.nextInt();
        String imageFormat = "";
        imageFormat = scanner.nextLine();//读取回车符号
        switch(formatChoice){
            case 1: imageFormat = ".jpg";break;
            case 2: imageFormat = ".jpeg";break;
            case 3: imageFormat = ".png";break;
            default: imageFormat = "文件格式错误";break;
        }
        PictureGet goalPicture = new PictureGet(filePathHead, pictureName, imageFormat);
        //判断图片是否存在
        boolean existence = goalPicture.judgeExistence();
        if(!existence) {
            //输入文件不存在，提示信息
            System.out.println("输入文件不存在");
            System.out.println("请检查图片信息是否正确");
            System.out.println("图片位置: " + goalPicture.getFilePath());
            System.out.println("图片格式: " + goalPicture.getFormat());
            System.out.println("图片名: " + goalPicture.getPictureName());
        }
        else{
            //存在时继续进行操作
            System.out.println("图片读取成功");
            //获取图片的Exif信息
            ImageMetadata image = goalPicture.getExif(goalPicture.getPicturePath());
            //打印图片的EXIF信息
            goalPicture.printExif(image);
            //修改图片部分像素点的RGB并将修改后的图片储存到源文件相同的文件夹中
            BufferedImage orgImage = PictureIO.getGoalImage(goalPicture.getPicturePath());
            if(orgImage != null) {
                System.out.println("请输入修改像素点的坐标（与该像素点同rgb的像素点颜色都会改变为指定值）");
                System.out.print("请输入横坐标:");
                int xiangsu_x = scanner.nextInt();
                System.out.print("请输入纵坐标:");
                int xiangsu_y = scanner.nextInt();
                System.out.println("请输入更换后的背景颜色rgb值");
                System.out.print("red:");
                int new_red = scanner.nextInt();
                System.out.print("green:");
                int new_green = scanner.nextInt();
                System.out.print("blue:");
                int new_blue = scanner.nextInt();
                System.out.print("透明度:");
                int new_ar = scanner.nextInt();
                BufferedImage newImage = SetRGB.setRGB(orgImage, xiangsu_x,
                        xiangsu_y, new_red, new_green, new_blue, new_ar);
                //当构建修改后的图片时，可能因为访问像素点错误，或是rgb颜色超出范围，会返回NULL
                if(newImage != null) {
                    System.out.print("请输入新图片名:");
                    String newPictureName = scanner.nextLine();
                    newPictureName = scanner.nextLine();
                    if(!PictureIO.writeGoalImage(newImage, goalPicture.getFilePath(), newPictureName)){
                        System.out.println("写入图片出错");
                    }
                }
                else {
                    System.out.println("请检查输入的像素点，rgb颜色值，不透明度是否越界");
                    System.out.println("本图片像素点范围，宽:" + orgImage.getWidth() +" 高:" + orgImage.getHeight());
                    System.out.println("rgb颜色值及不透明度范围0~255");
                    System.out.println("您输入的像素点坐标，x值:" + xiangsu_x + " y值:" + xiangsu_y);
                    System.out.println("rgb颜色值，red: " + new_red + " green: " + new_green + " blue: " + new_blue);
                    System.out.println("不透明度: " + new_ar);
                }
            }
            else {
                System.out.println("程序运行过程中图片被移除，请检查");
            }
        }

    }

}
