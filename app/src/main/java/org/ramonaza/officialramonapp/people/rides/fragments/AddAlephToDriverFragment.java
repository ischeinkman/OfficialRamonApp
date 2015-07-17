package org.ramonaza.officialramonapp.people.rides.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

import java.util.Arrays;
import java.util.List;

public class AddAlephToDriverFragment extends InfoWrapperCheckBoxesFragment {

    int driverId;

    public static AddAlephToDriverFragment newInstance(int driverId){
        AddAlephToDriverFragment fragment=new AddAlephToDriverFragment();
        Bundle args=new Bundle();
        args.putInt("DriverId", driverId);
        fragment.setArguments(args);
        return fragment;
    }

    public AddAlephToDriverFragment(){}

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
        ContactDatabaseHandler dbhandler=new ContactDatabaseHandler(getActivity());
        return dbhandler.getContacts(new String[]{
                ContactDatabaseContract.ContactListTable.COLUMN_PRESENT + "=1",
                ContactDatabaseContract.ContactListTable._ID + " NOT IN (" + "SELECT " + ContactDatabaseContract.RidesListTable.COLUMN_ALEPH + " FROM " + ContactDatabaseContract.RidesListTable.TABLE_NAME + ")"
        }, ContactDatabaseContract.ContactListTable.COLUMN_NAME+" ASC");
    }

    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            RidesDatabaseHandler rideshandler=new RidesDatabaseHandler(getActivity());
            ContactInfoWrapper[] alephs= Arrays.copyOf(params,params.length,ContactInfoWrapper[].class);
            rideshandler.addAlephsToCar(driverId,alephs);
            return null;
        }


    }
}
