package com.dpsn.espice.decryptonite.ConnectWithSite;

/**
 * Created by Sony on 02-05-2015.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dpsn.espice.decryptonite.DisqualifyActivity;
import com.dpsn.espice.decryptonite.LoginActivity;
import com.dpsn.espice.decryptonite.NavigationDrawerActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * This class connects with the site -
 * http://preayus.net76.net/mob/login.php?username=USERNAME$pass=PASSWORD
 * and if the login is successful starts NavigationDrawerActivty.
 */

public class LoginConnection {

    Context mContext;

    public static final String site = "http://preayus.net76.net/mob/login.php?username=";

    public LoginConnection(Context context){
        mContext = context;
    }

    public void Connect(final String username, final String password) {
    //This method use AysncTask to handle the connection with the server

        class SiteAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                return LoginDoInBackground(params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s == null) {
                    return;
                }
                String[] result = s.split("<br>");
                if (result[3].equals("disqualified")) {
                    Intent intent = new Intent(mContext, DisqualifyActivity.class);
                    mContext.startActivity(intent);
                } else if (result[2].equals("success")) {
                    Toast.makeText(mContext, "Login Successful", Toast.LENGTH_LONG).show();
                    Log.v(LoginActivity.LogTag, "Login Successful");
                    LoginActivity.mUsername = result[0];
                    LoginActivity.mPassword = result[1];
                    Intent intent = new Intent(mContext, NavigationDrawerActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext,
                            "Username or Password is incorrect", Toast.LENGTH_LONG).show();
                    Log.v(LoginActivity.LogTag, "Login Failed");
                }
                LoginActivity.loginButton.setClickable(true);
            }
        }

        SiteAsyncTask siteAsyncTask = new SiteAsyncTask();
        siteAsyncTask.execute(username, password);
    }

    public String LoginDoInBackground(String... params){
        String paramUsername = params[0];
        String paramPassword = params[1];
        Log.v(LoginActivity.LogTag, "paramUsername is : " + paramUsername +
                " paramPassword is : " + paramPassword);

        // Create an intermediate to connect with the Internet
        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(site + paramUsername + "&pass=" + paramPassword);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);

            InputStream inputStream = httpResponse.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String bufferedStrChunk = null;

            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk + " ");
            }
            Log.v(LoginActivity.LogTag, "Return of doInBackground :" +
                    paramUsername + "<br>" + paramPassword +
                    "<br>" + stringBuilder.toString());

            return paramUsername + "<br>" + paramPassword + "<br>" +
                    stringBuilder.toString();

        } catch (ClientProtocolException e) {
            Log.v(LoginActivity.LogTag, "ClientProtocolException");
            Toast.makeText(mContext, "Error: Please Check Your Internet Connection",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Log.v(LoginActivity.LogTag, "IOException");
            Toast.makeText(mContext, "Error: Please Check Your Internet Connection",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }
}