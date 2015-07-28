package org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragStyles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.fragments.InfoWrapperListFragment;
import org.ramonaza.officialramonapp.helpers.ui.other.InfoWrapperTextWithButtonAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class InfoWrapperTextWithButtonFragment extends InfoWrapperListFragment {


    public InfoWrapperTextWithButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public ArrayAdapter getAdapter() {
        return new InfoWrapperTextWithButtonAdapter(getActivity()) {
            @Override
            public String getButtonText() {
                return buttonName();
            }

            @Override
            public void onButton(InfoWrapper info) {
                onButtonClick(info);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoWrapper thisWrapper = (InfoWrapper) listView.getItemAtPosition(position);
                onButtonClick(thisWrapper);
            }
        });
        return rootView;
    }


    /**
     * Method to get each button name.
     * @return the name of the button.
     */
    public abstract String buttonName();

    /**
     * The action each button performs.
     *
     * @param mWrapper the button's InfoWrapper; can be cast as necessary
     */
    public abstract void onButtonClick(InfoWrapper mWrapper);



}
