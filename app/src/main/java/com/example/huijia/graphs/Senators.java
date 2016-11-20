package com.example.huijia.graphs;

/**
 * Created by Huijia on 11/20/16.
 */





        import android.app.Activity;
        import android.content.DialogInterface;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.*;
        import android.view.*;
        import android.widget.*;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;
        import org.w3c.dom.Text;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class Senators  extends Activity {
    //static String TAG = "Main Activity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senators);
        //Log.i(TAG, "Application runs super well!!!");

        Button buttonsenator = (Button) (findViewById(R.id.buttonsenator));
        /*
        buttonsenator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Button Clicked!!");
            }
        });*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void selfDestruct(View view) {
        // Kabloey
        downloadImageAsync();
        //Log.i(TAG, "Ran!");
    }

    private void downloadImageAsync() {
        // Now we can execute the long-running task at any time.
        new MyTask().execute();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    private class MyTask extends AsyncTask<Void, Void, String> {

        private Exception exception;
        ProgressBar p = (ProgressBar) (findViewById(R.id.progressBar));
        TextView responseView = (TextView) (findViewById(R.id.textView));
        TextView phoneView = (TextView) (findViewById(R.id.PHONE));
        TextView view1 = (TextView) (findViewById(R.id.textView5));
        TextView view3 = (TextView) (findViewById(R.id.textView7));
        EditText zipcode1 = (EditText)  (findViewById(R.id.editText));
        String zipcode =  zipcode1.getText().toString();

        String tempzipcode = "60201";
        String API_URL = "http://whoismyrepresentative.com/getall_mems.php?zip=";
        String makeJson = "&output=json";

        protected void onPreExecute() {


            p.setVisibility(View.VISIBLE);
            responseView.setText("");
            phoneView.setText("");
            view1.setText("");view3.setText("");



        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL(API_URL + zipcode + makeJson);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            p.setVisibility(View.GONE);
            Log.i("INFO", response);
            responseView.setText(response);


            try {

                JSONObject overall = (JSONObject) new JSONTokener(response).nextValue();
                System.out.println(overall);
                JSONArray array = overall.getJSONArray("results");
                JSONObject object = (JSONObject) array.get(0);
                String senate = object.getString("name");
                String phone = object.getString("phone");

                Log.i("INFO",senate+"it worked!");
                responseView.setText(senate);
                phoneView.setText(phone);

            } catch (Exception e) {
                String error = "Senator not found, enter another zip code";
                responseView.setText(error);
                Log.e("ERROR", "Did not work", e);
            }

        }
    }


}
