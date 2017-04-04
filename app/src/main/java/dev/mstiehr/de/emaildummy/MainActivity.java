package dev.mstiehr.de.emaildummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * todo: catch intent:  private void fireMail ()
    {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{CRASH_RECIPIENT});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                StringUtils.join(new String[]{CRASH_SUBJECT, exceptionType}, " | "));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, crashLog);
        // TODO: 11.10.2016 add log file
        final String logfilename =
                Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOWNLOADS
                        + File.separator + "logcat.txt";

        writeLogToFile(logfilename);    // create log file

        File file = new File(logfilename); // attach log file to email
        if (file.exists() && file.canRead())
        {
            final int max_len = getResources().getInteger(R.integer.max_logfile_size);
            if (file.length() > max_len || alignLogfile(file, max_len))
            {
                Uri uri = Uri.fromFile(file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            }
            else
            {
                crashLog += "\n\n\n *** could not attach logfile (" + file.length() + ") bytes) ***\n\n\n";
            }
        }

        startActivityForResult(emailIntent, CRASH_REQUEST);
    }
     *
     */
}
