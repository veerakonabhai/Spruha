package com.example.veerareddykonabhai.spruha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rilixtech.CountryCodePicker;

public class DonorSignIn extends AppCompatActivity {
    EditText signinemail,signinpass;
    public String name,mail;
    Button signin,createuser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog pd;
    String semail,spass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_sign_in);
        CountryCodePicker ccp;
        AppCompatEditText edtPhoneNumber;



        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        edtPhoneNumber = (AppCompatEditText) findViewById(R.id.phone_number_edt);



        ccp.registerPhoneNumberTextView(edtPhoneNumber);

        if(ccp.isValid()){
            Toast.makeText(DonorSignIn.this,""+ccp.getFullNumber(),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(DonorSignIn.this,"please enter valid number",Toast.LENGTH_LONG).show();
        }






        signinemail=(EditText)findViewById(R.id.signinemail);
        signinpass=(EditText)findViewById(R.id.signinpass);
        signin=(Button)findViewById(R.id.btn_signin);
        createuser=(Button)findViewById(R.id.btn_signup);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);
            finish();
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semail=signinemail.getText().toString();
                spass=signinpass.getText().toString();
                pd.setMessage("Signing in");
                pd.setCancelable(false);
                pd.show();
                    try {
                        mAuth.signInWithEmailAndPassword(semail, spass)
                                .addOnCompleteListener(DonorSignIn.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d("success", "signInWithEmail:onComplete:" + task.isSuccessful());

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (task.isSuccessful()) {
                                            pd.dismiss();


                                            editor.putString("email",mAuth.getCurrentUser().getEmail());
                                            editor.commit();
                                            Intent intent= new Intent(getApplicationContext(),Main2Activity.class);

                                            startActivity(intent);
                                            FirebaseUser user = mAuth.getCurrentUser();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please give correct values", Toast.LENGTH_SHORT).show();
                                            pd.dismiss();
                                        }

                                        // ...
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Please give correct values", Toast.LENGTH_LONG).show();
                        pd.dismiss();


                }
            }
        });
        createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),termlist.class);
                startActivity(intent);
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("success", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("now fail", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
