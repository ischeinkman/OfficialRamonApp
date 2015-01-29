package org.ramonaza.officialramonapp.uifragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        View rootView = inflater.inflate(R.layout.fragment_front_loading_page, container, false);
        LinearLayout myLayout;
        myLayout = (LinearLayout) rootView.findViewById(R.id.linearlayoutfront);
        new GetEventData().execute("http://69.195.124.114/~ramonaza/events/feed/");
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public class GetEventData extends AsyncTask<String, Void, String> {
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
                        Log.d("DEBUG", "Line Got");
                    }

                } catch (Exception e) {
                    Log.d("DEBUG",e.getMessage());
                }
            }

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("DEBUG","Begin frag trans");
            String[] splitFeed=result.split("<br/>");
            String parsedResult="";
            for(int i=1;i<=splitFeed.length-2;i++){
                parsedResult+=splitFeed[i]+"\n";
            }

            getFragmentManager().beginTransaction().replace(R.id.container,EventDisplayFragment.newInstance(parsedResult)).commit();
        }
    }

}
