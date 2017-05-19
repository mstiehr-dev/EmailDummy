package dev.mstiehr.de.emaildummy.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao
{
    @Query("SELECT * FROM MESSAGE")
    List<Message> getAll();

    @Query("SELECT * FROM MESSAGE WHERE SUBJECT = :subject")
    Message findBySubject(String subject);

    @Insert void insertAll(Message... messages);
    @Delete void delete(Message message);
}
