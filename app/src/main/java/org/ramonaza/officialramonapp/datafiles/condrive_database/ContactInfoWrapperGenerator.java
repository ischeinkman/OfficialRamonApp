package org.ramonaza.officialramonapp.datafiles.condrive_database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 3/14/15.
 */
public abstract class ContactInfoWrapperGenerator {

    public static List<ContactInfoWrapper> fromDataBase(Cursor queryResults){
        List<ContactInfoWrapper> rval=new ArrayList<ContactInfoWrapper>();
        queryResults.moveToFirst();
        if (queryResults.getCount()==0){
            return rval;
        }
        do {
            ContactInfoWrapper temp=new ContactInfoWrapper();
            temp.setId(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_CONTACT_ID)));
            temp.setName(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_NAME)));
            temp.setSchool(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_SCHOOL)));
            temp.setPhoneNumber(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_PHONE)));
            temp.setGradYear(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_GRADYEAR)));
            temp.setEmail(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_EMAIL)));
            temp.setAddress(queryResults.getString(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_ADDRESS)));
            if(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT))==1){
                temp.setPresent(true);
            }else if(queryResults.getInt(queryResults.getColumnIndexOrThrow(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT))==0){
                temp.setPresent(false);
            }
            rval.add(temp);
        }while(queryResults.moveToNext());
        return rval;
    }

    public static List<String[]> readAlephInfoCsv(Context context) {
        List<String[]> questionList = new ArrayList<String[]>();
        AssetManager assetManager = context.getAssets();

        try {
            String CSV_PATH="AlephNameSchYAddMailNum.csv";
            InputStream csvStream = assetManager.open(CSV_PATH);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                questionList.add(line);
            }
        } catch (IOException e) {
            Log.d("DEBUG", e.getMessage());
        }
        Log.d("DEBUG","List size: "+questionList.size());
        return questionList;
    }

    public static List<ContactInfoWrapper> getCtactInfoListFromCSV(Context context){
        List<String[]>cInfo=readAlephInfoCsv(context);
        List<ContactInfoWrapper> rval=new ArrayList<ContactInfoWrapper>();
        for(String[] argumentss:cInfo){
            rval.add(new ContactInfoWrapper(argumentss));
        }
        return rval;
    }
}
