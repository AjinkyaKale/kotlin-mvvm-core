package com.smartfarming.screenrender.manager;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.listeners.OnPermissionGranted;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.ExtractImagesListener;
import com.smartfarming.screenrender.util.Pref;
import com.smartfarming.screenrender.util.Utility;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class to handle all the Directories and File creation implementation
 */

public final class FileManager {

    public static long FILE_SIZE_LIMIT = 30000000;

    //Dir Names
    public static final String DIR_SMARTFARMING = "Smartfarming";

    // Extracted image dir
    public static final String DIR_IMAGES = "SF_Extract_images";

    public static String mRemovableSdCardPath = "";

    public static final String WEATHER_INFO_FILE = "weather_info.srl";

    public static final String LABEL_FILE_NAME = "label";

    public static final String EXTRACT_FOLDER = "Images";


    /**
     * @param filePath full path of file
     * @return
     */
    public static boolean deleteFile(final String filePath) {
        Log.v("deleteFile", "deleteFile Path: " + filePath);

        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * Get List of files contains inside folder
     */
    public static List<File> getFilesList(String dirPath) {

        try {
            File dir = new File(dirPath);
            return Arrays.asList(dir.listFiles());
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }

    public static void deleteAllFilesFromPath(String dirPath) {
        File dir = new File(dirPath);
        // Directory
        if (dir.exists() && dir.isDirectory()) {
            List<File> files = getFilesList(dirPath);
            if (files == null && files.size() <= 0) return;
            for (File file : files) {
                deleteFile(file.getAbsolutePath());
            }
        }
        if (dir.exists()) {
            dir.delete();
        }
    }

//    public static boolean deleteFileByName(Context context, String fileName) {
//        try {
//            String filePath = AppPreference.getInstance(context).getDefaultPath() + File.separator + fileName;
//            return deleteFile(filePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


    /**
     * Method to get path of Second Flow Path
     *
     * @return
     */
    public static String getRootPath(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator;
    }

    /**
     * Get directory path either exist or create new and
     *
     * @param context
     * @param folder
     * @return
     */
    public static File getDataDir(Context context, String folder) {
        String path = "";
        if (folder != null) {
            path = getRootPath(context) + folder;
        } else {
            path = getRootPath(context);
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    /**
     * Unzip file to application package path
     *
     * @param zipFilePath
     * @param unzipAtLocation
     * @throws Exception
     */
    public static void unzip(String zipFilePath, String unzipAtLocation, ExtractImagesListener extractImagesListener) throws Exception {


        File archive = new File(zipFilePath);
        try {
            ZipFile zipfile = new ZipFile(archive);

            int count = 0;
            for (Enumeration e = zipfile.entries(); e.hasMoreElements(); ) {
                // count++;
                ZipEntry entry = (ZipEntry) e.nextElement();
                unzipEntry(zipfile, entry, unzipAtLocation);
                //extractImagesListener.onGetExtractingProgress(count);
            }
        } catch (Exception e) {
            Log.e("Unzip zip", "Unzip exception", e);
            Utility.postNoInternetErrorModel();
        }
    }

    /**
     * Unzip file and save to directory
     *
     * @param zipfile
     * @param entry
     * @param outputDir
     * @throws IOException
     */
    private static void unzipEntry(ZipFile zipfile, ZipEntry entry, String outputDir) throws IOException {

        if (entry.isDirectory()) {
            createDir(new File(outputDir, entry.getName()));
            return;
        }

        File outputFile = new File(outputDir, entry.getName());
        if (!outputFile.getParentFile().exists()) {
            createDir(outputFile.getParentFile());
        }
        //Timber.v("unzipEntry :: Extracting::  %s", entry);
        Log.v("unzipEntry", "Extracting: " + entry);

        InputStream zin = zipfile.getInputStream(entry);
        BufferedInputStream inputStream = new BufferedInputStream(zin);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
        try {
            try {
                for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                    outputStream.write(c);
                }
            } finally {
                outputStream.close();
            }
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    /**
     * Create new directory if not exist
     *
     * @param dir
     */
    private static void createDir(File dir) {
        if (dir.exists()) {
            return;
        }
        Log.v("ZIP E", "Creating dir " + dir.getName());

        if (!dir.mkdirs()) {
            throw new RuntimeException("Can not create dir " + dir);
        }
    }


//    /**
//     * Get image drawable from image directory
//     *
//     * @param context
//     * @param imageId
//     * @return
//     */
//    public static Drawable getImageDrawable(Context context, String imageId) {
//        try {
//
//            String imageFilePath = FileManager.getDataDir(context, FileManager.DIR_IMAGES).getAbsolutePath() + File.separator + imageId;
//            Drawable imageDrawable = Drawable.createFromPath(imageFilePath);
//            if (imageDrawable != null) {
//                return imageDrawable;
//            } else {
//                return ContextCompat.getDrawable(context, R.drawable.ic_deafult_rectangle);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ContextCompat.getDrawable(context, R.drawable.ic_deafult_rectangle);
//        }
//    }


    /**
     * get bitmap for the image
     *
     * @param context
     * @param imageId image id to find image from path
     * @param type    check weather image is need to set to Dialog or screen.
     * @return
     */
    public static Bitmap getImageBitmap(Context context, String imageId, String type) {
        try {
            String imageFilePath = FileManager.getDataDir(context, FileManager.DIR_IMAGES).getAbsolutePath() + File.separator + imageId;
            File file = new File(imageFilePath);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                Bitmap bitmapToShow;
                if (type.equals(Constants.SCREEN)) {
                    bitmapToShow = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 3, bitmap.getHeight() / 3, false);
                } else {
                    bitmapToShow = bitmap;
                }

                return bitmapToShow;
            } else {
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_deafult_rectangle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_deafult_rectangle);
        }
    }


//    public static String getImagePath(Context context, String imageId) {
//        try {
//            String imageFilePath = FileManager.getDataDir(context, FileManager.EXTRACT_FOLDER).getAbsolutePath() + File.separator + imageId;
//            // File file = new File(imageFilePath);
//            return imageFilePath;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    public static String getImagePath(Context context, String imageId) {
        try {
            String imageFilePath = Pref.getInstance(context).getDefaultPath() + File.separator + FileManager.EXTRACT_FOLDER + File.separator + imageId;
            // File file = new File(imageFilePath);
            return imageFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Get image drawable Array from image directory
     *
     * @param context
     * @param screenModel
     * @return
     */
    public static Drawable[] getImageDrawable(Context context, ScreenModel screenModel) {
        Drawable[] imageArray;
        if (screenModel == null) return null;
        List<ImageModel> list = screenModel.getImageModels();

        if (list != null && list.size() != 0) {
            imageArray = new Drawable[list.size()];

            for (int i = 0; i < list.size(); i++) {
                String imageName = list.get(i).getName();
                try {
                    String imageFilePath = Pref.getInstance(context).getDefaultPath() + File.separator + imageName;
                    Drawable imageDrawable = Drawable.createFromPath(imageFilePath);
                    if (imageDrawable != null) {
                        imageArray[i] = imageDrawable;
                    } else {
                        imageArray[i] = ContextCompat.getDrawable(context, R.drawable.ic_default);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    imageArray[i] = ContextCompat.getDrawable(context, R.drawable.ic_default);
                }
            }
        } else {
            imageArray = new Drawable[1];
            imageArray[0] = ContextCompat.getDrawable(context, R.drawable.ic_default);
            return imageArray;
        }

        return imageArray;
    }


//    public static Drawable getScaleDrawable(Context context, String image_name) {
//        Drawable drawable = FileManager.getImageDrawable(context, image_name);
//        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 2.5),
//                (int) (drawable.getIntrinsicHeight() * 2.5));
//        ScaleDrawable sd = new ScaleDrawable(drawable, 0, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        return sd.getDrawable();
//    }


    //---------------------- memory management--------------------

    /**
     * Checks if is external card mounted.
     *
     * @return true, if is external card mounted
     */
    public static boolean isExternalCardMounted() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }


    public static String getInternalPath(Context context) {
        return context.getFilesDir().getAbsolutePath(); //+ File.separator + DIR_SMARTFARMING; //+ File.separator;
    }

    public static String getExternalPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath(); //+ File.separator + DIR_SMARTFARMING;// + File.separator;
    }

    public static String getRemovableSdCardPath() {
        return mRemovableSdCardPath; //+ File.separator + DIR_SMARTFARMING;// + File.separator;
    }

    /**
     * Get directory path either exist or create new and
     *
     * @param folder
     * @return
     */
    public static String getPathByFolderName(String selectedPath, String folder) {

        String path = "";
        if (selectedPath != null) {
            path = selectedPath + File.separator + folder;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    /**
     * Check whether internal memory available
     *
     * @param context
     * @return
     */
    public static boolean isInternalMemoryAvailable(Context context) {
        long freeBytesInternal = new File(context.getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        Log.v("", "free_internal: " + freeBytesInternal);
        if (freeBytesInternal > FILE_SIZE_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether External memory available
     *
     * @return
     */
    public static boolean isExternalMemoryAvailable() {

        if (isExternalCardMounted()) {
            File externalDir = Environment.getExternalStorageDirectory();
            long freeBytesExternal = externalDir.getFreeSpace();
            Log.v("", "free_external: " + freeBytesExternal);
            // long freeBytesExternal = new File(getExternalFilesDir(null).toString()).getFreeSpace();
            if (freeBytesExternal > FILE_SIZE_LIMIT) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Check whether device has Removable SD card
     *
     * @param context
     * @return
     */
    public static boolean isSdCardOnDevice(Context context) {
        File[] storage = ContextCompat.getExternalFilesDirs(context, null);
        if (storage.length > 1 && storage[0] != null && storage[1] != null)
            return true;
        else
            return false;
    }

    /**
     * Check whether removable sd card has enough memory
     *
     * @param context
     * @return
     */
    public static boolean isRemovableSdCardAvailable(Context context) {

        if (isSdCardOnDevice(context)) {
            File[] storage = ContextCompat.getExternalFilesDirs(context, null);
            File sdcard0 = storage[0];
            long freeBytesExternal_0 = sdcard0.getFreeSpace();
            Log.v("", "free_removable_0: " + freeBytesExternal_0);
            if (freeBytesExternal_0 > FILE_SIZE_LIMIT) {
                mRemovableSdCardPath = sdcard0.getAbsolutePath();
                return true;
            }

            File sdcard1 = storage[1];
            long freeBytesExternal_1 = sdcard1.getFreeSpace();
            Log.v("", "free_removable_1: " + freeBytesExternal_1);
            if (freeBytesExternal_1 > FILE_SIZE_LIMIT) {
                mRemovableSdCardPath = sdcard1.getAbsolutePath();
                return true;
            }
        }
        return false;
    }


    /**
     * Check whether sufficient memory available
     *
     * @param context
     * @return
     */
    public static boolean isMemoryAvailableForDownload(Context context) {

        // images zip file limit 30 mb

        // Check whether images already downloaded or not
        String defaultPath = Pref.getInstance(context).getDefaultPath();
        if (defaultPath == null) {
            //check whether sufficient memory available
            if (isInternalMemoryAvailable(context)) {
                Pref.getInstance(context).setDefaultPath(getInternalPath(context));
                return true;
            } else if (isExternalMemoryAvailable()) {
                Pref.getInstance(context).setDefaultPath(getExternalPath());
                return true;
            } else if (isRemovableSdCardAvailable(context)) {
                Pref.getInstance(context).setDefaultPath(getRemovableSdCardPath());
                return true;
            } else {
                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                Utility.postStorageSpaceErrorModel();
                return false;
            }

        } else {
            return true;
        }

    }


//    public static WeatherInfo readObject(Context context){
//
//        WeatherInfo weatherInfo;
//        FileInputStream fin = null;
//        ObjectInputStream ois = null;
//
//        try {
//
//            fin = new FileInputStream(new File(new File(context.getFilesDir(),"")+ File.separator + WEATHER_INFO_FILE));
//            ois = new ObjectInputStream(fin);
//            weatherInfo = (WeatherInfo) ois.readObject();
//            return  weatherInfo;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (fin != null) {
//                try {
//                    fin.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (ois != null) {
//                try {
//                    ois.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

//    public static void writeObject(Context context, WeatherInfo object){
//
//        ObjectOutput out = null;
//        try {
//            out = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(),"")+ File.separator + WEATHER_INFO_FILE));
//            out.writeObject(object);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * write lableResponseModel to the file (file Name : label)
     *
     * @param context
     * @param jsonObject
     */
  /*  public static void writeLabel(Context context, JSONObject jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File filePath = new File(context.getFilesDir()+File.separator + LABEL_FILE_NAME);
            FileWriter file = new FileWriter(filePath.toString());
            objectMapper.writeValue(file, jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * read label from the file in String format.
     *
     * @param context
     * @return
     */
  /*  public static String readLabel(Context context) {
        try {
            File filePath = new File(context.getFilesDir()+File.separator + LABEL_FILE_NAME);
            ObjectMapper mapper = new ObjectMapper();
            String jsonObject = mapper.
                    readValue(filePath, String.class);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    /**
     * write lableResponseModel to the file (file Name : label)
     *
     * @param context
     * @param jsonObject
     */
    public static void writeLabel(final Context context, final JSONObject jsonObject, final OnPermissionGranted listiner) {
        final ObjectMapper objectMapper = new ObjectMapper();
        Permissions.check(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, null,
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        listiner.onGranted();
                        try {
                            FileWriter file = new FileWriter(context.getExternalFilesDir(null)
                                    + File.separator + LABEL_FILE_NAME);
                            objectMapper.writeValue(file, jsonObject.toString());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);
                        listiner.onDenied(true);
                    }
                });

    }


    /**
     * read label from the file in String format.
     *
     * @param context
     * @return
     */
    public static String readLabel(final Context context) {
        final String[] jsonObject = {null};
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonObject[0] = mapper.
                    readValue(new File(context.getExternalFilesDir(null)
                            + File.separator + LABEL_FILE_NAME), String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject[0];
    }


}