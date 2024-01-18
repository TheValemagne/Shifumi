package com.example.shifumi.p2p.handler;

import android.content.Intent;

import com.example.shifumi.MainActivity;

public abstract class P2pHandler {
    protected final MainActivity mainActivity;

    protected P2pHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setNextHandler(P2pHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    private P2pHandler nextHandler;

    public void handle(Intent intent) {
        if(this.nextHandler!=null){
            this.nextHandler.handle(intent);
        }
    }
}
