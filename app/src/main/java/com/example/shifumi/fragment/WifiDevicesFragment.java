package com.example.shifumi.fragment;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.databinding.FragmentWifiDeviceListBinding;
import com.example.shifumi.fragment.adapter.WifiPeerListRecyclerViewAdapter;
import com.example.shifumi.fragment.listener.ReloadButtonListener;
import com.example.shifumi.fragment.placeholder.WifiDeviceContent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A fragment representing a list of Items.
 */
public class WifiDevicesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private FragmentWifiDeviceListBinding binding;
    private WifiPeerListRecyclerViewAdapter adapter;

    public Collection<WifiP2pDevice> getPeers() {
        return peers;
    }

    private Collection<WifiP2pDevice> peers;

    public WifiDevicesFragment() {
        // Required empty public constructor
    }

    /**
     * Actualisation de la liste
     *
     * @param peers nouvelle liste d'appareils wifi direct
     */
    public void updateData(Collection<WifiP2pDevice> peers) {
        switchView(binding.switcher, peers.size());

        if (peers.isEmpty()) {
            return;
        }

        adapter.updateData(WifiDeviceContent.placeholderItemsMapper(peers));
        this.peers.clear();
        this.peers.addAll(peers);
    }

    /**
     * Changement de la vue principale affiché
     *
     * @param viewSwitcher l'élément viewSwitcher du fragment
     * @param devicesCount nombre d'appareilles wifi disponibles
     */
    private void switchView(ViewSwitcher viewSwitcher, int devicesCount) {
        if (devicesCount == 0 && viewSwitcher.getCurrentView().getId() == R.id.devices_list_scroll) {
            // Affichage de la page vide
            viewSwitcher.showNext();
            return;
        }

        if (devicesCount > 0 && viewSwitcher.getCurrentView().getId() == R.id.emptyBox) {
            // Affichage de la liste d'appareils
            viewSwitcher.showNext();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        peers = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWifiDeviceListBinding.inflate(inflater, container, false);

        binding.reloadButton.setOnClickListener(new ReloadButtonListener((MainActivity) requireActivity()));

        initRecyclerView(binding.devicesList);

        return binding.getRoot();
    }

    /**
     * Initialisation de la liste d'apapreils wifi disponibles
     *
     * @param recyclerView l'élément recyclerView de la page
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        // Set the adapter
        Context context = recyclerView.getContext();

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        adapter = new WifiPeerListRecyclerViewAdapter(
                WifiDeviceContent.placeholderItemsMapper(peers),
                mainActivity.getPeerToPeerManager());
        recyclerView.setAdapter(adapter);
    }

}