package wgu.edu.vacationapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.edu.vacationapplication.Entities.Vacation;
import wgu.edu.vacationapplication.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Vacation> mSearchResults;
    private final Context context;
    private final LayoutInflater mInflater;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationNameItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            vacationNameItemView = itemView.findViewById(R.id.searchVacationName);
            startDateItemView = itemView.findViewById(R.id.searchStartDate);
            endDateItemView = itemView.findViewById(R.id.searchEndDate);
            if (vacationNameItemView == null) {
                throw new IllegalStateException("vacationNameItemView not found in the layout");
            }
            else if (startDateItemView == null) {
                throw new IllegalStateException("startDateItemView not found in the layout");
            }
            else if (endDateItemView == null) {
                throw new IllegalStateException("endDateItemView not found in the layout");
            }
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
                    context.startActivity(intent);
                }
            });
        }
    }
    public SearchAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.search_list_item, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        if (mSearchResults != null) {
            Vacation current = mSearchResults.get(position);
            String name = current.getVacationName();
            Log.println(Log.ASSERT, "Confirm variable set", name);
            String startDate = current.getStartDate();
            Log.println(Log.ASSERT, "Confirm variable set", startDate);
            String endDate = current.getEndDate();
            Log.println(Log.ASSERT, "Confirm variable set", endDate);
            holder.vacationNameItemView.setText(name);
            holder.startDateItemView.setText(startDate);
            holder.endDateItemView.setText(endDate);
        } else {
            holder.vacationNameItemView.setText("No vacation name");
            holder.startDateItemView.setText("No start date");
            holder.endDateItemView.setText("No end date");
        }
    }

    @Override
    public int getItemCount() {
        if (mSearchResults != null) {
            return mSearchResults.size();
        } else return 0;
    }

    public void setSearchResults(List<Vacation> vacations) {
        mSearchResults = vacations;
        notifyDataSetChanged();
    }
}
