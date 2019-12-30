package net.alcuria.review.calc.dao;

import net.alcuria.review.calc.LeechSubject;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface LeechSubjectDao {

    @Query("SELECT * FROM LeechSubject")
    Flowable<List<LeechSubject>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<LeechSubject> subjects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(LeechSubject subject);

    @Query("DELETE FROM leechsubject")
    void deleteAll();

}
