package com.example.project.Utilities;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.project.Activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchDataTask extends AsyncTask<String, Void, String> {


    private final MainActivity mainActivity;

    public FetchDataTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(String... params) {
        String reqmethod = params[1];
        if (reqmethod.equalsIgnoreCase("GET")) {

            String jsonStr = new HttpHandler().makeServiceCall(params[0]);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String books = jsonObj.toString();
                } catch (final JSONException e) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
                return jsonStr;

            } else {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mainActivity.getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        } else if (reqmethod.equalsIgnoreCase("POST")) {
            return "ciao";
        }
        return null;
    }

    @Override
    protected void onPostExecute(String dataFetched) {
        //parse the JSON data and then display
        mainActivity.parseJSON(dataFetched);
    }

}
