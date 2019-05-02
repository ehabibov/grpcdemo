package unit;

import client.Client;
import dataproviders.BalanceRangeProvider;
import dataproviders.PeopleIdProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import rules.ClientRule;
import rules.ServerRule;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import proto.Friend;
import proto.Person;
import proto.ServerHello;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(locations = {"classpath:server-test-config.xml", "classpath:client-test-config.xml"})
public class ServerTest {

    private static ServerRule serverRule = new ServerRule();
    private static ClientRule clientRule = new ClientRule();
    private static Client client;

    @ClassRule public static RuleChain chain = RuleChain.outerRule(new SpringClassRule()).around(serverRule).around(clientRule);

    //@ClassRule
    //public static RuleChain chain = RuleChain.outerRule(serverRule).around(clientRule);

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    //@Rule
    //public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @BeforeClass
    public static void setUp() {
        client = clientRule.getClientInstance();
    }

    @Test()
    public void testHello(){
        client = clientRule.getClientInstance();
        ServerHello hello = client.sendHello();
        assertEquals(hello.getServerMessage(), "Response: Hello!");
    }

    @Test
    @Parameters(source = BalanceRangeProvider.class, method = "provideBalanceRange")
    public void testBalanceRange(int lowThreshold, int highThreshold){
        ArrayList<Person> people = client.sendBalanceRequest(lowThreshold, highThreshold);

        assertEquals(people.size(), 10);
        assertEquals(people.get(0).getName(), "Dillon Herrera");
        assertEquals(people.get(1).getName(), "Workman Morgan");
        assertEquals(people.get(2).getName(), "Woodard Buck");
        assertEquals(people.get(3).getName(), "Mcgee Sanchez");
        assertEquals(people.get(4).getName(), "Davis Angelita");
        assertEquals(people.get(5).getName(), "Shepard Mccall");
        assertEquals(people.get(6).getName(), "Schmidt Weber");
        assertEquals(people.get(7).getName(), "Koch Marla");
        assertEquals(people.get(8).getName(), "Powell Delaney");
        assertEquals(people.get(9).getName(), "Barrera Corina");
    }

    @Test
    @Parameters(source = PeopleIdProvider.class, method = "providePair")
    public void testFriends(int[] array) throws Exception {
        ArrayList<Friend> friends = client.sendPeopleIndexes(array);

        assertEquals(friends.size(), 9);
        assertEquals(friends.get(0).getName(), "Velma Maynard");
        assertEquals(friends.get(1).getName(), "Shawn Townsend");
        assertEquals(friends.get(2).getName(), "Agnes Patterson");
        assertEquals(friends.get(3).getName(), "Downs Benson");
        assertEquals(friends.get(4).getName(), "Landry Harding");
        assertEquals(friends.get(5).getName(), "Lawrence Mullins");
        assertEquals(friends.get(6).getName(), "Jasmine Thompson");
        assertEquals(friends.get(7).getName(), "Boyd Bowen");
        assertEquals(friends.get(8).getName(), "Cathryn Allison");
    }
}