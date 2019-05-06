package unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unit.tests.ServerTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ServerTest.class
})

public class ServerTestSuite { }