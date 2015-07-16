package org.ramonaza.officialramonapp.people.rides.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapperGenerator;

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
        ContactDatabaseHelper dbH=new ContactDatabaseHelper(getActivity());
        SQLiteDatabase db=dbH.getWritableDatabase();
        String query=("SELECT * FROM "+ ContactDatabaseContract.ContactListTable.TABLE_NAME+
                " WHERE "+ ContactDatabaseContract.ContactListTable.COLUMN_PRESENT+"=1 AND"+
                " "+ ContactDatabaseContract.ContactListTable._ID +" IN ("+
                "SELECT "+ ContactDatabaseContract.RidesListTable.COLUMN_ALEPH+" FROM "+ ContactDatabaseContract.RidesListTable.TABLE_NAME+")");
        Cursor cursor=db.rawQuery(query,null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }

    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            ContactDatabaseHelper dbHelper=new ContactDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            for (InfoWrapper aleph:params) {
                db.delete(ContactDatabaseContract.RidesListTable.TABLE_NAME, ContactDatabaseContract.RidesListTable.COLUMN_ALEPH+"="+aleph.getId()+" and "+ ContactDatabaseContract.RidesListTable.COLUMN_CAR+"="+driverId,null);
            }
            return null;
        }


    }
}
