package com.example.shifumi.network;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Gestion d'envoi de données
 */
public final class SendObjectHandler extends Thread{
    private static final String TAG = "SendObjectHandler";
    private final ObjectOutputStream outgoingFlow;
    private final Queue<Object> toSend = new PriorityQueue<>();

    public SendObjectHandler(Client client) {
        outgoingFlow = client.getOutgoingFlow();
    }
    public void run(){
        while(!this.isInterrupted()){
            synchronized (this) {
                if(toSend.peek()!=null){
                    try {
                        outgoingFlow.writeObject(toSend.remove());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    continue;
                }
                try {
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
            toSend.add(object);
            this.notifyAll();
            Log.d(TAG, "Sended : " + object.toString());
        }
    }

}
