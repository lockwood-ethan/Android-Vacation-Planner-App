package wgu.edu.vacationapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.edu.vacationapplication.Entities.Excursion;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID=:vaca ORDER BY excursionID ASC")
    List<Excursion> getAssociatedExcursions(int vaca);

    @Query("UPDATE VACATIONS SET excursionCount = excursionCount + 1 WHERE vacationID=:vaca")
    void increaseExcursionCount(int vaca);

    @Query("UPDATE VACATIONS SET excursionCount = excursionCount - 1 WHERE vacationID=:vaca")
    void decreaseExcursionCount(int vaca);
}
