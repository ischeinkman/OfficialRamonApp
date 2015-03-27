package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ramonaza.officialramonapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomAlephFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomAlephFragment extends Fragment {



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddCustomAlephFragment.
     */
    public static AddCustomAlephFragment newInstance() {
        AddCustomAlephFragment fragment = new AddCustomAlephFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddCustomAlephFragment() {
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
        return inflater.inflate(R.layout.fragment_add_custom_aleph, container, false);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
