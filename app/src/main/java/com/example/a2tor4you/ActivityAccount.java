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
import android.widget.TextView;

public class ActivityAccount extends AppCompatActivity {


    DBHelper dbHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView accountName = findViewById(R.id.txtAccNameSurnameSubHeading);
        TextView emailName = findViewById(R.id.txtAccEmailSubHeading);

        dbHelper = new DBHelper(this);

        String phoneNumber = getIntent().getStringExtra("phone");
        String password = getIntent().getStringExtra("password");
        String selectedRole = getIntent().getStringExtra("selectedRole");


        //String firstName = dbHelper.getUserName(phoneNumber, password, selectedRole);
        String userName = dbHelper.getUserName(phoneNumber, password, selectedRole);

        if (userName != null) {
            accountName.setText("");
            accountName.setText(userName);
            // The 'userName' variable now contains the user's first name.
            // You can use it for further customization.
        } else {
            // Handle the case where no results were found or userName is empty.
            // You can display an error message or take appropriate action.
        }

        //String email = dbHelper.getUserName(phoneNumber, password, selectedRole);
        String email = dbHelper.getEmail(phoneNumber, password, selectedRole);

        if (email != null) {
            emailName.setText("");
            emailName.setText(email);
            // The 'userName' variable now contains the user's first name.
            // You can use it for further customization.
        } else {
            // Handle the case where no results were found or userName is empty.
            // You can display an error message or take appropriate action.
        }

        ImageView btnBack = findViewById(R.id.btnBackAccount);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityHomeStudent.class)));

        Button btnEdit = findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("Tutor".equals(selectedRole)){
                    Intent intent = new Intent(ActivityAccount.this,ActivityTutorProfile.class);
                    intent.putExtra("phone", phoneNumber);
                    intent.putExtra("password", password);
                    intent.putExtra("selectedRole", selectedRole);
                    startActivity(intent);
                }
                else if ("Student".equals(selectedRole)){
                    Intent intent = new Intent(ActivityAccount.this,ActivityMyProfile.class);
                    intent.putExtra("phone", phoneNumber);
                    intent.putExtra("password", password);
                    intent.putExtra("selectedRole", selectedRole);
                    startActivity(intent);

                }


            }
        });



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