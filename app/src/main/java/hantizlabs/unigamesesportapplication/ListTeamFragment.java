package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Ian on 04/10/2016.
 */

public class ListTeamFragment extends ListFragment {

    private String[] values={"Tima","Guntab","Cartim","Devis","Francis"};
    private String[] positions={"Support","ADC","Jungle","Other","Other"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.list_players, container, false);

        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(new AdapterList(view.getContext(),values,positions));
        return view;
    }

    public class AdapterList extends ArrayAdapter<String>{
        private Context context;
        private String[] names;
        private String[] positions;
        public AdapterList(Context context, String[] objects, String[] positions) {
            super(context,-1, objects);
            this.context=context;
            names=objects;
            this.positions=positions;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            TextView name;
            TextView posit;

            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_list, parent, false);


            }

            name=(TextView) convertView.findViewById(R.id.name_player);
            posit=(TextView) convertView.findViewById(R.id.position_player);

            name.setText(names[position]);
            posit.setText(positions[position]);

            return convertView;
        }
    }


}
