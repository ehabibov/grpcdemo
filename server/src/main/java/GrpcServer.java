import io.grpc.Server;
import io.grpc.ServerBuilder;
import rpc.RpcImpl;

import java.io.IOException;
import java.util.logging.Logger;

public class GrpcServer {

    private static final Logger logger = Logger.getLogger(GrpcServer.class.getName());

    public static void main(String[] args) {
        Server server = ServerBuilder
                .forPort(Integer.parseInt(args[0]))
                .addService(new RpcImpl()).build();

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