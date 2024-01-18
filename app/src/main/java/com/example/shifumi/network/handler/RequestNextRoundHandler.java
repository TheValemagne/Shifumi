package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;
import com.example.shifumi.network.request.RequestNextRound;

import java.io.IOException;

public class RequestNextRoundHandler extends RequestHandler{
    public RequestNextRoundHandler(Server server) {
        super(server);
    }

    @Override
    public void handle(Object object) throws IOException, InterruptedException {
        if (!(object instanceof RequestNextRound)) {
            super.handle(object);
            return;
        }

        server.sendToAll(new RequestNextRound());
    }
}
