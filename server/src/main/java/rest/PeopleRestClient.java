package rest;

import bindings.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.util.ResourceUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class PeopleRestClient extends AbstractRestClient {

    private static final Logger logger = Logger.getLogger(PeopleRestClient.class);

    public PeopleRestClient(String target, String path, String secretName, String secretValue, String file) {
        super(target, path, secretName, secretValue, file);
    }

    public ArrayList<Person> getPeople() {
        ArrayList<Person> people = null;

        Client client = ClientBuilder.newClient();
        Invocation.Builder builder = client
                .register(JacksonJsonProvider.class)
                .target(this.target)
                .path(this.path)
                .request(MediaType.APPLICATION_JSON)
                .header(this.secretName, this.secretValue);
        try {
            logger.info("Getting people data from resource: " + this.target.concat(this.path));
            people = builder.get(new GenericType<ArrayList<Person>>() {});
        } catch (Exception ex) {
            logger.warn("Resource " + this.target.concat(this.path) + " is not available. " + ex.getMessage());
            logger.info("Reading from local data file: " + this.file);
            ObjectMapper mapper = new ObjectMapper();
            try {
                people = mapper.readValue(
                        ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX.concat(this.file)),
                        new TypeReference<ArrayList<Person>>(){});
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        } finally {
            client.close();
        }
        return people;
    }
}