package rpc.services;

import bindings.Person;
import dao.PeopleDao;
import dao.PeopleDaoImpl;
import io.grpc.stub.StreamObserver;
import proto.*;
import proto.GServiceGrpc.GServiceImplBase;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.apache.log4j.Logger;

public class GService extends GServiceImplBase {

    private static final Logger logger = Logger.getLogger(GService.class);

    private PeopleDao peopleDao;

    public GService() {
        peopleDao = new PeopleDaoImpl();
    }

    @Override
    public void greets(ClientHello request, StreamObserver<ServerHello> responseObserver) {
        String responseMessage = "Response: Hello!";
        ServerHello response = ServerHello.newBuilder().setServerMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Greet RPC call completed");
    }

    @Override
    public void listPeople(BalanceRange request, StreamObserver<proto.Person> responseObserver) {
        for (bindings.Person personBinding : peopleDao.getAllPeople()){
            String balance = personBinding.getBalance();
            if (isInRange(convertBalance(balance), request)){
                proto.Person personProto = proto.Person.newBuilder()
                        .setId(personBinding.getIndex())
                        .setName(personBinding.getFullName())
                        .setAge(personBinding.getAge())
                        .setEmail(personBinding.getEmail())
                        .setAddress(personBinding.getAddress())
                        .setCompany(personBinding.getCompany())
                        .setBalance(balance)
                        .build();
                responseObserver.onNext(personProto);
                logger.info("Person info sent: " + personProto.getName());
            }
        }
        responseObserver.onCompleted();
        logger.info("List people by balance RPC streaming call completed");
    }


    @Override
    public StreamObserver<PersonIndex> listFriends(final StreamObserver<Friend> responseObserver) {
        return new StreamObserver<PersonIndex>() {

            @Override
            public void onNext(PersonIndex personIndex) {
                for (bindings.Person personBinding : peopleDao.getAllPeople()){
                    if (personBinding.getIndex() == personIndex.getIndex()){
                        for (Person.Friend friendBinding : personBinding.getFriends()){
                            proto.PersonID personId = proto.PersonID.newBuilder()
                                    .setId(personBinding.getIndex())
                                    .setName(personBinding.getFullName())
                                    .build();
                            proto.Friend friend = proto.Friend.newBuilder()
                                    .setPersonId(personId)
                                    .setName(friendBinding.getName())
                                    .setId(friendBinding.getId())
                                    .build();
                            responseObserver.onNext(friend);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.warn("Error during sending friends", throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    private static double convertBalance(String balance){
        Number convertedBalance = -1D;
        try {
            convertedBalance = NumberFormat.getCurrencyInstance(Locale.US).parse(balance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedBalance.doubleValue();
    }

    private static boolean isInRange(double balance, BalanceRange request){
        return balance >= request.getLowBalanceThreshold() &&
                balance < request.getHighBalanceThreshold();
    }

}