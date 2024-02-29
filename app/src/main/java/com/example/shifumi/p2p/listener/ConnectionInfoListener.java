package com.example.shifumi.p2p.listener;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.shifumi.MainActivity;
import com.example.shifumi.R;
import com.example.shifumi.fragment.PlayFragment;
import com.example.shifumi.network.InitClientRunnable;
import com.example.shifumi.p2p.PeerToPeerManager;

/**
 * Ecouteur pour les informations de connexion
 */
public class ConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener{
    private final MainActivity mainActivity;
    private final PeerToPeerManager peerToPeerManager;

    /**
     * Ecouteur pour les informations de connexion
     *
     * @param mainActivity  activité principale
     * @param peerToPeerManager gestionnaire des échanges WiFi direct
     */
    public ConnectionInfoListener(MainActivity mainActivity, PeerToPeerManager peerToPeerManager) {
        this.mainActivity = mainActivity;
        this.peerToPeerManager = peerToPeerManager;
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (!info.groupFormed){
            return;
        }

        if(info.isGroupOwner) { // action supplémentaire coté serveur
            Log.d("Serveur", "Addresse groupOwner " + info.groupOwnerAddress.toString());
            peerToPeerManager.startServer();
        } else { // coté client
            Log.d("Client", "Address groupOwner " + info.groupOwnerAddress.toString());
        }

        // initialisation du client du joueur
        Thread thread = new Thread(new InitClientRunnable(info.groupOwnerAddress, mainActivity));
        thread.start();

        Toast.makeText(mainActivity, R.string.connectionSuccessful, Toast.LENGTH_LONG).show();

        // affichage de l'écran de jeu
        mainActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new PlayFragment())
                .commit();
    }
}
