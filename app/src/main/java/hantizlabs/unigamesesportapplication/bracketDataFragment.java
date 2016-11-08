package hantizlabs.unigamesesportapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class bracketDataFragment extends Fragment {
    ArrayList<Match> passedListMatch = new ArrayList<>();
    BracketAdapter bracketAdapter;
    RecyclerView matchList;

    public bracketDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bracket_data, container, false);
        Bundle args = getArguments();
        passedListMatch = (ArrayList<Match>) args.get("passedList");

        matchList = (RecyclerView) rootView.findViewById(R.id.matchList);
        bracketAdapter = new BracketAdapter(passedListMatch);
        matchList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        matchList.setAdapter(bracketAdapter);
        matchList.setLayoutManager(llm);
        matchList.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
