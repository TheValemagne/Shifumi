package com.example.shifumi;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shifumi.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.shifumi.databinding.FragmentWifiDeviceItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WifiPeerListRecyclerViewAdapter extends RecyclerView.Adapter<WifiPeerListRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public WifiPeerListRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentWifiDeviceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void updateData(List<PlaceholderItem> items) {
        this.mValues.clear();
        this.mValues.addAll(items);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentWifiDeviceItemBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}