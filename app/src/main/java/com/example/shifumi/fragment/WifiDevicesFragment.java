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
import com.example.shifumi.placeholder.WifiDeviceContent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A fragment representing a list of Items.
 */
public class WifiDevicesFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public Collection<WifiP2pDevice> getPeers() {
        return peers;
    }

    private Collection<WifiP2pDevice> peers;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WifiDevicesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WifiDevicesFragment newInstance(int columnCount) {
        WifiDevicesFragment fragment = new WifiDevicesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateData(Collection<WifiP2pDevice> peers) {
        ViewSwitcher viewSwitcher = (ViewSwitcher) getView();
        switchView(viewSwitcher, peers.size());

        assert viewSwitcher != null;
        View displayedView = viewSwitcher.getCurrentView();

        if (!(displayedView instanceof RecyclerView)) {
            return;
        }

        RecyclerView recyclerView = (RecyclerView) displayedView;
        WifiPeerListRecyclerViewAdapter adapter = (WifiPeerListRecyclerViewAdapter) recyclerView.getAdapter();

        assert adapter != null;
        adapter.updateData(WifiDeviceContent.placeholderItemsMapper(peers));
        this.peers.clear();
        this.peers.addAll(peers);
    }

    private void switchView(ViewSwitcher viewSwitcher, int devicesCount) {
        if (devicesCount == 0 && viewSwitcher.getCurrentView().getId() == R.id.devices_list) {
            viewSwitcher.showNext();
            return;
        }

        if (devicesCount > 0 && viewSwitcher.getCurrentView().getId() == R.id.emptyBox) {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentWifiDeviceListBinding binding = FragmentWifiDeviceListBinding.inflate(inflater, container, false);
        ViewSwitcher view = binding.getRoot();

        binding.reloadButton.setOnClickListener(v -> ((MainActivity) requireActivity()).getPeerToPeerManager().discoverPeers());

        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(ViewSwitcher view) {
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = getRecyclerView(view);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        recyclerView.setAdapter(new WifiPeerListRecyclerViewAdapter(WifiDeviceContent.placeholderItemsMapper(peers),
                mainActivity.getPeerToPeerManager()));
    }

    private static RecyclerView getRecyclerView(ViewSwitcher viewSwitcher) {
        if (viewSwitcher.getCurrentView() instanceof RecyclerView) {
            return (RecyclerView) viewSwitcher.getCurrentView();
        }

        return (RecyclerView) viewSwitcher.getNextView();
    }
}