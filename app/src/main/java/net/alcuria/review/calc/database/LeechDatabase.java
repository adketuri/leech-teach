package net.alcuria.review.calc.database;

import android.content.Context;

import net.alcuria.review.calc.LeechSubject;
import net.alcuria.review.calc.dao.LeechSubjectDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The database of the most recently fetched leeches.
 */
@Database(entities = {LeechSubject.class}, version = 1, exportSchema = false)
public abstract class LeechDatabase extends RoomDatabase {

    private static LeechDatabase instance;

    public abstract LeechSubjectDao leechSubject();

    public static void init(Context context){
        if (instance != null) {
            throw new IllegalStateException("Already initialized database. Access with get()");
        }
        instance = Room.databaseBuilder(context, LeechDatabase.class, "leech-db").build();
    }

    public static LeechDatabase getInstance(){
        if (instance == null){
            throw new IllegalStateException("Initialize first with init()");
        }
        return instance;
    }

}
