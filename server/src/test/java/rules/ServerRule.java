package rules;

import io.grpc.testing.GrpcCleanupRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.GrpcServer;

public class ServerRule implements TestRule {

    private GrpcServer server;
    private GrpcCleanupRule cleanupRule;

    public ServerRule(GrpcCleanupRule cleanupRule) {
        this.cleanupRule = cleanupRule;
    }

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ApplicationContext ctx = new ClassPathXmlApplicationContext("server-test-config.xml");
                server = (GrpcServer) ctx.getBean("server");
                server.start();
                cleanupRule.register(server.getServerInstance());
                base.evaluate();
            }
        };
    }

    public GrpcServer getServerInstance(){
        return this.server;
    }
}