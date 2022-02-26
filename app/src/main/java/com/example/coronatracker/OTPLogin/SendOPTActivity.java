package com.example.coronatracker.OTPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatracker.HomeActivity;
import com.example.coronatracker.R;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOPTActivity extends AppCompatActivity {

    EditText inputMobileNumber, etopt;
    Button btnGetOTP, btnSubmitOTP, btnResendOTP;
    TextView userMobileNumber,tvcountryCode;

    String phoneNumber, verificationID,countryCode;

    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    PhoneAuthProvider.ForceResendingToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_optactivity);

        userMobileNumber = (TextView) findViewById(R.id.tvUserNumber);
        tvcountryCode = (TextView) findViewById(R.id.countryCode);
        inputMobileNumber = (EditText) findViewById(R.id.input_mobile_number);
        etopt = (EditText) findViewById(R.id.etopt);
        btnGetOTP = (Button) findViewById(R.id.btn_get_opt);
        btnSubmitOTP = (Button) findViewById(R.id.btn_submit_otp);
        btnResendOTP = (Button) findViewById(R.id.btn_resend_otp);
        btnResendOTP.setEnabled(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();


        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryCode = tvcountryCode.getText().toString().trim();
                phoneNumber = countryCode+inputMobileNumber.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    if ((phoneNumber).length() == 13) {

                        sendVerificationCaode(phoneNumber);

                    } else {
                        inputMobileNumber.setError("Enter a valid number");
                        Toast.makeText(SendOPTActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    inputMobileNumber.setError("Enter Mobile Number");
                    Toast.makeText(SendOPTActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
                userMobileNumber.setText(String.valueOf(phoneNumber));
            }
        });

        btnSubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etopt.getText().toString().isEmpty()) {
                    etopt.setError("Enter code");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, etopt.getText().toString());
                authenticateUser(credential);
            }
        });

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnResendOTP.setEnabled(false);
                sendVerificationCaode(phoneNumber);
            }
        });



        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(SendOPTActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID = s;
                token = forceResendingToken;

                btnResendOTP.setEnabled(false);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                btnResendOTP.setEnabled(true);

            }
        };
    }

    private void sendVerificationCaode(String phoneNumber) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setActivity(this)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void authenticateUser(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(@NonNull AuthResult authResult) {

                Toast.makeText(SendOPTActivity.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SendOPTActivity.this, HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SendOPTActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
    }
}