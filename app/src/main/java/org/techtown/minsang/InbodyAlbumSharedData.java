package org.techtown.minsang;

public class InbodyAlbumSharedData {
    private String albumimage;
    private String albumdate;

    public InbodyAlbumSharedData(String albumimage, String albumdate) {
        this.albumimage = albumimage;
        this.albumdate = albumdate;
    }

    public String getAlbumimage() {
        return albumimage;
    }

    public void setAlbumimage(String albumimage) {
        this.albumimage = albumimage;
    }

    public String getAlbumdate() {
        return albumdate;
    }

    public void setAlbumdate(String albumdate) {
        this.albumdate = albumdate;
    }
}
