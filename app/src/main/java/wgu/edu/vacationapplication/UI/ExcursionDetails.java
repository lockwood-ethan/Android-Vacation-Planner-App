package wgu.edu.vacationapplication.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.R;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    double price;
    int excursionID;
    int vacationId;
    EditText editName;
    EditText editPrice;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        editName = findViewById(R.id.excursiontitletext);
        editPrice = findViewById(R.id.excursionpricetext);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationId = getIntent().getIntExtra("vacationID", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        editName.setText(name);
        editPrice.setText(Double.toString(price));
        repository = new Repository(getApplication());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0) {
                    excursionID = 1;
                    excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
                    repository.insert(excursion);
                    this.finish();
                } else {
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
                    repository.insert(excursion);
                    this.finish();
                }
            } else {
                try {
                    excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
                    repository.update(excursion);
                    this.finish();
                } catch (Exception e) {
                    Log.e("Problem Updating", String.valueOf(e));
                }
            }
        }

        if (item.getItemId() == R.id.excursiondelete) {
            Excursion excursion;
            excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
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

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }
}