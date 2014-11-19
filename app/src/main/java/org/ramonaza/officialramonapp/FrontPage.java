package org.ramonaza.officialramonapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class FrontPage extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if(position==0){
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();}
        else if(position==1){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragmentSong.newInstance(position + 1))
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            //case 3:
                //mTitle = getString(R.string.title_section3);
                //break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.front_page, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /**if (id == R.id.action_settings) {
            return true;
        }**/
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setTitle("Frontpage");
            View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);
            LinearLayout myLayout;
            myLayout = (LinearLayout) rootView.findViewById(R.id.linearlayoutfront);
            /**Button b1=new Button(rootView.getContext());
            b1.setText("Hello World");
            b1.setBackgroundColor(000000);
            myLayout.addView(b1);
            HashMap<String,Button> ooo=new HashMap<String, Button>();
            String[] ddd={"l","m"}; //TODO: Parse Site
            for(String a:ddd){
                ooo.put(a,new Button(rootView.getContext()));
                ooo.get(a).setText(a);
                ooo.get(a).setBackgroundColor(000000);
                myLayout.addView(ooo.get(a));
            }**/
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FrontPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }
    public static class PlaceholderFragmentSong extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static int heyletseeifthisworks; //int to store the position in
        public static PlaceholderFragmentSong newInstance(int sectionNumber) {
            PlaceholderFragmentSong fragment = new PlaceholderFragmentSong();
            Bundle args = new Bundle();
            heyletseeifthisworks=sectionNumber;
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragmentSong() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setTitle("Songs");
            View rootView = inflater.inflate(R.layout.fragment_song_page, container, false);
            Button upYouMenButton=(Button) rootView.findViewById(R.id.upYouMenButton);
            upYouMenButton.setOnClickListener(new View.OnClickListener(){ //Confused by this syntax? So am I.
                public void onClick(View v){  //We appear to be defining an instance method for a nameless OnClickListener, though I am not sure.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction().replace(R.id.container, UpYouMenFragment.newInstance(heyletseeifthisworks + 1))
                            .addToBackStack(null); //At least this is clear. Begin the transaction, replace the frag with the new one, and then allow it to be back buttoned in a single line.
                transaction.commit();
                }
            });
            Button forTommorowAndTodayButton =(Button) rootView.findViewById(R.id.forTommorowAndTodayButton);
            forTommorowAndTodayButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, ForTommorowAndTodayFrag.newInstance(heyletseeifthisworks + 1))
                            .addToBackStack(null);
                    transaction.commit();
                }
            });
            Button worldOfAZAButton =(Button) rootView.findViewById(R.id.worldOfAZAButton);
            worldOfAZAButton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, WorldOfAZAFrag.newInstance(heyletseeifthisworks + 1))
                            .addToBackStack(null);
                    transaction.commit();
                }
            });
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FrontPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }



    }





    //Fragment for Up You Men
    public static class UpYouMenFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public static UpYouMenFragment newInstance(int sectionNumber) {
            UpYouMenFragment fragment = new UpYouMenFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public UpYouMenFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setTitle("Up You Men");
            View rootView = inflater.inflate(R.layout.up_you_men_text, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FrontPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    //Fragment for Tomorrow And Today
    public static class ForTommorowAndTodayFrag extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public static ForTommorowAndTodayFrag newInstance(int sectionNumber) {
            ForTommorowAndTodayFrag fragment = new ForTommorowAndTodayFrag();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ForTommorowAndTodayFrag() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setTitle("For Tomorrow And Today");
            View rootView = inflater.inflate(R.layout.tomorow_and_today_text, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FrontPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    //Fragment for World of AZA
    public static class WorldOfAZAFrag extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";


        public static WorldOfAZAFrag newInstance(int sectionNumber) {
            WorldOfAZAFrag fragment = new WorldOfAZAFrag();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public WorldOfAZAFrag() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            actionBar.setTitle("World of AZA");
            View rootView = inflater.inflate(R.layout.world_of_aza_text, container, false);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FrontPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }
}
