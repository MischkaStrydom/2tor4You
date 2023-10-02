package com.example.a2tor4you;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ActivityAccount extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ImageView btnBack = findViewById(R.id.btnBackAccount);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityHomeStudent.class)));

        ImageView notification = findViewById(R.id.btnNotifications);
        ImageView settings = findViewById(R.id.btnSettings);
        ImageView terms = findViewById(R.id.btnTermsAndC);
        ImageView logout = findViewById(R.id.btnLogOut);

        notification.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityNotifications.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivitySettings.class)));
        terms.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityTermAndConditions.class)));
       // logout.setOnClickListener(view -> ));

        ImageButton btnContactUs = findViewById(R.id.btnContactUs);

        btnContactUs.setOnClickListener(view -> {
            showAlertDialog("Contact 2tor4You: 2tor4you@gmail.com");

        });
    }

    /*Displays email details when click Contact 2tor4You button*/
    private void showAlertDialog(String message){
        AlertDialog dialog = new AlertDialog.Builder(ActivityAccount.this)
                .setTitle("Contact Us")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

}