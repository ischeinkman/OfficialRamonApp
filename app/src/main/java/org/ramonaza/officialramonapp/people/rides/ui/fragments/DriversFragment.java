package org.ramonaza.officialramonapp.people.rides.ui.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperButtonListFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;
import org.ramonaza.officialramonapp.people.rides.ui.activities.AddCustomDriverActivity;
import org.ramonaza.officialramonapp.people.rides.ui.activities.RidesDriverManipActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriversFragment extends InfoWrapperButtonListFragment {


    public static DriversFragment newInstance() {
        DriversFragment fragment = new DriversFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DriversFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutId = R.layout.fragment_rides_drivers;
        View rootView = super.onCreateView(inflater, container, savedInstanceState); //Retrieve the parent's view to manipulate
        Button customButton = (Button) rootView.findViewById(R.id.AddCustomDriveButton);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customDriverIntent = new Intent(getActivity(), AddCustomDriverActivity.class);
                startActivity(customDriverIntent);
            }
        });
        refreshData();
        return rootView;
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        Intent intent=new Intent(getActivity(), RidesDriverManipActivity.class);
        intent.putExtra("DriverId",mWrapper.getId());
        startActivity(intent);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
        return handler.getDrivers(null,ContactDatabaseContract.DriverListTable.COLUMN_NAME+" DESC");
    }
}



