import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.FirstServiceGrpc;
import proto.Request;

import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    private ManagedChannel channel;
    private FirstServiceGrpc.FirstServiceBlockingStub blockingStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext());
    }

    public GrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        this.channel = channelBuilder.build();
        this.blockingStub = FirstServiceGrpc.newBlockingStub(channel).withWaitForReady();
        logger.info("Client started...");
    }

    public static void main(String[] args) {

        GrpcClient client = new GrpcClient(args[0], Integer.parseInt(args[1]));
        Request request = Request.newBuilder().setMyRequestMessage("Request: MESSAGE").build();
        logger.info(request.getMyRequestMessage());
        logger.info("Client terminated...");
    }
}
