package rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.*;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    private ManagedChannel channel;
    private GServiceGrpc.GServiceBlockingStub blockingStub;
    private GServiceGrpc.GServiceStub asyncStub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext());
    }

    public GrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        this.channel = channelBuilder.build();
        this.blockingStub = GServiceGrpc.newBlockingStub(channel).withWaitForReady();
        this.asyncStub = GServiceGrpc.newStub(channel).withWaitForReady();
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


    public void sendPeopleIndexes(int[] indexes) throws Exception {
        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<PersonIndex> requestObserver = asyncStub.listFriends(new StreamObserver<Friend>() {

            @Override
            public void onNext(Friend friend) {
                String responseString = String.format("Friend=[id=%d, name=%s, Person=[index=%d, name=%s]",
                        friend.getId(), friend.getName(),friend.getPersonId().getId(), friend.getPersonId().getName());
                logger.info(responseString);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.log(Level.WARNING, "Error during receiving friends", throwable);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        });

        for (int index : indexes) {
            PersonIndex personIndex = PersonIndex.newBuilder().setIndex(index).build();
            requestObserver.onNext(personIndex);
            logger.info("Looking friends of person with index=" + index);
        }
        requestObserver.onCompleted();
        finishLatch.await(1, TimeUnit.MINUTES);
    }
}