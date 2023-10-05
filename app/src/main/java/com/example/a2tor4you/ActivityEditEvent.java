package com.example.a2tor4you;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a2tor4you.utils.AndroidUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityEditEvent extends AppCompatActivity {
    EditText txt_EventTitle, txt_EventNotes;
    Button btnEventDate, btnEventTime, btn_SaveEvent;

    RadioGroup rdoGroupGrade1;
    RadioButton rdo_Online, rdo_InPerson;
    static String eventTitle, eventDate, eventTime, eventNotes;
    Calendar calendar = Calendar.getInstance();
    static boolean eventOnline, eventInPerson;
    DBHelper dbHelper ;
    DBHelper myDB;
    boolean isCheckedLocationOnline;
    boolean isCheckedLocationOffline;
    boolean isAllFieldsChecked = false;
    boolean isHandlingCheckedChange = false;
    //Calendar calendar = Calendar.getInstance();
    private int maxWordCount = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        ImageView btnBack = findViewById(R.id.btnBackEditEvent);
        btnBack.setOnClickListener(view -> startActivity(new Intent(ActivityEditEvent.this, ActivityHomeStudent.class)));

        dbHelper = new DBHelper(this);

        myDB = new DBHelper(this); // Initialize myDB

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int loggedInUserId = preferences.getInt("loggedInUserId", -1); // -1 is a default value if key not found

        //Get all profile edits/spinners/buttons...

        txt_EventTitle = findViewById(R.id.txt_EventTitle);
        btnEventDate = findViewById(R.id.btnEventDate);
        btnEventTime = findViewById(R.id.btnEventTime);
        rdo_Online = findViewById(R.id.rdo_Online);
        rdo_InPerson = findViewById(R.id.rdo_InPerson);
        txt_EventNotes = findViewById(R.id.txt_EventNotes);
        btn_SaveEvent = findViewById(R.id.btn_SaveEvent);
        rdoGroupGrade1 = findViewById(R.id.rdoGroupGrade1);


//        txt_EventNotes = new EditText(this);
//        int maxLength = 3;
//        txt_EventNotes.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

//        txt_EventNotes.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String text = editable.toString();
//
//                // Split the text into words using whitespace as a delimiter
//                String[] words = text.split("\\s+");
//
//                // Check if the word count exceeds the maximum limit
//                if (words.length > maxWordCount) {
//                    // If the word count is exceeded, remove extra words
//                    int selectionStart = txt_EventNotes.getSelectionStart();
//                    int selectionEnd = txt_EventNotes.getSelectionEnd();
//                    editable.delete(selectionStart - 1, selectionEnd);
//                }
//            }
//        });


        int selectedRadioButtonId = rdoGroupGrade1.getCheckedRadioButtonId();

        rdoGroupGrade1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (!isHandlingCheckedChange) {
                    isHandlingCheckedChange = true;
                }
            }
        });



        // handle the SAVE button
        btn_SaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.

//                //Get state of online
//                if (rdo_Online.isChecked()) {
//                    isCheckedLocationOnline = true;
//                } else {
//                    isCheckedLocationOnline = false;
//
//                }
//                //Get state of offline
//                if (rdo_InPerson.isChecked()) {
//                    isCheckedLocationOffline = true;
//                } else {
//                    isCheckedLocationOffline = false;
//                }

                if(rdo_Online.isChecked()){
                    isCheckedLocationOnline = true;
                }
                else{
                    isCheckedLocationOnline = false;
                }
                if(rdo_InPerson.isChecked()){
                    isCheckedLocationOffline = true;
                }
                else{
                    isCheckedLocationOffline = false;
                }

                //Insert event details to the database
                eventTitle = txt_EventTitle.getText().toString();
                eventDate = btnEventDate.getText().toString();
                eventTime = btnEventTime.getText().toString();
                eventOnline = isCheckedLocationOnline;
                eventInPerson = isCheckedLocationOffline;
                eventNotes = txt_EventNotes.getText().toString();

                //SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues eventValues = new ContentValues();
                eventValues.put("userID", loggedInUserId);
                eventValues.put("eventTitle", eventTitle);
                eventValues.put("eventDate", eventDate);
                eventValues.put("startTime", eventTime);
                eventValues.put("locationOnline", eventOnline);
                eventValues.put("locationOffline", eventInPerson);
                eventValues.put("notes", eventNotes);

                boolean result = myDB.insertDataUser("Event", eventValues);

               // if (isAllFieldsChecked) {

                    if (result) {




                        // the boolean variable turns to be true then
                        // only the user must be proceed to the activity2
                        Toast.makeText(ActivityEditEvent.this, "Event added successfully", Toast.LENGTH_SHORT).show();

                        txt_EventTitle.setText("");
                        btnEventDate.setText("");
                        btnEventTime.setText("");
                        rdo_Online.setChecked(false);
                        rdo_InPerson.setChecked(false);
                        txt_EventNotes.setText("");
                        txt_EventTitle.setText("");


                    } else {
                        AndroidUtil.showToast(getApplicationContext(), "An unknown error has occurred while saving student data!");
                    }
              //  }

                // Redirect to the activity displaying the list of events
//                Intent intent = new Intent(ActivityEditEvent.this, ActivityEventsListView.class);
//                startActivity(intent);
            }
        });

        btnEventDate.setBackgroundColor(getResources().getColor(R.color.primary));
        // Date Picker
        btnEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker(); // Open date picker dialog
            }
        });

        // Time Picker
        btnEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimePicker(); // Open time picker dialog
            }
        });

//        //Get state of online
//        if (rdo_Online.isChecked()) {
//            isCheckedLocationOnline = true;
//        } else {
//            isCheckedLocationOnline = false;
//
//        }
//        //Get state of offline
//        if (rdo_InPerson.isChecked()) {
//            isCheckedLocationOffline = true;
//        } else {
//            isCheckedLocationOffline = false;
//        }


        // RadioButtons select and deselect
        rdo_Online = findViewById(R.id.rdo_Online);
        rdo_InPerson = findViewById(R.id.rdo_InPerson);


    }

    private void openTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {


                //Showing the picked value in the textView
                btnEventTime.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));

            }
        }, 15, 30, false);

        timePickerDialog.show();
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txt_EventTitle.length() == 0) {
            txt_EventTitle.setError("This field is required");
            return false;
        }

        if (btnEventDate.length() == 0) {
            btnEventDate.setError("This field is required");
            return false;
        }

        if (btnEventTime.length() == 0) {
            btnEventTime.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }


        /*// handle the PROCEED button
        btn_SaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(ActivityEditEvent.this, ActivityCalendar.class);
                    startActivity(i);
                }
            }
        });*/


    /*// function which checks all the text fields are filled or not by the user.
    private boolean CheckAllFields() {
        if (txt_EventTitle.length() == 0) {
            txt_EventTitle.setError("This field is required");
            return false;
        }

        if (btnEventDate.length() == 0) {
            btnEventDate.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }*/

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String formattedDate = dateFormat.format(calendar.getTime());
                        btnEventDate.setText(formattedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}

