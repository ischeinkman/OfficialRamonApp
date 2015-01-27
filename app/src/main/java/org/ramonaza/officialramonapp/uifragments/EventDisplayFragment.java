package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;

/*
 * Created by Ilan Scheinkman
 */
public class EventDisplayFragment extends Fragment {
    private static final String EVENT_HTML = "org.ramonaza.officialramonapp.EVENT_HTML";

    private String eventHtml;


    public static EventDisplayFragment newInstance(String eventHtmlText) {
        EventDisplayFragment fragment = new EventDisplayFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_HTML, eventHtmlText);
        fragment.setArguments(args);
        return fragment;
    }

    public EventDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventHtml = getArguments().getString(EVENT_HTML);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_event_display, container, false);
        TextView eventTextView=(TextView) rootView.findViewById(R.id.EventDisplayTextView);
        eventTextView.setText(Html.fromHtml(eventHtml));
        Log.d("DEBUG","Returning eventview");
        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
