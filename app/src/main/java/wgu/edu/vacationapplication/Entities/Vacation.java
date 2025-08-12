package wgu.edu.vacationapplication.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String lodging;

    public Vacation(int vacationID, String vacationName, String lodging) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.lodging = lodging;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }
}
