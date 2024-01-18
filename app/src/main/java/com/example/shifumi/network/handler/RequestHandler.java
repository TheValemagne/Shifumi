package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;

import java.io.IOException;

public abstract class RequestHandler {
    protected final Server server;

    public RequestHandler(Server server) {
        this.server = server;
    }

    public void setNextHandler(RequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    private RequestHandler nextHandler;

    public void handle(Object object) throws IOException, InterruptedException {
        if(this.nextHandler!=null){
            this.nextHandler.handle(object);
        }
    }
}
