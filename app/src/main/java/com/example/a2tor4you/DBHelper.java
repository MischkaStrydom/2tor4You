package com.example.a2tor4you;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.a2tor4you.utils.AndroidUtil;
import com.google.firestore.v1.CursorOrBuilder;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "TutorDB.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //User table
        String userSQL = "CREATE TABLE User (userID INTEGER PRIMARY KEY AUTOINCREMENT, firstName VARCHAR(255), lastName VARCHAR(255),phoneNumber VARCHAR(12)," +
                " email VARCHAR(255), password VARCHAR(255),userRole VARCHAR(25), createdAt DATETIME)";

        //Student table
        String studentSQL = "CREATE TABLE Student (studentID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, image BLOB, dob DATE, gender VARCHAR(25), province VARCHAR(255)," +
                "city VARCHAR(255), school VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Tutor table
        String tutorSQL = "CREATE TABLE Tutor (tutorID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, image BLOB, dob DATE, gender VARCHAR(25), province VARCHAR(255)," +
                "city VARCHAR(255), school VARCHAR(255), uni VARCHAR(255), YearsOfExperience INTEGER, TotalTutorHours FLOAT, TotalStudentTaught INTEGER," +
                "aboutMe VARCHAR(255), pricePerHour FLOAT, locationOnline BOOLEAN DEFAULT 0, locationOffline BOOLEAN DEFAULT 0," +
                "extraFreeIntro BOOLEAN DEFAULT 0, extraQualifiedTeacher BOOLEAN DEFAULT 0, extraNotes VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Admin table
        String adminSQL = "CREATE TABLE Admin (adminID INTEGER PRIMARY KEY AUTOINCREMENT,userID INTEGER, adminRole VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Subjects
        String subjectSQL = "CREATE TABLE Subject (subjectID INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(255))";

        //Tutor Subjects --- Tutor Profile
        String tutorSubjectSQL = "CREATE TABLE TutorSubject (tutorSubjectID INTEGER PRIMARY KEY AUTOINCREMENT, tutorID INTEGER, subjectID INTEGER, grade VARCHAR(255)," +
                " FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID), FOREIGN KEY (subjectID) REFERENCES Subject(subjectID))";

        //Event
        String EventSQL = "CREATE TABLE Event (eventID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, eventTitle VARCHAR(255), eventDate DATE, " +
                "startTime DATETIME, locationOnline BOOLEAN DEFAULT 0, locationOffline BOOLEAN DEFAULT 0, notes VARCHAR(255), " +
                "isCancelled BOOLEAN DEFAULT 0, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Reviews
        String ReviewSQL = "CREATE TABLE Review (reviewID INTEGER PRIMARY KEY AUTOINCREMENT, studentID INTEGER, tutorID INTEGER, ratingValue INTEGER," +
                " reviewText VARCHAR(255), FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID), FOREIGN KEY (studentID) REFERENCES Student(studentID))";

        //Report
        String ReportSQL = "CREATE TABLE Report (reportID INTEGER PRIMARY KEY AUTOINCREMENT,adminID INTEGER, reportedID INTEGER, reporteeID INTEGER, reportText VARCHAR(255)," +
                " reportCategory VARCHAR(255), reportedAt DATETIME, resolvedAt DATETIME, resolutionText VARCHAR(255), FOREIGN KEY (adminID) REFERENCES Admin(adminID), " +
                "FOREIGN KEY (reportedID) REFERENCES User(userID), FOREIGN KEY (reporteeID) REFERENCES User(userID))";

        //Delete Account
        String DeleteSQL = "CREATE TABLE DeletedAccount (deletedAccountID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, deletedDate DATE, " +
                "FOREIGN KEY (userID) REFERENCES User(userID))";

        //Notification Preference
        String NotificationPreferenceSQL = "CREATE TABLE NotificationPreference (preferenceID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER," +
                "emailNotificationsEnabled BOOLEAN DEFAULT 0, messagesNotificationsEnabled BOOLEAN DEFAULT 0, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Password Change
        String PasswordChangeSQL = "CREATE TABLE PasswordChange (passwordID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, currentPassword VARCHAR(255), " +
                "newPassword VARCHAR(255), confirmPassword VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";


        db.execSQL(userSQL);
        db.execSQL(studentSQL);
        db.execSQL(tutorSQL);
        db.execSQL(adminSQL);
        db.execSQL(subjectSQL);
        db.execSQL(tutorSubjectSQL);
        db.execSQL(EventSQL);
        db.execSQL(ReviewSQL);
        db.execSQL(ReportSQL);
        db.execSQL(DeleteSQL);
        db.execSQL(NotificationPreferenceSQL);
        db.execSQL(PasswordChangeSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Student");
        db.execSQL("DROP TABLE IF EXISTS Tutor");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Subject");
        db.execSQL("DROP TABLE IF EXISTS TutorSubject");
        db.execSQL("DROP TABLE IF EXISTS Event");
        db.execSQL("DROP TABLE IF EXISTS ReviewSQL");
        db.execSQL("DROP TABLE IF EXISTS Report");
        db.execSQL("DROP TABLE IF EXISTS DeletedAccount");
        db.execSQL("DROP TABLE IF EXISTS NotificationPreference");
        db.execSQL("DROP TABLE IF EXISTS PasswordChange");
        onCreate(db);
    }


    //Test insert Student data


    public boolean insertData(String tableName, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(tableName, null, values);
        return result != -1;
    }

    public Cursor getAllItems(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        return cursor;
    }


    //username in this case is user phone number
    public Cursor getUser(String tableName, String userName, String userPass, String userRole) {
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "SELECT * FROM " + tableName + " WHERE username = '" + userName +
                "' AND password = '" + userPass + "' AND userRole = '" + userRole + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


    public boolean isNewStudentEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userRole FROM User WHERE email = ?", new String[]{email});

        boolean isStudent = false;

        if (cursor.moveToFirst()) {
            do {
                String role = cursor.getString(cursor.getColumnIndexOrThrow("userRole"));
                if ("Student".equalsIgnoreCase(role)) {
                    isStudent = true;
                    break; // Exit the loop if a Student role is found
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return isStudent;
    }

    public boolean isNewTutorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userRole FROM User WHERE email = ?", new String[]{email});

        boolean isTutor = false;

        if (cursor.moveToFirst()) {
            do {
                String role = cursor.getString(cursor.getColumnIndexOrThrow("userRole"));
                if ("Tutor".equalsIgnoreCase(role)) {
                    isTutor = true;
                    break; // Exit the loop if a Student role is found
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return isTutor;
    }

    //Login Checks
    public boolean login(String phoneNumber, String password, String selectedRole) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the query to check if the user exists with the provided credentials
        String query = "SELECT * FROM User WHERE phoneNumber = ? AND password = ? AND userRole = ?";
        String[] selectionArgs = {phoneNumber, password, selectedRole};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean isLoggedIn = false;

        if (cursor != null && cursor.moveToFirst()) {
            isLoggedIn = true;
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return isLoggedIn;
    }

    //User Name for account screen

    public String getUserName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT firstName, lastName FROM User WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userName = null; // Initialize to null in case of no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("firstName");
            int surnameIndex = cursor.getColumnIndex("lastName");

            String name = cursor.getString(nameIndex);
            String surname = cursor.getString(surnameIndex);

            // Check for null values and construct the userName accordingly
            if (name != null && surname != null) {
                userName = name + " " + surname; // Concatenate name and surname.
            } else if (name != null) {
                userName = name;
            } else if (surname != null) {
                userName = surname;
            }
        }

        // Close the cursor and database.
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return userName;
    }


    //Get email
    public String getEmail(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT email FROM User WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userEmail = null; // Initialize to null in case of no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("email");

            String email = cursor.getString(nameIndex);

            // Check for null values and construct the userName accordingly
            if (email != null) {
                userEmail = email; // Concatenate name and surname.
            }

            // Close the cursor and database.
            if (cursor != null) {
                cursor.close();
            }
            db.close();

        }
        return userEmail;
    }


    //Get user role
    public String getUserRole(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT userRole FROM User WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String userRole = null; // Initialize to null in case of no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("userRole");

            String role = cursor.getString(nameIndex);

            // Check for null values and construct the userName accordingly
            if (role != null) {
                userRole = role; // Concatenate name and surname.
            }

            // Close the cursor and database.
            if (cursor != null) {
                cursor.close();
            }
            db.close();

        }
        return userRole;
    }


    public int getUserId(String phoneNumber, String password, String userRole) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userID FROM User WHERE phoneNumber = ?  AND password = ?  AND userRole = ? ", new String[]{phoneNumber, password, userRole});


        int userID = -1; // Initialize to -1 in case of no matching record.

        if (cursor.moveToFirst()) userID = cursor.getInt(0);
        cursor.close();
        db.close();

        return userID;
    }



}
