package org.ramonaza.officialramonapp.datafiles.condrive_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by ilanscheinkman on 3/12/15.
 */
public class ConDriveDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="ContactDriverDatabase";
    public static final int DATABASE_VERSION=3;
    Context context;

    public ConDriveDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConDriveDatabaseContract.DriverListTable.CREATE_TABLE);
        db.execSQL(ConDriveDatabaseContract.ContactListTable.CREATE_TABLE);
        db.execSQL(ConDriveDatabaseContract.RidesListTable.CREATE_TABLE);
        try {
            genDatabaseFromCSV(db);
        } catch (ContactCSVReadError contactCSVReadError) {
            contactCSVReadError.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConDriveDatabaseContract.DELETE_TABLES);
        db.execSQL(ConDriveDatabaseContract.DriverListTable.CREATE_TABLE);
        db.execSQL(ConDriveDatabaseContract.ContactListTable.CREATE_TABLE);
        db.execSQL(ConDriveDatabaseContract.RidesListTable.CREATE_TABLE);
        try {
            genDatabaseFromCSV(db);
        } catch (ContactCSVReadError contactCSVReadError) {
        }
    }
    public void genDatabaseFromCSV(SQLiteDatabase db) throws ContactCSVReadError{
        List<ContactInfoWrapper> csvContacts= ContactInfoWrapperGenerator.getCtactInfoListFromCSV(context);
        for(ContactInfoWrapper cAleph: csvContacts){
            ContentValues value=new ContentValues();
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_NAME, cAleph.getName());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_ADDRESS,cAleph.getAddress());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_EMAIL,cAleph.getEmail());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_GRADYEAR,cAleph.getGradYear());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PHONE,cAleph.getPhoneNumber());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_SCHOOL,cAleph.getSchool());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,0);
            long rowId=db.insert(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,null,value);
            if(rowId==-1l){
                throw new ContactCSVReadError("Null Contact Read", cAleph);
            }
        }
    }

    public void addContact(ContactInfoWrapper toAdd, SQLiteDatabase db) throws ContactCSVReadError{
        ContentValues value=new ContentValues();
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_NAME, toAdd.getName());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_ADDRESS,toAdd.getAddress());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_EMAIL,toAdd.getEmail());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_GRADYEAR,toAdd.getGradYear());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PHONE,toAdd.getPhoneNumber());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_SCHOOL,toAdd.getSchool());
        value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,toAdd.isPresent());
        long rowId=db.insert(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,null,value);
        if(rowId==-1l){
            throw new ContactCSVReadError("Null Contact Read", toAdd);
        }
    }
    public void addDriver(DriverInfoWrapper toAdd, SQLiteDatabase db){
        ContentValues value=new ContentValues();
        value.put(ConDriveDatabaseContract.DriverListTable.COLUMN_NAME,toAdd.getName());
        value.put(ConDriveDatabaseContract.DriverListTable.COLUMN_SPACE,toAdd.getSpots());
        long rowId=db.insert(ConDriveDatabaseContract.DriverListTable.TABLE_NAME,null,value);
    }
    public class ContactCSVReadError extends Exception{
        public ContactCSVReadError(String errorMessage, ContactInfoWrapper erroredAleph){
            super(String.format("%s ON %s",errorMessage,erroredAleph));

        }
    }
}
