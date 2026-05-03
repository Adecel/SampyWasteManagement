package com.sampy.waste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private List<Client> clientList;

    public ClientAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.tvClientName.setText(client.name);
        holder.tvClientAddress.setText(client.address);
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView tvClientName, tvClientAddress;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvClientAddress = itemView.findViewById(R.id.tvClientAddress);
        }
    }
}
