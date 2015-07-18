package org.ramonaza.officialramonapp.events.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ramonaza.officialramonapp.events.backend.EventInfoWrapper;
import org.ramonaza.officialramonapp.events.ui.activities.EventPageActivity;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperButtonListFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by Ilan Scheinkman
 */
public class EventListFragment extends InfoWrapperButtonListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String EVENT_DATA = "org.ramonaza.officialramonapp.EVENT_DATA";




    public static EventListFragment newInstance(int sectionNumber) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        Intent intent=new Intent(getActivity(), EventPageActivity.class);
        intent.putExtra(EVENT_DATA,(EventInfoWrapper) mWrapper);
        startActivity(intent);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        StringBuilder builder = new StringBuilder(100000);

        String url = "http://69.195.124.114/~ramonaza/events/feed/";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s;
            while ((s = buffer.readLine()) != null) {
                builder.append(s);
            }

        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }

        String html = builder.toString();
        String[] itemmedRSS = html.split("<item>");
        int itemmedLength=itemmedRSS.length-1;
        EventInfoWrapper[] events = new EventInfoWrapper[itemmedLength];
        for(int i=0;i<itemmedLength;i++){
            events[i]=new EventInfoWrapper(itemmedRSS[i+1]);
        }
        return events;
    }
}
