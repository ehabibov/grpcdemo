import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.GreatServiceGrpc;
import proto.Request;
import proto.Response;

import java.util.logging.Logger;

public class GrpcClient {
    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost",55000)
                .usePlaintext()
                .build();
        logger.info("Client started...");

        GreatServiceGrpc.GreatServiceBlockingStub stub = GreatServiceGrpc.newBlockingStub(channel);

        Response response = stub.myRpcCall(Request.newBuilder()
                .setMyRequestMessage("MESSAGE")
                .build()
        );
        logger.info(response.getMyResponseMessage());
        channel.shutdown();
        logger.info("Client terminated...");
    }


}
