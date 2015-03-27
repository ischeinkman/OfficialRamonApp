package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlephsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlephsFragment extends Fragment {


    private LinearLayout presentAlpehLayout;

    public static AlephsFragment newInstance() {
        AlephsFragment fragment = new AlephsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AlephsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_rides_alephs, container, false);
        this.presentAlpehLayout=(LinearLayout)rootView.findViewById(R.id.PresentAlephList);
        refreshData();
        return rootView;
    }

    public void refreshData(){
        new getPresentAlephs(presentAlpehLayout).execute(); //Because refresh looks better.
    }

    public class AlephButtonListener implements View.OnClickListener{
        ContactInfoWrapper mContact;
        LinearLayout parentLayout;
        public void setContact(ContactInfoWrapper inContact){
            this.mContact=inContact;
        }
        public void setParentLayout(LinearLayout linearLayout){
            this.parentLayout=linearLayout;
        }
        public void onClick(View v){
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues updateVals=new ContentValues();
            updateVals.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,0); //Deletes the aleph from the present list on the button click, because there is no other functionality required for alephs other than add and delete.
            String[] idArg=new String[]{""+mContact.getId()};
            db.update(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,
                    updateVals,
                    ConDriveDatabaseContract.ContactListTable.COLUMN_CONTACT_ID + "=?",
                    idArg);
            refreshData();
        }
    }



    public class getPresentAlephs extends AsyncTask<Void,Integer,List<ContactInfoWrapper>> {

        private LinearLayout cLayout;

        @Override
        protected List<ContactInfoWrapper> doInBackground(Void... params) {
            ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity().getApplicationContext());
            SQLiteDatabase db=dbHelpter.getReadableDatabase();
            Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=1 ORDER BY %s DESC",ConDriveDatabaseContract.ContactListTable.TABLE_NAME,ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,ConDriveDatabaseContract.ContactListTable.COLUMN_NAME),null);
            return ContactInfoWrapperGenerator.fromDataBase(cursor);
        }

        @Override
        protected void onPostExecute(List<ContactInfoWrapper> alephs) {
            super.onPostExecute(alephs);
            List<Button> contactButtons=new ArrayList<Button>();
            if(alephs.size()==0){
                return;
            }
            for(ContactInfoWrapper aleph: alephs){
                Button temp=new Button(getActivity());
                temp.setBackground(getResources().getDrawable(R.drawable.general_textbutton_layout));
                temp.setText(aleph.getName());
                AlephButtonListener deleteButtonListener=new AlephButtonListener();
                deleteButtonListener.setContact(aleph);
                deleteButtonListener.setParentLayout(cLayout);
                temp.setOnClickListener(deleteButtonListener);
                contactButtons.add(temp);
            }
            for(Button cButton:contactButtons){
                cLayout.addView(cButton);
            }

        }
        public getPresentAlephs(LinearLayout linearLayout){
            cLayout=linearLayout;
            cLayout.removeAllViewsInLayout();
        }
    }


}
