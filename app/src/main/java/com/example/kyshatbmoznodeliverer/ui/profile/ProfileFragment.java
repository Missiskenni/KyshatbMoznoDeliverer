package com.example.kyshatbmoznodeliverer.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kyshatbmoznodeliverer.Models.User;
import com.example.kyshatbmoznodeliverer.OneOrderActivity;
import com.example.kyshatbmoznodeliverer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference user_ref;
    FirebaseAuth auth;
    FirebaseUser fUser;

    Button changePass, btnViewOrders;
    TextView yourEmail, yourName, yourPhone;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yourEmail = view.findViewById(R.id.yourEmail);
        yourName = view.findViewById(R.id.yourName);
        yourPhone = view.findViewById(R.id.yourPhone);

        changePass = view.findViewById(R.id.btnChangePass);
        btnViewOrders = view.findViewById(R.id.btnViewOrders);

        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OneOrderActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        user_ref = db.getReference("Users").child(fUser.getUid());

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePass();
            }
        });

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                yourEmail.setText(user.getEmail());
                yourPhone.setText(user.getPhoneNumber());
                yourName.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showChangePass(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Изменение пароля");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View changePassWindow = inflater.inflate(R.layout.change_password, null);
        dialog.setView(changePassWindow);

        final EditText enterOldPassword = changePassWindow.findViewById(R.id.enterOldPassword);
        final EditText enterNewPassword = changePassWindow.findViewById(R.id.enterNewPassword);
        final EditText commitNewPassword = changePassWindow.findViewById(R.id.commitNewPassword);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("Сохранить пароль", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String oldPass = enterOldPassword.getText().toString();
                String newPass = enterNewPassword.getText().toString();
                String comNewPass = commitNewPassword.getText().toString();

                if (TextUtils.isEmpty(oldPass)){
                    Toast.makeText(getActivity(), "Введите ваш старый пароль...", Toast.LENGTH_SHORT).show();
                }
                else if (newPass.length()<6){
                    Toast.makeText(getActivity(), "Новый пароль должен иметь минимум 6 символов...", Toast.LENGTH_SHORT).show();
                }
                else if (!newPass.equals(comNewPass)){
                    Toast.makeText(getActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    updatePassword(oldPass, newPass);
                }
            }
        });

        dialog.show();
    }

    private void updatePassword(String oldPass, String newPass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId = auth.getCurrentUser().getUid();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users").child(userId);

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                user.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        myRef.child("password").setValue(newPass);
                        Toast.makeText(getActivity(), "Пароль изменен!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}