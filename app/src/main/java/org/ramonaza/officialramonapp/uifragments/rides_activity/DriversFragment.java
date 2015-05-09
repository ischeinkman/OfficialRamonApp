package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.AddCustomDriverActivity;
import org.ramonaza.officialramonapp.datafiles.InfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.DriverInfoWrapperGenerator;
import org.ramonaza.officialramonapp.uifragments.InfoWrapperButtonListFragment;

import java.util.List;

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
        //TODO: Create an activity for the manipulation of drivers
    }

    @Override
    public List<? extends InfoWrapper> generateInfo() {
        ConDriveDatabaseHelper dbHelpter = new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelpter.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s DESC", ConDriveDatabaseContract.DriverListTable.TABLE_NAME, ConDriveDatabaseContract.DriverListTable.COLUMN_NAME), null);
        return DriverInfoWrapperGenerator.fromDataBase(cursor);
    }
}



