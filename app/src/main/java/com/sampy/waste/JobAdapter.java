package com.sampy.waste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;

    public JobAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.txtAddress.setText(job.getAddress());
        String status = job.getStatus();

        holder.txtStatus.setText("Status: " + status);

// Change color based on status
        if (status.equals("Collected")) {
            holder.txtStatus.setTextColor(android.graphics.Color.GREEN);
        }
        else if (status.equals("Missed")) {
            holder.txtStatus.setTextColor(android.graphics.Color.RED);
        }
        else {
            holder.txtStatus.setTextColor(android.graphics.Color.parseColor("#FFA500")); // orange/yellow
        }
        holder.txtDriver.setText("Driver: " + job.getDriver());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView txtAddress, txtStatus, txtDriver;

        public JobViewHolder(View itemView) {
            super(itemView);

            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDriver = itemView.findViewById(R.id.txtDriver);
        }
    }
}