package com.xhj.samples.activitiy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.widget.TextView;

import com.xhj.samples.R;

public class ResultActivity extends AppCompatActivity {
    private TextView mTextView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        Intent intent = getIntent();
        mTextView = (TextView) findViewById(R.id.result_text);
        StringBuilder builder = new StringBuilder("当前登录账户: ");
        builder.append("\n")
                .append(intent.getStringExtra("userName"))
                .append("\n")
                .append("userId: ").append(intent.getStringExtra("userId"));
        mTextView.setText(builder.toString());
    }
}
