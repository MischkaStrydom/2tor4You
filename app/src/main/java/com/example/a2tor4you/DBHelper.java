package com.example.a2tor4you;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
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
        String studentSQL = "CREATE TABLE Student (studentID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, dob DATE, gender VARCHAR(25), province VARCHAR(255)," +
                "city VARCHAR(255), school VARCHAR(255), image BLOB, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Tutor table
        String tutorSQL = "CREATE TABLE Tutor (tutorID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, dob DATE, gender VARCHAR(25), province VARCHAR(255)," +
                "city VARCHAR(255), school VARCHAR(255), uni VARCHAR(255), YearsOfExperience INTEGER, TotalTutorHours FLOAT, TotalStudentTaught INTEGER," +
                "aboutMe VARCHAR(255), pricePerHour FLOAT, locationOnline BOOLEAN DEFAULT 0, locationOffline BOOLEAN DEFAULT 0," +
                "extraQualifiedTeacher BOOLEAN DEFAULT 0, extraNotes VARCHAR(255), image BLOB, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Admin table
        String adminSQL = "CREATE TABLE Admin (adminID INTEGER PRIMARY KEY AUTOINCREMENT,userID INTEGER, adminRole VARCHAR(255), FOREIGN KEY (userID) REFERENCES User(userID))";

        //Subjects
        String subjectSQL = "CREATE TABLE Subject (subjectID INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR(255))";

        //Tutor Subjects --- Tutor Profile
        String tutorSubjectSQL = "CREATE TABLE TutorSubject (tutorSubjectID INTEGER PRIMARY KEY AUTOINCREMENT, tutorID INTEGER, subjectID INTEGER, grade VARCHAR(255)," +
                " FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID), FOREIGN KEY (subjectID) REFERENCES Subject(subjectID))";

        //Event
        String EventSQL = "CREATE TABLE Event (eventID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, eventTitle VARCHAR(255), eventDate DATE, " +
                "startTime TIME, locationOnline BOOLEAN DEFAULT 0, locationOffline BOOLEAN DEFAULT 0, notes VARCHAR(255), " +
                "isCancelled BOOLEAN DEFAULT 0, FOREIGN KEY (userID) REFERENCES User(userID))";

        //Reviews
        String ReviewSQL = "CREATE TABLE Review (reviewID INTEGER PRIMARY KEY AUTOINCREMENT, studentID INTEGER, tutorID INTEGER, ratingValue INTEGER," +
                " reviewText VARCHAR(255), FOREIGN KEY (tutorID) REFERENCES Tutor(tutorID), FOREIGN KEY (studentID) REFERENCES Student(studentID))";

        //Report
//        String ReportSQL = "CREATE TABLE Report (reportID INTEGER PRIMARY KEY AUTOINCREMENT,adminID INTEGER, reportedID INTEGER, reporteeID INTEGER, reportText VARCHAR(255)," +
//                " reportCategory VARCHAR(255), reportedAt DATETIME, resolvedAt DATETIME, resolutionText VARCHAR(255), FOREIGN KEY (adminID) REFERENCES Admin(adminID), " +
//                "FOREIGN KEY (reportedID) REFERENCES User(userID), FOREIGN KEY (reporteeID) REFERENCES User(userID))";

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
      //  db.execSQL(ReportSQL);
        db.execSQL(DeleteSQL);
        db.execSQL(NotificationPreferenceSQL);
        db.execSQL(PasswordChangeSQL);


        //Test insert Student data

       // db.execSQL("INSERT INTO Subject " + "(subject) VALUES('Maths')");
