package com.example.yevgeniya.universitymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.widget.ExpandableListView.*;

public class GotoActivity extends Activity implements OnClickListener, OnChildClickListener, OnGroupClickListener{

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SimpleExpandableListAdapter adapter;
    private ExpandableListView elv;
    private static AdapterHelper ah;
    private int[]officePos=new int[]{0,1,2,3};
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        elv = (ExpandableListView)findViewById(R.id.officeELV);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUser=user;
                    Toast.makeText(getApplicationContext(), "onAuthStateChanged:signed_in:" + user.getUid(),Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "onAuthStateChanged:signed_out:" + user.getUid(),Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        ah = new AdapterHelper(this);
        adapter = ah.getAdapter();
        elv.setAdapter(adapter);

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        intent = new Intent("com.example.yevgeniya.universitymanager.deanoffice");
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        for(int i:officePos){
            if(i!=groupPosition) elv.collapseGroup(groupPosition);
        }
        return false;
    }
}
