package com.example.app1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app1.model.Users;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView backtologinLink = findViewById(R.id.backtologin_link);
        backtologinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        EditText fullname = findViewById(R.id.fullnameInput);
        EditText username = findViewById(R.id.usernameInput);
        EditText phone = findViewById(R.id.phoneInput);
        EditText email = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(username.getText().toString().equals(""))
                {
                    Toast.makeText(view.getContext(), "Please enter username!", Toast.LENGTH_LONG).show();
                    return;
                }
                Users users = new Users(view.getContext());

                SQLiteDatabase db = users.dbHelper.getReadableDatabase();
                String query = "SELECT " +
                        "(SELECT COUNT(*) FROM users WHERE username = ?) AS usernameExists, " +
                        "(SELECT COUNT(*) FROM users WHERE email = ?) AS emailExists, " +
                        "(SELECT COUNT(*) FROM users WHERE phone = ?) AS phoneExists";
                Cursor cursor = db.rawQuery(query, new String[]{
                        username.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString()
                });
                if (cursor.moveToFirst()){
                    @SuppressLint("Range") int usernameExists = cursor.getInt(cursor.getColumnIndex("usernameExists"));
                    @SuppressLint("Range") int emailExists = cursor.getInt(cursor.getColumnIndex("emailExists"));
                    @SuppressLint("Range") int phoneExists = cursor.getInt(cursor.getColumnIndex("phoneExists"));
                    if (usernameExists > 0){
                        Toast.makeText(view.getContext(), "Username already exists", Toast.LENGTH_LONG).show();
                    } else if (emailExists > 0) {
                        Toast.makeText(view.getContext(), "Email already exists", Toast.LENGTH_LONG).show();
                    } else if (phoneExists > 0) {
                        Toast.makeText(view.getContext(), "Phone already exists", Toast.LENGTH_LONG).show();
                    } else {
                        users.insertUser(username.getText().toString(),
                                password.getText().toString(),
                                phone.getText().toString(),
                                email.getText().toString(),
                                fullname.getText().toString()
                        );
                    }
                }
            }
        });
    }
}