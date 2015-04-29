package org.ramonaza.officialramonapp.uifragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.ramonaza.officialramonapp.datafiles.InfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PresentAlephFromListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresentAlephFromListFragment extends InfoWrapperCheckBoxesFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PresentAlephFromListFragment.
     */
    public static PresentAlephFromListFragment newInstance() {
        PresentAlephFromListFragment fragment = new PresentAlephFromListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSubmitButton(List<InfoWrapper> checked, List<InfoWrapper> unchecked) {
        InfoWrapper[] toSetPresent=new InfoWrapper[checked.size()];
        toSetPresent=checked.toArray(toSetPresent);
        new SubmitFromList().execute(toSetPresent);
    }

    @Override
    public List<? extends InfoWrapper> generateInfo() {
        ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelpter.getReadableDatabase();
        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=0 ORDER BY %s ASC", ConDriveDatabaseContract.ContactListTable.TABLE_NAME,ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,ConDriveDatabaseContract.ContactListTable.COLUMN_NAME),null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }


    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            for (InfoWrapper untypedContact:params) {
                ContactInfoWrapper contact=(ContactInfoWrapper) untypedContact;
                ContentValues cValues = new ContentValues();
                cValues.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT, 1);
                Log.d("PrAlList.Submit", String.format("Submitting id: %d", contact.getId()));
                db.update(ConDriveDatabaseContract.ContactListTable.TABLE_NAME, cValues, ConDriveDatabaseContract.ContactListTable._ID + "=?", new String[]{"" + contact.getId()});

            }
            return null;
        }


    }

}
