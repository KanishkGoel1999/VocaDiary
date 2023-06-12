package com.example.android.vocadiaryk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class signInPage extends AppCompatActivity{
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    Button sendOtp;
    Button resendOtp;
    Button verifyOtp;
    TextView phone;
    TextView otp;
    FirebaseAuth mAuth;
    String verificationId;
    TextView signInWithEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);

//        TextView username = findViewById(R.id.username);
//        TextView password = findViewById(R.id.password);
//        MaterialButton login = findViewById(R.id.logInButton);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
        googleBtn = findViewById(R.id.googleBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        sendOtp = findViewById(R.id.sendOtp);
        resendOtp = findViewById(R.id.resendOtp);
        verifyOtp = findViewById(R.id.verifyOtp);
        phone = findViewById(R.id.phone);
        mAuth = FirebaseAuth.getInstance();
        otp = findViewById(R.id.otp);
        signInWithEmail = findViewById(R.id.signInWithEmail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount((this));
        if(acct!=null){
            navigateToSecondActivity();
        }


        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(signInPage.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
                else{
                    String phoneNumber = phone.getText().toString();
                    sendCode(phoneNumber);
                    sendOtp.setVisibility(View.GONE);
                    resendOtp.setVisibility((View.VISIBLE));
                    verifyOtp.setVisibility(View.VISIBLE);
                }

            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(signInPage.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
                else{
                    String phoneNumber = phone.getText().toString();
                    sendCode(phoneNumber);
                }
            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(signInPage.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    verifyCode(otp.getText().toString());
                }
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(signInPage.this, emailPassword.class);
                startActivity(intent);
            }
        });

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NotNull String s,
                               @NotNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
//            Log.d(TAG, "onVerificationCompleted:" + credential);
//            signInWithPhoneAuthCredential(credential);
            final String code = credential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NotNull FirebaseException e) {
            Toast.makeText(signInPage.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }
    };

    private void sendCode(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInByCredential(credential);
    }

    private void signInByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    navigateToSecondActivity();
                }
            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(signInPage.this, HomeActivity.class);
        startActivity(intent);
    }
}

