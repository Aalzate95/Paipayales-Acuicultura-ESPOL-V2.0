package com.paipayales.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paipayales.app.R;
import com.paipayales.app.db.entity.Pool;

import java.util.List;

public class PoolListAdapter extends RecyclerView.Adapter<PoolListAdapter.PoolViewHolder> {

    private final LayoutInflater mInflater;
    private List<Pool> mPools; // Cached copy of pools

    public PoolListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PoolViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoolViewHolder holder, int position) {
        if (mPools != null) {
            Pool current = mPools.get(position);
            holder.poolName.setText(current.name);
            holder.poolNumericCode.setText(current.numeric_code);
        } else {
            //holder.poolName.setText("No existen piscinas registradas.");
        }
    }

    public void setPools(List<Pool> pools) {
        mPools = pools;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPools != null) {
            return mPools.size();
        } else return 0;
    }

    class PoolViewHolder extends RecyclerView.ViewHolder {
        private final TextView poolName;
        private final TextView poolNumericCode;

        private PoolViewHolder(View itemView) {
            super(itemView);
            poolName = itemView.findViewById(R.id.tv_pool_name);
            poolNumericCode = itemView.findViewById(R.id.tv_numeric_code);
        }
    }
}
