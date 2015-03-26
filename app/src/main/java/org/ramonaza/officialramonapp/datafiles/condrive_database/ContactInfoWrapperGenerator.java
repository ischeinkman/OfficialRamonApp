package org.ramonaza.officialramonapp.datafiles.condrive_database;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 3/14/15.
 */
public abstract class ContactInfoWrapperGenerator {
    private static final String CSV_NAME="AlephNameSchYAddMailNum.csv";
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
        List<String[]> alephCSVline = new ArrayList<String[]>();



        try {
            InputStream csvStream = getCSVStream(context);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                alephCSVline.add(line);
            }
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
        Log.d("DEBUG", "List size: " + alephCSVline.size());
        return alephCSVline;
    }
    private static InputStream getCSVStream(Context context) throws IOException{
        InputStream rval;

        File downloadDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] ddFiles=downloadDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.equals(CSV_NAME);

            }
        });
        if(ddFiles.length>0){
            File dFile=ddFiles[0];
            rval=new FileInputStream(dFile);
        }else{
            rval = context.getAssets().open(CSV_NAME);
        }
        return rval;
    }


    public static List<ContactInfoWrapper> getCtactInfoListFromCSV(Context context){
        List<String[]>cInfo=readAlephInfoCsv(context);
        List<ContactInfoWrapper> rval=new ArrayList<ContactInfoWrapper>();
        for(String[] argumentss:cInfo){
            rval.add(createContactInfoWrapperFromCSVargs(argumentss));
        }
        return rval;
    }

    public static ContactInfoWrapper createContactInfoWrapperFromCSVargs(String[] args) {
        ContactInfoWrapper rRapper=new ContactInfoWrapper();
        rRapper.setName(args[0]);
        rRapper.setSchool(args[1]);
        rRapper.setGradYear(args[2]);
        rRapper.setAddress(args[3]);
        rRapper.setEmail(args[4]);
        rRapper.setPhoneNumber(args[5]);
        return rRapper;
    }
}
