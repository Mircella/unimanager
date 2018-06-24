package com.example.yevgeniya.unimanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yevgeniya.unimanager.additional.AdapterHelper;
import com.example.yevgeniya.unimanager.additional.Student;
import com.example.yevgeniya.unimanager.listeners.IDListener;
import com.example.yevgeniya.unimanager.listeners.NotificationListener;
import com.example.yevgeniya.unimanager.listeners.StudentListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class MenuActivity extends Activity implements View.OnClickListener, ExpandableListView.OnChildClickListener,ExpandableListView.OnGroupExpandListener {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseDatabase database;
    private DatabaseReference reference1,reference2;
    private StudentListener dbListener1;
    private NotificationListener dbListener2;
    private IDListener dbListener3;
    private Query query;

    private String studentID;
    private ArrayList<String>queries;

    private SimpleExpandableListAdapter adapter;
    private ExpandableListView elv;
    private static AdapterHelper ah;
    private int[]officePos=new int[]{0,1,2,3};
    private final int[] prevPositions = {-1};

    private TextView fb,fb2,issue,office,config;
    private Button show;

    private Intent configIntent,ticketIntent;

    private final int DIALOG = 1;
    private DialogInterface.OnClickListener listener;
    private View dialogView;
    private Dialog ticketDialog;

    private int groupPos, childPos;

    private Student student1,student2,student3;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        elv = (ExpandableListView)findViewById(R.id.officeELV);
        config = (TextView)findViewById(R.id.configTV);
        fb = (TextView)findViewById(R.id.firebaseTV);
        fb2 = (TextView)findViewById(R.id.firebaseTV2);
        show = (Button) findViewById(R.id.showBTN);
        queries = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        studentID = mUser.getUid();

        database = FirebaseDatabase.getInstance();
        reference1 = database.getReference().child("students");
        reference2 = database.getReference().child("notifications");
        query = reference1.orderByChild("id").equalTo(studentID);
        if(dbListener1==null){
            dbListener1 = new StudentListener();
            }
        if(dbListener2==null){
            dbListener2 = new NotificationListener(this,studentID);
            }
        if(dbListener3==null){
            dbListener3 = new IDListener();
        }

        reference1.addChildEventListener(dbListener1);
        reference2.addChildEventListener(dbListener2);
        query.addChildEventListener(dbListener3);
        show.setOnClickListener(this);

        ah = new AdapterHelper(this);
        adapter = ah.getAdapter();
        elv.setAdapter(adapter);
        elv.setOnChildClickListener(this);
        elv.setOnGroupExpandListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        boolean soundOn = sp.getBoolean("sound",true);
        boolean vibrOn = sp.getBoolean("vibration",true);
        config.setText("sound "+soundOn+" vibration "+vibrOn);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:{
                configIntent = new Intent(this,ConfigActivity.class);
                startActivity(configIntent);
            }break;
            case R.id.tickets:{
                queries = dbListener3.getQueries();
                ticketIntent = new Intent(this,TicketActivity.class);
                ticketIntent.putExtra("tickets",queries);
                startActivity(ticketIntent);
            }break;
            case R.id.logout:{
                mAuth.signOut();
                this.finish();
            }break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showBTN:{
                StringBuilder stringBuilder = new StringBuilder();
                queries = dbListener3.getQueries();
                for(String line:queries){
                    stringBuilder.append(line);
                }
                fb2.setText(stringBuilder.toString());
            }break;
        }

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        groupPos = groupPosition;
        childPos = childPosition;
        showDialog(DIALOG);
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case Dialog.BUTTON_POSITIVE:{
                        queries = dbListener3.getQueries();
                        if(queries.size()<3){
                        String officeName = office.getText().toString();
                         String issueName = issue.getText().toString();
                        student1 = new Student(studentID,officeName,issueName);
                        fb.setText("FB data "+studentID+" "+officeName+" "+issueName);
                        reference1.push().setValue(student1);
                        }else{
                            Toast.makeText(getApplicationContext(), "You may get only 3 tickets", Toast.LENGTH_SHORT).show();

                        }
                    }break;
                    case Dialog.BUTTON_NEUTRAL:{}break;
                }

            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("E-Ticket");
        builder.setPositiveButton("YES",listener);
        builder.setNeutralButton("NO",listener);
        dialogView = (LinearLayout)getLayoutInflater().inflate(R.layout.ticket,null);
        builder.setView(dialogView);
        office = (TextView)dialogView.findViewById(R.id.officeTV);
        office.setText(ah.getOffice(groupPos));
        issue = (TextView)dialogView.findViewById(R.id.issueTV);
        issue.setText(ah.getItem(groupPos,childPos));
        ticketDialog = builder.create();
        ticketDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }
        });
        return ticketDialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if(id==DIALOG){
            office.setText(""+ah.getOffice(groupPos));
            issue.setText(""+ah.getItem(groupPos,childPos));
        }
    }


    @Override
    public void onGroupExpand(int groupPosition) {
        if(prevPositions[0]>=0){
            elv.collapseGroup(prevPositions[0]);
        }
        prevPositions[0]=groupPosition;
    }
}
