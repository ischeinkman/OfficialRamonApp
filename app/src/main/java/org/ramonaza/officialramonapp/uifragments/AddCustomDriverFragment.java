package org.ramonaza.officialramonapp.uifragments;


import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.DriverInfoWrapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomDriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomDriverFragment extends Fragment {



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddCustomDriverFragment.
     */
    public static AddCustomDriverFragment newInstance() {
        AddCustomDriverFragment fragment = new AddCustomDriverFragment();
        Bundle args = new Bundle();
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
        View rootView= inflater.inflate(R.layout.fragment_add_custom_driver, container, false);
        Button submitButton=(Button) rootView.findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new SubmitListener(getActivity(),rootView));
        return rootView;
    }

    public class SubmitListener implements View.OnClickListener{
        Context context;
        DriverInfoWrapper mDriver;
        View myView;

        public SubmitListener(Context incontext, View inView){
            this.myView=inView;
            this.context=incontext;
            this.mDriver=new DriverInfoWrapper();
        }

        @Override
        public void onClick(View v) {
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();

            EditText nameField=(EditText) myView.findViewById(R.id.AddDriverName);
            EditText addressField=(EditText) myView.findViewById(R.id.AddDriverSpots);
            String tryName=nameField.getText().toString();
            String trySpots=addressField.getText().toString();
            if (tryName.equals("") || trySpots.equals("")){
                Toast.makeText(context,R.string.error_blank_responce,Toast.LENGTH_SHORT).show();
                return;
            }
            mDriver.setName(tryName);
            mDriver.setSpots(Integer.parseInt(trySpots));

            try {
                dbHelper.addDriver(mDriver,db);
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
