package org.ramonaza.officialramonapp.people.rides.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;

/**
 * Created by ilanscheinkman on 7/16/15.
 */
public class DriverDatabaseHandler {

    SQLiteDatabase db;

    public DriverDatabaseHandler(Context context){
        ContactDatabaseHelper dbHelper=new ContactDatabaseHelper(context);
        this.db=dbHelper.getWritableDatabase();
    }

    public DriverDatabaseHandler(SQLiteDatabase db){
        this.db=db;
    }

    public DriverInfoWrapper[] getDrivers(String[] whereclauses){
        String query=String.format("SELECT * FROM %s ",ContactDatabaseContract.DriverListTable.TABLE_NAME);
        if(whereclauses.length>0){
            query+="WHERE ";
            for (String wc:whereclauses) query+=wc+" AND ";
        }
        Cursor queryResults=db.rawQuery(query,null);
        queryResults.moveToFirst();
        if (queryResults.getCount()==0){
            return new DriverInfoWrapper[0];
        }
        DriverInfoWrapper[] drivers=new DriverInfoWrapper[queryResults.getCount()];
        int i=0;
        queryResults.moveToFirst();
        do {
            DriverInfoWrapper temp=new DriverInfoWrapper();
            temp.setId(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable._ID)));
            temp.setName(queryResults.getString(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_NAME)));
            temp.setSpots(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_SPACE)));
            temp.setArea(queryResults.getInt(queryResults.getColumnIndexOrThrow(ContactDatabaseContract.DriverListTable.COLUMN_AREA)));
            drivers[i]=temp;
            i++;
        }while(queryResults.moveToNext());
        return drivers;
    }

    public void addDriver(DriverInfoWrapper toAdd) throws DriverReadError{
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_NAME,toAdd.getName());
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_SPACE,toAdd.getSpots());
        long rowId=db.insert(ContactDatabaseContract.DriverListTable.TABLE_NAME,null,value);
        if(rowId==-1l) throw new DriverReadError("Null Driver Read",toAdd);
        else toAdd.setId((int) rowId);
    }

    public void deleteDriver(DriverInfoWrapper toDelete){
        db.delete(ContactDatabaseContract.DriverListTable.TABLE_NAME,"?=?",new String[]{
                ContactDatabaseContract.DriverListTable._ID,
                ""+toDelete.getId()
        });
    }

    public void updateDriver(DriverInfoWrapper toUpdate) throws DriverReadError{
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_NAME,toUpdate.getName());
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_SPACE,toUpdate.getSpots());
        long rowId=db.update(ContactDatabaseContract.DriverListTable.TABLE_NAME, value, "?=?", new String[]{
                ContactDatabaseContract.DriverListTable._ID,
                ""+toUpdate.getId()
        });
        if(rowId==-1l) throw new DriverReadError("Null Driver Read",toUpdate);
    }

    public void updateRides(DriverInfoWrapper[] drivers, ContactInfoWrapper[] driverless){
        String driverlessIDs="(";
        for(ContactInfoWrapper aleph: driverless){
            driverlessIDs+=aleph.getId()+", ";
        }
        driverlessIDs+=")";
        db.delete(ContactDatabaseContract.DriverListTable.TABLE_NAME,"? IN ?",new String[]{
                ContactDatabaseContract.RidesListTable.COLUMN_ALEPH,
                driverlessIDs});
        for(DriverInfoWrapper driver:drivers){
            for(ContactInfoWrapper aleph:driver.getAlephsInCar()){
                ContentValues cValues = new ContentValues();
                cValues.put(ContactDatabaseContract.RidesListTable.COLUMN_ALEPH,aleph.getId());
                cValues.put(ContactDatabaseContract.RidesListTable.COLUMN_CAR,driver.getId());
                db.insert(ContactDatabaseContract.RidesListTable.TABLE_NAME, null, cValues);
            }
        }
    }


    public class DriverReadError extends Exception{
        public DriverReadError(String errorMessage, DriverInfoWrapper erroredDriver){
            super(String.format("%s ON %s",errorMessage,erroredDriver));

        }
    }
}
