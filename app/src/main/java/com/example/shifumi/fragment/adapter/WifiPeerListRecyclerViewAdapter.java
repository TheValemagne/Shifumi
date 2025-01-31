package com.example.shifumi.fragment.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shifumi.p2p.PeerToPeerManager;
import com.example.shifumi.fragment.placeholder.WifiDeviceContent.WifiDeviceItem;
import com.example.shifumi.databinding.FragmentWifiDeviceItemBinding;

import java.util.List;

/**
 * Adaptater pour la liste d'appareils WiFi direct accessibles
 * {@link RecyclerView.Adapter} that can display a {@link WifiDeviceItem}.
 */
public final class WifiPeerListRecyclerViewAdapter extends RecyclerView.Adapter<WifiPeerListRecyclerViewAdapter.ViewHolder> {

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

    /**
     * Actualisation de la liste de donées
     *
     * @param items la nouvelle liste d'appareils wifi
     */
    public void updateData(List<WifiDeviceItem> items) {
        this.mValues.clear();
        this.mValues.addAll(items);
        this.notifyItemRangeChanged(0, items.size());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position).content);

        holder.itemView.setOnClickListener(v -> peerToPeerManager.connectToPeer(mValues.get(position).wifiP2pDevice.deviceAddress));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Element d'affichage de la WifiPeerList
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;

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