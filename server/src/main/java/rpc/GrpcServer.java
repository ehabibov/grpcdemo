package rpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.inprocess.InProcessServerBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class);
    private Server server;

    public GrpcServer(int port, List<BindableService> services) {
        ServerBuilder serverBuilder = ServerBuilder
                .forPort(port);
        for (BindableService service : services){
            serverBuilder.addService(service);
        }
        this.server = serverBuilder.build();
    }

    public GrpcServer(String name, List<BindableService> services) {
        InProcessServerBuilder serverBuilder = InProcessServerBuilder
                .forName(name)
                .directExecutor();
        for (BindableService service : services){
            serverBuilder.addService(service);
        }
        this.server = serverBuilder.build();
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
                logger.info("Await termination...");
                server.awaitTermination(10, TimeUnit.SECONDS);
                logger.info("Server terminated...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Server getServerInstance(){
        return this.server;
    }
}