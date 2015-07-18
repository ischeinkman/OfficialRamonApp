package org.ramonaza.officialramonapp.people.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;

/**
 * A simple database helper for accessing the contact/rides database.
 * All database interactions should be handled by a separate handler.
 * Created by ilanscheinkman on 3/12/15.
 */
public class ContactDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="ContactDriverDatabase";
    public static final int DATABASE_VERSION=3;
    private Context context;

    public ContactDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactDatabaseContract.DriverListTable.CREATE_TABLE);
        db.execSQL(ContactDatabaseContract.ContactListTable.CREATE_TABLE);
        db.execSQL(ContactDatabaseContract.RidesListTable.CREATE_TABLE);
        try {
            genDatabaseFromCSV(db);
        } catch (ContactCSVReadError contactCSVReadError) {
            contactCSVReadError.printStackTrace();
        }
    }
    public void onDelete(SQLiteDatabase db){
        db.execSQL(ContactDatabaseContract.DriverListTable.DELETE_TABLE);
        db.execSQL(ContactDatabaseContract.ContactListTable.DELETE_TABLE);
        db.execSQL(ContactDatabaseContract.RidesListTable.DELETE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactDatabaseContract.DELETE_TABLES);
        db.execSQL(ContactDatabaseContract.DriverListTable.CREATE_TABLE);
        db.execSQL(ContactDatabaseContract.ContactListTable.CREATE_TABLE);
        db.execSQL(ContactDatabaseContract.RidesListTable.CREATE_TABLE);
        try {
            genDatabaseFromCSV(db);
        } catch (ContactCSVReadError contactCSVReadError) {
            contactCSVReadError.printStackTrace();
        }
    }
    public void genDatabaseFromCSV(SQLiteDatabase db) throws ContactCSVReadError{
        ContactInfoWrapper[] csvContacts= ContactCSVHandler.getCtactInfoListFromCSV(context);
        for(ContactInfoWrapper cAleph: csvContacts){
            ContentValues value=new ContentValues();
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_NAME, cAleph.getName());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_ADDRESS,cAleph.getAddress());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_EMAIL,cAleph.getEmail());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_GRADYEAR,cAleph.getGradYear());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_PHONE,cAleph.getPhoneNumber());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_SCHOOL,cAleph.getSchool());
            value.put(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,0);
            long rowId=db.insert(ContactDatabaseContract.ContactListTable.TABLE_NAME,null,value);
            if(rowId==-1l){
                throw new ContactCSVReadError("Null Contact Read", cAleph);
            }
        }
    }

    public void addContact(ContactInfoWrapper toAdd, SQLiteDatabase db) throws ContactCSVReadError{
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_NAME, toAdd.getName());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_ADDRESS,toAdd.getAddress());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_EMAIL,toAdd.getEmail());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_GRADYEAR,toAdd.getGradYear());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PHONE,toAdd.getPhoneNumber());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_SCHOOL,toAdd.getSchool());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,toAdd.isPresent());
        long rowId=db.insert(ContactDatabaseContract.ContactListTable.TABLE_NAME,null,value);
        if(rowId==-1l){
            throw new ContactCSVReadError("Null Contact Read", toAdd);
        }
    }
    public void addDriver(DriverInfoWrapper toAdd, SQLiteDatabase db){
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_NAME,toAdd.getName());
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_SPACE,toAdd.getSpots());
        long rowId=db.insert(ContactDatabaseContract.DriverListTable.TABLE_NAME,null,value);
    }
    
    public class ContactCSVReadError extends Exception{
        public ContactCSVReadError(String errorMessage, ContactInfoWrapper erroredAleph){
            super(String.format("%s ON %s",errorMessage,erroredAleph));

        }
    }
}
