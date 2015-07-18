package org.ramonaza.officialramonapp.helpers.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.helpers.backend.InfoWrapper;

/**
 * Parent fragment class for all InfoWrapper top level
 * lists that then lead to detail pages.
 */
public abstract class InfoWrapperButtonListFragment extends Fragment {

    protected View rootView; //Root View containing all other children
    protected LinearLayout mLayout; //To be populated with buttons
    protected ProgressBar progressBar;
    protected int mLayoutId; //For children to override as necessary.
    protected GetInfoWrappers currentAsync;

    public InfoWrapperButtonListFragment() {
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
        if (mLayoutId == 0) mLayoutId = R.layout.fragment_info_wrapper_button_list;
        rootView = inflater.inflate(mLayoutId, container, false);
        mLayout = (LinearLayout) rootView.findViewById(R.id.cListLinearList);
        progressBar = (ProgressBar) rootView.findViewById(R.id.cProgressBar);
        refreshData();
        return rootView;
    }

    public void refreshData() {
        currentAsync = new GetInfoWrappers(getActivity(), mLayout, progressBar);
        currentAsync.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onDetach() {
        currentAsync.cancel(true);
        super.onDetach();
    }

    /**
     * The action each button performs.
     *
     * @param mWrapper the button's InfoWrapper; can be cast as necessary
     */
    public abstract void onButtonClick(InfoWrapper mWrapper);

    /**
     * Generates the activity's InfoWrapper list in an async task.
     *
     * @return the list of InfoWrappers
     */
    public abstract InfoWrapper[] generateInfo();



    /**
     * The class for retrieving the InfoWrappers.
     */
    protected class GetInfoWrappers extends AsyncTask<Void, Integer, InfoWrapper[]> {
        protected LinearLayout mLayout;
        protected Context mContext;
        protected ProgressBar mBar;

        /**
         * Constructs the activity.
         *
         * @param context      the context to use
         * @param linearLayout the layout to populate with buttons
         * @param progressBar  the bar to report progress to
         */
        public GetInfoWrappers(Context context, LinearLayout linearLayout, ProgressBar progressBar) {
            this.mContext = context;
            this.mBar = progressBar;
            this.mLayout = linearLayout;
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
            mLayout.removeAllViewsInLayout(); //In case this is not the first time running .refreshData()
            mBar.setVisibility(View.GONE);
            int infoLen=infoWrappers.length;
            Button[] contactButtons = new Button[infoLen];
            for (int i=0;i<infoLen;i++) {
                InfoWrapper info=infoWrappers[i];
                Button temp = new Button(mContext);
                temp.setBackground(getResources().getDrawable(R.drawable.general_textbutton_layout));
                temp.setText(info.getName());
                InfoWrapperButtonListener buttonClickListener = new InfoWrapperButtonListener(info);
                temp.setOnClickListener(buttonClickListener);
                contactButtons[i]=temp;
            }
            for (Button cButton : contactButtons) {
                mLayout.addView(cButton);
            }


        }
    }

    protected class InfoWrapperButtonListener implements View.OnClickListener {

        protected InfoWrapper mInfoWrapper;

        public InfoWrapperButtonListener(InfoWrapper infoWrapper) {
            mInfoWrapper = infoWrapper;
        }

        @Override
        public void onClick(View v) {
            onButtonClick(mInfoWrapper);
        }
    }
}

