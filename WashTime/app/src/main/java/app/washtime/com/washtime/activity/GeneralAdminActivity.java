package app.washtime.com.washtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.adapter.StudentHomeAdapter;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.StudentHomeTask;
import app.washtime.com.washtime.task.impl.StudentHomeTaskImpl;

public class GeneralAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button mLogout;
    private Button mAddNewLocation;
    private Student mGA;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_general_admin);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.ga_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // set drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ga_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.ga_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set navigation header
        Intent intent = getIntent();
        mGA = (Student) intent.getSerializableExtra("admin");
        addNavHeader(navigationView.getHeaderView(0));

        // set list view
        StudentHomeAdapter mAdapter = new StudentHomeAdapter(getApplicationContext());
        StudentHomeTask task = new StudentHomeTaskImpl();
        task.getAllLocations(mAdapter);
        mListView = (ListView) findViewById(R.id.ga_list_view);
        mListView.setAdapter(mAdapter);

        // logout button
        addListenerForLogoutButton();

        // add new location button
        addListenerForAddNewLocationButton();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ga_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.ga_admin_setting:
                Intent intent = new Intent(getApplicationContext(), GeneralAdminSettingActivity.class);
                intent.putExtra("admin", mGA);
                startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ga_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addListenerForLogoutButton() {
        mLogout = (Button) findViewById(R.id.ga_logout);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
            }
        });
    }

    private void addListenerForAddNewLocationButton () {
        mAddNewLocation = (Button) findViewById(R.id.ga_button_new_location);

        mAddNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewLocation = new Intent(getApplicationContext(), AddNewLocationActivity.class);
                addNewLocation.putExtra("admin", mGA);
                startActivity(addNewLocation);
            }
        });
    }

    private void addNavHeader(View view) {
        TextView adminName = (TextView) view.findViewById(R.id.ga_nav_header_admin_name);
        adminName.setText(mGA.getLastName() + " " + mGA.getFirstName());
    }
}
