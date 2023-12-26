package com.example.shifumi;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shifumi.p2p.PeerToPeerManager;
import com.example.shifumi.placeholder.WifiDeviceContent.WifiDeviceItem;
import com.example.shifumi.databinding.FragmentWifiDeviceItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WifiDeviceItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WifiPeerListRecyclerViewAdapter extends RecyclerView.Adapter<WifiPeerListRecyclerViewAdapter.ViewHolder> {

    private final List<WifiDeviceItem> mValues;
    private final PeerToPeerManager peerToPeerManager;

    public WifiPeerListRecyclerViewAdapter(List<WifiDeviceItem> items, PeerToPeerManager peerToPeerManager) {
        mValues = items;
        this.peerToPeerManager = peerToPeerManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentWifiDeviceItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void updateData(List<WifiDeviceItem> items) {
        this.mValues.clear();
        this.mValues.addAll(items);
        this.notifyItemRangeChanged(0, items.size());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        holder.itemView.setOnClickListener(v -> peerToPeerManager.connectToPeer(mValues.get(position).wifiP2pDevice.deviceAddress));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public WifiDeviceItem mItem;

        public ViewHolder(FragmentWifiDeviceItemBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}