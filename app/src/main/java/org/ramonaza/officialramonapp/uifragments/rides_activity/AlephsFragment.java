package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.AddCustomAlephActivity;
import org.ramonaza.officialramonapp.activities.PresentListedAlephActivity;
import org.ramonaza.officialramonapp.datafiles.InfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;
import org.ramonaza.officialramonapp.uifragments.InfoWrapperButtonListFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlephsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlephsFragment extends InfoWrapperButtonListFragment {

    private static final String EXTRA_PARENT_ACTIVITY="parent activity";

    public static AlephsFragment newInstance() {
        AlephsFragment fragment = new AlephsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AlephsFragment() {
        // Required empty public constructor
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
        ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues updateVals=new ContentValues();
        updateVals.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,0); //Deletes the aleph from the present list on the button click, because there is no other functionality required for alephs other than add and delete.
        String[] idArg=new String[]{""+mWrapper.getId()};
        db.update(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,
                updateVals,
                ConDriveDatabaseContract.ContactListTable._ID + "=?",
                idArg);
        refreshData();
    }

    @Override
    public List<? extends InfoWrapper> generateInfo() {
        ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelpter.getReadableDatabase();
        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=1 ORDER BY %s ASC",ConDriveDatabaseContract.ContactListTable.TABLE_NAME,ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,ConDriveDatabaseContract.ContactListTable.COLUMN_NAME),null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }
}
