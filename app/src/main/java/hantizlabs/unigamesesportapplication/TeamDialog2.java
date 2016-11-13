package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ian on 05/11/2016.
 * This is new functionality compared to what we showed during the presentation,
 * PLEASE KEEP IT IN MIND
 */

public class TeamDialog2 extends DialogFragment {
    // Array with names of images next to names
    int[] pictures = {R.drawable.pive, R.drawable.gato, R.drawable.piveazul, R.drawable.piva, R.drawable.toro, R.drawable.nami, R.drawable.yi, R.drawable.zac};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListViewAdapter adapter;
        Bundle bundle = this.getArguments();
        String teamName = bundle.getString("teamName");
        ArrayList<String> lineup = bundle.getStringArrayList("lineup");

        getDialog().setTitle(teamName);

        View view = inflater.inflate(R.layout.team_dialog2, container, false);
        ListView list = (ListView) view.findViewById(R.id.list1);

        adapter = new ListViewAdapter(getContext(), lineup, pictures);
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    public class ListViewAdapter extends ArrayAdapter<String> {
        // Commented "pos" is here to stay because we should get players in-game position later on
        // so it will be used later (hopefully)
        Context context;
        ArrayList<String> content;
        int[] images;

        //String[] pos;
        ListViewAdapter(Context context, ArrayList<String> content, int[] images/*,String[] pos*/) {
            super(context, R.layout.team_dialog_list, content);
            this.context = context;
            this.content = content;
            this.images = images;
            //this.pos=pos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.team_dialog_list, null, true);

            TextView names = (TextView) rowView.findViewById(R.id.name_p2);
            //TextView positions =(TextView) rowView.findViewById(R.id.position_p2);
            ImageView image = (ImageView) rowView.findViewById(R.id.position_pic2);

            names.setText(content.get(position));
            // positions.setText(pos[position]);
            // positions.setTextSize(15);
            image.setImageResource(images[position]);
            return rowView;

        }
    }
}
