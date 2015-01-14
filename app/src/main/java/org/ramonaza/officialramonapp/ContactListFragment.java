package org.ramonaza.officialramonapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactListFragment  extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    public int fraglayer;
    public Contact alephIterator; //TODO:Find a non-hack way to iterate

    public static ContactListFragment newInstance(int sectionNumber) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.fraglayer=sectionNumber;
        return fragment;
    }

    public ContactListFragment() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Contact List");
        View rootView = inflater.inflate(R.layout.fragment_contact_list_page, container, false);
        LinearLayout cLayout=(LinearLayout) rootView.findViewById(R.id.cListLinearList);
        List<String[]> contactInfo=readCsv(this.getActivity());
        List<Button> contactButtons=new ArrayList<Button>();
        List<Contact> alephs=new ArrayList<Contact>();
        for(String[] argumentss:contactInfo){
            alephs.add(new Contact(argumentss));
        }
        for(Contact aleph: alephs){
            Button temp=new Button(this.getActivity());
            temp.setBackground(getResources().getDrawable(R.drawable.songbuttonlayout));
            Log.d("DEBUG","Setting text:"+aleph.getName());
            temp.setText(aleph.getName());
            this.alephIterator=aleph;
            temp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.container, ContactAlephFragment.newInstance(fraglayer + 1, alephIterator))
                            .addToBackStack(null);
                    transaction.commit();
                }
            });
            contactButtons.add(temp);
        }
        for(Button cButton:contactButtons){
            Log.d("DEBUG","Adding button: "+cButton.getText());
            cLayout.addView(cButton);
        }
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontPage) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public final List<String[]> readCsv(Context context) {
        List<String[]> questionList = new ArrayList<String[]>();
        AssetManager assetManager = context.getAssets();

        try {
            String CSV_PATH="AlephNameSchYAddMailNum.csv";
            InputStream csvStream = assetManager.open(CSV_PATH);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                questionList.add(line);
                Log.d("DEBUG",String.format("List:{%s,\n %s,\n %s,\n %s,\n%s}",line[0],line[1],line[2],line[3],line[4]));
            }
        } catch (IOException e) {
            Log.d("DEBUG",e.getMessage());
        }
        Log.d("DEBUG","List size: "+questionList.size());
        return questionList;
    }

}
