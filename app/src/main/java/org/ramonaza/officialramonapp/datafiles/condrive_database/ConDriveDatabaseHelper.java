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
    public static final int DATABASE_VERSION=1;
    Context context;

    public ConDriveDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConDriveDatabaseContract.CREATE_TABLES);
        try {
            genDatabaseFromCSV(db);
        } catch (ContactCSVReadError contactCSVReadError) {
            contactCSVReadError.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void genDatabaseFromCSV(SQLiteDatabase db) throws ContactCSVReadError{
        List<ContactInfoWrapper> csvContacts= ContactInfoWrapperGenerator.getCtactInfoListFromCSV(context);
        int id=1;
        for(ContactInfoWrapper cAleph: csvContacts){
            ContentValues value=new ContentValues();
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_CONTACT_ID,id);
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_NAME, cAleph.getName());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_ADDRESS,cAleph.getAddress());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_EMAIL,cAleph.getEmail());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_GRADYEAR,cAleph.getGradYear());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PHONE,cAleph.getPhoneNumber());
            value.put(ConDriveDatabaseContract.ContactListTable.COLUMN_SCHOOL,cAleph.getSchool());
            long rowId=db.insert(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,null,value);
            if(rowId==-1l){
                throw new ContactCSVReadError("Null Contact Read", cAleph);
            }
            id++;
        }
    }
    public class ContactCSVReadError extends Exception{
        public ContactCSVReadError(String errorMessage, ContactInfoWrapper erroredAleph){
            super(String.format("%s ON %s",errorMessage,erroredAleph));

        }
    }
}
