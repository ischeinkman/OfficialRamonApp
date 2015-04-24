package org.ramonaza.officialramonapp.uifragments.rides_activity;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.AddCustomDriverActivity;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseContract;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.DriverInfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.DriverInfoWrapperGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DriversFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DriversFragment extends Fragment {

    private LinearLayout driverLayout;




    public static DriversFragment newInstance() {
        DriversFragment fragment = new DriversFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DriversFragment() {
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
        View rootView= inflater.inflate(R.layout.fragment_rides_drivers, container, false);
        driverLayout=(LinearLayout) rootView.findViewById(R.id.CurrentDriverList);
        Button customButton=(Button) rootView.findViewById(R.id.AddCustomDriveButton);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customDriverIntent=new Intent(getActivity(), AddCustomDriverActivity.class);
                startActivity(customDriverIntent);
            }
        });
        refreshData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        new GetDrivers(driverLayout).execute();
    }

    public class GetDrivers extends AsyncTask<Void,Void,List<DriverInfoWrapper>>{
        private LinearLayout cLayout;


        @Override
        protected List<DriverInfoWrapper> doInBackground(Void... params) {
            ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelpter.getReadableDatabase();
            Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s ORDER BY %s DESC", ConDriveDatabaseContract.DriverListTable.TABLE_NAME,ConDriveDatabaseContract.DriverListTable.COLUMN_NAME),null);
            return DriverInfoWrapperGenerator.fromDataBase(cursor);
        }

        @Override
        protected void onPostExecute(List<DriverInfoWrapper> drivers) {
            super.onPostExecute(drivers);
            super.onPostExecute(drivers);
            List<Button> contactButtons=new ArrayList<Button>();
            Log.d("DriversFrag", String.format("Present drivers list size:%d", drivers.size()));
            cLayout.removeAllViewsInLayout();
            if(drivers.size()==0){
                return;
            }
            for(DriverInfoWrapper dR: drivers){
                Button temp=new Button(getActivity());
                temp.setBackground(getResources().getDrawable(R.drawable.general_textbutton_layout));
                temp.setText(dR.getName());
                DriverButtonListener deleteButtonListener=new DriverButtonListener(dR);
                temp.setOnClickListener(deleteButtonListener);
                contactButtons.add(temp);
            }
            for(Button cButton:contactButtons){
                cLayout.addView(cButton);
            }

        }


        public GetDrivers(LinearLayout linearLayout){
            cLayout=linearLayout;
        }
    }

    public class DriverButtonListener implements View.OnClickListener{
        private DriverInfoWrapper mDriver;

        @Override
        public void onClick(View v) {

        }
        public DriverButtonListener(DriverInfoWrapper inDriver){
            mDriver=inDriver;
        }
    }
}



