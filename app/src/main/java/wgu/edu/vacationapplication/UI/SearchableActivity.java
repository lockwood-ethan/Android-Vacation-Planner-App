package wgu.edu.vacationapplication.UI;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class SearchableActivity extends AppCompatActivity {
    Repository repository;

    TextView vacationName;
    TextView startDateText;
    TextView endDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        handleIntent(intent);

    }
    public void doMySearch(String query) {
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        List<Vacation> searchResults = new ArrayList<>();
        for (Vacation vacation : allVacations) {
            if (query.equals(vacation.getVacationName())) {
                searchResults.add(vacation);
            }
        }
        RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
        final SearchAdapter searchAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter.setSearchResults(searchResults);
    }
    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }
}
