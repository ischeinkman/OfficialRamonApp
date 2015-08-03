package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragStyles.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;
import org.ramonaza.officialramonapp.people.rides.ui.activities.RidesDriverManipActivity;

public class RemoveAlephFromDriverFragment extends InfoWrapperCheckBoxesFragment {

    public static final String EXTRA_DRIVERID= RidesDriverManipActivity.EXTRA_DRIVERID;
    private int driverId;

    public static RemoveAlephFromDriverFragment newInstance(int driverId){
        RemoveAlephFromDriverFragment fragment=new RemoveAlephFromDriverFragment();
        Bundle args=new Bundle();
        args.putInt(EXTRA_DRIVERID, driverId);
        fragment.setArguments(args);
        return fragment;
    }

    public RemoveAlephFromDriverFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.driverId=getArguments().getInt(EXTRA_DRIVERID);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSubmitButton(InfoWrapper[] checked, InfoWrapper[] unchecked) {
        new SubmitFromList().execute(checked);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
        return handler.getAlephsInCar(driverId);
    }

    private class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
            for (InfoWrapper aleph:params) {
                handler.removeAlephFromCar(aleph.getId());
            }
            return null;
        }


    }
}
