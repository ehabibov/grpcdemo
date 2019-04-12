import bindings.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rest.PersonRestClient;
import rpc.GrpcServer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        PersonRestClient restClient = (PersonRestClient) ctx.getBean("personRestClient");
        GrpcServer server = (GrpcServer) ctx.getBean("server");
        List<Person> persons = restClient.getPersons();

        for (Person person : persons){
            System.out.println(person.toString());
        }

        server.start();
        server.blockUntilShutdown();
    }
}