package wgu.edu.vacationapplication.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class ReportActivity extends AppCompatActivity {
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.searchReportView);
        final ReportAdapter reportAdapter = new ReportAdapter(this);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter.setReportResults(allVacations);
    }
}