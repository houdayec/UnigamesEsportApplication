package hantizlabs.unigamesesportapplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.R.attr.fragment;

/**
 * Created by Ian on 28/09/2016.
 */

public class TeamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_5, container, false);
        View view= (View)inflater.inflate(R.layout.team_fragment, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(new TeamAdapter(view.getContext()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team aux = (Team) parent.getItemAtPosition(position);
                if(aux.getName().equals("Team1")){
                    Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                    Fragment listFrag = new ListTeamFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_grid, listFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                //
            }
        });
        return view;
    }
    class Team {
        String name;
        int photo;

        public Team(String name, int photo) {
            this.name = name;
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public int getPhoto() {
            return photo;
        }

        public int getId() {
            return name.hashCode();
        }
    }
    class TeamAdapter extends BaseAdapter {
        private Context context;
        public  Team[] TEAMS = {
                new Team("Team1",R.drawable.a_avator),
                new Team("Team2",R.drawable.b_avator),
                new Team("Team3",R.drawable.c_avator),
                new Team("Team4",R.drawable.d_avator),
                new Team("Team5",R.drawable.e_avator),
                new Team("Team6",R.drawable.f_avator),
        };
        public TeamAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return TEAMS.length;
        }

        @Override
        public Object getItem(int position) {
            return TEAMS[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imagenTeam;
            TextView nameTeam;
            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_tile, parent, false);
            }

            imagenTeam = (ImageView) convertView.findViewById(R.id.tile_picture);
            nameTeam = (TextView) convertView.findViewById(R.id.tile_title);

            //final Team item = (Team) getItem(position);
            //imagenTeam.setImageResource(TEAMS[position].getPhoto());//item.getPhoto());
            nameTeam.setText(TEAMS[position].getName());//item.getName());

            Glide.with(imagenTeam.getContext()).
                    load(TEAMS[position].getPhoto()).
                    into(imagenTeam);

            return convertView;
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle(R.string.team_event);
        }
    }


}
