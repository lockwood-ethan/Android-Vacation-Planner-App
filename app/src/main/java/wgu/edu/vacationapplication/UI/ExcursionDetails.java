package wgu.edu.vacationapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.R;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    String date;
    String vacationStartDate;
    String vacationEndDate;
    int excursionID;
    int vacationId;
    EditText editName;
    TextView editDate;
    TextView startDateText;
    TextView endDateText;
    Repository repository;
    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    LocalDate currentDate = LocalDate.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editName = findViewById(R.id.excursiontitletext);
        editDate = findViewById(R.id.excursiondatetext);
        startDateText = findViewById(R.id.startDate);
        endDateText = findViewById((R.id.endDate));

        excursionID = getIntent().getIntExtra("id", -1);
        vacationId = getIntent().getIntExtra("vacationID", -1);
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        vacationEndDate = getIntent().getStringExtra("vacationEndDate");
        editName.setText(name);
        if (date == null) {
            editDate.setText("Enter Date");
        } else {
            editDate.setText(date);
        }
        repository = new Repository(getApplication());
        startDateText.setText(vacationStartDate);
        endDateText.setText(vacationEndDate);

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) {
                    info = currentDate.toString();
                }
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ComponentName componentName = new ComponentName(this, SearchableActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            try {
                myCalendarStart.setTime(sdf.parse(vacationStartDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                myCalendarEnd.setTime(sdf.parse(vacationEndDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (myCalendar.before(myCalendarStart) || myCalendar.after(myCalendarEnd)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExcursionDetails.this);
                builder.setMessage("Excursion date must be between the vacation start and end dates");
                builder.setTitle("Invalid Date");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0) {
                    excursionID = 1;
                    excursion = new Excursion(excursionID, editName.getText().toString(), editDate.getText().toString(), vacationId, vacationStartDate, vacationEndDate);
                    repository.insert(excursion);
                    this.finish();
                } else {
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(excursionID, editName.getText().toString(), editDate.getText().toString(), vacationId, vacationStartDate, vacationEndDate);
                    repository.insert(excursion);
                    this.finish();
                }
            } else {
                try {
                    excursion = new Excursion(excursionID, editName.getText().toString(), editDate.getText().toString(), vacationId, vacationStartDate, vacationEndDate);
                    repository.update(excursion);
                    this.finish();
                } catch (Exception e) {
                    Log.e("Problem Updating", String.valueOf(e));
                }
            }
        }

        if (item.getItemId() == R.id.excursiondelete) {
            Excursion excursion;
            excursion = new Excursion(excursionID, editName.getText().toString(), editDate.getText().toString(), vacationId, vacationStartDate, vacationEndDate);
            if (excursionID == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExcursionDetails.this);
                builder.setMessage("This excursion is not saved");
                builder.setTitle("Cannot Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                repository.delete(excursion);
                this.finish();
            }
        }

        if (item.getItemId() == R.id.notify) {
            String date = editDate.getText().toString();
            Date myDate = null;
            try {
                myDate = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("notifyStart", editName.getText().toString() + " is today!");
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }
}