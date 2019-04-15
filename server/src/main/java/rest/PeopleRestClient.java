package rest;

import bindings.Person;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

public class PeopleRestClient extends AbstractRestClient{

    public PeopleRestClient(String target, String path, String secretName, String secretValue) {
        super(target, path, secretName, secretValue);
    }

    public ArrayList<Person> getPeople() {
        Client client = ClientBuilder.newClient();
        ArrayList<Person> persons = ClientBuilder.newClient()
                .register(JacksonJsonProvider.class)
                .target(this.target)
                .path(this.path)
                .request(MediaType.APPLICATION_JSON)
                .header(this.secretName, this.secretValue)
                .get(new GenericType<ArrayList<Person>>() {});
        client.close();
        return persons;
    }
}