package org.ramonaza.officialramonapp.people.rides.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.fragments.InfoWrapperCheckBoxesFragment;
import org.ramonaza.officialramonapp.people.backend.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ConDriveDatabaseHelper;
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
        ConDriveDatabaseHelper dbH=new ConDriveDatabaseHelper(getActivity());
        SQLiteDatabase db=dbH.getWritableDatabase();
        String query=("SELECT * FROM "+ ConDriveDatabaseContract.ContactListTable.TABLE_NAME+
                " WHERE "+ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT+"=1 AND"+
                " "+ConDriveDatabaseContract.ContactListTable._ID +" IN ("+
                "SELECT "+ConDriveDatabaseContract.RidesListTable.COLUMN_ALEPH+" FROM "+ConDriveDatabaseContract.RidesListTable.TABLE_NAME+")");
        Cursor cursor=db.rawQuery(query,null);
        return ContactInfoWrapperGenerator.fromDataBase(cursor);
    }

    public class SubmitFromList extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            for (InfoWrapper aleph:params) {
                db.delete(ConDriveDatabaseContract.RidesListTable.TABLE_NAME,ConDriveDatabaseContract.RidesListTable.COLUMN_ALEPH+"="+aleph.getId()+" and "+ConDriveDatabaseContract.RidesListTable.COLUMN_CAR+"="+driverId,null);
            }
            return null;
        }


    }
}
