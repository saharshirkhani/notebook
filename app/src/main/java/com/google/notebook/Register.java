package com.google.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    TextView login;
    EditText name;
    EditText pass;
    EditText email;
    Button register;
    DatabaseReference mrootref;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login=findViewById(R.id.login);
        name=findViewById(R.id.editname);
        pass=findViewById(R.id.editpass);
        email=findViewById(R.id.editemail);
        register=findViewById(R.id.btnsubmit);


        mrootref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, com.google.notebook.login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtname=name.getText().toString();
                String txtemail=email.getText().toString();
                String txtpass=pass.getText().toString();

                if (TextUtils.isEmpty(txtname)||TextUtils.isEmpty(txtemail)||TextUtils.isEmpty(txtpass)){
                    Toast.makeText(Register.this,"pls fill all field!!☻",Toast.LENGTH_LONG).show();
                }
                else if (txtpass.length()>8){
                    Toast.makeText(Register.this,"opps!! pls use long pass ",Toast.LENGTH_LONG).show();
                }
                else regitering(txtemail,txtname,txtpass);

            }
        });


    }

    public void regitering(final String email, final String name, final String pass){


        mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("name" , name);
                data.put("email" , email);
                data.put("password",pass);
                mrootref.child("User").child(mAuth.getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(Register.this,"Registre is successfully!",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Register.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        }


                    }
                });

            }
        });
    }
}
