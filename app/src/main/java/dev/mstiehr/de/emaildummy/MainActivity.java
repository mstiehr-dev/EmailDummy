package dev.mstiehr.de.emaildummy;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RuntimePermissions
public class MainActivity extends AppCompatActivity
{
    final static boolean ATTACH_LOG_FILE = false;

    ListView mListView;
    List<String> subjects = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setEmptyView(findViewById(android.R.id.empty));
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects));

        ImageView iv = (ImageView) findViewById(android.R.id.empty);
        iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                MainActivityPermissionsDispatcher.openTestMailWithCheck(MainActivity.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    protected void openTestMail ()
    {
        String emailText = "this is the crashlog with userDetails";
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"m.stiehr@itc-ag.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                StringUtils.join(new String[]{"CRASH_SUBJECT", "exceptionType"}, " | "));

        //writeLogToFile(logFilePath);    // create log file

        String logFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "MDE_LOGS" + File.separator  + "2017-06-14-TransMeter-log.txt";

        File file = new File(logFilePath); // attach log file to email
        if (file.exists() && file.canRead())
        {
            if(ATTACH_LOG_FILE)
            {
                final int max_len = 1_024_000;
                if (file.length() < max_len || alignLogfile(file, max_len))
                {
                    Uri uri = FileProvider
                            .getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                    //                Uri uri = Uri.fromFile(file);
                    emailIntent.setDataAndType(uri, "text/plain");
                    emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            }
            else
            {
                // read file and add to content
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    StringBuilder sb = new StringBuilder((int)file.length());
                    sb.append(String.format("\n\n***** LOGFILE %s *****\n\n", file.getAbsoluteFile()));
                    while((line=br.readLine())!=null)
                    {
                        sb.append(line);
                    }
                    sb.append("\n\n");
                    emailText += sb.toString();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailText);

        startActivityForResult(emailIntent, 1337);
    }

    private boolean alignLogfile(File file, int max_len)
    {
        try
        {
            long start = file.length() - max_len; // keep the last 500kb
            byte[] buf = new byte[max_len];
            FileInputStream fis = new FileInputStream(file);
            int offset = 0;
            while(offset < start)
            {   // move access position (seek) and discard bytes
                offset += fis.read(buf, 0, (start - offset) < max_len ? (int) (start - offset) : max_len);
            }

            fis.read(buf, 0, max_len); // finally read important bytes

            FileOutputStream fos = new FileOutputStream(file); // overwrite logfile
            fos.write(buf);

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
