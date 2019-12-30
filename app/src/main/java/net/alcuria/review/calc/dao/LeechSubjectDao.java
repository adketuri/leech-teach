package net.alcuria.review.calc.dao;

import net.alcuria.review.calc.LeechSubject;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface LeechSubjectDao {

    @Query("SELECT * FROM leechsubject")
    Flowable<List<LeechSubject>> getAll();

    @Insert
    Completable insertAll(List<LeechSubject> subjects);

    @Query("DELETE FROM leechsubject")
    void deleteAll();

}
