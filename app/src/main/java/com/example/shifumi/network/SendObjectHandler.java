package com.example.shifumi.network;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestion d'envoi de données
 */
public final class SendObjectHandler extends Thread{
    private static final String TAG = "SendObjectHandler";
    private final ObjectOutputStream outgoingFlow;
    private final Queue<Object> waitingQueue = new LinkedList<>();

    /**
     * Gestion d'envoi de données
     *
     * @param client client du joueur
     */
    public SendObjectHandler(Client client) {
        outgoingFlow = client.getOutgoingFlow();
    }
    public void run(){
        while(!this.isInterrupted()){
            synchronized (this) {
                while (waitingQueue.peek() != null){ // envoi des données dans la liste d'attente
                    try {
                        Log.d(TAG, "Sending : " + waitingQueue.peek());
                        outgoingFlow.writeObject(waitingQueue.remove());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                try { // attente de l'ajout d'une donné à transférer
                    this.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "is interrupted");
                }
            }

        }
    }

    /**
     * Envoi de données au serveur
     *
     * @param object données à envoyer
     */
    public void send(Object object){
        synchronized (this) {
            waitingQueue.add(object);
            this.notifyAll(); // notification de l'ajout d'une donné à envoyer
        }
    }

}
