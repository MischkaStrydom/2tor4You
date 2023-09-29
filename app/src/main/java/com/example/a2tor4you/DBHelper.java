package com.example.a2tor4you;
import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{


    public static final String DB_NAME = "TutorDB.db";

    public DBHelper( Context context) { super(context, DB_NAME, null, 2);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        //User table
        String userSQL = "CREATE TABLE User (userID INTEGER PRIMARY KEY AUTOINCREMENT, firstName VARCHAR(255), lastName VARCHAR(255), email VARCHAR(255), " +
                "phoneNumber VARCHAR(12), password VARCHAR(255), dob DATE, createdAt DATETIME, updatedAt DATETIME, userRole VARCHAR(25), isVerifiedAccount BOOLEAN DEFAULT 0)";

        //Student table
        String studentSQL = "CREATE TABLE Student (studentID INTEGER PRIMARY KEY AUTOINCREMENT, enrollmentDate DATE, " +
                "studSubject VARCHAR(255), studGrade VARCHAR(255), otherAttributes VARCHAR(255), FOREIGN KEY (userID) REFERENCES Users(userID))";

        //Tutor table
        String tutorSQL = "CREATE TABLE Tutor (tutorID INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(255), experience VARCHAR(255), hourlyRate FLOAT, " +
                "otherAttributes VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Admin table
        String adminSQL = "CREATE TABLE Admin (adminID INTEGER PRIMARY KEY AUTOINCREMENT, adminRole VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Pull Report for Admin
        String pullReportSQL = "CREATE TABLE PullReport (pullReportID INTEGER PRIMARY KEY AUTOINCREMENT, numberOfTutorsOnboarded INTEGER, numberOfTutorsOffboarded INTEGER, " +
                "numberOfStudentsOnboarded INTEGER, numberOfStudentsOffboarded INTEGER, " +
                "numberOfCurrentTutors INTEGER, numberOfCurrentStudents INTEGER, FOREIGN KEY (userID) REFERENCES User(userID), FOREIGN KEY (adminID) REFERENCES Admin(adminID))";

        //Event
        String EventSQL = "CREATE TABLE Event (eventID INTEGER PRIMARY KEY AUTOINCREMENT, eventTitle VARCHAR(255), eventDate DATE, " +
                "startTime DATETIME, endTime DATETIME, duration INTEGER, location VARCHAR(255), notes VARCHAR(255), sessionRating INTEGER, feedbackReview VARCHAR(255), " +
                "isCancelled BOOLEAN DEFAULT 0, FOREIGN KEY (studentID) REFERENCES Student(studentID), FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID))";

        //Report
        String ReportSQL = "CREATE TABLE Report (reportID INTEGER PRIMARY KEY AUTOINCREMENT, reportText VARCHAR(255), reportCategory VARCHAR(255), " +
                "reportedAt DATETIME, resolvedAt DATETIME, resolutionText VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID), " +
                "FOREIGN KEY (adminID) REFERENCES Admin(adminID), FOREIGN KEY (eventID) REFERENCES Event(eventID))";

        //Delete Account
        String DeleteSQL = "CREATE TABLE DeletedAccount (deletedAccountID INTEGER PRIMARY KEY AUTOINCREMENT, deletedDate DATE, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Rating
        String RatingSQL = "CREATE TABLE Rating (ratingID INTEGER PRIMARY KEY AUTOINCREMENT, ratingValue INTEGER, reviewText VARCHAR(255), " +
                "ratedAt DATETIME, FOREIGN KEY (userID) REFERENCES User(userID), " +
                "FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID), FOREIGN KEY (eventID) REFERENCES Event(eventID))";

        //Location
        String LocationSQL = "CREATE TABLE Location (locationID INTEGER PRIMARY KEY AUTOINCREMENT, locationType VARCHAR(255))";

        //Notification Preference
        String NotificationPreferenceSQL = "CREATE TABLE NotificationPreference (preferenceID INTEGER PRIMARY KEY AUTOINCREMENT, emailNotificationsEnabled BOOLEAN DEFAULT 0," +
                "messagesNotificationsEnabled BOOLEAN DEFAULT 0, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Notification -------- is this needed?????
        String NotificationSQL = "CREATE TABLE Notification (notificationID INTEGER PRIMARY KEY AUTOINCREMENT, notificationType VARCHAR(255), notificationMessage VARCHAR(255), " +
                "timeStamp DATETIME, relatedEntityID INTEGER, linkToView VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID), " +
                "FOREIGN KEY (preferenceID) REFERENCES NotificationPreference(preferenceID))";

        //Password Change
        String PasswordChangeSQL = "CREATE TABLE PasswordChange (passwordID INTEGER PRIMARY KEY AUTOINCREMENT, currentPassword VARCHAR(255), newPassword VARCHAR(255), " +
                "confirmPassword VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //User Profile
        String UserProfileSQL = "CREATE TABLE UserProfile (profileID INTEGER PRIMARY KEY AUTOINCREMENT, location VARCHAR(255), contactInfo VARCHAR(255), education VARCHAR(255)," +
                "profilePic VARCHAR(255), otherAttributes VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

    db.execSQL(userSQL);
    db.execSQL(studentSQL);
    db.execSQL(tutorSQL);
    db.execSQL(adminSQL);
    db.execSQL(pullReportSQL);
    db.execSQL(EventSQL);
    db.execSQL(ReportSQL);
    db.execSQL(DeleteSQL);
    db.execSQL(RatingSQL);
    db.execSQL(LocationSQL);
    db.execSQL(NotificationPreferenceSQL);
    db.execSQL(NotificationSQL);
    db.execSQL(PasswordChangeSQL);
    db.execSQL(UserProfileSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Student");
        db.execSQL("DROP TABLE IF EXISTS Tutor");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS PullReport");
        db.execSQL("DROP TABLE IF EXISTS Event");
        db.execSQL("DROP TABLE IF EXISTS Report");
        db.execSQL("DROP TABLE IF EXISTS DeletedAccount");
        db.execSQL("DROP TABLE IF EXISTS Rating");
        db.execSQL("DROP TABLE IF EXISTS Location");
        db.execSQL("DROP TABLE IF EXISTS NotificationPreference");
        db.execSQL("DROP TABLE IF EXISTS Notification");
        db.execSQL("DROP TABLE IF EXISTS PasswordChange");
        db.execSQL("DROP TABLE IF EXISTS UserProfile");
    }

    public boolean insertData(String tableName,ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(tableName, null, values);
        return result != -1;
    }

    public Cursor getAllItems(String tableName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        return cursor;
    }


    //username in this case is user phone number
    public Cursor getUser(String tableName, String userName, String userPass, String userRole){
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "SELECT * FROM " + tableName + " WHERE username = '" + userName +
                        "' AND password = '" + userPass + "' AND userRole = '" + userRole + "'";

        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


}
