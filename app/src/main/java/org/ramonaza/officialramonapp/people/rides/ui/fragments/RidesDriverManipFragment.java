package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.other.InfoWrapperTextWithButtonAdapter;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;
import org.ramonaza.officialramonapp.people.rides.ui.activities.AddAlephToDriverActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class RidesDriverManipFragment extends Fragment {

    private DriverInfoWrapper mDriver;
    private int driverId;
    private View rootView;
    private ListView passengersView;
    private InfoWrapperTextWithButtonAdapter mAdapter;
    private PopulateViewTask popTask;
    private DeleteFromCarTask deleteTask;

    public RidesDriverManipFragment() {
    }

    public static RidesDriverManipFragment newInstance(int inDriver){
        RidesDriverManipFragment rFrag=new RidesDriverManipFragment();
        Bundle args=new Bundle();
        args.putInt("DriverId",inDriver);
        rFrag.setArguments(args);
        return rFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        driverId= args.getInt("DriverId");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_rides_driver_manip, container, false);
        this.rootView=rootView;
        passengersView=(ListView) rootView.findViewById(R.id.Passengers);
        this.mAdapter=new InfoWrapperTextWithButtonAdapter(getActivity()) {
            @Override
            public String getButtonText() {
                return "Delete";
            }

            @Override
            public void onButton(InfoWrapper info) {
                if(deleteTask != null) deleteTask.cancel(true);
                deleteTask=new DeleteFromCarTask();
                deleteTask.execute(info);
                refreshData();
            }
        };
        passengersView.setAdapter(mAdapter);
        (rootView.findViewById(R.id.AddAlephToDriverButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(getActivity(), AddAlephToDriverActivity.class);
                intentAdd.putExtra("DriverId", mDriver.getId());
                startActivity(intentAdd);
            }
        });
        /*rootView.findViewById(R.id.RemoveAlephFromDriverButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRemove=new Intent(getActivity(), RemoveAlephFromDriverActivity.class);
                intentRemove.putExtra("DriverId",mDriver.getId());
                startActivity(intentRemove);
            }
        });*/
        refreshData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void refreshData(){
        popTask= new PopulateViewTask(this.driverId,this.rootView, this.mAdapter);
        popTask.execute();
    }

    private class PopulateViewTask extends AsyncTask<Void,Void,ContactInfoWrapper[]>{

        int driverId;
        View view;
        ArrayAdapter mAdapter;

        public PopulateViewTask(int id, View rootView, ArrayAdapter adapter){
            this.driverId=id;
            this.view=rootView;
            this.mAdapter=adapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAdapter.clear();
        }

        @Override
        protected ContactInfoWrapper[] doInBackground(Void... params) {
            RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
            return handler.getAlephsInCar(driverId);
        }

        @Override
        protected void onPostExecute(ContactInfoWrapper[] contactInfoWrappers) {
            super.onPostExecute(contactInfoWrappers);
            if (!isAdded() || isDetached()) {
                return; //In case the calling activity is no longer attached
            }
            RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
            mDriver= handler.getDriver(driverId);
            ActionBar actionBar=getActivity().getActionBar();
            actionBar.setTitle(mDriver.getName());
            ((TextView) view.findViewById(R.id.DriverName)).setText(mDriver.getName());
            ((TextView) view.findViewById(R.id.FreeSpots)).setText("" + mDriver.getFreeSpots());
            mAdapter.addAll(contactInfoWrappers);
        }
    }

    private class DeleteFromCarTask extends AsyncTask<InfoWrapper,Void,Void> {

        @Override
        protected Void doInBackground(InfoWrapper ... params) {
            RidesDatabaseHandler handler=new RidesDatabaseHandler(getActivity());
            for (InfoWrapper aleph:params) {
                handler.removeAlephFromCar(aleph.getId());
            }
            return null;
        }

    }
}
