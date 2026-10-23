package com.tahirsallie.smartpantry.ui;

import android.graphics.Color;
import com.tahirsallie.smartpantry.logic.ExpiryUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tahirsallie.smartpantry.R;
import com.tahirsallie.smartpantry.model.PantryItem;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private List<PantryItem> items;
    private final OnItemActionListener listener;

    private boolean highlightExpiring = true;

    public void setHighlightExpiring(boolean highlightExpiring) {
        this.highlightExpiring = highlightExpiring;
    }

    public PantryAdapter(List<PantryItem> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    /** Replaces the data and redraws the list. */
    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = items.get(position);

        holder.name.setText(item.getName());

        // Show 2 instead of 2.0 for whole numbers
        double q = item.getQuantity();
        String qtyText = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
        holder.quantity.setText(qtyText + " " + item.getUnit());

        String expiry = item.getExpiryDate();
        if (expiry == null || expiry.trim().isEmpty()) {
            holder.expiry.setVisibility(View.GONE);
        } else {
            holder.expiry.setVisibility(View.VISIBLE);
            String text = "Expires: " + expiry;
            int color = holder.defaultExpiryColor;      // reset: rows are recycled

            Integer days = highlightExpiring ? ExpiryUtils.daysUntilExpiry(expiry) : null;
            if (days != null) {
                if (days < 0) {
                    text = "Expired (" + expiry + ")";
                    color = Color.parseColor("#C62828");              // red
                } else if (days <= ExpiryUtils.SOON_DAYS) {
                    text = (days == 0) ? "Expires today"
                            : "Expires in " + days + (days == 1 ? " day" : " days");
                    color = Color.parseColor("#E65100");              // orange
                }
            }
            holder.expiry.setText(text);
            holder.expiry.setTextColor(color);
        }

        holder.itemView.setOnClickListener(v -> listener.onEdit(item));
        holder.delete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        final TextView name, quantity, expiry;
        final ImageButton delete;
        final int defaultExpiryColor;

        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            quantity = itemView.findViewById(R.id.text_quantity);
            expiry = itemView.findViewById(R.id.text_expiry);
            delete = itemView.findViewById(R.id.button_delete);
            defaultExpiryColor = expiry.getCurrentTextColor();
        }
    }
    }

