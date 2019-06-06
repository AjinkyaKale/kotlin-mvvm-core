package com.smartfarming.screenrender.model;


import android.os.Parcel;
import android.os.Parcelable;

public class DownloadImageModel implements Parcelable {

    public DownloadImageModel() {

    }

    private int progress;
    private String unzipAtFilePath;
    private String downloadedZipFilePath;

    private String downloadStatus;

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(progress);
    }

    private DownloadImageModel(Parcel in) {

        progress = in.readInt();
    }

    public static final Parcelable.Creator<DownloadImageModel> CREATOR = new Parcelable.Creator<DownloadImageModel>() {
        public DownloadImageModel createFromParcel(Parcel in) {
            return new DownloadImageModel(in);
        }

        public DownloadImageModel[] newArray(int size) {
            return new DownloadImageModel[size];
        }
    };


    public String getUnzipAtFilePath() {
        return unzipAtFilePath;
    }

    public void setUnzipAtFilePath(String unzipAtFilePath) {
        this.unzipAtFilePath = unzipAtFilePath;
    }

    public String getDownloadedZipFilePath() {

        return downloadedZipFilePath;
    }

    public void setDownloadedZipFilePath(String downloadedZipFilePath) {

        this.downloadedZipFilePath = downloadedZipFilePath;
    }
}
