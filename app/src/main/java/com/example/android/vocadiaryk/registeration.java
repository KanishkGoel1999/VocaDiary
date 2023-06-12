package com.example.android.vocadiaryk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.vocadiaryk.data.userHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class registeration extends AppCompatActivity {

    TextView username;
    TextView password;
    Button register;
    FirebaseAuth mAuth;
    String usernameToStore;
    String passwordToStore;
    TextView name;
    TextView mobile;
    TextView dob;
    String nameStored, mob, dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        username=findViewById(R.id.username_cred);
        password = findViewById(R.id.password_cred);
        register = findViewById(R.id.register_cred);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        dob = findViewById(R.id.dob);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = String.valueOf(username.getText());
                pass = String.valueOf(password.getText());
                usernameToStore = email;
                passwordToStore = pass;
                nameStored = String.valueOf(name.getText());
                mob = String.valueOf(mobile.getText());
                dateOfBirth = String.valueOf(dob.getText());


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(registeration.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(registeration.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(registeration.this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();

                                    //change
                                    storeNewUserData();

                                    finish();
                                    Intent intent = new Intent(registeration.this, emailPassword.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(registeration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }


    private void storeNewUserData(){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("USERS");

        userHelperClass addNewUser = new userHelperClass(nameStored, mob, dateOfBirth, passwordToStore);
        reference.child(mob).setValue(addNewUser);

    }
}