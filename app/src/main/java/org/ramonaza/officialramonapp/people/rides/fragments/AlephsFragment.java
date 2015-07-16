package org.ramonaza.officialramonapp.people.rides.fragments;


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
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperButtonListFragment;
import org.ramonaza.officialramonapp.people.activities.AddCustomAlephActivity;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapperGenerator;
import org.ramonaza.officialramonapp.people.rides.activities.PresentListedAlephActivity;

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
        ContactDatabaseHelper dbHelper=new ContactDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues updateVals=new ContentValues();
        updateVals.put(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,0); //Deletes the aleph from the present list on the button click, because there is no other functionality required for alephs other than add and delete.
        String[] idArg=new String[]{""+mWrapper.getId()};
        db.update(ContactDatabaseContract.ContactListTable.TABLE_NAME,
                updateVals,
                ContactDatabaseContract.ContactListTable._ID + "=?",
                idArg);
        refreshData();
    }

    @Override
    public InfoWrapper[] generateInfo() {
        ContactDatabaseHelper dbHelpter=new ContactDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelpter.getReadableDatabase();
        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=1 ORDER BY %s ASC", ContactDatabaseContract.ContactListTable.TABLE_NAME, ContactDatabaseContract.ContactListTable.COLUMN_PRESENT, ContactDatabaseContract.ContactListTable.COLUMN_NAME),null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }
}
