package dev.mstiehr.de.emaildummy.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Message
{
    @PrimaryKey private int uid;

    @ColumnInfo(name = "timestamp") private long timestamp;
    @ColumnInfo(name = "subject")   private String subject;
    @ColumnInfo(name = "content")   private String content;

    public int getUid ()
    {
        return uid;
    }

    public void setUid (int uid)
    {
        this.uid = uid;
    }

    public long getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (long timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }
}
