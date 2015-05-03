package com.dpsn.espice.decryptonite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dpsn.espice.decryptonite.ConnectWithSite.DashboardConnection;

/**
 * Created by Sony on 03-05-2015.
 */

/*
 * File: DashboardFragment.java
 * ==============================
 * This Fragment shows the rank of the user, his level and highest level achieved.
 * When the Fragment is created a getInformation Method is invoked to connect to the
 * internet and display information.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    static TextView userRankTextView;
    static TextView userLevelTextView;
    static TextView highestLevelTextView;
    Button playButton;

    DashboardConnection dConnection;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);

        dConnection = new DashboardConnection(getActivity());

        userRankTextView = (TextView) rootView.findViewById(R.id.userRankTextView);
        userLevelTextView = (TextView) rootView.findViewById(R.id.userLevelTextView);
        highestLevelTextView = (TextView) rootView.findViewById(R.id.highestLevelTextView);
        playButton = (Button) rootView.findViewById(R.id.playDashboard);

        getInformation();

        playButton.setOnClickListener(this);

        return rootView;
    }

    /*
     * This method calls Connect method in DashboardConnection class.
     * The postExecute method calls the displayInformation(String s);
     */
    private void getInformation() {
        Log.v(LoginActivity.LogTag, "Username: " + LoginActivity.mUsername + " Password: " + LoginActivity.mPassword);
        dConnection.Connect(LoginActivity.mUsername, LoginActivity.mPassword);
    }

    public static void displayInformation(String s) {
        String[] strings = s.split("<br>");
        userLevelTextView.setText(userLevelTextView.getText().toString() + strings[3]);
        highestLevelTextView.setText(highestLevelTextView.getText().toString() + strings[4]);
        userRankTextView.setText(userRankTextView.getText().toString() + strings[5]);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playDashboard){
            Fragment fragment = new PlayFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                            .commit();
            NavigationDrawerActivity.mDrawerList.setItemChecked(2, true);
            getActivity().getActionBar().setTitle("Play");
        }
    }
}
