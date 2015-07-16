package org.ramonaza.officialramonapp.people.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ilanscheinkman on 7/16/15.
 */
public class ContactDatabaseHandler {

    SQLiteDatabase db;

    public ContactDatabaseHandler(Context context){
        ContactDatabaseHelper dbHelper=new ContactDatabaseHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public ContactDatabaseHandler(SQLiteDatabase db){
        this.db=db;
    }

    public void close(){
        db.close();
    }

    public void addContact(ContactInfoWrapper toAdd) throws ContactCSVReadError{
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_NAME, toAdd.getName());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_ADDRESS,toAdd.getAddress());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_EMAIL,toAdd.getEmail());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_GRADYEAR,toAdd.getGradYear());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PHONE,toAdd.getPhoneNumber());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_SCHOOL,toAdd.getSchool());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,toAdd.isPresent());
        long rowId=db.insert(ContactDatabaseContract.ContactListTable.TABLE_NAME,null,value);
        if(rowId==-1l) throw new ContactCSVReadError("Null Contact Read", toAdd);
        else toAdd.setId((int) rowId);
    }

    public void updateContact(ContactInfoWrapper toUpdate) throws ContactCSVReadError{
        ContentValues value=new ContentValues();
        value.put(ContactDatabaseContract.DriverListTable.COLUMN_NAME,toUpdate.getName());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_NAME, toUpdate.getName());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_ADDRESS,toUpdate.getAddress());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_EMAIL,toUpdate.getEmail());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_GRADYEAR,toUpdate.getGradYear());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PHONE,toUpdate.getPhoneNumber());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_SCHOOL,toUpdate.getSchool());
        value.put(ContactDatabaseContract.ContactListTable.COLUMN_PRESENT,toUpdate.isPresent());
        long rowId=db.update(ContactDatabaseContract.DriverListTable.TABLE_NAME, value, "id=?", new String[]{""+toUpdate.getId()});
        if(rowId==-1l) throw new ContactCSVReadError("Null Contact Read",toUpdate);
    }

    public void deleteContact(ContactInfoWrapper toDelete){
        db.delete(ContactDatabaseContract.ContactListTable.TABLE_NAME,"?=?", new String[]{
                ContactDatabaseContract.ContactListTable._ID,
                ""+toDelete.getId()
        });
    }

    public void updateField(String field, String value, ContactInfoWrapper[] toUpdate){
        String alephIDs="(";
        for(ContactInfoWrapper aleph:toUpdate) alephIDs+=aleph.getId()+", ";
        alephIDs+=")";
        db.execSQL("UPDATE ? SET ? = ? WHERE ? IN ?",new String[]{
                ContactDatabaseContract.ContactListTable.TABLE_NAME,
                field,
                value,
                ContactDatabaseContract.ContactListTable._ID,
                alephIDs
        });

    }

    public class ContactCSVReadError extends Exception{
        public ContactCSVReadError(String errorMessage, ContactInfoWrapper erroredAleph){
            super(String.format("%s ON %s",errorMessage,erroredAleph));

        }
    }
}
