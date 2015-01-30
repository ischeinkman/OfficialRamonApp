package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.EventPageActivity;
import org.ramonaza.officialramonapp.datafiles.EventInfoWrapper;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by Ilan Scheinkman
 */
public class EventListFragment extends Fragment {
    private static final String EVENT_DATA = "org.ramonaza.officialramonapp.EVENT_DATA";

    private List<EventInfoWrapper> events;


    public static EventListFragment newInstance(ArrayList<EventInfoWrapper> events) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EVENT_DATA, events);
        fragment.setArguments(args);
        return fragment;
    }

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            events = getArguments().getParcelableArrayList(EVENT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_event_list, container, false);
        List<Button> eventButtons=new ArrayList<Button>();
        LinearLayout layout=(LinearLayout) rootView.findViewById(R.id.EventDisplayScrollLayout);
        for(EventInfoWrapper event: events){
            Log.d("DEBUG",event.getName());
            Button temp=new Button(this.getActivity());
            temp.setBackground(getResources().getDrawable(R.drawable.songbuttonlayout));
            temp.setText(event.getDate());
            EventButtonListener buttonClickListener=new EventButtonListener();
            buttonClickListener.setEventInfoWrapper(event);
            temp.setOnClickListener(buttonClickListener);
            eventButtons.add(temp);
        }
        for(Button eventButton:eventButtons){
            layout.addView(eventButton);
        }
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

    public class EventButtonListener implements View.OnClickListener{
        EventInfoWrapper eventInfoWrapper;
        public EventButtonListener setEventInfoWrapper(EventInfoWrapper input){
            this.eventInfoWrapper=input;
            return this;
        }
        public void onClick(View v){
            Intent intent=new Intent(getActivity(), EventPageActivity.class);
            intent.putExtra(EVENT_DATA,eventInfoWrapper);
            startActivity(intent);
        }
    }


}
