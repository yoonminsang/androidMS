package org.techtown.minsang;

import android.graphics.Bitmap;

public class AlbumData {
    private Bitmap albumimage;
    private String albumdate;

    public AlbumData(String albumdate, Bitmap albumimage) {
        this.albumimage = albumimage;
        this.albumdate = albumdate;
    }

    public Bitmap getAlbumimage() {
        return albumimage;
    }

    public void setAlbumimage(Bitmap albumimage) {
        this.albumimage = albumimage;
    }

    public String getAlbumdate() {
        return albumdate;
    }

    public void setAlbumdate(String albumdate) {
        this.albumdate = albumdate;
    }
}
