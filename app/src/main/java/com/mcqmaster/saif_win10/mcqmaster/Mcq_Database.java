package com.mcqmaster.saif_win10.mcqmaster;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Mcq_Q_entity.class}, version = 1, exportSchema = false)
public abstract class Mcq_Database extends RoomDatabase
{
    public abstract Mcq_Q_Dao mcq_q_dao();

    private static volatile Mcq_Database INSTANCE;

    static Mcq_Database getDatabase (final Context context){
        if (INSTANCE == null) {
            synchronized (Mcq_Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Mcq_Database.class, "McqDb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


