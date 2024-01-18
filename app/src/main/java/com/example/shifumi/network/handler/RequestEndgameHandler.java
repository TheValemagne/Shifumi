package com.example.shifumi.network.handler;

import com.example.shifumi.network.Server;
import com.example.shifumi.network.request.RequestEndgame;

import java.io.IOException;

public class RequestEndgameHandler extends RequestHandler{
    public RequestEndgameHandler(Server server) {
        super(server);
    }

    @Override
    public void handle(Object object) throws IOException, InterruptedException {
        if (!(object instanceof RequestEndgame)) {
            super.handle(object);
            return;
        }

        server.sendToAll(new RequestEndgame());
    }
}
