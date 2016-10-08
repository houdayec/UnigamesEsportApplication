package hantizlabs.unigamesesportapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Corentin on 20/09/2016.
 */
public class Fragment_Infos extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.infos_layout, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Team 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Team 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Team 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Team 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Team 5"));
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        viewPager.setAdapter(new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    TabFragment1 tab1 = new TabFragment1();
                    return tab1;
                case 1:
                    TabFragment2 tab2 = new TabFragment2();
                    return tab2;
                case 2:
                    BracketFragment tab3 = new BracketFragment();
                    return tab3;
                case 3:
                    TabFragment4 tab4 = new TabFragment4();
                    return tab4;
                case 4:
                    TabFragment5 tab5 = new TabFragment5();
                    return tab5;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}


