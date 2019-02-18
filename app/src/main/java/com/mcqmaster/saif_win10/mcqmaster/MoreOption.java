package com.mcqmaster.saif_win10.mcqmaster;

import android.app.Activity;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MoreOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_option);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new PrivacyPolicy()).commit();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                privacyPolicyFragment();
                return true;
            case R.id.item2:
                startActivity(new Intent(MoreOption.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void privacyPolicyFragment()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new PrivacyPolicy()).addToBackStack(null).commit();
    }
}
