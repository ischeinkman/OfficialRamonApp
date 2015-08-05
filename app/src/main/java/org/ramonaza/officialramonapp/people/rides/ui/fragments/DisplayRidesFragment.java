package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayRidesFragment extends Fragment {

    private TextView ridesDisplay;
    private ProgressBar mBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DisplayRidesFragment.
     */
    public static DisplayRidesFragment newInstance() {
        DisplayRidesFragment fragment = new DisplayRidesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayRidesFragment() {
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
        View rootView= inflater.inflate(R.layout.fragment_display_rides, container, false);
        ridesDisplay=(TextView)rootView.findViewById(R.id.RidesTextList);
        mBar=(ProgressBar) rootView.findViewById(R.id.cProgressBar);
        new CreateRidesText().execute();
        return rootView;
    }

    private String createRidesList(){
        SQLiteDatabase db= new ContactDatabaseHelper(getActivity()).getWritableDatabase();
        RidesDatabaseHandler rhandler=new RidesDatabaseHandler(db);
        DriverInfoWrapper[] rides=rhandler.getDrivers(null, ContactDatabaseContract.DriverListTable.COLUMN_NAME+" ASC");
        ContactDatabaseHandler chandler= new ContactDatabaseHandler(db);
        String[] whereclause= new String[]{
                String.format("%s = %d", ContactDatabaseContract.ContactListTable.COLUMN_PRESENT, 1),
                String.format("not %s in (SELECT %s FROM %s)", ContactDatabaseContract.ContactListTable._ID,
                    ContactDatabaseContract.RidesListTable.COLUMN_ALEPH, ContactDatabaseContract.RidesListTable.TABLE_NAME)
        };
        ContactInfoWrapper[] driverless= chandler.getContacts(whereclause, null);
        String ridesList="";
        for(DriverInfoWrapper driver:rides){
            ridesList+=String.format("<h1><b><u>%s</u></b></h1>",driver.getName(), driver.getFreeSpots());
            for (ContactInfoWrapper alephInCar:driver.getAlephsInCar()){
                ridesList+=String.format("-%s<br/>",alephInCar.getName());
            }
            ridesList+="<b>Free Spots: "+driver.getFreeSpots();
            ridesList+="</b><br/><br/>";
        }
        if(driverless.length >0){
            ridesList+="<h1><b><u>Driverless</u></b></h1>";
            for(ContactInfoWrapper driverlessAleph: driverless) ridesList+=String.format("-%s<br/>",driverlessAleph.getName());
        }
        return ridesList;
    }

    private class CreateRidesText extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... params) {
            return createRidesList();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mBar.setVisibility(View.GONE);
            ridesDisplay.setText(Html.fromHtml(s));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
