package hantizlabs.unigamesesportapplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Ian on 28/09/2016.
 */

public class TeamFragment extends Fragment {
    //private String[] valuesT1={"Tima","Guntab","Cartim","Devis","Francis"};
    //private String[] positionsT1={"Support","ADC","Jungle","Other","Other"};
    Bundle bundle= new Bundle();
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

                /**
                 * change the ALL the dialogs for a reyclerviewDialog (ListTeamFragment) and make
                 * it look fancier
                 */


                if(aux.getName().equals("Team1")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                    //Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
                   /* AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater2 = getLayoutInflater(null);
                    View v=inflater2.inflate(R.layout.title_dialog, null);
                    builderSingle.setCustomTitle(v);
                   // builderSingle.setTitle("Team Members");
                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(new AdapterList(getContext(),valuesT1,positionsT1), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });;
                    builderSingle.show();*/

                    /*FragmentManager fm = getFragmentManager();
                    ListTeamFragment dialogFragment = new ListTeamFragment ();
                    bundle.putInt("Teams",1);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");*/

                    /*Fragment listFrag = new ListTeamFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_grid, listFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/
                }
                if(aux.getName().equals("Team2")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",1);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                    /*FragmentManager fm = getFragmentManager();
                    ListTeamFragment dialogFragment = new ListTeamFragment ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");*/
                    /*AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater2 = getLayoutInflater(null);
                    View v=inflater2.inflate(R.layout.title_dialog, null);
                    builderSingle.setCustomTitle(v);
                    // builderSingle.setTitle("Team Members");
                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(new AdapterList(getContext(),valuesT1,positionsT1), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });;
                    builderSingle.show();*/
                }
                if(aux.getName().equals("Team3")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",1);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                    /*FragmentManager fm = getFragmentManager();
                    TeamsDialog dialogFragment = new TeamsDialog ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");*/
                }
                if(aux.getName().equals("Team4")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                }
                if (aux.getName().equals("Team5")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",1);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                    /*FragmentManager fm = getFragmentManager();
                    ListTeamFragment dialogFragment = new ListTeamFragment ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");*/
                }
                if(aux.getName().equals("Team6")){
                    FragmentManager fm = getFragmentManager();
                    TeamDialog2 dialogFragment = new TeamDialog2 ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");
                    /*FragmentManager fm = getFragmentManager();
                    ListTeamFragment dialogFragment = new ListTeamFragment ();
                    bundle.putInt("Teams",2);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(fm, "Sample Fragment");*/
                }
                //
            }
        });
        return view;
    }

    /**
     * Used for the gridview
     * each team has a picture and an image associated
     */
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

    /**
     * Adapter for the teams
     * instantiate the layout and add the pictures and the
     * textviews
     */
    class TeamAdapter extends BaseAdapter {
        private Context context;
        public  Team[] TEAMS = {
                new Team("Team1",R.drawable.origen),
                new Team("Team2",R.drawable.g2logo),
                new Team("Team3",R.drawable.counter),
                new Team("Team4",R.drawable.vitality),
                new Team("Team5",R.drawable.fnatic),
                new Team("Team6",R.drawable.r),
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
            //nameTeam = (TextView) convertView.findViewById(R.id.tile_title);




            //nameTeam.setText(TEAMS[position].getName());//item.getName());

            Glide.with(imagenTeam.getContext()).
                    load(TEAMS[position].getPhoto()).
                    into(imagenTeam);

            return convertView;
        }
    }

    /**
     * changing the name of the toolbar
     */
    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle(R.string.team_event);
        }
    }

    /**
     * Adapter for the listview inside the dialogs
     * pass the context and the arrays of values
     * it could be improved with some pictures
     *
     * remove if you implement the recycler view
     */

   /* public class AdapterList extends ArrayAdapter<String> {
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
