package com.example.kyshatbmoznodeliverer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyshatbmoznodeliverer.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText emailSignIn, passwordSignIn;
    Button btnLogin;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users_ref;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        role = "";

        emailSignIn = (EditText)findViewById(R.id.emailSignIn);
        passwordSignIn = (EditText)findViewById(R.id.passwordSignIn);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        FirebaseUser user = auth.getCurrentUser();
        if (user!=null){
            users_ref = db.getReference("Users").child(user.getUid());
            users_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User curUser = snapshot.getValue(User.class);
                    assert curUser!=null;
                    role = curUser.getRole();
                    if (user != null && user.isEmailVerified()&&curUser.getRole().equals("Deliverer")){
                        startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    public void SignIn(){

        if (TextUtils.isEmpty(emailSignIn.getText().toString())){
            Toast.makeText(MainActivity.this, "Введите ваш логин", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passwordSignIn.getText().toString())) {
            Toast.makeText(MainActivity.this, "Введите ваш пароль", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.signInWithEmailAndPassword(emailSignIn.getText().toString(), passwordSignIn.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;

                            users_ref = db.getReference("Users").child(user.getUid());
                            users_ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User curUser = snapshot.getValue(User.class);
                                    assert curUser!=null;
                                    role = curUser.getRole();
                                    if (user.isEmailVerified()&&role.equals("Deliverer")) {
                                        startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                                        finish();
                                    }
                                    else if(!user.isEmailVerified()){
                                        Toast.makeText(MainActivity.this, "Проверти свою почту для подтверждения", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "Роль не подходит", Toast.LENGTH_SHORT).show();
                                        auth.signOut();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Ошибка авторизации " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

}