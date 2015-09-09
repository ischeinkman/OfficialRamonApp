package org.ramonaza.officialramonapp.events.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ramonaza.officialramonapp.events.backend.EventInfoWrapper;
import org.ramonaza.officialramonapp.events.backend.EventRSSHandler;
import org.ramonaza.officialramonapp.events.ui.activities.EventPageActivity;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragStyles.InfoWrapperTextListFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by Ilan Scheinkman
 */
public class EventListFragment extends InfoWrapperTextListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private EventRSSHandler handler;




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
        intent.putExtra(EventPageActivity.EVENT_DATA, handler.getEventRSS(mWrapper.getId()));
        startActivity(intent);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        String url = "http://69.195.124.114/~ramonaza/events/feed/";
        handler=new EventRSSHandler(url, true);
        return handler.getEventsFromRss();
    }
}