//        String[] subjects = {
//                "Mathematics", "Science", "English", "History", "Geography", "Physical Education",
//                "Business Studies", "Economics", "Information Technology", "Geology", "Art", "Music",
//                "Dramatic Arts", "Physical Science", "Agricultural Sciences", "Consumer Studies",
//                "Visual Arts", "Religious Studies", "Life Orientation", "Engineering Graphics and Design",
//                "Hospitality Studies", "Zulu", "Afrikaans", "Life Sciences", "Music", "Technology",
//                "Xhosa", "CAT", "French", "Accounting", "EMS"
//        };
//
//        for (String subject : subjects) {
//
//            String insertQuery = "INSERT INTO Subject (subject) VALUES('" + subject + "')";
//            db.execSQL(insertQuery);
//        }
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






    public boolean insertData(String tableName, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(tableName, null, values);
        return result != -1;
    }

    public boolean insertDataUser(String tableName, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to match the loggedInUserID

// Add the userID to the ContentValues to ensure it's associated with the event
       // values.put("userID", loggedInUserID);

        // Insert the new row
        long result = db.insert(tableName, null, values);

        return result != -1;
    }



    public Cursor viewData(int loggedInUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT eventTitle, eventDate, notes, startTime, locationOnline FROM Event WHERE userID = ?";

        String[] selectionArgs = {String.valueOf(loggedInUserId)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        return cursor;
    }

    public Cursor viewTutorData() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the SQL query using a JOIN operation to retrieve data from both tables
        String query = "SELECT User.firstName, User.lastName, Tutor.YearsOfExperience, Tutor.TotalTutorHours, Tutor.pricePerHour " +
                "FROM User " +
                "INNER JOIN Tutor ON User.userID = Tutor.userID";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }


    public List<Date> getEventDatesForUser(int userID) {
        List<Date> eventDates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Modify the query to filter by userID
        String query = "SELECT eventDate FROM Event WHERE userID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor.moveToFirst()) {
            do {
                // Convert the date string to a Date object
                String dateString = cursor.getString(0); // Assuming eventDate is in the first column
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(dateString);
                    eventDates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return eventDates;
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


    public int getUserId(String phoneNumber, String password, String userRole) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userID FROM User WHERE phoneNumber = ?  AND password = ?  AND userRole = ? ", new String[]{phoneNumber, password, userRole});


        int userID = -1; // Initialize to -1 in case of no matching record.

        if (cursor.moveToFirst()) userID = cursor.getInt(0);
        cursor.close();
        db.close();

        return userID;
    }




    // Update the User table
    // Update a row in a table
    // Update a row in a table
    public boolean updateUserData(String table_name, int userId, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.update(table_name, values, "userID=?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected > 0;
    }


    public boolean isUserIDExists(String tableName, int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + tableName + " WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userID)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = cursor != null && cursor.moveToFirst();

        // Close the cursor and database.
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return exists;
    }

    public boolean deleteUserAndRelatedData(int loggedInUserID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = 0;

        // Delete user from the User table
        affectedRows = db.delete("User", "userID=?", new String[]{String.valueOf(loggedInUserID)});


        if (affectedRows > 0) {
            // User deleted successfully from the User table, now delete related data

            // Delete related data from the Student table
            affectedRows += db.delete("Student", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("Tutor", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            //Tutor Subjects
            //affectedRows += db.delete("TutorSubject", "userID=?", new String[]{String.valueOf(loggedInUserID)}); --------- tutorID not userID????
            affectedRows += db.delete("Event", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("Student", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            //Reviews
            affectedRows += db.delete("Report", "reportedID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("Report", "reporteeID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("Student", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("NotificationPreference", "userID=?", new String[]{String.valueOf(loggedInUserID)});
            affectedRows += db.delete("PasswordChange", "userID=?", new String[]{String.valueOf(loggedInUserID)});


            // Delete related data from other tables where loggedInUserID is used as a foreign key
            // Add more delete statements for other tables as needed

            if (affectedRows > 0) {
                // Successfully deleted user and related data
                return true;
            }
        }

        // User not found or deletion failed
        return false;
    }

    public boolean changeUserPassword(int userID, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rowsAffected = db.update("User", values, "userID=?", new String[]{String.valueOf(userID)});
        db.close();

        return rowsAffected > 0;
    }


    public Boolean getInfo(String TABLE_NAME, int userId, String fieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + fieldName + " FROM " + TABLE_NAME + " WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        Boolean fieldValue = null; // Initialize to null in case of no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(fieldName);

            // Get the integer value from the cursor.
            int intValue = cursor.getInt(columnIndex);

            // Convert the integer to a Boolean (true for 1, false for 0).
            fieldValue = (intValue == 1);

            // Close the cursor.
            cursor.close();
        }

        // Close the database.
        db.close();

        return fieldValue;
    }


    public String getField(String TABLE_NAME, int userId, String fieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + fieldName + " FROM " + TABLE_NAME + " WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        String fieldValue = null; // Initialize to null in case of no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(fieldName);

            String value = cursor.getString(columnIndex);

            if (value != null) {
                fieldValue = value;
            }

            // Close the cursor.
            cursor.close();
        }

        // Close the database.
        db.close();

        return fieldValue;
    }


    public float getFieldAsFloat(String TABLE_NAME, int userId, String fieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + fieldName + " FROM " + TABLE_NAME + " WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        float fieldValue = -1.0f; // Initialize to a default value (e.g., -1.0f) for no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(fieldName);

            fieldValue = cursor.getFloat(columnIndex);

            // Close the cursor.
            cursor.close();
        }

        // Close the database.
        db.close();

        return fieldValue;
    }

    public int getFieldAsInt(String TABLE_NAME, int userId, String fieldName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + fieldName + " FROM " + TABLE_NAME + " WHERE userID = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        int fieldValue = -1; // Initialize to a default value (e.g., -1.0f) for no matching record.

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(fieldName);

            fieldValue = cursor.getInt(columnIndex);

            // Close the cursor.
            cursor.close();
        }

        // Close the database.
        db.close();

        return fieldValue;
    }

//    public SimpleCursorAdapter populateListViewFromDB() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String[] columns = getColumnNames("Event");
//
//        String[] fromFieldNames = new String[]{"eventTitle", "notes"};
//
//        Cursor cursor = db.query("Event", columns, null, null, null, null, null);
//
//        int[] toViewIDs = new int[]{R.id.item_name, R.id.item_Notes};
//
//        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
//                context,
//                R.layout.my_row,
//                cursor,
//                fromFieldNames,
//                toViewIDs
//        );
//        return contactAdapter;
//    }
//
//
//    public String[] getColumnNames(String tableName) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns = null;
//
//        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
//
//        if (cursor != null) {
//            columns = new String[cursor.getCount()];
//            int index = 0;
//
//            if (cursor != null && cursor.moveToFirst()) {
//                int nameIndex1 = cursor.getColumnIndex("eventID");
//                int nameIndex2 = cursor.getColumnIndex("eventTitle");
//                int nameIndex3 = cursor.getColumnIndex("eventDate");
//                int nameIndex4 = cursor.getColumnIndex("startTime");
//                int nameIndex5 = cursor.getColumnIndex("locationOnline");
//                int nameIndex6 = cursor.getColumnIndex("locationOffline");
//                int nameIndex7 = cursor.getColumnIndex("notes");
//
//                String eventID = cursor.getString(nameIndex1);
//                String eventTitle = cursor.getString(nameIndex2);
//                String eventDate = cursor.getString(nameIndex3);
//                String startTime = cursor.getString(nameIndex4);
//                String locationOnline = cursor.getString(nameIndex5);
//                String locationOffline = cursor.getString(nameIndex6);
//                String notes = cursor.getString(nameIndex7);
//
//            }
//
//            cursor.close();
//        }
//
//        db.close();
//
//        return columns;
//    }




}