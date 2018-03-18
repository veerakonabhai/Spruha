package com.example.veerareddykonabhai.spruha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    DatabaseReference dref;
    FirebaseDatabase database;

    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Spruha");
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        tv2=(TextView)headerview.findViewById(R.id.Gmail);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tv2.setText(sharedPreferences.getString("email","mail"));

        navigationView.setNavigationItemSelectedListener(this);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dref=database.getReference("Requests");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user=mAuth.getCurrentUser();
                List<Card> list;
                list=new ArrayList<Card>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Reciever value=dataSnapshot1.getValue(Reciever.class);
                    Card C=new Card();
                    C.setName(value.getName());
                    C.setBlood(value.getBlood());
                    C.setContact(value.getContact());
                    C.setLocality(value.getLocality());
                    C.setAddress(value.getAddress());
                    list.add(C);
                }
                RecyclerView rview=(RecyclerView)
                           findViewById(R.id.rview);
                LinearLayoutManager manager=new LinearLayoutManager(Main2Activity.this,LinearLayoutManager.VERTICAL,false);
                rview.setLayoutManager(manager);
                rview.setAdapter(new
                        donoradapter(list,Main2Activity.this,1));

                Toast.makeText(getApplicationContext(),"Total Requests :" +list.size(),Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        if (item.getTitle().equals("Home")) {
            dref=database.getReference("Requests");
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseUser user=mAuth.getCurrentUser();
                    List<Card> list;
                    list=new ArrayList<Card>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        Reciever value=dataSnapshot1.getValue(Reciever.class);
                        Card C=new Card();
                        C.setName(value.getName());
                        C.setBlood(value.getBlood());
                        C.setContact(value.getContact());
                        C.setLocality(value.getLocality());
                        C.setAddress(value.getAddress());
                        list.add(C);
                    }
                    RecyclerView rview=(RecyclerView)
                            findViewById(R.id.rview);
                    LinearLayoutManager manager=new LinearLayoutManager(Main2Activity.this,LinearLayoutManager.VERTICAL,false);
                    rview.setLayoutManager(manager);
                    rview.setAdapter(new
                            donoradapter(list,Main2Activity.this,1));

                    Toast.makeText(getApplicationContext(),"Total Requests :" +list.size(),Toast.LENGTH_LONG ).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else if (item.getTitle().equals("Profile")) {


        } else if (item.getTitle().equals("Signout")){
            mAuth.signOut();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
