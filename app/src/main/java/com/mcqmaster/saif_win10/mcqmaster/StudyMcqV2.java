package com.mcqmaster.saif_win10.mcqmaster;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Ignore;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Objects;
import java.util.Observer;

import io.github.kexanie.library.MathView;

public class StudyMcqV2 extends AppCompatActivity {

    //For inferential add
    private InterstitialAd mInterstitialAd;

    //geting mcq_viewmodel object
    private Mcq_ViewModel mcq_viewModel;


    String mPost_key;
    String child_Name;

    String q;
    String o1;
    String o2;
    String o3;
    String o4;
    String ans;
    String e;
    int total_N_Q;
    int level_cards;
    int level_question;

    //not used in database or room database
    int level_offline;
    int level_cards_offline;

    private Firebase mAnswer;
    private Firebase mExplanation;
    private Firebase mTotalQ;
    private int mQuestNum = 1;
    private String ansChoice = "";
    private int totalQuestion;
    private String totalQ;
    String showResult;

    private int totalPoint = 0;
    int cycle = 0;
    Integer level = 0;

    String[] questionN = {"q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10", "q11", "q12", "q13", "q14", "q15", "q16", "q17", "q18", "q19", "q20", "q21", "q22", "q23", "q24", "q25", "q26", "q27", "q28", "q29", "q30", "q31", "q32", "q33", "q34", "q35", "q36", "q37", "q38", "q39", "q40", "q41"};

    int q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17, q18, q19, q20, q21, q22, q23, q24, q25, q26, q27, q28, q29, q30, q31, q32, q33, q34, q35, q36, q37, q38, q39, q40, q41 = 0;
    int r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40 = 0;

    int answeredQn = 0;
    int unAnsweredQN = 0;

    private TextView mTxt_quest;
    private CheckBox mChkOpt1;
    private CheckBox mChkOpt2;
    private CheckBox mChkOpt3;
    private CheckBox mChkOpt4;
    private TextView mTxt_ans;
    private TextView mTxt_expl;
    private Button mBtn_submit;
    private TextView mTxt_id;

    private TextView mTxt_level;
    private TextView mTxt_Qnumber;
    private TextView mTxt_totalQ;
    private TextView mTxt_PointEachQ;
    private TextView mTxt_cycle;
    private TextView mTxt_RIGHTvWRONG;
    private TextView mTxt_Header_topic;

