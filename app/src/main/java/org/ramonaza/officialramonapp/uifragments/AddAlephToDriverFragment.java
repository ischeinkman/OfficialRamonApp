package org.ramonaza.officialramonapp.uifragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import org.ramonaza.officialramonapp.datafiles.InfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;

import java.util.List;

public class AddAlephToDriverFragment extends InfoWrapperCheckBoxesFragment {

    int driverId;

    public static AddAlephToDriverFragment newInstance(int driverId){
        AddAlephToDriverFragment fragment=new AddAlephToDriverFragment();
        Bundle args=new Bundle();
        args.putInt("DriverId", driverId);
        fragment.setArguments(args);
        return fragment;
    }

    public AddAlephToDriverFragment(){}

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
                " "+ConDriveDatabaseContract.ContactListTable._ID +" NOT IN ("+
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
                ContentValues cValues = new ContentValues();
                cValues.put(ConDriveDatabaseContract.RidesListTable.COLUMN_ALEPH,aleph.getId());
                cValues.put(ConDriveDatabaseContract.RidesListTable.COLUMN_CAR,driverId);
                db.insert(ConDriveDatabaseContract.RidesListTable.TABLE_NAME, null, cValues);
            }
            return null;
        }


    }
}
