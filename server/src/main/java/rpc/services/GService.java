package rpc.services;

import io.grpc.stub.StreamObserver;
import proto.ClientHello;
import proto.GServiceGrpc.GServiceImplBase;
import proto.ServerHello;

import java.util.logging.Logger;

public class GService extends GServiceImplBase {
    private static final Logger logger = Logger.getLogger(GService.class.getName());

    @Override
    public void greets(ClientHello request, StreamObserver<ServerHello> responseObserver) {
        String responseMessage = "Response: Hello!";
        ServerHello response = ServerHello.newBuilder().setServerMessage(responseMessage).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("RPC call completed");
    }
}