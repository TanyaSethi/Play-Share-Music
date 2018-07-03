package androidapp.musicshare;

/**
 * Created by Rp on 3/30/2016.
 */
public class Beanclass {

    private String songname;

    public Beanclass(String Songname) {
        this.songname = Songname;
    }

    public String getSong() {
        return songname;
    }

    public void setSong(String songname) {
        this.songname = songname;
    }
}
