package com.example.shifumi.network;

import android.util.Log;

import com.example.shifumi.game.Choice;
import com.example.shifumi.network.manager.ClientRoundManager;
import com.example.shifumi.network.request.RequestEndgame;
import com.example.shifumi.network.request.RequestNextRound;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe pour la communication coté client / joueur
 */
public final class Client extends ClientBase {
    private static final String TAG = "Client";
    private final InetAddress groupOwnerAddress;
    private final ClientRoundManager clientRoundManager;
    private final SendObjectHandler sendObjectHandler;

    /**
     * Classe pour la communication coté client / joueur
     *
     * @param groupOwnerAddress  adresse de l'hôte de la partie
     * @param clientRoundManager gestionnaire de manche coté joueur
     * @throws IOException erreur lors de la création du socket
     */
    public Client(InetAddress groupOwnerAddress,
                  ClientRoundManager clientRoundManager) throws IOException {
        super(new Socket(groupOwnerAddress.getHostAddress(), Server.PORT));

        this.groupOwnerAddress = groupOwnerAddress;
        this.clientRoundManager = clientRoundManager;
        this.sendObjectHandler = new SendObjectHandler(this);
    }

    /**
     * Envoi de données au serveur
     *
     * @param object données à envoyer
     */
    public void send(Object object) {
        this.sendObjectHandler.send(object);
    }

    @Override
    public synchronized void start() {
        super.start();
        this.sendObjectHandler.start();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.sendObjectHandler.interrupt();
    }

    @Override
    public void run() {
        super.run();

        Log.d(TAG, String.format("Client connecté à l'adresse : %s, adresse client : %s", groupOwnerAddress, socket.getLocalAddress()));

        while (!this.isInterrupted()) {
            try {
                playRound(); // réalisation d'une manche de jeu

                Object nextResponse = this.incomingFlow.readObject(); // lecture des instruction pour la suite de la partie

                // gestion de l'après manche
                if (nextResponse instanceof RequestNextRound) {
                    clientRoundManager.onNext(); // passer à la prochaine manche
                } else if (nextResponse instanceof RequestEndgame) {
                    clientRoundManager.onEnd(); // fin de partie
                }

            } catch (SocketException | InterruptedException e) {
                Log.e(TAG, e.toString());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        Log.d(TAG, "Client déconnecté");
    }

    /**
     * Réalisation d'une manche coté joueur
     *
     * @throws InterruptedException   erreur liée à l'attente ou arrêt d'un thread
     * @throws IOException            erreur lors de l'envoi de données
     * @throws ClassNotFoundException erreur si classe inexistante
     */
    private void playRound() throws InterruptedException, IOException, ClassNotFoundException {
        synchronized (ownChoiceLock) {
            while (getOwnChoice().equals(Choice.UNSET)) { // attente de la validation du choix du joueur
                ownChoiceLock.wait();
            }

            this.outgoingFlow.writeObject(getOwnChoice()); // envoit du choix au serveur
        }

        Object response;

        do {
            response = this.incomingFlow.readObject(); // attente du choix de l'adversaire
        } while (!(response instanceof Choice)); // tant que l'objet obtenu n'est pas un choix

        setOpponentChoice((Choice) response); // actualisation du choix de l'adversaire

        clientRoundManager.onReceive(getOwnChoice(), getOpponentChoice()); // actualisation du score et affichage de l'écran de fin de manche
        this.resetChoices(); // réinitialisation des choix des deux joueurs
    }

}
