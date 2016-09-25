package com.xhj.samples.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.widget.TextView;

import com.xhj.samples.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {
    @BindView(R.id.result_text)
    TextView mTextView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        Intent intent = getIntent();
//        mTextView = (TextView) findViewById(R.id.result_text);
        StringBuilder builder = new StringBuilder("当前登录账户: ");
        builder.append("\n")
                .append(intent.getStringExtra("userName"))
                .append("\n")
                .append("userId: ").append(intent.getStringExtra("userId"));
        mTextView.setText(builder.toString());
    }
}
