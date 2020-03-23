package com.example.carnaticapp;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import com.google.firebase.auth.PhoneMultiFactorGenerator;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.MultiFactorSession;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * Activity that allows the user to enroll second factors.
 */
public class MultiFactorEnrollActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "PhoneAuthActivity";

    private EditText mPhoneNumberText;
    private EditText mSmsCodeText;

    private String mCodeVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        TextView titleText = findViewById(R.id.titleText);
        titleText.setText("SMS as a Second Factor");

        findViewById(R.id.status).setVisibility(View.GONE);
        findViewById(R.id.detail).setVisibility(View.GONE);
        findViewById(R.id.buttonStartVerification).setOnClickListener(this);
        findViewById(R.id.buttonVerifyPhone).setOnClickListener(this);
        mPhoneNumberText = findViewById(R.id.fieldPhoneNumber);
        mSmsCodeText = findViewById(R.id.fieldVerificationCode);
    }

    private void onClickVerifyPhoneNumber() {
        final String phoneNumber = mPhoneNumberText.getText().toString();

        final OnVerificationStateChangedCallbacks callbacks =
                new OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        // Instant-validation has been disabled (see requireSmsValidation below).
                        // Auto-retrieval has also been disabled (timeout is set to 0).
                        // This should never be triggered.
                        throw new RuntimeException(
                                "onVerificationCompleted() triggered with instant-validation and auto-retrieval disabled.");
                    }

                    @Override
                    public void onCodeSent(
                            final String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        Log.d(TAG, "onCodeSent:" + verificationId);
                        Toast.makeText(
                                MultiFactorEnrollActivity.this, "SMS code has been sent", Toast.LENGTH_SHORT)
                                .show();

                        mCodeVerificationId = verificationId;
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.w(TAG, "onVerificationFailed ", e);
                        Toast.makeText(
                                MultiFactorEnrollActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                };

        FirebaseAuth.getInstance()
                .getCurrentUser()
                .getMultiFactor()
                .getSession()
                .addOnCompleteListener(
                        new OnCompleteListener<MultiFactorSession>() {
                            @Override
                            public void onComplete(@NonNull Task<MultiFactorSession> task) {
                                if (task.isSuccessful()) {
                                    PhoneAuthOptions phoneAuthOptions =
                                            PhoneAuthOptions.newBuilder()
                                                    .setPhoneNumber(phoneNumber)
                                                    // A timeout of 0 disables SMS-auto-retrieval.
                                                    .setTimeout(0L, TimeUnit.SECONDS)
                                                    .setMultiFactorSession(task.getResult())
                                                    .setCallbacks(callbacks)
                                                    // Disable instant-validation.
                                                    .requireSmsValidation(true)
                                                    .build();

                                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
                                } else {
                                    Toast.makeText(
                                            MultiFactorEnrollActivity.this,
                                            "Failed to get session: " + task.getException(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
    }

    private void onClickSignInWithPhoneNumber() {
        String smsCode = mSmsCodeText.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mCodeVerificationId, smsCode);
        enrollWithPhoneAuthCredential(credential);
    }

    private void enrollWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .getMultiFactor()
                .enroll(PhoneMultiFactorGenerator.getAssertion(credential), /* displayName= */ null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(
                                MultiFactorEnrollActivity.this,
                                "MFA enrollment was successful",
                                Toast.LENGTH_LONG)
                                .show();

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "MFA failure", e);
                        Toast.makeText(
                                MultiFactorEnrollActivity.this,
                                "MFA enrollment was unsuccessful. " + e,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStartVerification:
                onClickVerifyPhoneNumber();
                break;
            case R.id.buttonVerifyPhone:
                onClickSignInWithPhoneNumber();
                break;
        }
    }
}