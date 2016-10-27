package hantizlabs.unigamesesportapplication.CustomTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Corentin on 15/10/2016.
 */

public class LatoTextView extends TextView {

    //Constructor with custom font, here Lato
    public LatoTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf"));
    }
}
