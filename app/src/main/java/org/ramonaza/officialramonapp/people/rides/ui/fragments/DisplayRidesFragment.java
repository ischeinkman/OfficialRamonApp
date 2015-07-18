package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayRidesFragment extends Fragment {


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
        TextView ridesDisplay=(TextView)rootView.findViewById(R.id.RidesTextList);
        ridesDisplay.setText(Html.fromHtml(createRidesList()));
        return rootView;
    }

    private String createRidesList(){
        RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
        DriverInfoWrapper[] rides=handler.getDrivers(null, ContactDatabaseContract.DriverListTable.COLUMN_NAME+" ASC");
        String ridesList="";
        for(DriverInfoWrapper driver:rides){
            ridesList+=String.format("<h1><b><u>%s</u></b></h1>",driver.getName());
            for (ContactInfoWrapper alephInCar:driver.getAlephsInCar()){
                ridesList+=String.format("-%s<br/>",alephInCar.getName());
            }
            ridesList+="<br/>";
        }
        return ridesList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
