package com.wss503.recipic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmailRegister;
    EditText etPasswordRegister;
    EditText etRepeatPasswordRegister;
    EditText etFirstName;
    EditText etLastName;

    Button bCancel;
    Button bCreateAccount;

    String genderValue;

    String URL= "https://fooderloo.com/recipic/index.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmailRegister = (EditText) findViewById(R.id.etEmailRegister);
        etPasswordRegister = (EditText) findViewById(R.id.etPasswordRegister);
        etRepeatPasswordRegister = (EditText) findViewById(R.id.etRepeatPasswordRegister);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        bCancel = (Button) findViewById(R.id.bCancel);
        bCreateAccount = (Button) findViewById(R.id.bCreateAccount);

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(switchActivityIntent);
            }
        });

        bCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailRegister.getText().toString();
                String password = etPasswordRegister.getText().toString();
                String repeatPassword = etRepeatPasswordRegister.getText().toString();
                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String gender = genderValue;

                if (!password.equals(repeatPassword)) {
                    Toast.makeText(getApplicationContext(),"Password does not match!",Toast.LENGTH_LONG).show();
                } else {
                    RegisterActivity.AttemptRegister attemptRegister = new RegisterActivity.AttemptRegister();
                    attemptRegister.execute(email, password, gender, firstname, lastname);
                }
            }
        });
    }

    public void onRadioButtonGenderClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Remove all existing options in the radio group
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupGender);
        rg.clearCheck();

        // Check the selected radio button
        ((RadioButton) view).setChecked(true);
        this.genderValue = ((RadioButton) view).getText().toString();

    }

    private class AttemptRegister extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String email = args[0];
            String password = args[1];
            String gender = args[2];
            String firstname = args[3];
            String lastname = args[4];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("firstname", firstname));
            params.add(new BasicNameValuePair("lastname", lastname));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

            return json;
        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            try {
                if (result != null) {
                    if(result.getString("success").equals("1")) {
                        Intent switchActivityIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(switchActivityIntent);
                    }
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}