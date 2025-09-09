package wgu.edu.vacationapplication.UI;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.edu.vacationapplication.Database.Repository;
import wgu.edu.vacationapplication.Entities.Excursion;
import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Vacation> mSearchResults;
    private final Context context;
    private final LayoutInflater mInflater;
    class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationNameItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;
        private final TextView excursionCountItemView;


        public ReportViewHolder(View itemView) {
            super(itemView);
            vacationNameItemView = itemView.findViewById(R.id.searchVacationName);
            startDateItemView = itemView.findViewById(R.id.searchStartDate);
            endDateItemView = itemView.findViewById(R.id.searchEndDate);
            excursionCountItemView = itemView.findViewById(R.id.excursionCountText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Vacation current = mSearchResults.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("lodging", current.getLodging());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("excursionCount", current.getExcursionCount());
                    context.startActivity(intent);
                }
            });
        }
    }
    public ReportAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if (mSearchResults != null) {
            Vacation current = mSearchResults.get(position);
            String name = current.getVacationName();
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            String excursionCount = String.valueOf(current.getExcursionCount());
            holder.vacationNameItemView.setText(name);
            holder.startDateItemView.setText(startDate);
            holder.endDateItemView.setText(endDate);
            holder.excursionCountItemView.setText(excursionCount);
        } else {
            holder.vacationNameItemView.setText("No vacation name");
            holder.startDateItemView.setText("No start date");
            holder.endDateItemView.setText("No end date");
            holder.excursionCountItemView.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        if (mSearchResults != null) {
            return mSearchResults.size();
        } else return 0;
    }

    public void setReportResults(List<Vacation> vacations) {
        mSearchResults = vacations;
        notifyDataSetChanged();
    }
}