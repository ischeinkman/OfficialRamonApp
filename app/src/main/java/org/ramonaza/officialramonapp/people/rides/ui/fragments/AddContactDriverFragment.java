package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragStyles.InfoWrapperTextListFragment;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseContract;
import org.ramonaza.officialramonapp.people.backend.ContactDatabaseHandler;
import org.ramonaza.officialramonapp.people.backend.ContactInfoWrapper;
import org.ramonaza.officialramonapp.people.rides.ui.activities.AddCustomDriverActivity;

/**
 * Created by ilanscheinkman on 8/25/15.
 */
public class AddContactDriverFragment extends InfoWrapperTextListFragment {

    public static AddContactDriverFragment newInstance() {
        AddContactDriverFragment fragment = new AddContactDriverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onButtonClick(InfoWrapper mWrapper) {
        ContactInfoWrapper aleph=(ContactInfoWrapper) mWrapper;
        Intent intent=new Intent(getActivity(), AddCustomDriverActivity.class);
        intent.putExtra(AddCustomDriverActivity.PRESET_NAME, aleph.getName());
        intent.putExtra(AddCustomDriverActivity.PRESET_ADDRESS, aleph.getAddress());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public InfoWrapper[] generateInfo() {
        ContactDatabaseHandler handler=new ContactDatabaseHandler(getActivity());
        ContactInfoWrapper[] currentContacts= handler.getContacts(null, ContactDatabaseContract.ContactListTable.COLUMN_NAME+" ASC");
        return currentContacts;
    }
}
