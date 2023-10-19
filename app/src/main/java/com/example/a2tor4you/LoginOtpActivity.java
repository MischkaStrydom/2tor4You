package com.example.a2tor4you;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtpActivity extends AppCompatActivity {
    static String phoneNumber;
    static String password;
    static String selectedRole;
    DBHelper myDb;

    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    ImageView btnBack;
    EditText otpInput;
    Button nextBtn;
    TextView resendOtpTextView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        btnBack = findViewById(R.id.btnBackOTP);
        otpInput = findViewById(R.id.txtOTPNum);
        nextBtn = findViewById(R.id.btnNext);
        resendOtpTextView = findViewById(R.id.resend_otp_textview);

        //On button back takes user back to login screen
        btnBack.setOnClickListener(view -> startActivity(new Intent(LoginOtpActivity.this,ActivityLogin.class)));

        phoneNumber = getIntent().getExtras().getString("phone");
        password = getIntent().getExtras().getString("password");
        selectedRole = getIntent().getExtras().getString("selectedRole");


        sendOtp(phoneNumber,false);
        nextBtn.setOnClickListener(view -> {
            String enteredOtp = otpInput.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
            signIn(credential);

        });


        resendOtpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp(phoneNumber,true);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllFields()) {
                    // All fields are filled, proceed with OTP verification
                    String enteredOtp = otpInput.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOtp);
                    signIn(credential);
                } else {
                    // Fields are missing, show an error message
                    AndroidUtil.showToast(getApplicationContext(), "All fields are required.");
                }
            }
        });

    }

    void sendOtp(String phoneNumber,boolean isResponse) {
        startResendTimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(), "OTP verification failed");

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext(), "OTP sent successfully");

                            }
                        });

        if(isResponse){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }

    void signIn(PhoneAuthCredential phoneAuthCredential){
        //login and go to next activity

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                myDb = new DBHelper(LoginOtpActivity.this);

                if(task.isSuccessful()){


                    if(selectedRole.equals("Admin"))
                    {
                        phoneNumber = getIntent().getExtras().getString("phone");

                        String admin = myDb.getAdminRole(phoneNumber);

                        if(admin.equals("CEO")){
                            Intent intent = new Intent(LoginOtpActivity.this, AdminActivity.class);
                            intent.putExtra("phone", phoneNumber);
                            intent.putExtra("password", password);
                            intent.putExtra("selectedRole", selectedRole);
                            startActivity(intent);
                        } else if (admin.equals("Application Admin")) {
                            Intent intent = new Intent(LoginOtpActivity.this, AdminHome.class); //ActivityReportView
                            intent.putExtra("phone", phoneNumber);
                            intent.putExtra("password", password);
                            intent.putExtra("selectedRole", selectedRole);
                            startActivity(intent);
                        }

                    }
                    else {

                        Intent intent = new Intent(LoginOtpActivity.this, ActivityHomeStudent.class);
                        intent.putExtra("phone", phoneNumber);
                        intent.putExtra("password", password);
                        intent.putExtra("selectedRole", selectedRole);
                        startActivity(intent);
                    }
                }
                else{
                    AndroidUtil.showToast(getApplicationContext(),"OTP verification failed");
                }

            }
        });

    }
    void startResendTimer()
    {
        resendOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText("Resend OTP in " + timeoutSeconds + " seconds");
                if(timeoutSeconds <= 0){
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resendOtpTextView.setEnabled(true);
                        }
                    });
                }


            }
        },0,1000);
    }

    private boolean checkAllFields() {
        // Check if OTP is entered
        String enteredOtp = otpInput.getText().toString().trim();

        // Check if OTP is filled
        if (enteredOtp.isEmpty()) {
            otpInput.setError("OTP is required");
            return false;
        }

        return true; // All fields are filled
    }

}