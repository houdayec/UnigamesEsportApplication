package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Intent.getIntent;

/**
 * Created by Ian on 04/10/2016.
 */

public class ListTeamFragment extends DialogFragment {

    //Creation of teams
    String[] teamMembers={"Tima","Guntab","Cartim","Devis","Francis"};
    String[] teamMembers2={"Tima2","Guntab2","Cartim2","Devis2","Francis2"};
    private StaggeredGridLayoutManager gridPlayers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= (View)inflater.inflate(R.layout.players_of_team_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.players);
        recyclerView.setHasFixedSize(true);

        gridPlayers = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridPlayers);


        final Button button = (Button)view.findViewById(R.id.close_button);
        //Listening the button
        /*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                getDialog().dismiss();
            }
        });
        */

        TeamsRecyclerViewAdapter rcAdapter;
        //Reuse same fragment with bundle
        Bundle bundle=this.getArguments();
        //Retrieve the key passed from the TeamFragment
        int key=bundle.getInt("Teams");
        Log.d("Key value", String.valueOf(key));
        switch (key){
            //Depending on the key we display different team information
            case 1:
                rcAdapter= new TeamsRecyclerViewAdapter(getContext(),teamMembers);
                recyclerView.setAdapter(rcAdapter);
                getDialog().setTitle("Team Members");
                break;
            case 2:
                rcAdapter= new TeamsRecyclerViewAdapter(getContext(),teamMembers2);
                recyclerView.setAdapter(rcAdapter);
                getDialog().setTitle("Team Members");
                break;
        }
        return view;
    }

    //Size of dialog
    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    //Creation of adapter for displaying members information in recycler view
    public class TeamsRecyclerViewAdapter  extends RecyclerView.Adapter<TeamsViewHolders> {
        Context ctx;
        String[] teamMembers;


        public TeamsRecyclerViewAdapter(Context ctx, String[] teamMembers){
            this.ctx=ctx;
            this.teamMembers=teamMembers;
        }

        @Override
        public TeamsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_players, null);
            TeamsViewHolders v = new TeamsViewHolders(layoutView);

            return v;
        }

        @Override
        public void onBindViewHolder(TeamsViewHolders holder, int position) {

            if(position==0) {
               // holder.name.setGravity(Gravity.CENTER);
                //((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
                //holder.itemView.setLayoutParams(layoutParams);
                StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                holder.itemView.setLayoutParams(layoutParams);
                //holder.itemView.setBackgroundColor(Color.YELLOW);
                holder.name.setBackgroundColor(getResources().getColor(R.color.colorTeamLeader));
                holder.name.setTextColor(getResources().getColor(R.color.colorPrimary));

            }else {
                //holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            holder.name.setText(teamMembers[position]);
        }

        @Override
        public int getItemCount() {
            return teamMembers.length;
        }
    }


    public class TeamsViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        public TeamsViewHolders(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.playerName);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

   /* private String[] values={"Tima","Guntab","Cartim","Devis","Francis"};
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
    }*/


}
