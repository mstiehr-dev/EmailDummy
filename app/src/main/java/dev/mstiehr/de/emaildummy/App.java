package dev.mstiehr.de.emaildummy;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

public class App extends Application
{

    AppDatabase db;

    @Override
    public void onCreate ()
    {
        super.onCreate();

//        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db-messages").build();
    }

    public static AppDatabase getDb (Context context)
    {
        return ((App) context.getApplicationContext()).db;
    }
}
