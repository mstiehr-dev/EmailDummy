package dev.mstiehr.de.emaildummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EmailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(null!=getIntent())
            fillViewsFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent (Intent intent)
    {
        fillViewsFromIntent(intent);
    }

    private void fillViewsFromIntent(Intent intent)
    {
        TextView tvSubj = (TextView) findViewById(R.id.tv_subject);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        String subject = intent.getStringExtra(android.content.Intent.EXTRA_SUBJECT);
        String content = intent.getStringExtra(android.content.Intent.EXTRA_TEXT);

        tvSubj.setText(subject);
        tvContent.setText(content);
    }
}
