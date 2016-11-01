package example.com.popularmovies.Detail;

/**
 * Created by WSIM on 31/10/2016.
 */

public class Trailer {
    String url;
    String title;

    public Trailer(String url, String title){
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
