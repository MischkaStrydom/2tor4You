package com.example.a2tor4you;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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