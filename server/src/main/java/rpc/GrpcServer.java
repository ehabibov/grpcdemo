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

    public void startServer(){
        try {
            server.start();
            logger.info("Server started...");
            server.awaitTermination();
            logger.info("Server terminated...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}