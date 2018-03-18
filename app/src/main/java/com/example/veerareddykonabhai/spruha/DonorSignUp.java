package com.example.veerareddykonabhai.spruha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DonorSignUp extends AppCompatActivity {
    EditText dname,ddob,dlocality,dcontact1,dcontact2,demail,dpass,drepass;
    Button signup;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference dref,count;
    String name, dob, locality, contact1, contact2, email, pass, repass,gender,blood;
    RadioButton Gbutton,Bbutton;
    RadioGroup Ggroup,Bgroup;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_sign_up);
        dname=(EditText)findViewById(R.id.name_donor);
        ddob=(EditText)findViewById(R.id.dateofbirth);
      /*  dlocality=(EditText)findViewById(R.id.locality_donor);*/
        dcontact1=(EditText)findViewById(R.id.contact1);
        dcontact2=(EditText)findViewById(R.id.contact2);
        demail=(EditText)findViewById(R.id.email_donor);
        dpass=(EditText)findViewById(R.id.password);
        drepass=(EditText)findViewById(R.id.retypePassword);
        signup=(Button)findViewById(R.id.signin);
        database = FirebaseDatabase.getInstance();
        dref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        count=FirebaseDatabase.getInstance().getReference().child("counts");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=sharedPreferences.edit();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = dname.getText().toString();
                dob = ddob.getText().toString();
                locality = dlocality.getText().toString();
                contact1 = dcontact1.getText().toString();
                contact2 = dcontact2.getText().toString();
                email = demail.getText().toString();
                pass = dpass.getText().toString();
                repass = drepass.getText().toString();
                Ggroup=(RadioGroup) findViewById(R.id.radio_gender);
                Bgroup=(RadioGroup) findViewById(R.id.radio_blood);
                int selectG=Ggroup.getCheckedRadioButtonId();
                int selectB=Bgroup.getCheckedRadioButtonId();
                Gbutton=(RadioButton) findViewById(selectG);
                Bbutton=(RadioButton) findViewById(selectB);
                gender=Gbutton.getText().toString();
                blood=Bbutton.getText().toString();


                try{
                //  Log.d("success", "123");
                 if (!name.equals("") && !email.equals("") && !contact1.equals("") && contact1.length() == 10 && !locality.equals("")) {
                    Log.d("signing", name);
                    count.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Long coun1 = dataSnapshot.getValue(Long.class);
                            if (pass.equals(repass)) {
                                Donor donor = new Donor(coun1, name, contact1, contact2, email, locality, gender, blood);
                                pd.setMessage("Signing up");
                                pd.setCancelable(false);
                                pd.show();
                                editor.putString("email",email);
                                editor.commit();
                                Map<String, Object> postValues = donor.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/Donors/donor" + String.valueOf(coun1), postValues);
                                coun1++;
                                final Long finalCoun = coun1;
                                mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(getApplicationContext(), "Donor Successfully added", Toast.LENGTH_LONG).show();
                                    }
                                });

                                dref.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        try {
                                            dref.child("counts").setValue(finalCoun);
                                            Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                                            startActivity(intent);
                                            finish();
                                            pd.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "User adding failed", Toast.LENGTH_LONG).show();
                                            pd.dismiss();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "User adding failed", Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });
                            }

                            else{

                                Toast.makeText(getApplicationContext(),"Please enter the same password ",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "please give all details", Toast.LENGTH_LONG).show();
                    Log.d("success", "khvh");
                }
              /*if (pass.equals(repass)){
                   mAuth.createUserWithEmailAndPassword(email, pass)
                           .addOnCompleteListener(DonorSignUp.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   Log.d("success", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                   // If sign in fails, display a message to the user. If sign in succeeds
                                   // the auth state listener will be notified and logic to handle the
                                   // signed in user can be handled in the listener.
                                   if (!task.isSuccessful()) {
Toast.makeText(getApplicationContext(),"abc",Toast.LENGTH_LONG);                                   }

                                   // ...
                               }
                           });
               }*/
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();

            }
            }
        });
        }
}
