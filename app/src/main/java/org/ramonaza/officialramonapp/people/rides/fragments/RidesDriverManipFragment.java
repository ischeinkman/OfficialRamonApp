package org.ramonaza.officialramonapp.people.rides.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapperGenerator;
import org.ramonaza.officialramonapp.people.rides.activities.AddAlephToDriverActivity;
import org.ramonaza.officialramonapp.people.rides.activities.RemoveAlephFromDriverActivity;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapperGenerator;

/**
 * A placeholder fragment containing a simple view.
 */
public class RidesDriverManipFragment extends Fragment {

    private DriverInfoWrapper mDriver;
    private int driverId;
    private View rootView;

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
        ActionBar actionBar=getActivity().getActionBar();
        driverId= args.getInt("DriverId");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_rides_driver_manip, container, false);
        this.rootView=rootView;
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
        switch (item.getItemId()){
            case R.id.action_add_aleph:
                Intent intentAdd=new Intent(getActivity(),AddAlephToDriverActivity.class);
                intentAdd.putExtra("DriverId", mDriver.getId());
                startActivity(intentAdd);
                break;
            case R.id.action_remove_aleph:
                Intent intentRemove=new Intent(getActivity(), RemoveAlephFromDriverActivity.class);
                intentRemove.putExtra("DriverId",mDriver.getId());
                startActivity(intentRemove);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshData(){
        new PopulateView(this.driverId,this.rootView).execute();
    }

    private class PopulateView extends AsyncTask<Void,Void,ContactInfoWrapper[]>{

        int driverId;
        View view;
        public PopulateView(int id, View rootView){
            this.driverId=id;
            this.view=rootView;
        }

        @Override
        protected ContactInfoWrapper[] doInBackground(Void... params) {
            SQLiteDatabase db=new ContactDatabaseHelper(getActivity()).getReadableDatabase();
            String query="SELECT * FROM "+ ContactDatabaseContract.RidesListTable.TABLE_NAME +" JOIN "+ ContactDatabaseContract.ContactListTable.TABLE_NAME +
                    " ON "+ ContactDatabaseContract.RidesListTable.TABLE_NAME+"."+ ContactDatabaseContract.RidesListTable.COLUMN_ALEPH +"="+ ContactDatabaseContract.ContactListTable.TABLE_NAME+"."+ ContactDatabaseContract.ContactListTable._ID+
                    " WHERE "+ ContactDatabaseContract.RidesListTable.TABLE_NAME+"."+ ContactDatabaseContract.RidesListTable.COLUMN_CAR+"="+driverId+
                    " ORDER BY "+ ContactDatabaseContract.ContactListTable.TABLE_NAME+"."+ ContactDatabaseContract.ContactListTable.COLUMN_NAME+" ASC";
            Cursor cursor=db.rawQuery(query, null);
            return ContactInfoWrapperGenerator.fromDataBase(cursor);
        }

        @Override
        protected void onPostExecute(ContactInfoWrapper[] contactInfoWrappers) {
            super.onPostExecute(contactInfoWrappers);
            SQLiteDatabase db=new ContactDatabaseHelper(getActivity()).getReadableDatabase();
            String query=String.format("SELECT * FROM %s WHERE %s=%d LIMIT 1", ContactDatabaseContract.DriverListTable.TABLE_NAME, ContactDatabaseContract.DriverListTable._ID, driverId);
            Cursor cursor=db.rawQuery(query, null);
            mDriver= DriverInfoWrapperGenerator.fromDataBase(cursor)[0];
            for(ContactInfoWrapper inCar:contactInfoWrappers){
                mDriver.addAlephToCar(inCar);
            }
            ActionBar actionBar=getActivity().getActionBar();
            actionBar.setTitle(mDriver.getName());
            ((TextView) view.findViewById(R.id.DriverName)).setText(mDriver.getName());
            ((TextView) view.findViewById(R.id.FreeSpots)).setText(""+mDriver.getFreeSpots());
            LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.Passengers);
            linearLayout.removeAllViewsInLayout();
            for (ContactInfoWrapper contact:contactInfoWrappers){
                TextView textView=new TextView(getActivity());
                textView.setTextSize(20);
                textView.setText(contact.getName());
                linearLayout.addView(textView);
            }
        }
    }
}
