package com.smartfarming.screenrender.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.downloader.Error;
import com.downloader.*;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.model.DownloadImageModel;
import com.smartfarming.screenrender.util.ExtractImagesListener;
import com.smartfarming.screenrender.util.Pref;
import com.smartfarming.screenrender.util.Utility;

import java.io.*;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;


public class DownloadImageService extends IntentService implements ExtractImagesListener {

    public static final String DOWNLOAD_FAILED = "download_failed";

    public static final String DOWNLOAD_COMPLETED = "download_completed";

    public static final String DOWNLOAD_PROGRESS = "download_progress";

    public static final String EXTRACTING_IMAGES = "extracting_images";

    public static final String EXTRA_PATH = "zip_path";

    public static final String ZIP_DOWNLOAD_URL = "zip_path";


    public static final String DOWNLOAD_FOLDER_NAME = "Images.zip";

    private String extractedImagesFolder;

    public DownloadImageService() {
        super("DownloadImageModel Service");
    }

    DownloadImageModel downloadImageModel = new DownloadImageModel();

    public static String LENGTH = "";

    private String path[];
    private int serviceIndex = 0;


    @Override
    protected void onHandleIntent(Intent intent) {
        Pref.getInstance(this).setDefaultPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath());
        if (intent.getExtras() != null) {
            path = intent.getExtras().getStringArray(EXTRA_PATH);
            downloadImages(path[serviceIndex]);
        }
    }


    /**
     * Download zip file, extract it and save to app package path
     *
     * @param path
     */
    private void downloadZip(String zipUrl, String path) throws SocketException {

        File outputFile = new File(Pref.getInstance(getApplicationContext()).getDefaultPath(), "SF-Zip-Images.zip");
        String unzipPath = FileManager.getPathByFolderName(Pref.getInstance(getApplicationContext()).getDefaultPath(), FileManager.DIR_IMAGES);
        Log.v("DownloadService", "unzipPath: " + unzipPath);
        downloadImageModel.setUnzipAtFilePath(unzipPath);
        downloadImageModel.setDownloadedZipFilePath(outputFile.getAbsolutePath());

        // String zipUrl = "http://smart-farming.dev.easternenterprise.com/zip/app-images.zip";
        //String zipUrl = BuildConfig.DOMAIN_URL + path;
        Log.v("DownloadService", "zipUrl: " + zipUrl);
        int count;
        try {
            URL url = new URL(zipUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(outputFile.getAbsolutePath());

            System.out.println("lengthOfFile : " + lengthOfFile);

            byte data[] = new byte[lengthOfFile];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                int progress = (int) ((total * 100) / lengthOfFile);

                if (progress < 100) {
                    downloadImageModel.setProgress(progress);
                    downloadImageModel.setDownloadStatus(DOWNLOAD_PROGRESS);
                    sendIntent(downloadImageModel);
                }
                output.write(data, 0, count);
            }
            onDownloadComplete();
            output.flush();
            output.close();
            input.close();
        } catch (ProtocolException e) {
            downloadImageModel.setDownloadStatus(DOWNLOAD_FAILED);
            sendIntent(downloadImageModel);
            stopSelf();
            e.printStackTrace();

        } catch (IOException e) {
            downloadImageModel.setDownloadStatus(DOWNLOAD_FAILED);
            sendIntent(downloadImageModel);
            stopSelf();
            e.printStackTrace();

        }
    }


    private void downloadImages(String serverURL) {


        extractedImagesFolder = FileManager.getPathByFolderName(/*getApplicationContext().getExternalFilesDir(null).getAbsolutePath()*/ Pref.getInstance(this).getDefaultPath(), FileManager.EXTRACT_FOLDER);

        PRDownloader.download(serverURL, Pref.getInstance(this).getDefaultPath(), DOWNLOAD_FOLDER_NAME)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                        // do anything with progress
                        downloadImageModel.setProgress((int) progressPercent);
                        downloadImageModel.setDownloadStatus(DOWNLOAD_PROGRESS);
                        sendIntent(downloadImageModel);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        String path = getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + DOWNLOAD_FOLDER_NAME;
                        File imageZipFile = new File(path);
                        extractImages(imageZipFile.getAbsolutePath(), extractedImagesFolder);
                    }

                    @Override
                    public void onError(Error error) {
                        downloadImageModel.setDownloadStatus(DOWNLOAD_FAILED);
                        sendIntent(downloadImageModel);
                    }
                });
    }


    /**
     * On completion of download extract zip file and save to app path
     */
    private void extractImages(String source, String destination) {

        // unzip file
        try {
            FileManager.unzip(source, destination, this);
            // delete zip file
            FileManager.deleteAllFilesFromPath(source);
            //moveFiles(destination ,extractedImagesFolder );
            serviceIndex++;
            if (serviceIndex < path.length) {
                downloadImages(path[serviceIndex]);
            } else {
                downloadImageModel.setProgress(100);
                downloadImageModel.setDownloadStatus(DOWNLOAD_COMPLETED);
                sendIntent(downloadImageModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Utility.postStorageSpaceErrorModel();
        }

    }

//    private void moveFiles(String fromPath , String toPath){
//        File[] from = new File(fromPath).listFiles();
//        for (File file : from) {
//            File to = new File(toPath);
//            file.renameTo(to);
//        }
//    }


    /**
     * Send Intent to display progress of downloading
     *
     * @param downloadImageModel
     */
    private void sendIntent(DownloadImageModel downloadImageModel) {

        Intent intent = new Intent(BaseActivity.IMAGE_DOWNLOAD_PROGRESS);
        intent.putExtra("downloadImageModel", downloadImageModel);
        LocalBroadcastManager.getInstance(DownloadImageService.this).sendBroadcast(intent);
    }


    /**
     * On completion of download extract zip file and save to app path
     */
    private void onDownloadComplete() {

        // unzip file
        try {
            String unzipDestination = downloadImageModel.getUnzipAtFilePath();
            if (unzipDestination == null) {
                File file = new File(downloadImageModel.getDownloadedZipFilePath());
                unzipDestination = file.getParentFile().getAbsolutePath();
            }
            FileManager.unzip(downloadImageModel.getDownloadedZipFilePath(), unzipDestination, this);
            // delete zip file
            FileManager.deleteAllFilesFromPath(downloadImageModel.getDownloadedZipFilePath());

            downloadImageModel.setProgress(100);
            downloadImageModel.setDownloadStatus(DOWNLOAD_COMPLETED);
            sendIntent(downloadImageModel);

        } catch (Exception e) {
            e.printStackTrace();
            Utility.postStorageSpaceErrorModel();
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }


    @Override
    public void onGetExtractingProgress(int progress) {
        downloadImageModel.setDownloadStatus(EXTRACTING_IMAGES);
        downloadImageModel.setProgress(progress);
        sendIntent(downloadImageModel);
    }
}
