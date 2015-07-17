package org.ramonaza.officialramonapp.people.rides.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperButtonListFragment;
import org.ramonaza.officialramonapp.people.activities.AddCustomAlephActivity;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.rides.activities.PresentListedAlephActivity;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlephsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlephsFragment extends InfoWrapperButtonListFragment {

    private static final String EXTRA_PARENT_ACTIVITY="parent activity";

    private SQLiteDatabase db;

    public static AlephsFragment newInstance() {
        AlephsFragment fragment = new AlephsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AlephsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContactDatabaseHelper dbh=new ContactDatabaseHelper(getActivity());
        this.db=dbh.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutId=R.layout.fragment_rides_alephs;
        View rootView= super.onCreateView(inflater,container,savedInstanceState); //Retrieve the parent's view to manipulate
        Button listAdd=(Button) rootView.findViewById(R.id.AddPresetAlephButton);
        listAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presListIntent=new Intent(getActivity(), PresentListedAlephActivity.class);
                startActivity(presListIntent);
            }
        });
        Button customAdd=(Button) rootView.findViewById(R.id.AddCustomAlephButton);
        customAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customAlephIntent = new Intent(getActivity(), AddCustomAlephActivity.class);
                customAlephIntent.putExtra(EXTRA_PARENT_ACTIVITY,getActivity().getClass());
                startActivity(customAlephIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        //TODO: Create an activity for manipulation of present alephs
        RidesDatabaseHandler handler=new RidesDatabaseHandler(db);
        handler.setAlephAbsent(mWrapper.getId());
        refreshData();
    }

    @Override
    public InfoWrapper[] generateInfo() {
        ContactDatabaseHandler handler=new ContactDatabaseHandler(db);
        return handler.getContacts(new String[]{
                ContactDatabaseContract.ContactListTable.COLUMN_PRESENT+"=1",
        }, ContactDatabaseContract.ContactListTable.COLUMN_NAME+" ASC");
    }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    public void onResume() {
        ContactDatabaseHelper databaseHelper=new ContactDatabaseHelper(getActivity());
        db=databaseHelper.getWritableDatabase();
        super.onResume();
    }
}
