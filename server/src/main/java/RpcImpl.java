import io.grpc.stub.StreamObserver;
import proto.GreatServiceGrpc;
import proto.Request;
import proto.Response;

public class RpcImpl extends GreatServiceGrpc.GreatServiceImplBase {

    @Override
    public void myRpcCall(Request request, StreamObserver<Response> responseObserver) {
        String message = "Response: ".concat(request.getMyRequestMessage());

        Response response = Response.newBuilder()
                .setMyResponseMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
