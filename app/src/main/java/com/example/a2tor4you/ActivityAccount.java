package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityAccount extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ImageButton btnReport;

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
        String userID = getIntent().getStringExtra("userID");


        //String firstName = dbHelper.getUserName(phoneNumber, password, selectedRole);
        String userName = dbHelper.getUserName(Long.parseLong(userID));

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

        btnReport = findViewById(R.id.btnReport);

        btnReport.setOnClickListener(view ->  {
            reportProblem();
        });

        ImageButton btnContactUs = findViewById(R.id.btnContactUs);

        btnContactUs.setOnClickListener(view -> {
            showAlertDialog("Contact us at: 2tor4you@gmail.com");

        });


        // Navigation Bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.account);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.account:
                        return true;

                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), ActivityCalendar.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(), ActivityFindTutor.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.messages:
                        startActivity(new Intent(getApplicationContext(), ChatMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ActivityHomeStudent.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }


    /*Report a Problem*/

    /*Our email = 2tor4YouApp@gmail.com*/
    /*Email password = ProjectApp1234*/
    private static final int EMAIL_REQUEST_CODE = 1;

    private void reportProblem() {
        String email = "2tor4YouApp@gmail.com";
        String subject = "Reporting for app " + getString(R.string.app_name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "");

        startActivityForResult(Intent.createChooser(intent, "Choose an email client:"), EMAIL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EMAIL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Email sent successfully, show a toast message
                Toast.makeText(this, "Email sending failed", Toast.LENGTH_SHORT).show();
            } else {
                // Email sending failed or user canceled the operation
                Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
            }
        }
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