package com.example.coronatracker.OTPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatracker.HomeActivity;
import com.example.coronatracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SubmitOTPActivity extends AppCompatActivity {

    EditText inputNumber1,inputNumber2,inputNumber3,inputNumber4,inputNumber5,inputNumber6;
    Button submit;
    TextView userNumber,resendotp;
    String getotpbackend,phonenumber;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_otpactivity);

        inputNumber1 = (EditText) findViewById(R.id.inputotp1);
        inputNumber2 = (EditText) findViewById(R.id.inputotp2);
        inputNumber3 = (EditText) findViewById(R.id.inputotp3);
        inputNumber4 = (EditText) findViewById(R.id.inputotp4);
        inputNumber5 = (EditText) findViewById(R.id.inputotp5);
        inputNumber6 = (EditText) findViewById(R.id.inputotp6);
        userNumber = (TextView) findViewById(R.id.tvUserNumber);
        resendotp = (TextView) findViewById(R.id.tvresendotp);
        submit = (Button) findViewById(R.id.btn_submit_otp);

        phonenumber = getIntent().getStringExtra("mobileno");
        userNumber.setText(String.format("+91-%s",phonenumber));

        getotpbackend = getIntent().getStringExtra("backendotp");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputNumber1.getText().toString().trim().isEmpty()&&!inputNumber2.getText().toString().trim().isEmpty()&&
                        !inputNumber3.getText().toString().trim().isEmpty()&&!inputNumber4.getText().toString().trim().isEmpty()&&
                        !inputNumber5.getText().toString().trim().isEmpty()&&!inputNumber6.getText().toString().trim().isEmpty()){

                    String enteredotpcode = inputNumber1.getText().toString() +
                            inputNumber2.getText().toString() +
                            inputNumber3.getText().toString() +
                            inputNumber4.getText().toString() +
                            inputNumber5.getText().toString() +
                            inputNumber6.getText().toString();

                    if(getotpbackend !=null){

                        PhoneAuthCredential  phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend,enteredotpcode
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            UserModel userModel = new UserModel();
                                            userModel.setPhonenumber(phonenumber);

                                            database.getReference().child("Users").child(user.getUid()).setValue(userModel);

                                            Intent intent = new Intent(SubmitOTPActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(SubmitOTPActivity.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else {
                        Toast.makeText(SubmitOTPActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(SubmitOTPActivity.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOTPmove();

        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCaode(phonenumber);
            }
        });


    }

    private void sendVerificationCaode(String phonenumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(SubmitOTPActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(SubmitOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String newbackendotp,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {

                newbackendotp=getotpbackend;
            Toast.makeText(SubmitOTPActivity.this, "OTP sent successful", Toast.LENGTH_SHORT).show();
        }
    };

    private void numberOTPmove() {

        inputNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!s.toString().trim().isEmpty()){
                            inputNumber2.requestFocus();
                        }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputNumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputNumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser!=null){
            startActivity(new Intent(SubmitOTPActivity.this,HomeActivity.class));
            finish();
        }
    }
}