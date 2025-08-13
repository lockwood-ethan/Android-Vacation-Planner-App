package wgu.edu.vacationapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    LocalDate currentDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusWeeks(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    String stringCurrentDate = currentDate.format(formatter);
    String stringEndDate = endDate.format(formatter);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        // System.out.println(getIntent().getStringExtra("test"));
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mysample) {
            repository = new Repository(getApplication());
            // Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation = new Vacation(0, "Cabo San Lucas", "AirBnB", stringCurrentDate , stringEndDate);
            repository.insert(vacation);
            vacation = new Vacation(0, "Bahamas", "Ritz Carlton", stringCurrentDate, stringEndDate);
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Swim with Dolphins", stringCurrentDate, 1);
            repository.insert(excursion);
            excursion = new Excursion(0, "Romantic Dinner Date", stringCurrentDate, 1);
            repository.insert(excursion);
            this.onResume();

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }
}