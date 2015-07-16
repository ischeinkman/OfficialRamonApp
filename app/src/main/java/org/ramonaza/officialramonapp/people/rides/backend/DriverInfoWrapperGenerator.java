package org.ramonaza.officialramonapp.people.rides.backend;

import android.database.Cursor;

import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 3/30/15.
 */
public abstract class DriverInfoWrapperGenerator {
    public static DriverInfoWrapper[] fromDataBase(Cursor queryResults){
        List<DriverInfoWrapper> rvalList=new ArrayList<DriverInfoWrapper>();
        queryResults.moveToFirst();
        if (queryResults.getCount()==0){
            return new DriverInfoWrapper[0];
        }
        do {
            DriverInfoWrapper temp=new DriverInfoWrapper();
            temp.setId(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable._ID)));
            temp.setName(queryResults.getString(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_NAME)));
            temp.setSpots(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_SPACE)));
            temp.setArea(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_AREA)));
            rvalList.add(temp);
        }while(queryResults.moveToNext());
        return rvalList.toArray(new DriverInfoWrapper[rvalList.size()]);
    }
}
