import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.GrpcServer;

public class ServerMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        GrpcServer server = (GrpcServer) ctx.getBean("server");

        server.start();
        server.blockUntilShutdown();
    }
}