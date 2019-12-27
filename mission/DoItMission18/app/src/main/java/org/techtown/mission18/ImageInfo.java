package org.techtown.mission18;

public class ImageInfo {

    String path;
    String displayName;
    String dateAdded;

    public ImageInfo(String path, String displayName, String dateAdded) {
        this.path = path;
        this.displayName = displayName;
        this.dateAdded = dateAdded;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "path='" + path + '\'' +
                ", displayName='" + displayName + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }

}
