package org.ramonaza.officialramonapp.people.backend;

import android.content.Context;
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
public abstract class ContactCSVHandler {
    private static final String CSV_NAME = "AlephNameSchYAddMailNum.csv";

    private static List<String[]> readAlephInfoCsv(Context context) {
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
        return alephCSVline;
    }

    private static InputStream getCSVStream(Context context) throws IOException {
        InputStream rval;

        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File[] ddFiles = downloadDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.equals(CSV_NAME);

            }
        });
        if (ddFiles.length > 0) {
            File dFile = ddFiles[0];
            rval = new FileInputStream(dFile);
        } else {
            rval = context.getAssets().open("DefaultContactFileTemplate.csv");
        }
        return rval;
    }

    /**
     * Retrieves contacts from a CSV file in the downloads folder.
     * @param context the context to use
     * @return the contacts from the CSV file in an array
     */
    public static ContactInfoWrapper[] getCtactInfoListFromCSV(Context context) {
        List<String[]> cInfo = readAlephInfoCsv(context);
        ContactInfoWrapper[] rval = new ContactInfoWrapper[cInfo.size()];
        for (int i = 0; i < cInfo.size(); i++) {
            rval[i] = createContactInfoWrapperFromCSVargs(cInfo.get(i));
        }
        return rval;
    }

    private static ContactInfoWrapper createContactInfoWrapperFromCSVargs(String[] args) {
        ContactInfoWrapper rRapper = new ContactInfoWrapper();
        rRapper.setName(args[0]);
        rRapper.setSchool(args[1]);
        rRapper.setGradYear(args[2]);
        rRapper.setAddress(args[3]);
        rRapper.setEmail(args[4]);
        rRapper.setPhoneNumber(args[5]);
        return rRapper;
    }
}
