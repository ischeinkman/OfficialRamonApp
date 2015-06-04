package org.ramonaza.officialramonapp.datafiles.condrive_database;

import android.database.Cursor;

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
            temp.setId(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.DriverListTable._ID)));
            temp.setName(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.DriverListTable.COLUMN_NAME)));
            temp.setSpots(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.DriverListTable.COLUMN_SPACE)));
            temp.setArea(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.DriverListTable.COLUMN_AREA)));
            rvalList.add(temp);
        }while(queryResults.moveToNext());
        return rvalList.toArray(new DriverInfoWrapper[rvalList.size()]);
    }
}
