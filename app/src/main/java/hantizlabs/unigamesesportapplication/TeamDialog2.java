package hantizlabs.unigamesesportapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ian on 05/11/2016.
 */

public class TeamDialog2 extends DialogFragment {

    String[] teamMembers={"Tima","Guntab","Cartim","Devis","Francis"};//getActivity().getIntent().getExtras().getStringArrayList("members");
    String[] teamMembers2={"Tima2","Guntab2","Cartim2","Devis2","Francis2"};
    String[] positions={"ADC","Mid","Support","Carry","Otro"};
    int[] pictures={R.drawable.pive,R.drawable.gato,R.drawable.piveazul,R.drawable.piva,R.drawable.toro};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.team_dialog2, container, false);
        ListView list = (ListView) view.findViewById(R.id.list1);

        ListViewAdapter adapter;
        Bundle bundle=this.getArguments();
        int key=bundle.getInt("Teams");
        switch (key){
            case 1:
                adapter= new ListViewAdapter(getContext(),teamMembers,pictures,positions);
                list.setAdapter(adapter);
                getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
                //getDialog().setTitle("Team Members");
                break;
            case 2:
                adapter= new ListViewAdapter(getContext(),teamMembers2,pictures,positions);
                list.setAdapter(adapter);
                getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
                //getDialog().setTitle("Team Members");
                break;
        }
        //ListViewAdapter adapter = new ListViewAdapter(getContext(),)
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
        Context context;
        String[] content;
        int[] images;
        String[] pos;
        public ListViewAdapter(Context context,String[] content, int[] images,String[] pos) {
            super(context, R.layout.team_dialog_list, content);
            this.context=context;
            this.content=content;
            this.images=images;
            this.pos=pos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.team_dialog_list, null, true);

            TextView names = (TextView) rowView.findViewById(R.id.name_p2);
            TextView positions =(TextView) rowView.findViewById(R.id.position_p2);
            ImageView image = (ImageView) rowView.findViewById(R.id.position_pic2);

            names.setText(content[position]);
            positions.setText(pos[position]);
            positions.setTextSize(15);
            image.setImageResource(images[position]);
            return rowView;

        }
    }
}
