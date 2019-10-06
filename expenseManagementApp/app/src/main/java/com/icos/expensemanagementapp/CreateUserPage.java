package com.icos.expensemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUserPage extends AppCompatActivity {
    EditText username,password;
    Button createUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_page);
        username=findViewById(R.id.username2);
        password=findViewById(R.id.password2);
        createUser=findViewById(R.id.create_user);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Signup Successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CreateUserPage.this,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Signup failed, something went wrong. Try again.",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
}
