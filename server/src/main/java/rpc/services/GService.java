package rpc.services;

import bindings.Person;
import context.AppContext;
import io.grpc.stub.StreamObserver;
import org.springframework.context.ApplicationContext;
import proto.BalanceRange;
import proto.ClientHello;
import proto.GServiceGrpc.GServiceImplBase;
import proto.ServerHello;
import rest.PeopleRestClient;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class GService extends GServiceImplBase {

    private static final Logger logger = Logger.getLogger(GService.class.getName());

    private ArrayList<Person> people;

    public GService() {
        ApplicationContext ctx = AppContext.getApplicationContext();
        PeopleRestClient peopleRestClient = (PeopleRestClient) ctx.getBean("personRestClient");
        this.people = peopleRestClient.getPeople();
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
        for (bindings.Person personBinding : this.people){
            String balance = personBinding.getBalance();
            if (isInRange(convertBalance(balance), request)){
                proto.Person personProto = proto.Person.newBuilder()
                        .setId(personBinding.getIndex())
                        .setName(personBinding.getFullName())
                        .setAge(personBinding.getAge())
                        .setEmail(personBinding.getEmail())
                        .setAddress(personBinding.getAddress())
                        .setCompany(personBinding.getCompany())
                        .build();
                responseObserver.onNext(personProto);
                logger.info("Person info sent: " + personProto.getName());
            }
        }
        responseObserver.onCompleted();
        logger.info("List people by balance RPC streaming call completed");
    }


    @Override
    public void listFriends(proto.Person request, StreamObserver<proto.Friend> responseObserver) {
        super.listFriends(request, responseObserver);
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