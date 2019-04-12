import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.GrpcClient;


public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        GrpcClient client = (GrpcClient) ctx.getBean("client");
        client.sendRequest();
    }
}