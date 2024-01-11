package com.example.shifumi.p2p;

import android.util.Log;

import com.example.shifumi.network.Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.PriorityQueue;
import java.util.Queue;

public class SendObjectHandler extends Thread{
    private final ObjectOutputStream outgoingFlow;
    private final Queue<Object> toSend = new PriorityQueue<>();

    public SendObjectHandler(Client client) throws IOException {
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
                    Log.e("SendObjectHandler", "is interrupted");
                }
            }

        }
    }

    public void send(Object obj){
        synchronized (this) {
            toSend.add(obj);
            this.notifyAll();
            Log.d("SendObjectHandler", "Sended : " + obj.toString());
        }
    }

}
