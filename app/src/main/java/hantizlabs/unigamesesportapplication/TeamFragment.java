package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian on 28/09/2016.
 * This is new functionality compared to what we showed during the presentation,
 * PLEASE KEEP IT IN MIND
 */

public class TeamFragment extends Fragment {
    Bundle bundle = new Bundle();
    private List<Team> teamsList = new ArrayList<>();
    TeamAdapter teamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_fragment, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        new TaskTeams().execute();

        teamAdapter = new TeamAdapter(view.getContext());
        gridView.setAdapter(teamAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team clickedTeam = (Team) parent.getItemAtPosition(position);

                TeamDialog2 dialogFragment = new TeamDialog2();
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                bundle.putStringArrayList("lineup", clickedTeam.getLineup());
                bundle.putString("teamName", clickedTeam.getName());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "lineup");
            }
        });
        return view;
    }

    private class TaskTeams extends AsyncTask<URL, Integer, String> {
        HttpURLConnection urlConnection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        TaskTeams() {
        }

        protected String doInBackground(URL... urls) {
            URL retrieveURL;

            try {
                retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57cd7725140ba0a80f8b4567/participants?api_key=s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk&with_lineup=1");
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    teamsList = readJsonStream(in);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            teamAdapter.notifyDataSetChanged();
        }

        //JSON Reader magic
        List<Team> readJsonStream(InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try {
                return readTeamsArray(reader);
            } finally {
                reader.close();
            }
        }

        List<Team> readTeamsArray(JsonReader reader) throws IOException {
            List<Team> matches = new ArrayList<>();

            reader.beginArray();
            while (reader.hasNext()) {
                matches.add(readTeam(reader));
            }
            reader.endArray();
            return matches;
        }

        Team readTeam(JsonReader reader) throws IOException {
            String teamName = null;
            List<String> lineup = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (reader.peek() != JsonToken.NULL) {
                    if (name.equals("name")) {
                        teamName = reader.nextString();
                    } else if (name.equals("lineup")) {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            lineup.add(readLineup(reader));
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Team(teamName, lineup);
        }

        String readLineup(JsonReader reader) throws IOException {
            String teamMember = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (reader.peek() != JsonToken.NULL) {
                    if (name.equals("name")) {
                        teamMember = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return teamMember;
        }
    }

    /**
     * Used for the gridview
     * each team has a picture and an image associated
     */
    class Team {
        String name;
        List<String> lineup;
        int photo;

        Team(String name, List<String> lineup) {
            this.name = name;
            this.photo = 0;
            this.lineup = lineup;
        }

        public String getName() {
            return name;
        }

        int getPhoto() {
            return photo;
        }

        void setPhoto(int drawable) {
            photo = drawable;
        }

        public int getId() {
            return name.hashCode();
        }

        ArrayList<String> getLineup() {
            return (ArrayList<String>) lineup;
        }
    }

    /**
     * Adapter for the teams
     * instantiate the layout and add the pictures and the
     * textviews
     */
    class TeamAdapter extends BaseAdapter {
        private Context context;

        TeamAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return teamsList.size();
        }

        @Override
        public Object getItem(int position) {
            return teamsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageTeam;
            Team team = teamsList.get(position);

            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.item_tile, parent, false);
            }

            team.setPhoto(context.getResources().getIdentifier(team.getName().replaceAll("\\s+", "").toLowerCase(), "drawable", context.getPackageName()));
            imageTeam = (ImageView) convertView.findViewById(R.id.tile_picture);

            Glide.with(imageTeam.getContext()).
                    load(teamsList.get(position).getPhoto()).
                    into(imageTeam);

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
}
