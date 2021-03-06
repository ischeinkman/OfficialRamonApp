package org.ramonaza.officialramonapp.people.rides.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.people.rides.ui.activities.DisplayRidesActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigureRidesDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigureRidesDisplayFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConfigureRidesDisplayFragment.
     */
    public static ConfigureRidesDisplayFragment newInstance() {
        ConfigureRidesDisplayFragment fragment = new ConfigureRidesDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ConfigureRidesDisplayFragment() {
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
        final View rootView= inflater.inflate(R.layout.fragment_configure_rides_display, container, false);
        Button submitButton=(Button) rootView.findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner alSpinner=(Spinner) rootView.findViewById(R.id.AlgorithmSelection);
                int algorithm=alSpinner.getSelectedItemPosition()-1;
                CheckBox retainBox=(CheckBox) rootView.findViewById(R.id.RetainRides);
                boolean retain=retainBox.isChecked();
                Intent displayIntent=new Intent(getActivity(), DisplayRidesActivity.class);
                displayIntent.putExtra(DisplayRidesActivity.EXTRA_ALGORITHM, algorithm);
                displayIntent.putExtra(DisplayRidesActivity.EXTRA_RETAIN_RIDES, retain);
                startActivity(displayIntent);
            }
        });
        return rootView;
    }

}
