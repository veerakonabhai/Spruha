package com.example.veerareddykonabhai.spruha;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Request extends AppCompatActivity {
    EditText rname,rlocality,rcontact,raddress;
    Button request;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference dref,count1;
    String name,locality,contact,address,blood;
    RadioButton Bbutton;
    RadioGroup Bgroup;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        rname=(EditText)findViewById(R.id.name_request);
        rlocality=(EditText)findViewById(R.id.locality_request);
        rcontact=(EditText)findViewById(R.id.contact_request);
        raddress=(EditText)findViewById(R.id.address_request);
        request=(Button)findViewById(R.id.signin);
        database = FirebaseDatabase.getInstance();
        dref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        count1=FirebaseDatabase.getInstance().getReference().child("countr");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = rname.getText().toString();
                locality = rlocality.getText().toString();
                contact = rcontact.getText().toString();
                address = raddress.getText().toString();
                Bgroup=(RadioGroup) findViewById(R.id.radio_blood);
                int selectB=Bgroup.getCheckedRadioButtonId();
                Bbutton=(RadioButton) findViewById(selectB);
                blood=Bbutton.getText().toString();
                pd.setMessage("Adding Request");
                pd.setCancelable(false);
                pd.show();

                try
                {
                    if(!name.equals("") && !locality.equals("") && !contact.equals("") && !address.equals("")) {

                        count1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long coun2=dataSnapshot.getValue(Long.class);
                                Reciever reciever = new Reciever(coun2, name, contact, locality, address, blood);
                                Map<String, Object> postValues = reciever.toMap();
                                Map<String, Object> childUpdates = new HashMap<>();
                                childUpdates.put("/Requests/request" + String.valueOf(coun2), postValues);
                                coun2++;
                                final Long finalCoun = coun2;

                                dref.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        try {
                                            dref.child("countr").setValue(finalCoun);
                                            Toast.makeText(getApplicationContext(), " Request Added Successfully", Toast.LENGTH_LONG).show();
                                            pd.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "Request adding failed", Toast.LENGTH_LONG).show();
                                           pd.dismiss();
                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Request adding failed", Toast.LENGTH_LONG).show();
                                        pd.dismiss();
                                    }
                                });


                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Please try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
