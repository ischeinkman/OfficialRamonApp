package org.ramonaza.officialramonapp.helpers.ui.fragments;

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
import android.widget.ListView;
import android.widget.ProgressBar;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;
import org.ramonaza.officialramonapp.helpers.ui.other.InfoWrapperCheckboxListAdapter;

/**
 * Parent class for fragments with a checkbox list and a submit button.
 * WARNING: Parent activity should include an action_submit actionbar button.
 */
public abstract class InfoWrapperCheckBoxesFragment extends Fragment {

    protected View rootView; //Root View containing all other children
    protected ProgressBar progressBar;
    protected int mLayoutId; //For children to override as necessary.
    protected ListView listView;
    protected InfoWrapperCheckboxListAdapter mAdapter;
    protected GetInfoWrappers currentAsync;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mLayoutId == 0) mLayoutId = R.layout.fragment_info_wrapper_checkboxes;
        rootView = inflater.inflate(R.layout.fragment_info_wrapper_checkboxes, container, false);
        listView=(ListView) rootView.findViewById(R.id.infowrappercheckboxlist);
        mAdapter=new InfoWrapperCheckboxListAdapter(getActivity());
        progressBar = (ProgressBar) rootView.findViewById(R.id.cProgressBar);
        refreshData();
        listView.setAdapter(mAdapter);
        return rootView;
    }

    public abstract void onSubmitButton(InfoWrapper[] checked, InfoWrapper[] unchecked);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_submit:
                onSubmitButton(mAdapter.getBoth()[0], mAdapter.getBoth()[1]);
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
        if(currentAsync != null) currentAsync.cancel(true);
        currentAsync = new GetInfoWrappers(getActivity(),mAdapter,progressBar);
        currentAsync.execute();
    }


    public abstract InfoWrapper[] generateInfo();

    /**
     * The class for retrieving the InfoWrappers.
     */
    protected class GetInfoWrappers extends AsyncTask<Void, Integer, InfoWrapper[]> {
        protected Context mContext;
        protected ProgressBar mBar;
        protected InfoWrapperCheckboxListAdapter mAdapter;

        /**
         * Constructs the activity.
         *
         * @param context      the context to use
         * @param adapter      adapter to populate
         * @param progressBar  the bar to report progress to
         */
        public GetInfoWrappers(Context context, InfoWrapperCheckboxListAdapter adapter, ProgressBar progressBar) {
            this.mContext = context;
            this.mBar = progressBar;
            this.mAdapter=adapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBar.setVisibility(View.VISIBLE);
            mAdapter.clear();
        }

        @Override
        protected InfoWrapper[] doInBackground(Void... params) {
            return generateInfo();
        }

        @Override
        protected void onPostExecute(InfoWrapper[] infoWrappers) {
            super.onPostExecute(infoWrappers);
            if (!isAdded() || isDetached()) {
                return; //In case the calling activity is no longer attached
            }
            mAdapter.addAll(infoWrappers);
            mBar.setVisibility(View.GONE);
        }
    }
}


