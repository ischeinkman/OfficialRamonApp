package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.InfoWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for fragments with a checkbox list and a submit button.
 * WARNING: Parent activity should include an action_submit actionbar button.
 */
public abstract class InfoWrapperCheckBoxesFragment extends Fragment {

    protected View rootView; //Root View containing all other children
    protected LinearLayout mLayout; //To be populated with checkboxes
    protected int mLayoutId; //For children to override as necessary.
    protected ProgressBar progressBar;
    protected GetInfoWrappers currentAsync;
    protected InfoWrapperCheckbox[] allBoxes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mLayoutId == 0) mLayoutId = R.layout.fragment_info_wrapper_button_list;
        rootView = inflater.inflate(R.layout.fragment_info_wrapper_checkboxes, container, false);
        mLayout = (LinearLayout) rootView.findViewById(R.id.CheckBoxView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.cProgressBar);
        //allBoxes = new ArrayList<InfoWrapperCheckbox>();
        refreshData();
        return rootView;
    }

    public abstract void onSubmitButton(List<InfoWrapper> checked, List<InfoWrapper> unchecked);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_submit:
                List<InfoWrapper> checked = new ArrayList<InfoWrapper>();
                List<InfoWrapper> unchecked = new ArrayList<InfoWrapper>();
                for (InfoWrapperCheckbox box : allBoxes) {
                    if (box.isChecked()) {
                        checked.add(box.getmWrapper());
                    } else {
                        unchecked.add(box.getmWrapper());
                    }
                }
                onSubmitButton(checked, unchecked);
                getActivity().finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_checkbox_fragment,menu);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        currentAsync.cancel(true);
        super.onDetach();
    }

    public void refreshData() {
        currentAsync = new GetInfoWrappers(mLayout, getActivity());
        currentAsync.execute();
    }


    public abstract InfoWrapper[] generateInfo();


    protected class GetInfoWrappers extends AsyncTask<Void, Integer, InfoWrapper[]> {
        private LinearLayout cLayout;
        private Context context;

        public GetInfoWrappers(LinearLayout layout, Context context) {
            this.cLayout = layout;
            this.context = context;
        }

        @Override
        protected InfoWrapper[] doInBackground(Void... params) {
            return generateInfo();
        }

        @Override
        protected void onPostExecute(InfoWrapper[] infoWrappers) {
            if(!isAdded() ||isDetached()) return; //In case calling activity is no longer attached
            progressBar.setVisibility(View.INVISIBLE);
            int infoLen=infoWrappers.length;
            allBoxes=new InfoWrapperCheckbox[infoLen];
            for (int i=0;i<infoLen;i++) {
               allBoxes[i]=new InfoWrapperCheckbox(context,infoWrappers[i]);
            }
            for (CheckBox box : allBoxes) {
                cLayout.addView(box);
            }
        }
    }

    protected class InfoWrapperCheckbox extends CheckBox {
        private InfoWrapper mWrapper;

        public InfoWrapperCheckbox(Context context, InfoWrapper mWrapper) {
            super(context);
            this.mWrapper = mWrapper;
            this.setText(mWrapper.getName());
        }

        public InfoWrapper getmWrapper() {
            return mWrapper;
        }
    }

}
