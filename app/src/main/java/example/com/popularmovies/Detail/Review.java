package example.com.popularmovies.Detail;

/**
 * Created by WSIM on 31/10/2016.
 */

public class Review {

    String review;
    String username;

    public Review(String review, String username){
        this.review = review;
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public String getUsername() {
        return username;
    }
}
