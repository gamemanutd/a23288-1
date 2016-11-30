package com.egco428.a23288;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import javax.sql.DataSource;

public class UserPageActivity extends AppCompatActivity {

    public UserDataSource db;
    ArrayAdapter<UserData> userDataArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.back_user_page);


        ImageButton backUserPage = (ImageButton)findViewById(R.id.back1) ;
        backUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPageActivity.this);
                builder.setTitle("Attention");
                builder.setMessage("Confirm to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        db = new UserDataSource(this);
        db.open();

        ListView listview1 = (ListView) findViewById(R.id.listView1);
        final List<UserData> values = db.getAllUserData();
        userDataArrayAdapter = new userDataArrayAdapter(this, 0, values);
        listview1.setAdapter(userDataArrayAdapter);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userDataArrayAdapter.getCount()>0){
                    final UserData userData = values.get(position);
                    Double sendLaTi = Double.parseDouble((userData.getLati()));
                    Double sendLongTi = Double.parseDouble((userData.getLongti()));
                    String sendUsername = userData.getUser();

                    final Intent sendIntent = new Intent(UserPageActivity.this, MapsActivity.class);

                    Bundle send = new Bundle();

                    send.putDouble("LATI", sendLaTi);
                    send.putDouble("LONGTI", sendLongTi);
                    send.putString("USER", sendUsername);

                    sendIntent.putExtras(send);

                    startActivity(sendIntent);
                }
            }
        });
    }
    class userDataArrayAdapter extends ArrayAdapter<UserData>{
        Context context;
        List<UserData> objects;
        public userDataArrayAdapter(Context context, int resource, List<UserData> objects){
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }
        @Override public View getView(int position, View convertView, ViewGroup parent){
            UserData userData = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.mainpage_custom, null);

            TextView listUser = (TextView) view.findViewById(R.id.textView);
            listUser.setText(userData.getUser());

            return view;
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.listView1);

        db.open();
        List<UserData> values = db.getAllUserData();
        listView.setAdapter(userDataArrayAdapter);
    }

    @Override
    protected void onPause() {
        db.close();
        super.onPause();
    }
}
