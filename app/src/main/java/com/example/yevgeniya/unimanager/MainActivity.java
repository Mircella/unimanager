package com.example.yevgeniya.unimanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity implements View.OnClickListener {
    private Intent intent, menuIntent;
    private Button create,go, logout;
    private TextView greeting;
    FirebaseAuth mAuth;

    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    boolean enter=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create = (Button)findViewById(R.id.createBTN);
        go = (Button)findViewById(R.id.gotoBTN);
        logout = (Button)findViewById(R.id.logOutBTN);
        greeting = (TextView) findViewById(R.id.greetingTV);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUser=user;
                    menuIntent = new Intent(MainActivity.this,MenuActivity.class);
                    greeting.setText("Wellcome!");
                    startActivity(menuIntent);
                   //Toast.makeText(getApplicationContext(), "already entered" ,Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                   //Toast.makeText(getApplicationContext(), "onAuthStateChanged:signed_out:" + user.getUid(),Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mUser=user;
            menuIntent = new Intent(MainActivity.this,MenuActivity.class);
            greeting.setText("Wellcome!");
            startActivity(menuIntent);
        }
        mAuth.addAuthStateListener(mAuthListener);
        logout.setOnClickListener(this);
        create.setOnClickListener(this);
        go.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(enter){
        menuIntent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotoBTN: {

            }break;
            case R.id.createBTN: {
                intent = new Intent(this,SigninActivity.class);
                startActivity(intent);
            }break;
            case R.id.logOutBTN:{
                if(mAuth.getCurrentUser()!=null){
                    mAuth.signOut();
                    greeting.setText("You logged out");
                }
            }break;
        }
    }
}