    //for next previous and refresh button
    private Button btn_next;
    private Button btn_prev;
    private Button btn_refresh;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_test);
        Firebase.setAndroidContext(this);

        //connecting with viewmodel class
        mcq_viewModel = ViewModelProviders.of(this).get(Mcq_ViewModel.class);


        //For all the add (Change app unit id later)
        MobileAds.initialize(this,
                "ca-app-pub-2522810443010389~4508857974");

        // Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713
        // My AdMob App ID: ca-app-pub-2522810443010389~4508857974

        //sample interstitial add unit id: ca-app-pub-3940256099942544/1033173712
        //my interstitial add unit id: ca-app-pub-2522810443010389/2863893374

        //For Interstitial Add (Change Interstitial add unit later)
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2522810443010389/2863893374");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //This code will run after Interstitial code is closed
        mInterstitialAd.setAdListener(new AdListener() {
                                          @Override
                                          public void onAdClosed() {
                                              StudyMcqV2.super.onBackPressed();
                                          }
                                      }
        );

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mPost_key = Objects.requireNonNull(getIntent().getExtras()).getString("key_name");
        child_Name = Objects.requireNonNull(getIntent().getExtras()).getString("childName");
        //Toast.makeText(StudyMCQ.this,mPost_key+child_Name,Toast.LENGTH_SHORT).show();


        mTxt_quest = findViewById(R.id.mTxt_quest);
        mTxt_ans = findViewById(R.id.mTxt_ans);
        mTxt_expl = findViewById(R.id.mTxt_Expl);
        mChkOpt1 = findViewById(R.id.mChkOpt1);
        mChkOpt2 = findViewById(R.id.mChkOpt2);
        mChkOpt3 = findViewById(R.id.mChkOpt3);
        mChkOpt4 = findViewById(R.id.mChkOpt4);
        mBtn_submit = findViewById(R.id.mBtn_submit);
        mTxt_id = findViewById(R.id.mTxt_ID);

        mTxt_totalQ = findViewById(R.id.mTxt_totalQ);
        mTxt_Qnumber = findViewById(R.id.mTxt_Qnumber);
        mTxt_level = findViewById(R.id.mTxt_level);
        mTxt_PointEachQ = findViewById(R.id.mTxt_PointEachQ);

        //find the button for next previous and refresh
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        btn_refresh = findViewById(R.id.btn_refresh);
        mTxt_cycle = findViewById(R.id.mTxt_cycle);
        mTxt_RIGHTvWRONG = findViewById(R.id.mTxt_RIGHTvWRONG);
        mTxt_Header_topic = findViewById(R.id.mTxt_Header_topic);

        headerTopic();

        updateLevelStatus(level);
        eachQuestStatus();

        //For test purpose only
        readFromRoomDatabase();
        //updateQuestion();

    }

    public void headerTopic() {
        switch (child_Name) {
            case "bcs_bd":
                mTxt_Header_topic.setText(getResources().getString(R.string.bangladesh));
                break;
            case "previous_y_q":
                mTxt_Header_topic.setText(getResources().getString(R.string.previous_year_question));
                break;
            case "bcs_bvs":
                mTxt_Header_topic.setText(getResources().getString(R.string.bvs));
                break;
            case "bcs_evs":
                mTxt_Header_topic.setText(getResources().getString(R.string.evs));
                break;
            case "general_s":
                mTxt_Header_topic.setText(getResources().getString(R.string.general_science));
                break;
            case "bcs_geo":
                mTxt_Header_topic.setText(getResources().getString(R.string.geography));
                break;
            case "CV_bcs_international":
                mTxt_Header_topic.setText(getResources().getString(R.string.international));
                break;
            case "bcs_it":
                mTxt_Header_topic.setText(getResources().getString(R.string.computer));
                break;
            case "bcs_math":
                mTxt_Header_topic.setText(getResources().getString(R.string.mathematics));
                break;
            case "bcs_mental":
                mTxt_Header_topic.setText(getResources().getString(R.string.mental));
                break;
            case "bcs_value":
                mTxt_Header_topic.setText(getResources().getString(R.string.morality));
                break;
            case "bng_grammar":
                mTxt_Header_topic.setText(getResources().getString(R.string.bng_grammar));
                break;
            case "current_world_bd":
                mTxt_Header_topic.setText(getResources().getString(R.string.current_world_bd));
                break;
            case "current_world_int":
                mTxt_Header_topic.setText(getResources().getString(R.string.current_world_int));
                break;
            case "eng_grammar":
                mTxt_Header_topic.setText(getResources().getString(R.string.eng_grammar));
                break;
            case "eng_vocab":
                mTxt_Header_topic.setText(getResources().getString(R.string.vocabulary));
                break;
            case "gov_bank":
                mTxt_Header_topic.setText(getResources().getString(R.string.govt_bank));
                break;
            default:
                mTxt_Header_topic.setText("Report ERROR!");
                break;
        }

    }

    public void updateLevelEachQuestionStatus(int a) {
        if (a < 2) {
            mTxt_PointEachQ.setText("Primary");
            //mTxt_PointEachQ.setBackgroundColor(Color.parseColor("#80ff1a1a"));
            mTxt_PointEachQ.setBackground(getResources().getDrawable(R.drawable.button_red_transperant));
        } else if (a == 2 || a == 3) {
            mTxt_PointEachQ.setText("Learning");
            //mTxt_PointEachQ.setBackgroundColor(Color.parseColor("#80ffff1a"));
            mTxt_PointEachQ.setBackground(getResources().getDrawable(R.drawable.button_yellow_transperant));
        } else {
            mTxt_PointEachQ.setText("Master");
            //mTxt_PointEachQ.setBackgroundColor(Color.parseColor("#8033ff33"));
            mTxt_PointEachQ.setBackground(getResources().getDrawable(R.drawable.button_green_transperant));
        }
    }

    public void uncheckOption() {

        mChkOpt1.setChecked(false);
        mChkOpt2.setChecked(false);
        mChkOpt3.setChecked(false);
        mChkOpt4.setChecked(false);
    }

    public void initialState() {
        String pointVquestion = "Point: " + String.valueOf(totalPoint) + "/" + totalQ;
        String countTry = "Tried: " + cycle + " times";
        String questionNo = "Question No: " + questionN[mQuestNum - 1];
        mTxt_totalQ.setText(pointVquestion);
        mTxt_Qnumber.setText(questionNo);
        mTxt_cycle.setText(countTry);
    }

    public void initialState_offline() {
        String pointVquestion = "Point: " + String.valueOf(totalPoint) + "/" + String.valueOf(totalQuestion);
        String countTry = "Tried: " + cycle + " times";
        String questionNo = "Question No: " + questionN[mQuestNum - 1];
        mTxt_totalQ.setText(pointVquestion);
        mTxt_Qnumber.setText(questionNo);
        mTxt_cycle.setText(countTry);
    }

    public void updateQuestion() {

        //it reads from room database and update room database

        //The method commonLevelStatus() Status creates problem here; do not use it here!
        //commonLevelStatus();

        idEachQuest(child_Name, mPost_key, questionN[mQuestNum - 1]);

        mTotalQ = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/info/totalQ");
        mTotalQ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalQ = dataSnapshot.getValue(String.class);

                initialState();
                totalQuestion = Integer.parseInt(totalQ);

                //For firebase use only
                total_N_Q = totalQuestion;

                //beware, this method might create problem!
                commonLevelStatus();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final Firebase mQuestion = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/q");
        mQuestion.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = dataSnapshot.getValue(String.class);
                mTxt_quest.setText(question);
                //For database only
                q = question;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase mChoice1 = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/o1");
        //mChoice1.keepSynced(true);
        mChoice1.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String option1 = dataSnapshot.getValue(String.class);
                mChkOpt1.setText(option1);
                //For Database only
                o1 = option1;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase mChoice2 = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/o2");
        //mChoice2.keepSynced(true);
        mChoice2.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String option2 = dataSnapshot.getValue(String.class);
                mChkOpt2.setText(option2);
                //For database only
                o2 = option2;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase mChoice3 = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/o3");
        //mChoice3.keepSynced(true);
        mChoice3.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String option3 = dataSnapshot.getValue(String.class);
                mChkOpt3.setText(option3);
                //For database only
                o3 = option3;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase mChoice4 = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/o4");
        //mChoice4.keepSynced(true);
        mChoice4.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String option4 = dataSnapshot.getValue(String.class);
                mChkOpt4.setText(option4);
                //For database only
                o4 = option4;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mBtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mNextQuest = "NEXT";
                mBtn_submit.setText(mNextQuest);
                mBtn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mSubmit = "SUBMIT";
                        String mAnswer = "ANSWER";
                        String mExplanation = "Explanation will be here";
                        String mRightvWrong = "Check";

                        //set Background color for RIGHT v WRONG indicator Text box
                        mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_sub_design));

                        mBtn_submit.setText(mSubmit);
                        mTxt_ans.setText(mAnswer);
                        mTxt_expl.setText(mExplanation);
                        mTxt_RIGHTvWRONG.setText(mRightvWrong);
                        mQuestNum++;
                        eachQuestStatus();
                        //findQnumber++;
                        initialState();

                        uncheckOption();


                        if (mQuestNum > totalQuestion) {
                            mQuestNum = 1;
                            //to make value of int r back to zero
                            make_int_r_zero();
                            notificationForTotalPoint();
                            eachQuestStatus();
                            cycle++;
                            totalPoint = 0;
                            readFromRoomDatabase();
                            initialState();

                        } else {
                            readFromRoomDatabase();
                        }

                    }
                });


                checkOptionChoice();

                mAnswer = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/ans");
                //mAnswer.keepSynced(true);
                mAnswer.addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String answer = dataSnapshot.getValue(String.class);

                        //For database only
                        ans = answer;

                        if (ansChoice.equals(answer)) {
                            showResult = "Ans: " + answer;
                            mTxt_RIGHTvWRONG.setText("RIGHT");
                            mTxt_ans.setText(showResult);
                            //Toast.makeText(McqQuestion.this, "Correct", Toast.LENGTH_SHORT).show();

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_green_transperant));

                            answeredQn = mQuestNum - 1;
                            totalPoint++;

                            level = levelINCREASE(level);
                            //For room database use only
                            level_cards = level;

                            //updateLevelStatus(level);
                            eachQuestStatusSUCCESS();

                            initialState();

                        } else if (ansChoice.equals("NoAnswerChoosen")) {
                            initialState();
                            showResult = "Ans: " + answer;
                            mTxt_RIGHTvWRONG.setText("WRONG");

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_red_transperant));

                            mTxt_ans.setText(showResult);
                            unAnsweredQN = mQuestNum - 1;
                            eachQuestStatusFAILURE();
                            level = levelDECREASE(level);

                            //For room database use only
                            level_cards = level;

                            //updateLevelStatus(level);
                            //idEachQuest(child_Name,mPost_key,questionN[unAnsweredQN]);
                        } else {
                            showResult = "Ans: " + answer;
                            mTxt_RIGHTvWRONG.setText("WRONG");

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_red_transperant));

                            mTxt_ans.setText(showResult);
                            //Toast.makeText(McqQuestion.this, "Wrong Answer, Question No.: " + questionN[unAnsweredQN], Toast.LENGTH_SHORT).show();

                            initialState();
                            unAnsweredQN = mQuestNum - 1;
                            eachQuestStatusFAILURE();
                            level = levelDECREASE(level);

                            //For room database use only
                            level_cards = level;

                            //updateLevelStatus(level);
                            //idEachQuest(child_Name,mPost_key,questionN[unAnsweredQN]);

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                mExplanation = new Firebase("https://mcqmaster-cdc2c.firebaseio.com/" + child_Name + "/" + mPost_key + "/" + mQuestNum + "/e");
                //mExplanation.keepSynced(true);
                mExplanation.addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String explanation = dataSnapshot.getValue(String.class);
                        mTxt_expl.setText(explanation);
                        //For database only
                        e = explanation;
                        addQuestionDatabase();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

        });
    }

    public int levelINCREASE(int a) {
        if (totalPoint == totalQuestion) {
            if (a < 2) {
                a++;
                return a;
            } else if (a == 2 || a == 3) {
                a++;
                return a;
            } else {
                a = 4;
                return a;
            }
        } else {
            a = level;
            return a;
        }
    }

    public int levelINCREASE_offline(int a) {
        if (totalPoint == totalQuestion) {
            if (a < 2) {
                a++;
                return a;
            } else if (a == 2 || a == 3) {
                a++;
                return a;

            } else {
                a = 4;
                return a;
            }
        } else {
            a = level_offline;
            return a;
        }
    }

    public void updateLevelStatus(int a) {
        if (a < 2) {
            mTxt_level.setText("Primary");
            mTxt_level.setBackgroundColor(Color.parseColor("red"));
        } else if (a == 2 || a == 3) {
            mTxt_level.setText("Learning");
            mTxt_level.setBackgroundColor(Color.parseColor("yellow"));
        } else {
            //Toast.makeText(StudyMcqV2.this, "Congratulation, you got the highest mark!", Toast.LENGTH_SHORT).show();
            mTxt_level.setText("Master");
            mTxt_level.setBackgroundColor(Color.parseColor("green"));
        }
    }

    public void eachQuestStatusSUCCESS() {
        switch (questionN[answeredQn]) {
            case "q1":
                q1 = eachQuestStatusINCREASE(q1);

                r1 = 1;
                //For room database use only
                level_question = q1;
                updateLevelEachQuestionStatus(q1);
                break;
            case "q2":
                q2 = eachQuestStatusINCREASE(q2);

                r2 = 1;
                //For room database use only
                level_question = q2;
                updateLevelEachQuestionStatus(q2);
                break;
            case "q3":
                q3 = eachQuestStatusINCREASE(q3);

                r3 = 1;
                //For room database use only
                level_question = q3;
                updateLevelEachQuestionStatus(q3);
                break;
            case "q4":
                q4 = eachQuestStatusINCREASE(q4);

                r4 = 1;
                //For room database use only
                level_question = q4;
                updateLevelEachQuestionStatus(q4);
                break;
            case "q5":
                q5 = eachQuestStatusINCREASE(q5);

                r5 = 1;
                //For room database use only
                level_question = q5;
                updateLevelEachQuestionStatus(q5);
                break;
            case "q6":
                q6 = eachQuestStatusINCREASE(q6);

                r6 = 1;
                //For room database use only
                level_question = q6;
                updateLevelEachQuestionStatus(q6);
                break;
            case "q7":
                q7 = eachQuestStatusINCREASE(q7);

                r7 = 1;
                //For room database use only
                level_question = q7;
                updateLevelEachQuestionStatus(q7);
                break;
            case "q8":
                q8 = eachQuestStatusINCREASE(q8);

                r8 = 1;
                //For room database use only
                level_question = q8;
                updateLevelEachQuestionStatus(q8);
                break;
            case "q9":
                q9 = eachQuestStatusINCREASE(q9);

                r9 = 1;
                //For room database use only
                level_question = q9;
                updateLevelEachQuestionStatus(q9);
                break;
            case "q10":
                q10 = eachQuestStatusINCREASE(q10);

                r10 = 1;
                //For room database use only
                level_question = q10;
                updateLevelEachQuestionStatus(q10);
                break;
            case "q11":
                q11 = eachQuestStatusINCREASE(q11);

                r11 = 1;
                //For room database use only
                level_question = q11;
                updateLevelEachQuestionStatus(q11);
                break;
            case "q12":
                q12 = eachQuestStatusINCREASE(q12);

                r12 = 1;
                //For room database use only
                level_question = q12;
                updateLevelEachQuestionStatus(q12);
                break;
            case "q13":
                q13 = eachQuestStatusINCREASE(q13);

                r13 = 1;
                //For room database use only
                level_question = q13;
                updateLevelEachQuestionStatus(q13);
                break;
            case "q14":
                q14 = eachQuestStatusINCREASE(q14);

                r14 = 1;
                //For room database use only
                level_question = q14;
                updateLevelEachQuestionStatus(q14);
                break;
            case "q15":
                q15 = eachQuestStatusINCREASE(q15);

                r15 = 1;
                //For room database use only
                level_question = q15;
                updateLevelEachQuestionStatus(q15);
                break;
            case "q16":
                q16 = eachQuestStatusINCREASE(q16);

                r16 = 1;
                //For room database use only
                level_question = q16;
                updateLevelEachQuestionStatus(q16);
                break;
            case "q17":
                q17 = eachQuestStatusINCREASE(q17);

                r17 = 1;
                //For room database use only
                level_question = q17;
                updateLevelEachQuestionStatus(q17);
                break;
            case "q18":
                q18 = eachQuestStatusINCREASE(q18);

                r18 = 1;
                //For room database use only
                level_question = q18;
                updateLevelEachQuestionStatus(q18);
                break;
            case "q19":
                q19 = eachQuestStatusINCREASE(q19);

                r19 = 1;
                //For room database use only
                level_question = q19;
                updateLevelEachQuestionStatus(q19);
                break;
            case "q20":
                q20 = eachQuestStatusINCREASE(q20);

                r20 = 1;
                //For room database use only
                level_question = q20;
                updateLevelEachQuestionStatus(q20);
                break;
            case "q21":
                q21 = eachQuestStatusINCREASE(q21);

                r21 = 1;
                //For room database use only
                level_question = q21;
                updateLevelEachQuestionStatus(q21);
                break;
            case "q22":
                q22 = eachQuestStatusINCREASE(q22);

                r22 = 1;
                //For room database use only
                level_question = q22;
                updateLevelEachQuestionStatus(q22);
                break;
            case "q23":
                q23 = eachQuestStatusINCREASE(q23);

                r23 = 1;
                //For room database use only
                level_question = q23;
                updateLevelEachQuestionStatus(q23);
                break;
            case "q24":
                q24 = eachQuestStatusINCREASE(q24);

                r24 = 1;
                //For room database use only
                level_question = q24;
                updateLevelEachQuestionStatus(q24);
                break;
            case "q25":
                q25 = eachQuestStatusINCREASE(q25);

                r25 = 1;
                //For room database use only
                level_question = q25;
                updateLevelEachQuestionStatus(q25);
                break;
            case "q26":
                q26 = eachQuestStatusINCREASE(q26);

                r26 = 1;
                //For room database use only
                level_question = q26;
                updateLevelEachQuestionStatus(q26);
                break;
            case "q27":
                q27 = eachQuestStatusINCREASE(q27);

                r27 = 1;
                //For room database use only
                level_question = q27;
                updateLevelEachQuestionStatus(q27);
                break;
            case "q28":
                q28 = eachQuestStatusINCREASE(q28);

                r28 = 1;
                //For room database use only
                level_question = q28;
                updateLevelEachQuestionStatus(q28);
                break;
            case "q29":
                q29 = eachQuestStatusINCREASE(q29);

                r29 = 1;
                //For room database use only
                level_question = q29;
                updateLevelEachQuestionStatus(q29);
                break;
            case "q30":
                q30 = eachQuestStatusINCREASE(q30);

                r30 = 1;
                //For room database use only
                level_question = q30;
                updateLevelEachQuestionStatus(q30);
                break;
            case "q31":
                q31 = eachQuestStatusINCREASE(q31);

                r31 = 1;
                //For room database use only
                level_question = q31;
                updateLevelEachQuestionStatus(q31);
                break;
            case "q32":
                q32 = eachQuestStatusINCREASE(q32);

                r32 = 1;
                //For room database use only
                level_question = q32;
                updateLevelEachQuestionStatus(q32);
                break;
            case "q33":
                q33 = eachQuestStatusINCREASE(q33);

                r33 = 1;
                //For room database use only
                level_question = q33;
                updateLevelEachQuestionStatus(q33);
                break;
            case "q34":
                q34 = eachQuestStatusINCREASE(q34);

                r34 = 1;
                //For room database use only
                level_question = q34;
                updateLevelEachQuestionStatus(q34);
                break;
            case "q35":
                q35 = eachQuestStatusINCREASE(q35);

                r35 = 1;
                //For room database use only
                level_question = q35;
                updateLevelEachQuestionStatus(q35);
                break;
            case "q36":
                q36 = eachQuestStatusINCREASE(q36);

                r36 = 1;
                //For room database use only
                level_question = q36;
                updateLevelEachQuestionStatus(q36);
                break;
            case "q37":
                q37 = eachQuestStatusINCREASE(q37);

                r37 = 1;
                //For room database use only
                level_question = q37;
                updateLevelEachQuestionStatus(q37);
                break;
            case "q38":
                q38 = eachQuestStatusINCREASE(q38);

                r38 = 1;
                //For room database use only
                level_question = q38;
                updateLevelEachQuestionStatus(q38);
                break;
            case "q39":
                q39 = eachQuestStatusINCREASE(q39);

                r39 = 1;
                //For room database use only
                level_question = q39;
                updateLevelEachQuestionStatus(q39);
                break;
            case "q40":
                q40 = eachQuestStatusINCREASE(q40);

                r40 = 1;
                //For room database use only
                level_question = q40;
                updateLevelEachQuestionStatus(q40);
                break;
            case "q41":
                q41 = eachQuestStatusINCREASE(q41);
                updateLevelEachQuestionStatus(q41);
                break;

        }
    }

    public int eachQuestStatusINCREASE(int a) {
        if (a < 2) {
            a++;
            return a;
        } else if (a == 2 || a == 3) {
            a++;
            return a;
        } else {
            a = 4;
            return a;
        }
    }

    public void eachQuestStatus() {
        switch (questionN[mQuestNum - 1]) {
            case "q1":
                updateLevelEachQuestionStatus(q1);
                break;
            case "q2":
                updateLevelEachQuestionStatus(q2);
                break;
            case "q3":
                updateLevelEachQuestionStatus(q3);
                break;
            case "q4":
                updateLevelEachQuestionStatus(q4);
                break;
            case "q5":
                updateLevelEachQuestionStatus(q5);
                break;
            case "q6":
                updateLevelEachQuestionStatus(q6);
                break;
            case "q7":
                updateLevelEachQuestionStatus(q7);
                break;
            case "q8":
                updateLevelEachQuestionStatus(q8);
                break;
            case "q9":
                updateLevelEachQuestionStatus(q9);
                break;
            case "q10":
                updateLevelEachQuestionStatus(q10);
                break;
            case "q11":
                updateLevelEachQuestionStatus(q11);
                break;
            case "q12":
                updateLevelEachQuestionStatus(q12);
                break;
            case "q13":
                updateLevelEachQuestionStatus(q13);
                break;
            case "q14":
                updateLevelEachQuestionStatus(q14);
                break;
            case "q15":
                updateLevelEachQuestionStatus(q15);
                break;
            case "q16":
                updateLevelEachQuestionStatus(q16);
                break;
            case "q17":
                updateLevelEachQuestionStatus(q17);
                break;
            case "q18":
                updateLevelEachQuestionStatus(q18);
                break;
            case "q19":
                updateLevelEachQuestionStatus(q19);
                break;
            case "q20":
                updateLevelEachQuestionStatus(q20);
                break;
            case "q21":
                updateLevelEachQuestionStatus(q21);
                break;
            case "q22":
                updateLevelEachQuestionStatus(q22);
                break;
            case "q23":
                updateLevelEachQuestionStatus(q23);
                break;
            case "q24":
                updateLevelEachQuestionStatus(q24);
                break;
            case "q25":
                updateLevelEachQuestionStatus(q25);
                break;
            case "q26":
                updateLevelEachQuestionStatus(q26);
                break;
            case "q27":
                updateLevelEachQuestionStatus(q27);
                break;
            case "q28":
                updateLevelEachQuestionStatus(q28);
                break;
            case "q29":
                updateLevelEachQuestionStatus(q29);
                break;
            case "q30":
                updateLevelEachQuestionStatus(q30);
                break;
            case "q31":
                updateLevelEachQuestionStatus(q31);
                break;
            case "q32":
                updateLevelEachQuestionStatus(q32);
                break;
            case "q33":
                updateLevelEachQuestionStatus(q33);
                break;
            case "q34":
                updateLevelEachQuestionStatus(q34);
                break;
            case "q35":
                updateLevelEachQuestionStatus(q35);
                break;
            case "q36":
                updateLevelEachQuestionStatus(q36);
                break;
            case "q37":
                updateLevelEachQuestionStatus(q37);
                break;
            case "q38":
                updateLevelEachQuestionStatus(q38);
                break;
            case "q39":
                updateLevelEachQuestionStatus(q39);
                break;
            case "q40":
                updateLevelEachQuestionStatus(q40);
                break;
            case "q41":
                updateLevelEachQuestionStatus(q41);
                break;

        }
    }

    public void eachQuestStatusFAILURE() {
        switch (questionN[unAnsweredQN]) {
            case "q1":
                q1 = eachQuestStatusDECREASE(q1);
                r1 = 1;
                //For room database use only
                level_question = q1;
                updateLevelEachQuestionStatus(q1);
                break;
            case "q2":
                q2 = eachQuestStatusDECREASE(q2);
                r2 = 1;
                //For room database use only
                level_question = q2;
                updateLevelEachQuestionStatus(q2);
                break;
            case "q3":
                q3 = eachQuestStatusDECREASE(q3);
                r3 = 1;
                //For room database use only
                level_question = q3;
                updateLevelEachQuestionStatus(q3);
                break;
            case "q4":
                q4 = eachQuestStatusDECREASE(q4);
                r4 = 1;
                //For room database use only
                level_question = q4;
                updateLevelEachQuestionStatus(q4);
                break;
            case "q5":
                q5 = eachQuestStatusDECREASE(q5);
                r5 = 1;
                //For room database use only
                level_question = q5;
                updateLevelEachQuestionStatus(q5);
                break;
            case "q6":
                q6 = eachQuestStatusDECREASE(q6);
                r6 = 1;
                //For room database use only
                level_question = q6;
                updateLevelEachQuestionStatus(q6);
                break;
            case "q7":
                q7 = eachQuestStatusDECREASE(q7);
                r7 = 1;
                //For room database use only
                level_question = q7;
                updateLevelEachQuestionStatus(q7);
                break;
            case "q8":
                q8 = eachQuestStatusDECREASE(q8);
                r8 = 1;
                //For room database use only
                level_question = q8;
                updateLevelEachQuestionStatus(q8);
                break;
            case "q9":
                q9 = eachQuestStatusDECREASE(q9);
                r9 = 1;
                //For room database use only
                level_question = q9;
                updateLevelEachQuestionStatus(q9);
                break;
            case "q10":
                q10 = eachQuestStatusDECREASE(q10);
                r10 = 1;
                //For room database use only
                level_question = q10;
                updateLevelEachQuestionStatus(q10);
                break;
            case "q11":
                q11 = eachQuestStatusDECREASE(q11);
                r11 = 1;
                //For room database use only
                level_question = q11;
                updateLevelEachQuestionStatus(q11);
                break;
            case "q12":
                q12 = eachQuestStatusDECREASE(q12);
                r12 = 1;
                //For room database use only
                level_question = q12;
                updateLevelEachQuestionStatus(q12);
                break;
            case "q13":
                q13 = eachQuestStatusDECREASE(q13);
                r13 = 1;
                //For room database use only
                level_question = q13;
                updateLevelEachQuestionStatus(q13);
                break;
            case "q14":
                q14 = eachQuestStatusDECREASE(q14);
                r14 = 1;
                //For room database use only
                level_question = q14;
                updateLevelEachQuestionStatus(q14);
                break;
            case "q15":
                q15 = eachQuestStatusDECREASE(q15);
                r15 = 1;
                //For room database use only
                level_question = q15;
                updateLevelEachQuestionStatus(q15);
                break;
            case "q16":
                q16 = eachQuestStatusDECREASE(q16);
                r16 = 1;
                //For room database use only
                level_question = q16;
                updateLevelEachQuestionStatus(q16);
                break;
            case "q17":
                q17 = eachQuestStatusDECREASE(q17);
                r17 = 1;
                //For room database use only
                level_question = q17;
                updateLevelEachQuestionStatus(q17);
                break;
            case "q18":
                q18 = eachQuestStatusDECREASE(q18);
                r18 = 1;
                //For room database use only
                level_question = q18;
                updateLevelEachQuestionStatus(q18);
                break;
            case "q19":
                q19 = eachQuestStatusDECREASE(q19);
                r19 = 1;
                //For room database use only
                level_question = q19;
                updateLevelEachQuestionStatus(q19);
                break;
            case "q20":
                q20 = eachQuestStatusDECREASE(q20);
                r20 = 1;
                //For room database use only
                level_question = q20;
                updateLevelEachQuestionStatus(q20);
                break;
            case "q21":
                q21 = eachQuestStatusDECREASE(q21);
                r21 = 1;
                //For room database use only
                level_question = q21;
                updateLevelEachQuestionStatus(q21);
                break;
            case "q22":
                q22 = eachQuestStatusDECREASE(q22);
                r22 = 1;
                //For room database use only
                level_question = q22;
                updateLevelEachQuestionStatus(q22);
                break;
            case "q23":
                q23 = eachQuestStatusDECREASE(q23);
                r23 = 1;
                //For room database use only
                level_question = q23;
                updateLevelEachQuestionStatus(q23);
                break;
            case "q24":
                q24 = eachQuestStatusDECREASE(q24);
                r24 = 1;
                //For room database use only
                level_question = q24;
                updateLevelEachQuestionStatus(q24);
                break;
            case "q25":
                q25 = eachQuestStatusDECREASE(q25);
                r25 = 1;
                //For room database use only
                level_question = q25;
                updateLevelEachQuestionStatus(q25);
                break;
            case "q26":
                q26 = eachQuestStatusDECREASE(q26);
                r26 = 1;
                //For room database use only
                level_question = q26;
                updateLevelEachQuestionStatus(q26);
                break;
            case "q27":
                q27 = eachQuestStatusDECREASE(q27);
                r27 = 1;
                //For room database use only
                level_question = q27;
                updateLevelEachQuestionStatus(q27);
                break;
            case "q28":
                q28 = eachQuestStatusDECREASE(q28);
                r28 = 1;
                //For room database use only
                level_question = q28;
                updateLevelEachQuestionStatus(q28);
                break;
            case "q29":
                q29 = eachQuestStatusDECREASE(q29);
                r29 = 1;
                //For room database use only
                level_question = q29;
                updateLevelEachQuestionStatus(q29);
                break;
            case "q30":
                q30 = eachQuestStatusDECREASE(q30);
                r30 = 1;
                //For room database use only
                level_question = q30;
                updateLevelEachQuestionStatus(q30);
                break;
            case "q31":
                q31 = eachQuestStatusDECREASE(q31);
                r31 = 1;
                //For room database use only
                level_question = q31;
                updateLevelEachQuestionStatus(q31);
                break;
            case "q32":
                q32 = eachQuestStatusDECREASE(q32);
                r32 = 1;
                //For room database use only
                level_question = q32;
                updateLevelEachQuestionStatus(q32);
                break;
            case "q33":
                q33 = eachQuestStatusDECREASE(q33);
                r33 = 1;
                //For room database use only
                level_question = q33;
                updateLevelEachQuestionStatus(q33);
                break;
            case "q34":
                q34 = eachQuestStatusDECREASE(q34);
                r34 = 1;
                //For room database use only
                level_question = q34;
                updateLevelEachQuestionStatus(q34);
                break;
            case "q35":
                q35 = eachQuestStatusDECREASE(q35);
                r35 = 1;
                //For room database use only
                level_question = q35;
                updateLevelEachQuestionStatus(q35);
                break;
            case "q36":
                q36 = eachQuestStatusDECREASE(q36);
                r36 = 1;
                //For room database use only
                level_question = q36;
                updateLevelEachQuestionStatus(q36);
                break;
            case "q37":
                q37 = eachQuestStatusDECREASE(q37);
                r37 = 1;
                //For room database use only
                level_question = q37;
                updateLevelEachQuestionStatus(q37);
                break;
            case "q38":
                q38 = eachQuestStatusDECREASE(q38);
                r38 = 1;
                //For room database use only
                level_question = q38;
                updateLevelEachQuestionStatus(q38);
                break;
            case "q39":
                q39 = eachQuestStatusDECREASE(q39);
                r39 = 1;
                //For room database use only
                level_question = q39;
                updateLevelEachQuestionStatus(q39);
                break;
            case "q40":
                q40 = eachQuestStatusDECREASE(q40);
                r40 = 1;
                //For room database use only
                level_question = q40;
                updateLevelEachQuestionStatus(q40);
                break;
            case "q41":
                q41 = eachQuestStatusDECREASE(q41);
                updateLevelEachQuestionStatus(q41);
                break;

        }
    }

    public int eachQuestStatusDECREASE(int a) {
        if (a < 2) {
            return a;
        } else {
            a--;
            return a;
        }
    }

    public int levelDECREASE(int a) {
        if (a < 3) {
            return a;
        } else {
            a--;
            return a;
        }
    }

    public void idEachQuest(String a, String b, String c) {
        String id = "ID: " + a + "_" + b + "_" + c;
        mTxt_id.setText(id);
    }

    public void addQuestionDatabase() {
        String id = child_Name + "_" + mPost_key + "_" + questionN[mQuestNum - 1];

        Mcq_Q_entity mcq_q_entity = new Mcq_Q_entity();
        mcq_q_entity.setId(id);
        mcq_q_entity.setQ(q);
        mcq_q_entity.setO1(o1);
        mcq_q_entity.setO2(o2);
        mcq_q_entity.setO3(o3);
        mcq_q_entity.setO4(o4);
        mcq_q_entity.setAns(ans);
        mcq_q_entity.setE(e);
        mcq_q_entity.setTotal_N_Q(total_N_Q);
        mcq_q_entity.setLevel_cards(level_cards);
        mcq_q_entity.setLevel_question(level_question);

        mcq_viewModel.addMcq_q(mcq_q_entity);

        //Toast.makeText(getApplicationContext(), "successfully added to the database", Toast.LENGTH_SHORT).show();
    }

    public void readFromRoomDatabase() {


        String id = child_Name + "_" + mPost_key + "_" + questionN[mQuestNum - 1];
        final List<Mcq_Q_entity> readQfromDatabase = MainActivity.mcq_database.mcq_q_dao().find_quest_option(id);


        if (readQfromDatabase.isEmpty()) {
            updateQuestion();
        }

        if (!readQfromDatabase.isEmpty()) {
            for (final Mcq_Q_entity mqe : readQfromDatabase) {

                idEachQuest(child_Name, mPost_key, questionN[mQuestNum - 1]);

                String get_q = mqe.getQ();
                mTxt_quest.setText(get_q);
                String get_o1 = mqe.getO1();
                mChkOpt1.setText(get_o1);
                String get_o2 = mqe.getO2();
                mChkOpt2.setText(get_o2);
                String get_o3 = mqe.getO3();
                mChkOpt3.setText(get_o3);
                String get_o4 = mqe.getO4();
                mChkOpt4.setText(get_o4);


                totalQuestion = mqe.getTotal_N_Q();
                String special_id = child_Name + "_" + mPost_key + "_" + questionN[totalQuestion - 1];
                List<Mcq_Q_entity> read_level_fromDatabase = MainActivity.mcq_database.mcq_q_dao().find_quest_option(special_id);

                if (read_level_fromDatabase.isEmpty()) {
                    level_cards_offline = 0;
                    updateLevelStatus(level_cards_offline);
                }
                if (!read_level_fromDatabase.isEmpty()) {
                    for (Mcq_Q_entity mqe_level : read_level_fromDatabase) {
                        level_cards_offline = mqe_level.getLevel_cards();
                        updateLevelStatus(level_cards_offline);
                    }
                }

                //special only for this method
                level_offline = level_cards_offline;

                initialState_offline();

                //For firebase use only
                q = get_q;
                o1 = get_o1;
                o2 = get_o2;
                o3 = get_o3;
                o4 = get_o4;
                total_N_Q = totalQuestion;

                //Toast.makeText(getApplicationContext(), get_q + "\n" + get_o1 + "\n" + get_o2 + "\n" + get_o3 + "\n" + get_o4, Toast.LENGTH_SHORT).show();

                //selecting next previous and refresh
                next_previous_refresh_forRoomDatabase();


                mBtn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mNextQuest = "NEXT";
                        mBtn_submit.setText(mNextQuest);
                        mBtn_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Changing the text for submit, answer and explanation view
                                String mSubmit = "SUBMIT";
                                String mAnswer = "ANSWER";
                                String mExplanation = "Explanation will be here";
                                String mRightvWrong = "Check";

                                //set Background color for RIGHT v WRONG indicator Text box
                                mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_sub_design));

                                mBtn_submit.setText(mSubmit);
                                mTxt_ans.setText(mAnswer);
                                mTxt_expl.setText(mExplanation);
                                mTxt_RIGHTvWRONG.setText(mRightvWrong);

                                //Increase the question number
                                mQuestNum++;
                                eachQuestStatus();
                                initialState_offline();

                                uncheckOption();


                                if (mQuestNum > totalQuestion) {
                                    mQuestNum = 1;

                                    make_int_r_zero();
                                    notificationForTotalPoint();
                                    eachQuestStatus();
                                    readFromRoomDatabase();

                                    cycle++;
                                    totalPoint = 0;
                                    initialState_offline();
                                } else {

                                    readFromRoomDatabase();
                                }

                            }
                        });


                        checkOptionChoice();


                        String get_ans = mqe.getAns();
                        String get_e = mqe.getE();
                        mTxt_ans.setText(get_ans);
                        mTxt_expl.setText(get_e);

                        //For Firebase use only
                        ans = get_ans;
                        e = get_e;

                        if (ansChoice.equals(get_ans)) {
                            showResult = "Ans: " + get_ans;
                            mTxt_ans.setText(showResult);

                            mTxt_RIGHTvWRONG.setText("RIGHT!");
                            //Toast.makeText(McqQuestion.this, "Correct", Toast.LENGTH_SHORT).show();

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_green_transperant));

                            answeredQn = mQuestNum - 1;
                            //eachQuestStatusSUCCESS() is replaced with stopRepeatMarkCountSUCCESS() to stop repeat count
                            //eachQuestStatusSUCCESS();
                            stopRepeatMarkCountSUCCESS();

                            level_offline = levelINCREASE_offline(level_offline);
                            //For room database use only
                            level_cards = level_offline;

                            initialState_offline();
                            addQuestionDatabase();

                        } else if (ansChoice.equals("NoAnswerChoosen")) {
                            initialState_offline();
                            showResult = "Ans: " + get_ans;
                            mTxt_ans.setText(showResult);
                            mTxt_RIGHTvWRONG.setText("WRONG!");

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_red_transperant));

                            unAnsweredQN = mQuestNum - 1;

                            stopRepeatMarkCountFAILURE();
                            //eachQuestStatusFAILURE();

                            level_offline = levelDECREASE(level_offline);
                            //For room database use only
                            level_cards = level_offline;

                            addQuestionDatabase();

                        } else {
                            showResult = "Ans: " + get_ans;
                            mTxt_ans.setText(showResult);
                            mTxt_RIGHTvWRONG.setText("WRONG!");
                            //Toast.makeText(McqQuestion.this, "Wrong Answer, Question No.: " + questionN[unAnsweredQN], Toast.LENGTH_SHORT).show();

                            //set Background color for RIGHT v WRONG indicator Text box
                            mTxt_RIGHTvWRONG.setBackground(getResources().getDrawable(R.drawable.button_red_transperant));

                            initialState_offline();
                            unAnsweredQN = mQuestNum - 1;

                            stopRepeatMarkCountFAILURE();
                            //eachQuestStatusFAILURE();

                            level_offline = levelDECREASE(level_offline);

                            //For room database use only
                            level_cards = level_offline;

                            addQuestionDatabase();
                        }

                    }
                });

            }

        }


    }

    public void checkOptionChoice() {
        if (mChkOpt1.isChecked() && !mChkOpt2.isChecked() && !mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "a";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && mChkOpt2.isChecked() && !mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "b";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && !mChkOpt2.isChecked() && mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "c";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && !mChkOpt2.isChecked() && !mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "d";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && mChkOpt2.isChecked() && !mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "a,b";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && !mChkOpt2.isChecked() && mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "a,c";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && !mChkOpt2.isChecked() && !mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "a,d";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && mChkOpt2.isChecked() && mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "b,c";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && mChkOpt2.isChecked() && !mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "b,d";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && !mChkOpt2.isChecked() && mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "c,d";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && mChkOpt2.isChecked() && mChkOpt3.isChecked() && !mChkOpt4.isChecked()) {
            ansChoice = "a,b,c";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && mChkOpt2.isChecked() && !mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "a,b,d";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && !mChkOpt2.isChecked() && mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "a,c,d";
            uncheckOption();
        } else if (!mChkOpt1.isChecked() && mChkOpt2.isChecked() && mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "b,c,d";
            uncheckOption();
        } else if (mChkOpt1.isChecked() && mChkOpt2.isChecked() && mChkOpt3.isChecked() && mChkOpt4.isChecked()) {
            ansChoice = "a,b,c,d";
            uncheckOption();
        } else {
            ansChoice = "NoAnswerChoosen";
        }
    }

    public void next_previous_refresh_forRoomDatabase() {
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mSubmit = "SUBMIT";
                String mAnswer = "ANSWER";
                String mExplanation = "Explanation will be here";
                String chooseAnswer = "Check";
                mBtn_submit.setText(mSubmit);
                mTxt_ans.setText(mAnswer);
                mTxt_expl.setText(mExplanation);
                mTxt_RIGHTvWRONG.setText(chooseAnswer);

                updateQuestion();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLICKonNEXT_offline();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCLICKonPREV_offline();
            }
        });
    }

    public void onCLICKonNEXT_offline() {
        //Changing the text for submit, answer and explanation view
        String mSubmit = "SUBMIT";
        String mAnswer = "ANSWER";
        String mExplanation = "Explanation will be here";
        mBtn_submit.setText(mSubmit);
        mTxt_ans.setText(mAnswer);
        mTxt_expl.setText(mExplanation);

        //Increase the question number
        mQuestNum++;
        eachQuestStatus();
        initialState_offline();

        uncheckOption();


        if (mQuestNum > totalQuestion) {
            mQuestNum = 1;

            eachQuestStatus();
            readFromRoomDatabase();

            cycle++;
            totalPoint = 0;
            initialState_offline();
        } else {

            readFromRoomDatabase();
        }
    }

    public void onCLICKonPREV_offline() {
        //Changing the text for submit, answer and explanation view
        String mSubmit = "SUBMIT";
        String mAnswer = "ANSWER";
        String mExplanation = "Explanation will be here";
        mBtn_submit.setText(mSubmit);
        mTxt_ans.setText(mAnswer);
        mTxt_expl.setText(mExplanation);

        //Increase the question number

        if (cycle == 0 && mQuestNum == 1) {
            mQuestNum++;
        }


        initialState_offline();
        uncheckOption();


        if (mQuestNum == 1 && cycle != 0) {
            mQuestNum = totalQuestion;

            eachQuestStatus();
            readFromRoomDatabase();

            if (cycle > 0) {
                cycle--;
            }

            totalPoint = 0;
            initialState_offline();
        } else {

            mQuestNum--;
            eachQuestStatus();
            readFromRoomDatabase();
        }
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            super.onBackPressed();
        }
    }

    public void notificationForTotalPoint() {
        if (totalPoint == totalQuestion) {
            Toast.makeText(StudyMcqV2.this, "Congratulation, you got the highest mark!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(StudyMcqV2.this, "Total Point: " + totalPoint, Toast.LENGTH_LONG).show();
    }

    public void commonLevelStatus() {
        String extra_id = child_Name + "_" + mPost_key + "_" + questionN[totalQuestion - 1];
        List<Mcq_Q_entity> read_level_fromDatabase = MainActivity.mcq_database.mcq_q_dao().find_quest_option(extra_id);

        if (read_level_fromDatabase.isEmpty()) {
            level_cards_offline = 0;
            updateLevelStatus(level_cards_offline);
        }
        if (!read_level_fromDatabase.isEmpty()) {
            for (Mcq_Q_entity mqe_level : read_level_fromDatabase) {
                level_cards_offline = mqe_level.getLevel_cards();
                updateLevelStatus(level_cards_offline);
            }
        }
    }

    public void stopRepeatMarkCountSUCCESS() {
        switch (questionN[mQuestNum - 1]) {
            case "q1":
                if (r1 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r1 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q2":
                if (r2 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r2 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q3":
                if (r3 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r3 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q4":
                if (r4 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r4 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q5":
                if (r5 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r5 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q6":
                if (r6 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r6 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q7":
                if (r7 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r7 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q8":
                if (r8 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r8 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q9":
                if (r9 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r9 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q10":
                if (r10 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r10 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q11":
                if (r11 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r11 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q12":
                if (r12 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r12 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q13":
                if (r13 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r13 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q14":
                if (r14 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r14 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q15":
                if (r15 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r15 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q16":
                if (r16 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r16 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q17":
                if (r17 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r17 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q18":
                if (r18 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r18 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q19":
                if (r19 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r19 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q20":
                if (r20 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r20 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q21":
                if (r21 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r21 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q22":
                if (r22 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r22 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q23":
                if (r23 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r23 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q24":
                if (r24 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r24 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q25":
                if (r25 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r25 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q26":
                if (r26 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r26 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q27":
                if (r27 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r27 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q28":
                if (r28 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r28 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q29":
                if (r29 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r29 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q30":
                if (r30 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r30 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q31":
                if (r31 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r31 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q32":
                if (r32 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r32 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q33":
                if (r33 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r33 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q34":
                if (r34 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r34 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q35":
                if (r35 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r35 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q36":
                if (r36 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r36 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q37":
                if (r37 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r37 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q38":
                if (r38 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r38 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q39":
                if (r39 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r39 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q40":
                if (r40 == 0) {
                    totalPoint++;
                    eachQuestStatusSUCCESS();
                    break;
                }
                if (r40 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void stopRepeatMarkCountFAILURE() {
        switch (questionN[mQuestNum - 1]) {
            case "q1":
                if (r1 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r1 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q2":
                if (r2 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r2 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q3":
                if (r3 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r3 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q4":
                if (r4 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r4 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q5":
                if (r5 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r5 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q6":
                if (r6 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r6 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q7":
                if (r7 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r7 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q8":
                if (r8 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r8 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q9":
                if (r9 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r9 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q10":
                if (r10 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r10 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q11":
                if (r11 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r11 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q12":
                if (r12 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r12 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q13":
                if (r13 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r13 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q14":
                if (r14 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r14 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q15":
                if (r15 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r15 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q16":
                if (r16 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r16 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q17":
                if (r17 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r17 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q18":
                if (r18 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r18 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q19":
                if (r19 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r19 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q20":
                if (r20 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r20 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q21":
                if (r21 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r21 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q22":
                if (r22 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r22 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q23":
                if (r23 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r23 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q24":
                if (r24 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r24 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q25":
                if (r25 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r25 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q26":
                if (r26 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r26 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q27":
                if (r27 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r27 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q28":
                if (r28 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r28 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q29":
                if (r29 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r29 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q30":
                if (r30 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r30 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q31":
                if (r31 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r31 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q32":
                if (r32 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r32 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q33":
                if (r33 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r33 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q34":
                if (r34 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r34 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q35":
                if (r35 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r35 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q36":
                if (r36 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r36 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q37":
                if (r37 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r37 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q38":
                if (r38 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r38 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q39":
                if (r39 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r39 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
            case "q40":
                if (r40 == 0) {
                    eachQuestStatusFAILURE();
                    break;
                }
                if (r40 == 1) {
                    Toast.makeText(StudyMcqV2.this, "You already tried this question", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void make_int_r_zero() {
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r22 = 0;
        r23 = 0;
        r24 = 0;
        r25 = 0;
        r26 = 0;
        r27 = 0;
        r28 = 0;
        r29 = 0;
        r30 = 0;
        r31 = 0;
        r32 = 0;
        r33 = 0;
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 0;
        r38 = 0;
        r39 = 0;
        r40 = 0;
    }
}
