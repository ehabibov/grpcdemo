package rpc;

import io.grpc.stub.StreamObserver;
import proto.FirstServiceGrpc;
import proto.Request;
import proto.Response;

import java.util.logging.Logger;

public class RpcImpl extends FirstServiceGrpc.FirstServiceImplBase {
    private static final Logger logger = Logger.getLogger(RpcImpl.class.getName());

    @Override
    public void myRpcCall(Request request, StreamObserver<Response> responseObserver) {
        String message = "Response: MESSAGE";

        Response response = Response.newBuilder()
                .setMyResponseMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info(response.getMyResponseMessage());
    }
}
