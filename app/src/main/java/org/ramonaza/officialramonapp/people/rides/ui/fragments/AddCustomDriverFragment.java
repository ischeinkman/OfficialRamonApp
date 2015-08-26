package org.ramonaza.officialramonapp.people.rides.ui.fragments;


import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHelper;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.DriverInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.backend.RidesDatabaseHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomDriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomDriverFragment extends Fragment {

    private static final String PRESET_NAME="org.ramonaza.officialramonapp.NAME";
    private static final String PRESET_ADDRESS="org.ramonaza.officialramonapp.ADDRESS";

    private String presetName;
    private String presetAddress;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddCustomDriverFragment.
     */
    public static AddCustomDriverFragment newInstance(String presetName, String presetAddress) {
        AddCustomDriverFragment fragment = new AddCustomDriverFragment();
        Bundle args = new Bundle();
        args.putString(PRESET_NAME, presetName);
        args.putString(PRESET_ADDRESS,presetAddress);
        fragment.setArguments(args);
        return fragment;
    }

    public AddCustomDriverFragment() {
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
        presetName=getArguments().getString(PRESET_NAME);
        presetAddress=getArguments().getString(PRESET_ADDRESS);
        View rootView= inflater.inflate(R.layout.fragment_add_custom_driver, container, false);
        if(!(presetName == null || presetName.equals(""))){
            EditText nameField=(EditText) rootView.findViewById(R.id.AddDriverName);
            nameField.setText(presetName);
        }
        if(!(presetAddress == null || presetAddress.equals(""))){
            EditText addressField=(EditText) rootView.findViewById(R.id.AddDriverAddress);
            addressField.setText(presetAddress);
        }
        Button submitButton=(Button) rootView.findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new SubmitListener(getActivity(),rootView));
        return rootView;
    }

    public class SubmitListener extends AsyncTask<Void, Void, Void> implements View.OnClickListener{
        private Context context;
        private DriverInfoWrapper mDriver;
        private View myView;
        private String driverName;
        private String driverSpots;
        private String driverAddress;

        public SubmitListener(Context incontext, View inView){
            this.myView=inView;
            this.context=incontext;
            this.mDriver=new DriverInfoWrapper();
        }

        @Override
        public void onClick(View v) {
            EditText nameField=(EditText) myView.findViewById(R.id.AddDriverName);
            EditText spotsField=(EditText) myView.findViewById(R.id.AddDriverSpots);
            EditText addressField=(EditText) myView.findViewById(R.id.AddDriverAddress);
            String tryName=nameField.getText().toString();
            String trySpots=spotsField.getText().toString();
            String tryAddress=addressField.getText().toString();
            if (tryName.equals("") || trySpots.equals("") || tryAddress.equals("")){
                Toast.makeText(context, R.string.error_blank_responce, Toast.LENGTH_SHORT).show();
                return;
            }
            driverName=tryName;
            driverAddress=tryAddress;
            driverSpots=trySpots;
            this.execute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            SQLiteDatabase db= new ContactDatabaseHelper(context).getWritableDatabase();
            RidesDatabaseHandler handler=new RidesDatabaseHandler(db);
            mDriver.setName(driverName);
            mDriver.setSpots(Integer.parseInt(driverSpots));
            mDriver.setAddress(driverAddress);
            try {
                handler.addDriver(mDriver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(presetName != null && !presetName.equals("")){
                ContactDatabaseHandler cHandler= new ContactDatabaseHandler(db);
                ContactInfoWrapper[] handlerResults=cHandler.getContacts(new String[]{
                        ContactDatabaseContract.ContactListTable.COLUMN_NAME+" = \""+presetName+"\""
                }, null);
                if(handlerResults.length == 0 || handlerResults.length>1) return null;
                handlerResults[0].setPresent(true);
                try {
                    cHandler.updateContact(handlerResults[0]);
                } catch (ContactDatabaseHandler.ContactCSVReadError contactCSVReadError) {
                    contactCSVReadError.printStackTrace();
                }
                handler.addAlephsToCar(mDriver.getId(),handlerResults);
            }
            getActivity().finish();
            return null;
        }
    }


}
