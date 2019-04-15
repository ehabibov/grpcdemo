package rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.*;

import java.util.Iterator;
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
        logger.info("Client started...");
    }

    public void sendHello(){
        String requestMessage = "Request: Hi!";
        ClientHello request = ClientHello.newBuilder().setClientMessage(requestMessage).build();
        ServerHello response = this.blockingStub.greets(request);
        logger.info(request.getClientMessage());
        logger.info(response.getServerMessage());
    }

    public void sendBalanceRequest(int lowThreshold, int highThreshold){
        BalanceRange request = BalanceRange.newBuilder()
                .setLowBalanceThreshold(lowThreshold)
                .setHighBalanceThreshold(highThreshold)
                .build();

        Iterator<Person> responseIterator = this.blockingStub.listPeople(request);

        String requestString = String.format("Stream request for people with balance range $%s-$%s sent",
                request.getLowBalanceThreshold(), request.getHighBalanceThreshold());
        logger.info(requestString);

        while (responseIterator.hasNext()){
            Person person = responseIterator.next();

            String responseString = String.format("Received response: ID=[%d], name=[%s], age=[%d], email=[%s], company=[%s]",
                    person.getId(), person.getName(), person.getAge(), person.getEmail(), person.getCompany());
            logger.info(responseString);
        }
    }
}
