package com.example.deeppatel.mosersuggestionapp;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HR extends AppCompatActivity {

    String userid;
    Button submit_HR;
    ListView listview_HR;
    EditText editText_HR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr);
        userid = Settings.Secure.getString(HR.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        editText_HR = (EditText) findViewById(R.id.editText_HR);
        listview_HR = (ListView) findViewById(R.id.listview_HR);
        submit_HR = (Button) findViewById(R.id.submit_HR);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("HR");

        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);
        Query oldPosts = mDatabase.orderByChild("created/time").endAt(cutoff);
        oldPosts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemsnapshot: dataSnapshot.getChildren()){
                    itemsnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final FirebaseListAdapter<Suggestion> HRListAdapter = new FirebaseListAdapter<Suggestion>(
                this,
                Suggestion.class,
                android.R.layout.simple_list_item_1,
                mDatabase.limitToLast(10)) {

            @Override
            public Suggestion getItem(int position) {
                return super.getItem(getCount() - 1 - position);
            }

            @Override
            protected void populateView(View v, Suggestion model, int position) {

                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model.input);

            }
        };
        listview_HR.setAdapter(HRListAdapter);
        submit_HR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = editText_HR.getText().toString();

                if(getInput == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Input is empty", Toast.LENGTH_LONG).show();
                }

                else {

                    String k = mDatabase.push().getKey();
                    Suggestion s = new Suggestion(k, getInput, userid);
                    Map<String, Object> x = new HashMap<>();
                    x.put(k, s.toMap());
                    mDatabase.updateChildren(x);
                    editText_HR.setText("");
                }
            }
        });

    }
}
