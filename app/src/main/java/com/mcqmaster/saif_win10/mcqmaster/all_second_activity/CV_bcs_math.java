package com.mcqmaster.saif_win10.mcqmaster.all_second_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcqmaster.saif_win10.mcqmaster.Parameter;
import com.mcqmaster.saif_win10.mcqmaster.R;
import com.mcqmaster.saif_win10.mcqmaster.StudyMCQ;
import com.mcqmaster.saif_win10.mcqmaster.StudyMcqV2;

public class CV_bcs_math extends AppCompatActivity {

    //For inferential add
    private InterstitialAd mInterstitialAd;

    private RecyclerView mRecycleContent;
    private DatabaseReference mDatabase;

    TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_bcs_bsp);

        //For all the add (Change app unit id later)
        MobileAds.initialize(this,
                "ca-app-pub-2522810443010389~4508857974");

        // Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713
        // My AdMob App ID: ca-app-pub-2522810443010389~4508857974

        //sample interstitial add unit id: ca-app-pub-3940256099942544/1033173712
        //my interstitial add unit id: ca-app-pub-2522810443010389/2863893374

        //For Interstitial Add (Change Interstitial add unit later)
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("bcs_math");
        mDatabase.keepSynced(false);
        mRecycleContent = findViewById(R.id.mRtopic);
        mRecycleContent.setHasFixedSize(true);
        mRecycleContent.setLayoutManager(new LinearLayoutManager(this));

        mHeader = findViewById(R.id.mHeader);
        mHeader.setText(getResources().getString(R.string.mathematics));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Parameter,ParameterViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Parameter, ParameterViewHolder>
                (Parameter.class,R.layout.mcq_topic_v2,ParameterViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(ParameterViewHolder viewHolder, Parameter model, int position) {


                final String post_key = getRef(position).getKey();
                viewHolder.setSource(model.getSource());
                viewHolder.setTopic(model.getTopic());
                viewHolder.setSum(model.getSum());
                viewHolder.setTotal(model.getTotal());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {

                            Intent intent = new Intent(CV_bcs_math.this, StudyMCQ.class);
                            intent.putExtra("key_name", post_key);
                            intent.putExtra("childName", "bcs_math");
                            Toast.makeText(CV_bcs_math.this, "Please make sure you turn off the rotation of your device", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                });

                mInterstitialAd.setAdListener(new AdListener()
                                              {
                                                  @Override
                                                  public void onAdClosed() {
                                                      Intent intent = new Intent(CV_bcs_math.this, StudyMCQ.class);
                                                      intent.putExtra("key_name", post_key);
                                                      intent.putExtra("childName", "bcs_math");
                                                      Toast.makeText(CV_bcs_math.this, "Please make sure you turn off the rotation of your device", Toast.LENGTH_LONG).show();

                                                      startActivity(intent);
                                                  }
                                              }
                );

            }
        };
        mRecycleContent.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ParameterViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public ParameterViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }
        public void setSource(String source)
        {
            TextView post_source = mView.findViewById(R.id.mTxt_source);
            post_source.setText(source);
        }
        public void setTopic(String topic)
        {
            TextView post_topic = mView.findViewById(R.id.mTxt_topic);
            post_topic.setText(topic);
        }
        public void setSum(String sum)
        {
            TextView post_sum = mView.findViewById(R.id.mTxt_sum);
            post_sum.setText(sum);
        }
        public void setTotal(String total)
        {
            TextView post_total = mView.findViewById(R.id.mTxt_total);
            post_total.setText(total);
        }
    }
}
