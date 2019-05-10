package dao;

import bindings.Person;
import context.AppContext;
import org.springframework.context.ApplicationContext;
import rest.PeopleRestClient;

import java.util.ArrayList;

public class PeopleDaoImpl implements PeopleDao {

    private ArrayList<Person> people;

    public PeopleDaoImpl() {
        ApplicationContext ctx = AppContext.getApplicationContext();
        PeopleRestClient peopleRestClient = (PeopleRestClient) ctx.getBean("personRestClient");
        this.people = peopleRestClient.getPeople();
    }

    @Override
    public ArrayList<Person> getAllPeople() {
        return this.people;
    }

}
