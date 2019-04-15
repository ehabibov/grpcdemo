import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.GrpcClient;


public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        GrpcClient client = (GrpcClient) ctx.getBean("client");
        client.sendHello();
        client.sendBalanceRequest(2000, 3000);
    }
}