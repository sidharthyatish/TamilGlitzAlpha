package com.example.sidharthyatish.tamilglitzalpha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class TestReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reader);
        Intent intent=new Intent();
        String content=getIntent().getStringExtra("content");
        TextView tv= (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(Html.fromHtml(content.replaceAll("<img.+?>", "")));

    }
}
