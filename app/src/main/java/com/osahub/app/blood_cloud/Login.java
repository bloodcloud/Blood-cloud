package com.osahub.app.blood_cloud;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Login extends ActionBarActivity {
    final String LOG_TAG=Login.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.editText);
                EditText passward = (EditText) findViewById(R.id.editText2);
                final String eMail = email.getText().toString().trim();
                final String pass = passward.getText().toString();
                LoginController loginController = new LoginController(eMail, pass);
                loginController.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    protected class LoginController extends AsyncTask<Void,Void,Void> {


        private final String LOG_TAG = LoginController.class.getSimpleName();
        private String email,pass;
        LoginController(String email, String pass){
            this.email=email;
            this.pass=pass;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://blood-cloud.appspot.com/login?eMail="+email+"&pass="+pass;
            Log.v(LOG_TAG, "email " + email + pass);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response=="yes" ){
                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),Location.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(getApplicationContext(),Sorry.class).putExtra(Intent.EXTRA_TEXT,"Sorry,login agian with correct id and password");
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
            return null;

        }
    }
}
