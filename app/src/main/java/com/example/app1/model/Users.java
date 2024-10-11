package com.example.app1.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.app1.DatabaseHelper;

public class Users {
    public DatabaseHelper dbHelper;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String name;
    private String id;
    public Users(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void insertUser(String username, String password, String phone, String email, String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("phone", phone);
        values.put("email", email);
        values.put("name", name);
        db.insert( "Users",  null, values);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
