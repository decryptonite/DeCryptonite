package com.dpsn.espice.decryptonite.ConnectWithSite;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dpsn.espice.decryptonite.DisqualifyActivity;
import com.dpsn.espice.decryptonite.LoginActivity;
import com.dpsn.espice.decryptonite.PlayFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Sony on 03-05-2015.
 */
public class PlayConnection {

    Context mContext;

    public PlayConnection(Context ctx){
        mContext = ctx;
    }

    public void Connect(String username, String answer) {

        class SiteAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];
                String paramAnswer = params[1];
                Log.v(LoginActivity.LogTag, "Usrname: " + paramUsername + "Ans: " + paramAnswer);

                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(
                        "http://preayus.net76.net/mob/play.php?username=" +
                                paramUsername + "&answer=" + paramAnswer);
                try{
                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    InputStream inputStream = httpResponse.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk + " ");
                    }

                    Log.v(LoginActivity.LogTag, stringBuilder.toString());
                    return stringBuilder.toString();
                } catch (ClientProtocolException e) {
                    Toast.makeText(mContext, "Error: Please Check Your Internet Connection",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(mContext,"Error: Please Check Your Internet Connection",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] strings = s.split("<br>");
                if (strings[0].equals("disqualified")){
                    mContext.startActivity(new Intent(mContext, DisqualifyActivity.class));
                }
                else if(strings[0].equals("no answer")){
                    Log.v(LoginActivity.LogTag, "Displaying Questions");
                    PlayFragment.UpdateQuestion(strings[2], strings[3]);
                }
                else if(strings[0].equals("level up")){
                    Log.v(LoginActivity.LogTag, "Level Up");
                    Toast.makeText(mContext, "Congratulations, You have  leveled up.",
                            Toast.LENGTH_LONG).show();
                    PlayFragment.UpdateQuestion(strings[2], strings[3]);
                }
                else {
                    Log.v(LoginActivity.LogTag, "Wrong Answer");
                    Toast.makeText(mContext, "Sorry, Wrong Answer",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        SiteAsyncTask siteAsyncTask = new SiteAsyncTask();
        siteAsyncTask.execute(username, answer);
    }

}
