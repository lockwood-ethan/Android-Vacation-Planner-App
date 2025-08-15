package wgu.edu.vacationapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import wgu.edu.vacationapplication.DAO.ExcursionDAO;
import wgu.edu.vacationapplication.DAO.VacationDAO;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 6, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract ExcursionDAO excursionDAO();
    public abstract VacationDAO vacationDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
