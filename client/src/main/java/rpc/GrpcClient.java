package rpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class);

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

    public ServerHello sendHello(){
        String requestMessage = "Request: Hi!";
        ClientHello request = ClientHello.newBuilder().setClientMessage(requestMessage).build();
        ServerHello response = this.blockingStub.greets(request);
        logger.info(request.getClientMessage());
        logger.info(response.getServerMessage());
        return response;
    }

    public ArrayList<Person> sendBalanceRequest(int lowThreshold, int highThreshold){
        BalanceRange request = BalanceRange.newBuilder()
                .setLowBalanceThreshold(lowThreshold)
                .setHighBalanceThreshold(highThreshold)
                .build();

        Iterator<Person> responseIterator = this.blockingStub.listPeople(request);

        String requestString = String.format("Stream request for people with balance range $%s-$%s sent",
                request.getLowBalanceThreshold(), request.getHighBalanceThreshold());
        logger.info(requestString);

        ArrayList<Person> people = new ArrayList<>();
        while (responseIterator.hasNext()){
            Person person = responseIterator.next();
            people.add(person);
            String responseString = String.format("Received streamed response: ID=[%d], name=[%s], age=[%d], email=[%s], company=[%s]",
                    person.getId(), person.getName(), person.getAge(), person.getEmail(), person.getCompany());
            logger.info(responseString);
        }
        return people;
    }


    public ArrayList<Friend> sendPeopleIndexes(int[] indexes) throws Exception {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        ArrayList<Friend> friends = new ArrayList<>();
        StreamObserver<PersonIndex> requestObserver = asyncStub.listFriends(new StreamObserver<Friend>() {

            @Override
            public void onNext(Friend friend) {
                friends.add(friend);
                String responseString = String.format("Received streamed response: Friend=[id=%d, name=%s, Person=[index=%d, name=%s]",
                        friend.getId(), friend.getName(),friend.getPersonId().getId(), friend.getPersonId().getName());
                logger.info(responseString);
            }

            @Override
            public void onError(Throwable throwable) {
                logger.warn("Error during receiving friends", throwable);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        });

        for (int index : indexes) {
            PersonIndex personIndex = PersonIndex.newBuilder().setIndex(index).build();
            logger.info("Looking friends of person with index: " + index);
            requestObserver.onNext(personIndex);
        }
        requestObserver.onCompleted();
        finishLatch.await(1, TimeUnit.MINUTES);
        return friends;
    }
}