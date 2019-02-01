package com.mcqmaster.saif_win10.mcqmaster;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_bd;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_bsp;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_bvs;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_evs;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_generalS;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_geo;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_international;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_it;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_math;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_mental;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bcs_value;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_bng_grammar;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_current_world_bd;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_current_world_int;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_eng_grammar;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_eng_vocab;
import com.mcqmaster.saif_win10.mcqmaster.all_second_activity.CV_gov_bank;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Mcq_Database mcq_database;

//    CardView mCV_bcs_bsp, mCV_current_world_bd, mCV_current_world_int, mCV_bcs_bvs, mCV_bcs_evs,
//            mCV_bcs_bd, mCV_bcs_international, mCV_bcs_geo, mCV_bcs_generalS, mCV_bcs_it, mCV_bcs_math,
//            mCV_bcs_mental, mCV_bcs_value, mCV_eng_grammar, mCV_bng_grammar, mCV_eng_vocab, mCV_gov_bank;

    TextView mCV_bcs_bsp, mCV_current_world_bd, mCV_current_world_int, mCV_bcs_bvs, mCV_bcs_evs,
            mCV_bcs_bd, mCV_bcs_international, mCV_bcs_geo, mCV_bcs_generalS, mCV_bcs_it, mCV_bcs_math,
            mCV_bcs_mental, mCV_bcs_value, mCV_eng_grammar, mCV_bng_grammar, mCV_eng_vocab, mCV_gov_bank;

    ScrollView scrollView3, scrollView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcq_database = Room.databaseBuilder(getApplicationContext(), Mcq_Database.class, "McqDb").allowMainThreadQueries().build();


        mCV_bcs_bsp = findViewById(R.id.mCV_bcs_bsp);
        mCV_current_world_bd = findViewById(R.id.mCV_current_world_bd);
        mCV_current_world_int = findViewById(R.id.mCV_current_world_int);
        mCV_bcs_bvs = findViewById(R.id.mCV_bcs_bvs);
        mCV_bcs_evs = findViewById(R.id.mCV_bcs_evs);
        mCV_bcs_bd = findViewById(R.id.mCV_bcs_bd);
        mCV_bcs_international = findViewById(R.id.mCV_bcs_international);
        mCV_bcs_geo = findViewById(R.id.mCV_bcs_geo);
        mCV_bcs_generalS = findViewById(R.id.mCV_bcs_generalS);
        mCV_bcs_it = findViewById(R.id.mCV_bcs_it);
        mCV_bcs_math = findViewById(R.id.mCV_bcs_math);
        mCV_bcs_mental = findViewById(R.id.mCV_bcs_mental);
        mCV_bcs_value = findViewById(R.id.mCV_bcs_value);
        mCV_eng_grammar = findViewById(R.id.mCV_eng_grammar);
        mCV_bng_grammar = findViewById(R.id.mCV_bng_grammar);
        mCV_eng_vocab = findViewById(R.id.mCV_eng_vocab);
        mCV_gov_bank = findViewById(R.id.mCV_gov_bank);


        mCV_bcs_bsp.setOnClickListener(new Click());
        mCV_current_world_bd.setOnClickListener(new Click());
        mCV_current_world_int.setOnClickListener(new Click());
        mCV_bcs_bvs.setOnClickListener(new Click());
        mCV_bcs_evs.setOnClickListener(new Click());
        mCV_bcs_bd.setOnClickListener(new Click());
        mCV_bcs_international.setOnClickListener(new Click());
        mCV_bcs_geo.setOnClickListener(new Click());
        mCV_bcs_generalS.setOnClickListener(new Click());
        mCV_bcs_it.setOnClickListener(new Click());
        mCV_bcs_math.setOnClickListener(new Click());
        mCV_bcs_mental.setOnClickListener(new Click());
        mCV_bcs_value.setOnClickListener(new Click());
        mCV_eng_grammar.setOnClickListener(new Click());
        mCV_bng_grammar.setOnClickListener(new Click());
        mCV_eng_vocab.setOnClickListener(new Click());
        mCV_gov_bank.setOnClickListener(new Click());

    }

    public class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.mCV_bcs_bsp:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_bsp.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_current_world_bd:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_current_world_bd.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_current_world_int:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_current_world_int.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_bvs:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_bvs.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_evs:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_evs.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_bd:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_bd.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_international:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_international.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_geo:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_geo.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_generalS:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_generalS.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_it:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_it.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_math:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_math.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_mental:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_mental.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bcs_value:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bcs_value.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_eng_grammar:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_eng_grammar.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_bng_grammar:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_bng_grammar.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_eng_vocab:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_eng_vocab.class);
                    startActivity(intent);
                    break;
                case R.id.mCV_gov_bank:
                    Toast.makeText(MainActivity.this, "please activate your internet connection in order to continue", Toast.LENGTH_LONG).show();
                    intent = new Intent(getApplicationContext(), CV_gov_bank.class);
                    startActivity(intent);
                    break;
            }
        }
    }

}
