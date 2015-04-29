package org.ramonaza.officialramonapp.uifragments.frontal_activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import org.ramonaza.officialramonapp.activities.ContactDataActivity;
import org.ramonaza.officialramonapp.activities.FrontalActivity;
import org.ramonaza.officialramonapp.datafiles.InfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;
import org.ramonaza.officialramonapp.uifragments.InfoWrapperButtonListFragment;

import java.util.List;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactListFragment  extends InfoWrapperButtonListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.ALEPH_ID";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";
    private static final String PAGE_NAME="Contact List";
    public int fraglayer;

    public static ContactListFragment newInstance(int sectionNumber) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.fraglayer=sectionNumber;
        return fragment;
    }

    public ContactListFragment() {
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        Intent intent = new Intent(getActivity(), ContactDataActivity.class);
        intent.putExtra(EXTRA_LAYER, PAGE_NAME);
        intent.putExtra(EXTRA_CONTRUCTION_INFO, mWrapper.getId());
        Log.d("ContactListFrag",""+mWrapper.getId());
        startActivity(intent);
    }

    @Override
    public List<ContactInfoWrapper> generateInfo() {
        ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelpter.getReadableDatabase();
        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s ASC", ConDriveDatabaseContract.ContactListTable.TABLE_NAME, ConDriveDatabaseContract.ContactListTable.COLUMN_NAME), null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }



}


