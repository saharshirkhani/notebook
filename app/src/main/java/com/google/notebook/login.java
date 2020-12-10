package com.google.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText email;
    EditText pass;
    Button btnlogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.editemail);
        pass=findViewById(R.id.editpass);
        btnlogin=findViewById(R.id.btnlogin);

        firebaseAuth=FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textemail=email.getText().toString();
                String textpass=pass.getText().toString();
                if (TextUtils.isEmpty(textemail)||TextUtils.isEmpty(textpass)){
                    Toast.makeText(login.this,"fill all field",Toast.LENGTH_LONG).show();
                }
                else
                    login(textemail,textpass);
            }
        });

    }
    public void login(String email,String pass){
        firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(login.this,"login is successfully!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(login.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
