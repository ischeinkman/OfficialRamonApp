package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
 * Use the {@link PresentAlephFromListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresentAlephFromListFragment extends Fragment {

    private List<AlephBox> allBoxes;
    private LinearLayout listLayout;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PresentAlephFromListFragment.
     */
    public static PresentAlephFromListFragment newInstance() {
        PresentAlephFromListFragment fragment = new PresentAlephFromListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PresentAlephFromListFragment() {
        allBoxes=new ArrayList<AlephBox>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_present_listed_aleph, container, false);
        listLayout=(LinearLayout) rootView.findViewById(R.id.AlephAddPresentList);
        refreshData();
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_submit:
                Log.d("PrAlList.Submit","Button Pressed");
                new SubmitFromList().execute();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void refreshData(){
        new CreateCheckList(listLayout).execute();
    }

    public class CreateCheckList extends AsyncTask<Void,Void,List<ContactInfoWrapper>>{
        private LinearLayout cLayout;

        @Override
        protected List<ContactInfoWrapper> doInBackground(Void... params) {
            ConDriveDatabaseHelper dbHelpter=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelpter.getReadableDatabase();
            Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s WHERE %s=0 ORDER BY %s ASC", ConDriveDatabaseContract.ContactListTable.TABLE_NAME,ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT,ConDriveDatabaseContract.ContactListTable.COLUMN_NAME),null);
            return ContactInfoWrapperGenerator.fromDataBase(cursor);
        }
        @Override
        protected void onPostExecute(List<ContactInfoWrapper> alephs){
            for(ContactInfoWrapper aleph :alephs){
                allBoxes.add(new AlephBox(getActivity(),aleph));
            }
            for(CheckBox box:allBoxes){
                cLayout.addView(box);
            }
        }
        public CreateCheckList(LinearLayout layout){
            cLayout=layout;
        }
    }
    public class SubmitFromList extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(getActivity());
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            for (AlephBox cBox : allBoxes) {
                if (cBox.isChecked()) {
                    ContentValues cValues=new ContentValues();
                    cValues.put(ConDriveDatabaseContract.ContactListTable.COLUMN_PRESENT, 1);
                    Log.d("PrAlList.Submit",String.format("Submitting id: %d",cBox.getId()));
                    db.update(ConDriveDatabaseContract.ContactListTable.TABLE_NAME,cValues,ConDriveDatabaseContract.ContactListTable._ID+"=?",new String[]{""+cBox.getId()});
                }
            }
            return null;
        }


    }
    public class AlephBox extends CheckBox{
        private ContactInfoWrapper aleph;
        public AlephBox(Context context, ContactInfoWrapper inAleph){
            super(context);
            this.aleph=inAleph;
            this.setText(aleph.getName());
        }
        public int getId(){
            return aleph.getId();
        }
    }

}
