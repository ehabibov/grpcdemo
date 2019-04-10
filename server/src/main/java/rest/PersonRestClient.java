package rest;

import bindings.Person;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class PersonRestClient {

    private String target;
    private String path;
    private String secretName;
    private String secretValue;

    public PersonRestClient(String target, String path, String secretName, String secretValue) {
        this.target = target;
        this.path = path;
        this.secretName = secretName;
        this.secretValue = secretValue;
    }

    public List<Person> getPersons() {
        Client client = ClientBuilder.newClient();
        List<Person> persons = ClientBuilder.newClient()
                .register(JacksonJsonProvider.class)
                .target(this.target)
                .path(this.path)
                .request(MediaType.APPLICATION_JSON)
                .header(this.secretName, this.secretValue)
                .get(new GenericType<List<Person>>() {});

        client.close();
        return persons;
    }
}