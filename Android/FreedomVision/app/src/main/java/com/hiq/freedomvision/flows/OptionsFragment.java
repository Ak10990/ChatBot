package com.hiq.freedomvision.flows;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hiq.freedomvision.R;
import com.hiq.freedomvision.models.QueParse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by akanksha on 21/10/16.
 */

public class OptionsFragment extends Fragment implements OptionsAdapter.OptionsFragmentCallback {

    private static String OPTIONS = "OPTIONS";

    private QueParse queParse;
    private OptionDialogCallback callback;
    private HashMap<String, String> hotel = new HashMap<>();


    public static class ALL_DIALOGS {
        public static final int OPTION_NONE = -1;
        //        public static final int OPTION_BOOK = 0;
//        public static final int OPTION_TYPE = 1;
        public static final int OPTION_YN = 2;
    }

    public static OptionsFragment getInstance(QueParse queParse) {
        OptionsFragment dialog = new OptionsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(OPTIONS, queParse);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_options, container, false);

//        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setCanceledOnTouchOutside(false);
        initValues();
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            callback = (OptionDialogCallback) getActivity();
        } else {
            throw new IllegalArgumentException("This dialog must belong to " + MainActivity.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void initValues() {
        queParse = getArguments().getParcelable(OPTIONS);
    }

    private void initViews(View rootView) {
//        TextView queHeading = (TextView) rootView.findViewById(R.id.que_heading);
//        queHeading.setText(queParse.getHeading());
        GridView gridView = (GridView) rootView.findViewById(R.id.main_gridview);
        List<String> listParse = queParse.getOptions();

        hotel.clear();
        if (listParse.get(0).contains("!!!!!")) {
            for (int i = 0; i < listParse.size(); i++) {
                hotel.put(listParse.get(i).split("!!!!!")[0], listParse.get(i).split("!!!!!")[1]);
                listParse.set(i, listParse.get(i).split("!!!!!")[0]);
            }
        }
        switch (queParse.getOptionsType()) {
            case ALL_DIALOGS.OPTION_YN:
                gridView.setAdapter(new OptionsAdapter(getActivity(), listParse, R.layout.item_option_yn, this));
                break;
//            case ALL_DIALOGS.OPTION_TYPE:
//                gridView.setAdapter(new OptionsAdapter(getActivity(), listParse, R.layout.item_option_type, this));
//                break;
//            case ALL_DIALOGS.OPTION_BOOK://
//                gridView.setAdapter(new OptionsAdapter(getActivity(), listParse, R.layout.item_option_type, this));
//                break;
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (hotel.size() > 0) {
            String urlString = hotel.get(queParse.getOptions().get(position));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                getActivity().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                getActivity().startActivity(intent);
            }
        }
        callback.sendOption(queParse.getQuestion(), queParse.getOptions().get(position));
    }

    public interface OptionDialogCallback {
        void sendOption(String ques, String ans);
    }

}
