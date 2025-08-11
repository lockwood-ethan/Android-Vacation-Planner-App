package wgu.edu.vacationapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Callable;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class VacationDetails extends AppCompatActivity {
    String name;
    double price;
    int vacationId;
    EditText editName;
    EditText editPrice;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.titletext);
        editPrice = findViewById(R.id.pricetext);
        vacationId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        editName.setText(name);
        editPrice.setText(Double.toString(price));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationId);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Excursion> allExcursions = repository.getAssociatedExcursions(vacationId);
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(allExcursions);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;
            if (vacationId == -1) {
                if (repository.getmAllVacations().size() == 0) {
                    vacationId = 1;
                    vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.insert(vacation);
                    this.finish();
                } else {
                    vacationId = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.insert(vacation);
                    this.finish();
                }
            } else {
                try {
                    vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                    repository.update(vacation);
                    this.finish();
                } catch (Exception e) {
                    Log.e("Problem Updating", String.valueOf(e));
                }
            }
        }

        if (item.getItemId() == R.id.vacationdelete) {
            Vacation vacation;
            vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
            if (vacationId == -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VacationDetails.this);
                builder.setMessage("This vacation is not saved");
                builder.setTitle("Cannot Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if (repository.getAssociatedExcursions(vacationId).size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VacationDetails.this);
                builder.setMessage("This vacation has associated excursions");
                builder.setTitle("Cannot Delete");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                repository.delete(vacation);
                this.finish();
            }
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }
}