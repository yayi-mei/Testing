package testing_experiment;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
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

import java.io.File;
import java.io.IOException;

public class PictureGet {
    private String FilePath;
    private String PictureName;
    private String format;
    private String picturePath;
    public PictureGet(String FilePath, String PictureName, String format){
        this.FilePath = FilePath;
        this.PictureName = PictureName;
        this.format = format;
        this.picturePath = "";
    }
    //合并图片路径
    public String MergePath(){
        //合成图片文件的详细路径（使用的是绝对路径）
        if(this.format.equals("文件格式错误")){
            this.picturePath = "文件格式错误";
        }
        else {
            this.picturePath = this.FilePath + this.PictureName + this.format;
        }
        return this.picturePath;
    }
    //判断输入的图片是否存在
    public boolean judgeExistence(){
        //先合成图片文件的具体路径
        String goalImagePath = this.MergePath();
        boolean result = false;
        //构建指向目标文件的指针
        File imageFile = new File(goalImagePath);
        //判断目标文件是否存在
        result = Imaging.hasImageFileExtension(imageFile);
        return result;
    }
    //获取图片的EXIF信息
    public ImageMetadata getExif(String goalPath)throws ImageReadException, IOException {
        File imgFileName = new File(goalPath);
        ImageInfo sampleInfo = Imaging.getImageInfo(imgFileName);
        System.out.println("图片的媒体: " + sampleInfo.getMimeType());//获取图片的媒体类型
        System.out.println("图片的尺寸: " + Imaging.getImageSize(imgFileName));
        final ImageMetadata metadata = Imaging.getMetadata(imgFileName);
        return metadata;

    }
    public void printExif(final ImageMetadata metadata){
        //判断目标是否有EXIF信息
        if(metadata instanceof JpegImageMetadata){
            //打印基础的EXIF信息
            System.out.println("以下为图片的EXIF信息");
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
            printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
            printTagValue(jpegMetadata,
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            printTagValue(jpegMetadata,
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            System.out.println();
        }
        else {
            System.out.println("目标没有EXIF信息");
        }
    }
    //打印基础信息
    private static void printTagValue(final JpegImageMetadata jpegMetadata, final TagInfo tagInfo){
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
        if(field == null){
            System.out.println(tagInfo.name + ": " + "Not found");
        }
        else {
            System.out.println(tagInfo.name + ": " + field.getValueDescription());
        }
    }
    public String getPicturePath(){
        return this.picturePath;
    }
    public String getFilePath() {
        return this.FilePath;
    }
    public String getPictureName() {
        return this.PictureName;
    }
    public String getFormat() {
        return this.format;
    }
    public boolean setFilePath(String NewPath) {
        boolean result = false;
        this.FilePath = NewPath;
        result = true;
        return result;
    }
    public boolean setPictureName(String NewName) {
        boolean result = false;
        this.PictureName = NewName;
        result = true;
        return result;
    }
    public boolean setFormat(String NewFormat) {
        boolean result = false;
        this.format = NewFormat;
        result = true;
        return result;
    }
}
