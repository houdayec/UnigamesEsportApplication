package hantizlabs.unigamesesportapplication.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Corentin on 15/10/2016.
 */

public class MontserratTextView extends TextView {

    //Constructor with custom font, here Montserrat
    public MontserratTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf"));
    }

}
