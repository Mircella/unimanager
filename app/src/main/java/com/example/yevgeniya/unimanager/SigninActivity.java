package com.example.yevgeniya.unimanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.concurrent.TimeUnit;

public class SigninActivity extends Activity implements View.OnClickListener {
    private EditText phone,code;
    private Button send,confirm,resend;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        phone = (EditText)findViewById(R.id.phoneET);
        code = (EditText)findViewById(R.id.codeET);
        send = (Button)findViewById(R.id.sendBTN);
        send.setOnClickListener(this);
        confirm = (Button)findViewById(R.id.confirmBTN);
        resend = (Button)findViewById(R.id.resendBTN);
        resend.setOnClickListener(this);
        confirm.setOnClickListener(this);
        callback_verification();

        mAuth = FirebaseAuth.getInstance();
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(), "onAuthStateChanged:signed_in:" + user.getUid(),Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "onAuthStateChanged:signed_out:" + user.getUid(),Toast.LENGTH_SHORT).show();
                }

            }
        };
        mAuth.addAuthStateListener(mAuthListener);*/
    }

    private void startVerification(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,this,mCallbacks);
    }

    private void callback_verification(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential){
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e){
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(getApplicationContext(),"Invalid phone number",Toast.LENGTH_SHORT).show();

                }else if(e instanceof FirebaseTooManyRequestsException){
                    Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token){
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        final Intent intent = new Intent(this,MenuActivity.class);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user=task.getResult().getUser();
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){}
                }
            }
        });
    }

    private void verifyCode(String verifId,String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phone.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    private String correctNumber(String number){
        StringBuilder sb=new StringBuilder();
        char []arr=new char[number.length()];
        for(int i=0;i<number.length();i++){
            arr[i] = number.charAt(i);
        }
        if(arr[0]!='+'){
            sb.append("+");
            sb.append(number);
            return sb.toString();
        }return number;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendBTN:{
                if (!validatePhoneNumber()) {
                    return;
                }
                confirm.setVisibility(View.VISIBLE);
                code.setVisibility(View.VISIBLE);
                resend.setVisibility(View.VISIBLE);
                String number = phone.getText().toString();
                String numberCorrect = correctNumber(number);
                startVerification(numberCorrect);
            }break;
            case R.id.confirmBTN:{
                String codeVerif = code.getText().toString();
                if (TextUtils.isEmpty(codeVerif)) {
                    code.setError("Cannot be empty.");
                    return;
                }
                verifyCode(mVerificationId,codeVerif);
            }break;
            case R.id.resendBTN:
                resendVerificationCode(phone.getText().toString(), mResendToken);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
