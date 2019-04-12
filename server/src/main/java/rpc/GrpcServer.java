package rpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    private Server server;

    public GrpcServer(int port, BindableService service) {
        this.server = ServerBuilder
                .forPort(port)
                .addService(service)
                .build();
    }

    public void start(){
        try {
            server.start();
            logger.info("Server started...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void blockUntilShutdown(){
        if (server != null) {
            try {
                server.awaitTermination();
                logger.info("Server terminated...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}