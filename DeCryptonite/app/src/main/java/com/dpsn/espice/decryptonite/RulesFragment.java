package com.dpsn.espice.decryptonite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sony on 05-05-2015.
 */
public class RulesFragment  extends Fragment {

    public RulesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rule_fragment, container, false);
        return rootView;
    }
}
