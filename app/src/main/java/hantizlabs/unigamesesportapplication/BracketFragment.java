package hantizlabs.unigamesesportapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 08.11.2016.
 */

public class BracketFragment extends Fragment {
    int numberedRounds;
    private int allRounds;
    private Bracket bracket;
    private List<Match> matchList = new ArrayList<>();
    PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bracket_layout, container, false);
        new TaskTournament().execute(); //gets JSON data from toornament API

        //thanks to this we only need single class for both brackets
        //basically we configure how should the fragment content behave based on its type
        Bundle args = getArguments();
        if((boolean)args.get("isWinnerBracket")) {
            numberedRounds = 3;
            allRounds = 6;
            bracket = Bracket.WINNERS;
        }
        else {
            numberedRounds = 7;
            allRounds = 10;
            bracket = Bracket.LOSERS;
        }

        //adds correct amount of tabs to fragment + Grand Final if Winner's bracket
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        for(int i=1; i<=numberedRounds; i++) tabLayout.addTab(tabLayout.newTab().setText("Round " + i));
        tabLayout.addTab(tabLayout.newTab().setText("Quarterfinal"));
        tabLayout.addTab(tabLayout.newTab().setText("Semifinal"));
        tabLayout.addTab(tabLayout.newTab().setText("Final"));
        if(bracket == Bracket.WINNERS)
            tabLayout.addTab(tabLayout.newTab().setText("Grand Final"));

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        //adapter for pages with data
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null) {
                if (bracket == Bracket.WINNERS)
                    activity.getSupportActionBar().setTitle(R.string.wbracket);
                else
                    activity.getSupportActionBar().setTitle(R.string.lbracket);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args;
            List<Match> listMatchToPass;
            List<bracketDataFragment> tabs = new ArrayList<>();

            //fills each tab with correct data
            for(int i=1; i<=allRounds; i++) {
                bracketDataFragment tab = new bracketDataFragment();
                args = new Bundle();
                listMatchToPass = fillListFragment(i, bracket);
                args.putParcelableArrayList("passedList", (ArrayList<? extends Parcelable>) listMatchToPass);
                tab.setArguments(args);
                tabs.add(tab);
            }
            if(bracket == Bracket.WINNERS) { //grand final case
                bracketDataFragment tab = new bracketDataFragment();
                args = new Bundle();
                listMatchToPass = fillListFragment(1, Bracket.GRANDFINAL);
                args.putParcelableArrayList("passedList", (ArrayList<? extends Parcelable>) listMatchToPass);
                tab.setArguments(args);
                tabs.add(tab);
            }
            return tabs.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    public ArrayList<Match> fillListFragment(int roundNumber, Bracket bracket) {
        ArrayList<Match> filledListMatch = new ArrayList<>();

        for(int i = 0; i < matchList.size(); i++){
            if(matchList.get(i).getRound() == roundNumber && matchList.get(i).getBracket() == bracket) {
                filledListMatch.add(matchList.get(i));
            }
        }

        return filledListMatch;
    }

    private class TaskTournament extends AsyncTask<URL, Integer, String> {
        HttpURLConnection urlConnection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        TaskTournament() {}

        protected String doInBackground(URL... urls) {
            URL retrieveURL;

            try {
                retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57ee13fe140ba0cd2a8b4593/matches?api_key=s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    matchList = readJsonStream(in);
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
            pagerAdapter.notifyDataSetChanged();
        }

        //JSON Reader magic
        List<Match> readJsonStream(InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try {
                return readMatchesArray(reader);
            } finally {
                reader.close();
            }
        }

        List<Match> readMatchesArray(JsonReader reader) throws IOException {
            List<Match> matches = new ArrayList<>();

            reader.beginArray();
            while (reader.hasNext()) {
                matches.add(readMatch(reader));
            }
            reader.endArray();
            return matches;
        }

        Match readMatch(JsonReader reader) throws IOException {
            String status = null, date = null, time = null;
            Bracket bracket = null;
            int id = -1, round = -1;
            Match.Team team1 = null, team2 = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if(reader.peek() != JsonToken.NULL) {
                    switch (name) {
                        case "number":
                            id = reader.nextInt();
                            break;
                        case "status":
                            status = reader.nextString().toUpperCase();
                            break;
                        case "group_number":
                            int groupTemp = reader.nextInt();
                            if (groupTemp == 1) {
                                bracket = Bracket.WINNERS;
                            } else if (groupTemp == 2) {
                                bracket = Bracket.LOSERS;
                            } else if (groupTemp == 3) {
                                bracket = Bracket.GRANDFINAL;
                            }
                            break;
                        case "round_number":
                            round = reader.nextInt();
                            break;
                        case "date":
                            String tempDateRaw = reader.nextString();
                            String[] tempDate = tempDateRaw.split("T");
                            String[] tempTime = tempDate[1].split(":");

                            date = tempDate[0];
                            time = tempTime[0] + ":" + tempTime[1];
                            break;
                        case "opponents":
                            int counter = 0;
                            reader.beginArray();
                            while (reader.hasNext()) {
                                if (counter == 0) {
                                    team1 = readTeam(reader);
                                } else {
                                    team2 = readTeam(reader);
                                }
                                counter++;
                            }
                            reader.endArray();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return new Match(team1, team2, id, round, bracket, status, date, time);
        }

        Match.Team readTeam(JsonReader reader) throws IOException {
            String teamName = null;
            int score = 0;
            boolean winner = false;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if(reader.peek() != JsonToken.NULL) {
                    switch (name) {
                        case "participant":
                            if(reader.peek() == JsonToken.STRING) {
                                teamName = reader.nextString();
                            }
                            else if(reader.peek() == JsonToken.BEGIN_OBJECT) {
                                reader.beginObject();
                                while(reader.hasNext()) {
                                    String nameInParticipant = reader.nextName();
                                    if(nameInParticipant.equals("name")) {
                                        teamName = reader.nextString();
                                    }
                                    else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                            }
                            break;
                        case "score":
                            score = reader.nextInt();
                            break;
                        case "result":
                            winner = reader.nextInt() == 1;
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();

            return new Match.Team(teamName, score, winner);
        }
    }
}