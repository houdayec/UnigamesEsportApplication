package hantizlabs.unigamesesportapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


/**
 * Created by Paweł on 26/09/2016.
 */
public class EventFragment extends Fragment {
    SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_fragment, container, false);

        // To add new questions and answers we just have to add respective lines in arrays.xml file
        sectionAdapter = new SectionedRecyclerViewAdapter();
        String[] questions = this.getResources().getStringArray(R.array.questions);
        String[] answers = this.getResources().getStringArray(R.array.answers);

        for (int i = 0; i < Math.min(questions.length, answers.length); ++i) {
            sectionAdapter.addSection(new InfoSection(questions[i], answers[i]));
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }

    // Sets the name on the toolbar - please make similar function for every fragment
    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle(R.string.action_event);
        }
    }

    class InfoSection extends StatelessSection {
        String title;
        String content;
        boolean expanded = false;

        public InfoSection(String title, String content) {
            super(R.layout.event_fragment_head, R.layout.event_fragment_content);

            this.title = title;
            this.content = content;
        }

        @Override
        public int getContentItemsTotal() {
            return expanded ? 1 : 0;
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ContentViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ContentViewHolder itemHolder = (ContentViewHolder) holder;

            itemHolder.tvContent.setText(content);
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded = !expanded;
                    headerHolder.imgArrow.setImageResource(
                            expanded ? R.drawable.ic_keyboard_arrow_up : R.drawable.ic_keyboard_arrow_down
                    );
                    sectionAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView tvTitle;
        private final ImageView imgArrow;

        public HeaderViewHolder(View view) {
            super(view);

            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView tvContent;

        public ContentViewHolder(View view) {
            super(view);

            rootView = view;
            tvContent = (TextView) view.findViewById(R.id.tvItem);
        }
    }
}
