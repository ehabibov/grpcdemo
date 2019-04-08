import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.FirstServiceGrpc;
import proto.Request;

import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(args[0],Integer.parseInt(args[1]))
                .usePlaintext()
                .build();
        logger.info("Client started...");

        FirstServiceGrpc.FirstServiceBlockingStub stub = FirstServiceGrpc.newBlockingStub(channel);

        Request request = Request.newBuilder().setMyRequestMessage("Request: MESSAGE").build();

        stub.myRpcCall(request);
        logger.info(request.getMyRequestMessage());

        channel.shutdown();
        logger.info("Client terminated...");
    }
}
