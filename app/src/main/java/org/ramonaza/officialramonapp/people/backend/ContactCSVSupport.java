package org.ramonaza.officialramonapp.people.backend;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ilanscheinkman on 9/3/15.
 */
public class ContactCSVSupport {


    private static final String CSV_EXTENSION = ".csv";
    private static final String DEFAULT_CSV_NAME="DefaultContactFileTemplate.csv";

    private static File getCSVFile(Context context){
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] ddFiles = downloadDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                String extension = filename.substring(filename.lastIndexOf("."));
                return extension.equals(CSV_EXTENSION);

            }
        });
        if (ddFiles.length > 0) return ddFiles[0];
        else return null;
    }

    private static InputStream getContactReader(Context context){
        File csvFile=getCSVFile(context);
        if(csvFile != null) try {
            return new FileInputStream(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return context.getAssets().open(DEFAULT_CSV_NAME);
        } catch (IOException e) {
            return null;
        }
    }

    public static ContactCSVHandler getCSVHandler(Context context){
        File csvFile=getCSVFile(context);
        if(csvFile != null) try {
            return new ContactCSVHandler(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ContactCSVHandler(getContactReader(context));
    }
}
