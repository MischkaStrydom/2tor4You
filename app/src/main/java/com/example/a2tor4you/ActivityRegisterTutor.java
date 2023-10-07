package com.example.a2tor4you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRegisterTutor extends AppCompatActivity implements View.OnClickListener{

    static String tutorName;
    static String tutorSurname;
    static String completePhoneNumber;
    static String tutorEmail ;
    static String tutorPassword;
    static String confirmPassword;

    DBHelper myDB;
    //a constant to track the file chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;

    private Button btnChoose;
    private Button btnUpload;

    //ImageView
    private ImageView imageView;

    //a Uri object to store file path
    private Uri filePath;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

        ImageView btnBack = findViewById(R.id.btnBackRegisterTutor);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityRegisterTutor.this,MainActivity.class)));

        EditText name = findViewById(R.id.txt_TutorFirstName);
        EditText surname = findViewById(R.id.txt_TutorLastName);
        EditText phoneInput = findViewById(R.id.txt_TutorPhoneNum);
        EditText email = findViewById(R.id.txtTutorEmail);
        EditText password = findViewById(R.id.txtRegisterTutorPassword);
        EditText confirmPass = findViewById(R.id.txtConfirmRegisterTutorPassword);

        CheckBox verification = findViewById(R.id.chk_Agree);
        CheckBox suitable = findViewById(R.id.chk_Suitable);

        Button btn = findViewById(R.id.btnTutorSignUp);
        myDB = new DBHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(!verification.isChecked()){
                   AndroidUtil.showToast(getApplicationContext(), "Have to check both the check boxes.");
                   return;
               }
                if(!suitable.isChecked()){
                    AndroidUtil.showToast(getApplicationContext(), "Have to check both the check boxes.");
                    return;
                }

                 tutorName = name.getText().toString();
                 tutorSurname = surname.getText().toString();
                 completePhoneNumber = "+27" + phoneInput.getText().toString();
                 tutorEmail = email.getText().toString();
                 tutorPassword = password.getText().toString();
                 confirmPassword = confirmPass.getText().toString();

                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// Customize the format as needed
                String dateTimeString = dateFormat.format(new Date(currentTimeMillis));

                String tutorRole = "Tutor";

                // Check if the phone number is 10 digits
                if (phoneInput.length() != 9) {
                    AndroidUtil.showToast(getApplicationContext(), "Incorrect phone number.");
                    return; // Exit the method and do not proceed
                }

                //Check email exist and check the role
                boolean isEmailRegisteredAsTutor = myDB.isNewTutorEmail(tutorEmail);
                if (isEmailRegisteredAsTutor) {
                    AndroidUtil.showToast(getApplicationContext(), "Email is already registered as a Tutor.");
                    return;
                }


                // Check if passwords match
                if (!tutorPassword.equals(confirmPassword)) {
                    AndroidUtil.showToast(getApplicationContext(), "Passwords do not match.");
                    return; // Exit the method and do not proceed
                }


                ContentValues contentValues = new ContentValues();
                contentValues.put("firstName", tutorName );
                contentValues.put("lastName", tutorSurname );
                contentValues.put("phoneNumber", completePhoneNumber );
                contentValues.put("email", tutorEmail );
                contentValues.put("password", tutorPassword );
                contentValues.put("createdAt", dateTimeString);
                contentValues.put("userRole", tutorRole);

                boolean result = myDB.insertData("User", contentValues);

                if (result) {

                    // Insert the user ID into the "studentTable"
                    int userID = myDB.getUserId(completePhoneNumber, tutorPassword, tutorRole);
                    ContentValues tutorValues = new ContentValues();
                    tutorValues.put("userID", userID );
                    boolean tutorInsertResult  = myDB.insertData("Tutor", tutorValues);
                    boolean tutorPreffs  = myDB.insertData("NotificationPreference", tutorValues);
                    if (tutorInsertResult && tutorPreffs) {
                        name.setText("");
                        surname.setText("");
                        phoneInput.setText("");
                        email.setText("");
                        password.setText("");
                        confirmPass.setText("");

//                        // Pass the userId to another activity
//                        Intent intent = new Intent(ActivityRegisterTutor.this, ActivityLogin.class);
//                        intent.putExtra("userId", userID);
//                        startActivity(intent);

                        AndroidUtil.showToast(getApplicationContext(), "Registered Tutor Successful!");
                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving tutor data!");
                    }
                } else {
                    AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred during user registration!");
                }
            }
        });
        myDB.close();


        //Firebase storage of choosing and uploading document

        storageReference = FirebaseStorage.getInstance().getReference();

        //getting views from layout
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        //attaching listener
        btnChoose.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {

            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("image/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*method when the button upload is clicked.*/
    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == btnChoose) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == btnUpload) {
            uploadFile();
        }
    }
}