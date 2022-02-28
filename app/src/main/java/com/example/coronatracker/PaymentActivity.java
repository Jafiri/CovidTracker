package com.example.coronatracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    EditText amountet, paymentname;
    Button paymentbtn;
    TextView mastercardno, visacardno,upi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amountet = (EditText) findViewById(R.id.amountet);
        mastercardno = (TextView) findViewById(R.id.mastercardno);
        upi = (TextView) findViewById(R.id.upi);
        visacardno = (TextView) findViewById(R.id.visacardno);
        paymentname = (EditText) findViewById(R.id.paymentname);
        paymentbtn = (Button) findViewById(R.id.paymentbtn);
        //idtv = (TextView) findViewById(R.id.idtv);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Checkout.preload(PaymentActivity.this);
        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentname.getText().toString().isEmpty()) {
                    paymentname.setError("Enter name");
                    return;
                }
                if (amountet.getText().toString().isEmpty()) {
                    amountet.setError("Please enter amount");
                    return;
                }

                startPayment(Integer.parseInt(amountet.getText().toString()));
            }
        });

        String masterCardNumber = mastercardno.getText().toString();
        String visCardNumber = visacardno.getText().toString();
        String upiNumber = upi.getText().toString();

        mastercardno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) PaymentActivity.this.getSystemService(PaymentActivity.this.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("text", masterCardNumber);
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(PaymentActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });
        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) PaymentActivity.this.getSystemService(PaymentActivity.this.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("text", upiNumber);
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(PaymentActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        visacardno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) PaymentActivity.this.getSystemService(PaymentActivity.this.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("text", visCardNumber);
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(PaymentActivity.this, "Text Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startPayment(int amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_w0hZ5rRHF6tUWu");
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "Contribution to Covid Tracker");
            jsonObject.put("description", "Thank you for the contribution");
            jsonObject.put("theme.color", "#1E1E30");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", amount * 100);

            JSONObject retryobj = new JSONObject();
            retryobj.put("enabled", "true");
            retryobj.put("max_count", 4);

            jsonObject.put("retry", retryobj);

            checkout.open(PaymentActivity.this, jsonObject);

        } catch (Exception e) {
            Toast.makeText(PaymentActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        new AlertDialog.Builder(PaymentActivity.this).setTitle("Thank you for your support").
                setMessage("Name :" + paymentname.getText().toString() + "\nAmount :" + amountet.getText().toString() + "\nPayment ID :" + s + "\nPayment Successful")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PaymentActivity.this, "Please rise a ticket regarding the issue to the developer mailID", Toast.LENGTH_LONG).show();
            }
        }).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        new AlertDialog.Builder(PaymentActivity.this).setTitle("Transaction failed !").
                setMessage("Sorry for the inconvenience we'll get back soon")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PaymentActivity.this, "Please rise a ticket regarding the issue to the developer mailID", Toast.LENGTH_LONG).show();
            }
        }).show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}