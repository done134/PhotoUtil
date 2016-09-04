package com.done.photoutil;

/**
 * Created by Done on 2016/9/4.
 */
public class ImageFolder {
    public String name; //文件夹名称
    public String firstImagePath;//第一张图片路径
    public int imageSum;//图片总数
    public String folderPath;//图片文件夹路径

    public String getName() {
        return name;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageSum() {
        return imageSum;
    }

    public void setImageSum(int imageSum) {
        this.imageSum = imageSum;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
        int lastIndexOf = this.folderPath.lastIndexOf("/");
        this.name = this.folderPath.substring(lastIndexOf);
    }
}
