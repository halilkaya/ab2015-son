package tr.org.ab.ab2015ornek;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MesajGonder extends ActionBarActivity {

    private static final String URL = "http://54.154.164.189/api/messages";
    private EditText name;
    private EditText message;
    private Button send;
    private HTTPUtilities httpUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_gonder);

        degerleriAta();

    }

    private void degerleriAta() {
        name = (EditText) findViewById(R.id.name);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        httpUtilities = new HTTPUtilities();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesajiOlustur();
            }
        });
    }

    private void mesajiOlustur() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", name.getText().toString());
            object.put("message", message.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mesajiGonder(object);
    }

    private void mesajiGonder(final JSONObject object) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    httpUtilities.postRequest(URL, object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MesajGonder.this,
                               "Mesajınız gönderildi",
                               Toast.LENGTH_SHORT).show();
                finish();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mesaj_gonder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
