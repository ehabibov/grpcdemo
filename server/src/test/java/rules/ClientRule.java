package rules;

import io.grpc.testing.GrpcCleanupRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import client.Client;

public class ClientRule implements TestRule {

    private Client client;
    private GrpcCleanupRule cleanupRule;

    public ClientRule(GrpcCleanupRule cleanupRule) {
        this.cleanupRule = cleanupRule;
    }

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ApplicationContext ctx = new ClassPathXmlApplicationContext("client-test-config.xml");
                client = (Client) ctx.getBean("client");
                client.start();
                cleanupRule.register(client.getChannelInstance());
                base.evaluate();
            }
        };
    }

    public Client getClientInstance(){
        return this.client;
    }
}