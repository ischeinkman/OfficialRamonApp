package org.ramonaza.officialramonapp.people.rides.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapperGenerator;

import java.util.Arrays;
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
    public ContactInfoWrapper[] generateInfo() {
        ContactDatabaseHelper dbHelpter=new ContactDatabaseHelper(getActivity());
        SQLiteDatabase db=dbHelpter.getReadableDatabase();
        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=0 ORDER BY %s ASC", ContactDatabaseContract.ContactListTable.TABLE_NAME, ContactDatabaseContract.ContactListTable.COLUMN_PRESENT, ContactDatabaseContract.ContactListTable.COLUMN_NAME),null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }


    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            ContactDatabaseHandler handler=new ContactDatabaseHandler(getActivity());
            ContactInfoWrapper[] alephs= Arrays.copyOf(params,params.length,ContactInfoWrapper[].class);
            handler.updateField(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,"1",alephs);
            return null;
        }


    }

}
