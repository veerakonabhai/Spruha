package com.example.veerareddykonabhai.spruha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class termlist extends AppCompatActivity {
    CheckBox checkterm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termlist);
        checkterm=(CheckBox)findViewById(R.id.checkterm);
    }
    public void clicked(View v)
    {
        Intent intent = new Intent(v.getContext(),DonorSignUp.class);
        startActivity(intent);
    }
}
