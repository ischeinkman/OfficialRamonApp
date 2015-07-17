package org.ramonaza.officialramonapp.people.rides.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

import java.util.List;

public class RemoveAlephFromDriverFragment extends InfoWrapperCheckBoxesFragment {

    int driverId;

    public static RemoveAlephFromDriverFragment newInstance(int driverId){
        RemoveAlephFromDriverFragment fragment=new RemoveAlephFromDriverFragment();
        Bundle args=new Bundle();
        args.putInt("DriverId", driverId);
        fragment.setArguments(args);
        return fragment;
    }

    public RemoveAlephFromDriverFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.driverId=getArguments().getInt("DriverId");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSubmitButton(List<InfoWrapper> checked, List<InfoWrapper> unchecked) {
        InfoWrapper[] addToCar=new InfoWrapper[checked.size()];
        addToCar=checked.toArray(addToCar);
        new SubmitFromList().execute(addToCar);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
        return handler.getAlephsInCar(driverId);
    }

    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

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
