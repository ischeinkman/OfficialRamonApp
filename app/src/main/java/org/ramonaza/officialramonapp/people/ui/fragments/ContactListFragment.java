package org.ramonaza.officialramonapp.people.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.frontpage.ui.activities.FrontalActivity;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragStyles.InfoWrapperTextListFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.ui.activities.AddCustomAlephActivity;
import org.ramonaza.officialramonapp.people.ui.activities.ContactDataActivity;

/**
 * Created by Ilan Scheinkman on 1/12/15.
 */
public class ContactListFragment  extends InfoWrapperTextListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String EXTRA_CONTRUCTION_INFO="org.ramonaza.officialramonapp.ALEPH_ID";
    private static final String EXTRA_LAYER="org.ramonaza.officialramonapp.LAYER_NAME";
    private static final String PAGE_NAME="Contact List";
    private static final String EXTRA_PARENT_ACTIVITY="parent activity";
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contact_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_custom_aleph:
                Intent intent=new Intent(getActivity(), AddCustomAlephActivity.class);
                intent.putExtra(EXTRA_PARENT_ACTIVITY,getActivity().getClass());
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FrontalActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        Intent intent = new Intent(getActivity(), ContactDataActivity.class);
        intent.putExtra(EXTRA_LAYER, PAGE_NAME);
        intent.putExtra(EXTRA_CONTRUCTION_INFO, mWrapper.getId());
        startActivity(intent);
    }

    @Override
    public InfoWrapper[] generateInfo() {
        ContactDatabaseHandler handler=new ContactDatabaseHandler(getActivity());
        return handler.getContacts(null, ContactDatabaseContract.ContactListTable.COLUMN_NAME+" ASC");
    }



}


