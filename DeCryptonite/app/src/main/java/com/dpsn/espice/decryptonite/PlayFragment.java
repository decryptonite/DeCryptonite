package com.dpsn.espice.decryptonite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dpsn.espice.decryptonite.ConnectWithSite.PlayConnection;

/**
 * Created by Sony on 03-05-2015.
 */

/*
 * File : PlayFragment.java
 * ===============================
 * Each time the Activity is created a call is made to the server to fetch
 * the questions and  hints. When the user clicks the submit button a call is made
 * to the server. If the answer is correct the display is updated.
 */
public class PlayFragment extends Fragment implements View.OnClickListener{

    static TextView questionTextView;
    static TextView hintTextView;
    EditText answerEditText;
    Button submitButton;

    PlayConnection playConnection;

    public PlayFragment(){    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.play_fragment, container, false);

        playConnection = new PlayConnection(getActivity());

        questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        hintTextView = (TextView) rootView.findViewById(R.id.hintTextView);
        answerEditText = (EditText) rootView.findViewById(R.id.answerEditText);
        submitButton = (Button) rootView.findViewById(R.id.submitPlay);

        SetUpScreen();

        submitButton.setOnClickListener(this);

        return rootView;
    }

    /*
     * This method calls the Connect method of PlayConnection.
     * The postExecute  method of Connect calls UpdateQuestion
     */
    private void SetUpScreen() {
        String answer = "";
        playConnection.Connect(LoginActivity.mUsername, answer);
    }

    public static void UpdateQuestion(String question, String hint) {
        questionTextView.setText(question);
        hintTextView.setText(hint);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitPlay){
            String Answer = answerEditText.getText().toString();
            playConnection.Connect(LoginActivity.mUsername, Answer);
        }
    }


}
