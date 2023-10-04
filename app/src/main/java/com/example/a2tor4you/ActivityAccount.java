package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2tor4you.Model.UserModel;
import com.example.a2tor4you.utils.AndroidUtil;
import com.example.a2tor4you.utils.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ActivityAccount extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static String phoneNumber;
    static String password;
    static String selectedRole;
    ImageButton btnLogout;
    ImageButton btnReport;
    ImageView profile_image_view;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;
    DBHelper dbHelper ;

    public ActivityAccount() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        TextView accountName = findViewById(R.id.txtAccNameSurnameSubHeading);
        TextView emailName = findViewById(R.id.txtAccEmailSubHeading);
        dbHelper = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        if (loggedInUserId != -1) {
            // Fetch user's name and surname from the database based on userID
            String userName = dbHelper.getUserName(loggedInUserId); // Implement this method
            String email = dbHelper.getEmail(loggedInUserId);
            accountName.setText(userName);
            emailName.setText(email);
        } else {
            // Handle the case where the userID is not found (e.g., user not logged in)
            accountName.setText("Guest"); // Display a default value or handle it as needed
            emailName.setText("Guest Email"); // Display a default value or handle it as needed
        }


        // Log out Button
        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear user data or perform any necessary cleanup

                // Remove the loggedInUserId from SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("loggedInUserId");
                editor.apply();

                // Redirect to the login screen (you can use an Intent)
                Intent intent = new Intent(ActivityAccount.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // Button edit account
        Button btnEdit = findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (loggedInUserId != -1) {
                    // Fetch user's name and surname from the database based on userID
                    String userRole = dbHelper.getUserRole(loggedInUserId); // Implement this method
                    if ("Tutor".equals(userRole)){
                        Intent intent = new Intent(ActivityAccount.this,ActivityTutorProfile.class);
                        startActivity(intent);
                    }
                    else if ("Student".equals(userRole)){
                        Intent intent = new Intent(ActivityAccount.this,ActivityMyProfile.class);
                        startActivity(intent);

                    }

                } else {
                    // Handle the case where the userID is not found (e.g., user not logged in)
                    Toast.makeText(getApplicationContext(), "Unknown Error Occurred ", Toast.LENGTH_LONG).show();
                }


            }
        });

        ImageView notification = findViewById(R.id.btnNotifications);
        ImageView settings = findViewById(R.id.btnSettings);
        ImageView terms = findViewById(R.id.btnTermsAndC);


        notification.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityNotifications.class)));
        settings.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivitySettings.class)));
        terms.setOnClickListener(view -> startActivity(new Intent(ActivityAccount.this,ActivityTermAndConditions.class)));

        // Button report
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

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),ActivityAccount.class));
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ActivityHomeStudent.class));
                        return true;

                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(),ActivityCalendar.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.findTutors:
                        startActivity(new Intent(getApplicationContext(),ActivityFindTutor.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePic(getApplicationContext(),selectedImageUri,profile_image_view);
                        }
                    }
                }
        );
    }

    // Profile Pic
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_account, container, false);

        profile_image_view = view.findViewById(R.id.profile_image_view);

        getUserData();

        profile_image_view.setOnClickListener((v) -> {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });
        return view;

    }

    void getUserData() {

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        AndroidUtil.setProfilePic(getApplicationContext(), uri, profile_image_view);
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