package example.com.popularmovies.Detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 5/10/2016.
 */

public class DetailTrailer extends RecyclerView.ViewHolder{

    TextView trailerTitle, trailerLabel;
    TrailerPlayButton trailerPlayButton;
    TextView noTrailer;
    public DetailTrailer(View parent) {
        super(parent);
        noTrailer = (TextView)parent.findViewById(R.id.no_trailers);
        trailerTitle = (TextView)parent.findViewById(R.id.trailer_title);
        trailerLabel = (TextView)parent.findViewById(R.id.trailer_label);
        trailerPlayButton = (TrailerPlayButton)parent.findViewById(R.id.trailer_play);
    }

    public TextView getNoTrailer(){return noTrailer;}
    public TextView getTrailerTitle(){return trailerTitle;}
    public TextView getTrailerLabel(){return trailerLabel;}
    public TrailerPlayButton getTrailerPlay(){return trailerPlayButton;}
}
