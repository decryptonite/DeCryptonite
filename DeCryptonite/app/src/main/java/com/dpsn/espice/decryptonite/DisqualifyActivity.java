package com.dpsn.espice.decryptonite;

/**
 * Created by Sony on 03-05-2015.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
 * This Activity is called when the user has been disqualified.
 * Checks for disqualification is done in LoginActivity and also
 * in PlayActivity in their PostExecute method of respective Connection Activity.
 */
public class DisqualifyActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disqualify_activity);
    }
}