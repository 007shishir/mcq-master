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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcqmaster.saif_win10.mcqmaster.Parameter;
import com.mcqmaster.saif_win10.mcqmaster.R;
import com.mcqmaster.saif_win10.mcqmaster.StudyMCQ;
import com.mcqmaster.saif_win10.mcqmaster.StudyMcqV2;

public class CV_bcs_bvs extends AppCompatActivity {

    private RecyclerView mRecycleContent;
    private DatabaseReference mDatabase;

    TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_bcs_bsp);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("bcs_bvs");
        mDatabase.keepSynced(false);
        mRecycleContent = findViewById(R.id.mRtopic);
        mRecycleContent.setHasFixedSize(true);
        mRecycleContent.setLayoutManager(new LinearLayoutManager(this));

        mHeader = findViewById(R.id.mHeader);
        mHeader.setText(getResources().getString(R.string.bvs));

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
                            Intent intent = new Intent(CV_bcs_bvs.this, StudyMcqV2.class);
                            intent.putExtra("key_name", post_key);
                            intent.putExtra("childName", "bcs_bvs");
                            Toast.makeText(CV_bcs_bvs.this, "Please make sure you turn off the rotation of your device", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                    }
                });

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
