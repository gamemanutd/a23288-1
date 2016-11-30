package com.egco428.a23288;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {
    public UserDataSource db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new UserDataSource(this);
        db.open();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar);
    }
    public void ToSignup(View view){
        Intent intent = new Intent(MainActivity.this, SignUpPage.class);
        startActivity(intent);
    }
    public void cancelButton(View view){
        EditText username = (EditText)findViewById(R.id.edit_user);
        username.setText(null);
        EditText password = (EditText)findViewById(R.id.edit_pass);
        password.setText(null);
    }
    public void signInButton(View view){

        EditText username = (EditText)findViewById(R.id.edit_user);
        String getUsrname = username.getText().toString();
        EditText password = (EditText)findViewById(R.id.edit_pass);
        String getPsWord = password.getText().toString();
        String usePass = db.checkPassword(getUsrname);
        if (getUsrname.equals(null) || getPsWord.equals(null)) {
            Toast.makeText(getApplicationContext(), "Please insert username%password",
                    Toast.LENGTH_SHORT).show();
            }
        else {
            if (getPsWord.equals(usePass)){
                Toast.makeText(getApplicationContext(), "Loin Success",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,UserPageActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Failed to Login",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

}
