package org.ramonaza.officialramonapp.uifragments.frontal_activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.FrontalActivity;
import org.ramonaza.officialramonapp.datafiles.EventInfoWrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The fragment for the front page. TODO: Add event data.
 * Created by ilanscheinkman on 1/9/15.
 */
public class LoadingEventPageFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LoadingEventPageFragment newInstance(int sectionNumber) {
        LoadingEventPageFragment fragment = new LoadingEventPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public LoadingEventPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Frontpage");
        View rootView = inflater.inflate(R.layout.fragment_event_loading, container, false);
        LinearLayout myLayout;
        myLayout = (LinearLayout) rootView.findViewById(R.id.linearlayoutfront);
        new GetEventData(getFragmentManager()).execute("http://69.195.124.114/~ramonaza/events/feed/");
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public class GetEventData extends AsyncTask<String, Void, String> {

        FragmentManager fragmentManager;

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder builder = new StringBuilder(100000);

            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    Log.d("DEBUG","RESPONCE GET");
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        builder.append(s);
                        Log.d("DEBUG", "Line Got\n"+s);
                    }

                } catch (Exception e) {
                    Log.d("DEBUG",e.getMessage());
                }
            }

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if(!isAdded()) return;
            Log.d("DEBUG","Begin frag trans\n"+result);
            ArrayList<EventInfoWrapper> events=new ArrayList<EventInfoWrapper>();
            String[] itemmedRSS=result.split("<item>");
            List<String> aListRSS=new ArrayList<String>();
            Collections.addAll(aListRSS, itemmedRSS);
            aListRSS.remove(0);
            Log.d("DEBUG",""+itemmedRSS.length);
            for (String eventRSS:aListRSS){
                Log.d("DEBUG",eventRSS);
                events.add(new EventInfoWrapper(eventRSS));
            }
            try {
                fragmentManager.beginTransaction().replace(R.id.container, EventListFragment.newInstance(events)).commit();
            }catch (Exception e){
                Log.e("LoadingEvPgFrag","GetEventData onPost",e);
            }
        }
        public GetEventData(FragmentManager inFragMan){
            this.fragmentManager=inFragMan;
        }
    }

}
