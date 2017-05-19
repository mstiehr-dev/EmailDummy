package dev.mstiehr.de.emaildummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import dev.mstiehr.de.emaildummy.data.Message;

public class EmailActivity extends AppCompatActivity
{
    Message mMessage;
    AppDatabase db;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        db = App.getDb(this);

        if(null!=getIntent())
        {
            mMessage = createMessageFromIntent(getIntent());
            db.messageDao().insertAll(mMessage);

            showMessage(mMessage);
        }
    }

    @Override
    protected void onNewIntent (Intent intent)
    {
        mMessage = createMessageFromIntent(intent);
        showMessage(mMessage);
    }

    private Message createMessageFromIntent(Intent intent)
    {
        Message tMessage = new Message();
        tMessage.setTimestamp(System.currentTimeMillis());
        tMessage.setSubject(intent.getStringExtra(android.content.Intent.EXTRA_SUBJECT));
        tMessage.setContent(intent.getStringExtra(android.content.Intent.EXTRA_TEXT));

        return tMessage;
    }

    private void showMessage (Message message)
    {
        TextView tvSubj = (TextView) findViewById(R.id.tv_subject);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        String subject = message.getSubject();
        String content = message.getContent();

        tvSubj.setText(subject);
        tvContent.setText(content);
    }
}
