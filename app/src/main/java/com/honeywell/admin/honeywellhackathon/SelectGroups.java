package com.honeywell.admin.honeywellhackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectGroups extends AppCompatActivity {
    final Context context = this;
    private static final String[][] data = {{"sub1","sub2","sub3"},{"sub1","sub2"},{"sub1","sub2","sub3"}};
    private ExpandableListView expandableListView;
    Button button1;
    static int count1 = 0;
    static int count2 = 0;
    static int count3 = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandablelist);
        button1 = (Button)findViewById(R.id.btn1);
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView1);
        expandableListView.setAdapter(new SampleExpandableListAdapter(context, this, data));
        myRef.setValue("hi");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                String buffer = null;
                String output_String = "";
                for(int i=0; i<3; i++){
                    buffer = settings.getString(String.valueOf((int)i),"false");
                    if(buffer.equals("true"))
                        output_String = "group " + i + " ";
                        if(i == 0) {
                            myRef.child(output_String).child("users" + count1).setValue(user.getUid());
                            count1++;
                        }
                        if(i == 1) {
                        myRef.child(output_String).child("users" + count2).setValue(user.getUid());
                        count2++;
                    }
                        if(i == 2) {
                        myRef.child(output_String).child("users" + count3).setValue(user.getUid());
                        count3++;
                    }
                }
                //output_String += "is checked";
                //Toast.makeText(SelectGroups.this, output_String, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectGroups.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expandablelist, menu);
        return true;
    }

}

