package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import android.widget.Button;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button bLogin;
    Button bRegister;

    String URL= "https://fooderloo.com/recipic/index.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin=(Button)findViewById(R.id.bLogin);
        bRegister=(Button)findViewById(R.id.bRegister);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Invalid email or password!",Toast.LENGTH_LONG);
                }else{
                    AttemptLogin attemptLogin= new AttemptLogin();
                    attemptLogin.execute(etEmail.getText().toString(),etPassword.getText().toString(),"","","");
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String email= args[0];
            String password = args[1];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", ""));
            params.add(new BasicNameValuePair("firstname", ""));
            params.add(new BasicNameValuePair("lastname", ""));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;
        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    if(result.getString("success").equals("1")) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();

                        // Successfully logged in
                        JSONArray details = result.getJSONArray("details");
                        String firstname = details.getString(3);
                        String lastname = details.getString(4);
                        String gender = details.getString(2);
                        String email = details.getString(1);
//
                        Context context = LoginActivity.this;
                        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.saved_firstname), firstname);
                        editor.putString(getString(R.string.saved_lastname), lastname);
                        editor.putString(getString(R.string.saved_gender), gender);
                        editor.putString(getString(R.string.saved_email), email);
                        editor.commit();

                        Intent switchActivityIntentMain = new Intent(LoginActivity.this, CategoriesActivity.class);
                        startActivity(switchActivityIntentMain);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
