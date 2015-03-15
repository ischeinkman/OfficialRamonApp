package org.ramonaza.officialramonapp.uifragments.frontal_activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.activities.ContactDataActivity;
import org.ramonaza.officialramonapp.activities.FrontalActivity;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapperGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactListFragment  extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.CONSTRUCTION_INFO";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";
    private static final String PAGE_NAME="Contact List";
    public int fraglayer;

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
        List<Button> contactButtons=new ArrayList<Button>();
        List<ContactInfoWrapper> alephs= ContactInfoWrapperGenerator.getCtactInfoListFromCSV(this.getActivity());
        for(ContactInfoWrapper aleph: alephs){
            Button temp=new Button(this.getActivity());
            temp.setBackground(getResources().getDrawable(R.drawable.songbuttonlayout));
            temp.setText(aleph.getName());
            ButtonClickListener buttonClickListener=new ButtonClickListener();
            buttonClickListener.setContact(aleph);
            temp.setOnClickListener(buttonClickListener);
            contactButtons.add(temp);
        }
        for(Button cButton:contactButtons){
            cLayout.addView(cButton);
        }
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }



    public class ButtonClickListener implements View.OnClickListener{
        ContactInfoWrapper buttonContactInfoWrapper;
        public ButtonClickListener setContact(ContactInfoWrapper inContactInfoWrapper){
            this.buttonContactInfoWrapper = inContactInfoWrapper;
            return this;
        }

            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ContactDataActivity.class);
                intent.putExtra(EXTRA_LAYER,PAGE_NAME);
                intent.putExtra(EXTRA_CONTRUCTION_INFO,this.buttonContactInfoWrapper.getArgArray());
                startActivity(intent);
        }
        }

    }


