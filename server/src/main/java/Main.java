import bindings.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rest.PersonRestClient;
import rpc.GrpcServer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        PersonRestClient client = (PersonRestClient) ctx.getBean("personRestClient");
        List<Person> persons = client.getPersons();

        for (Person person : persons){
            System.out.println(person.getName().toString());
        }

        GrpcServer server = (GrpcServer) ctx.getBean("server");
        server.startServer();
    }
}