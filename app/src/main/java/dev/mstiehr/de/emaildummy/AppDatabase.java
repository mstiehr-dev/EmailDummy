package dev.mstiehr.de.emaildummy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import dev.mstiehr.de.emaildummy.data.Message;
import dev.mstiehr.de.emaildummy.data.MessageDao;

@Database(entities = {Message.class}, version=1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract MessageDao messageDao();
}
