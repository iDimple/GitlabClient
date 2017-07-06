package com.wei.mobileoffice.constants;

import com.wei.mobileoffice.OfficeAppContext;

import java.io.File;

public class AppConstants {

    public static final String INTENT_ORIGIN_ACTIVITY = "originActivity";
   private static final String PACKAGE_NAME = OfficeAppContext.getApplication().getPackageName();

    public static class FileConstants {
        public static final String APP_DIR_NAME = File.separator + PACKAGE_NAME;
        public static final String IMG_TOTAL_DIR = File.separator + "images";
        public static final String AVATAR_SUB_DIR = IMG_TOTAL_DIR + File.separator +  "avatar";
        public static final String IMG_SUB_DIR = IMG_TOTAL_DIR + File.separator + "img";
        public static final String PUB_IMG_DIR = File.separator + "photoAlbum";//相册，保存用户导出的照片
        public static final String UPDATE_DIR = File.separator + "update";// 升级目录
    }







}
