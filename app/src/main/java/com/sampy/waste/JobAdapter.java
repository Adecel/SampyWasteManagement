package com.sampy.waste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobList;

    public JobAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);

        holder.txtAddress.setText(job.getAddress());
        holder.txtWasteType.setText(job.getWasteType());
        holder.txtDateTime.setText(job.getDateTime());
        holder.txtDriver.setText("Collected by: " + job.getDriver());
        holder.txtStatusBadge.setText(job.getStatus());

        updateStatusUI(holder, job.getStatus());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    private void updateStatusUI(JobViewHolder holder, String status) {
        if ("Collected".equalsIgnoreCase(status) || "Completed".equalsIgnoreCase(status)) {
            holder.txtStatusBadge.setTextColor(android.graphics.Color.parseColor("#2E7D32"));
            holder.txtStatusBadge.getBackground().setTint(android.graphics.Color.parseColor("#E8F5E9"));
        } else if ("Missed".equalsIgnoreCase(status)) {
            holder.txtStatusBadge.setTextColor(android.graphics.Color.RED);
            holder.txtStatusBadge.getBackground().setTint(android.graphics.Color.parseColor("#FFEBEE"));
        } else {
            holder.txtStatusBadge.setTextColor(android.graphics.Color.parseColor("#E65100"));
            holder.txtStatusBadge.getBackground().setTint(android.graphics.Color.parseColor("#FFF3E0"));
        }
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress, txtWasteType, txtDateTime, txtDriver, txtStatusBadge;

        public JobViewHolder(View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtWasteType = itemView.findViewById(R.id.txtWasteType);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtDriver = itemView.findViewById(R.id.txtDriver);
            txtStatusBadge = itemView.findViewById(R.id.txtStatusBadge);
        }
    }
}
