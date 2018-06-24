package com.example.yevgeniya.universitymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity implements OnClickListener{
    private Intent intent;
    private Button create,go, logout;
    private TextView greeting;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create = (Button)findViewById(R.id.createBTN);
        go = (Button)findViewById(R.id.gotoBTN);
        logout = (Button)findViewById(R.id.logOutBTN);
        greeting = (TextView) findViewById(R.id.greetingTV);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            greeting.setText("You logged as "+mAuth.getCurrentUser().getPhoneNumber());
        }else{
            greeting.setText("You need to sigup to access offices of WSFiZ");
        }
        logout.setOnClickListener(this);
        create.setOnClickListener(this);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotoBTN: {
                intent = new Intent(this,GotoActivity.class);
                startActivity(intent);
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
