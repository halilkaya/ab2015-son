package tr.org.ab.ab2015ornek;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String URL = "http://54.154.164.189/api/messages";
    private HTTPUtilities httpUtilities;
    private ListView listView;
    private MessageModel messageModel;
    private ArrayList<MessageModel> messages;
    private CustomListAdapter adapter;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        degiskenleriAta();
        apiSorgusunuCalistir();

    }

    private void degiskenleriAta() {
        httpUtilities = new HTTPUtilities();
        listView = (ListView) findViewById(R.id.mesaj_listesi);
        messages = new ArrayList<MessageModel>();
    }

    private String mesajlariGetir() {
        String sonuc = null;
        try {
            sonuc = httpUtilities.getRequest(URL, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sonuc;
    }

    private void apiSorgusunuCalistir() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                jsonString = mesajlariGetir();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                veriyiAyikla();
            }
        }.execute();
    }

    private void veriyiAyikla() {
        try {

            JSONObject anaObje = new JSONObject(jsonString);
            JSONArray anaArray = anaObje.getJSONArray("messages");
            for (int i = 0; i < anaArray.length(); i++) {
                JSONObject object = anaArray.getJSONObject(i);
                String date_sent = object.getString("date_sent");
                String message = object.getString("message");
                String name = object.getString("name");
                messageModel = new MessageModel(date_sent,
                                                name,
                                                message);
                messages.add(messageModel);
            }
            verileriEkranaBas();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void verileriEkranaBas() {
        adapter = new CustomListAdapter(MainActivity.this, R.layout.list_item, messages);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.refresh) {
            adapter.clear();
            apiSorgusunuCalistir();
        } else if (id == R.id.new_message) {
            Intent newMessage = new Intent(MainActivity.this, MesajGonder.class);
            MainActivity.this.startActivity(newMessage);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.clear();
            apiSorgusunuCalistir();
        }
    }

}
