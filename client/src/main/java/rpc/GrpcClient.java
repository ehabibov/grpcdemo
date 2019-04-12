package rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.ClientHello;
import proto.GServiceGrpc;
import proto.ServerHello;

import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    private ManagedChannel channel;
    private GServiceGrpc.GServiceBlockingStub blockingStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext());
    }

    public GrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        this.channel = channelBuilder.build();
        this.blockingStub = GServiceGrpc.newBlockingStub(channel).withWaitForReady();
        this.blockingStub.getCallOptions().isWaitForReady();
        logger.info("Client started...");
    }

    public void sendRequest(){
        String requestMessage = "Request: Hi!";
        ClientHello request = ClientHello.newBuilder().setClientMessage(requestMessage).build();
        ServerHello response = this.blockingStub.greets(request);
        logger.info(request.getClientMessage());
        logger.info(response.getServerMessage());
        logger.info("Client terminated...");
    }
}
