package rest;

public class AbstractRestClient {

    protected String target;
    protected String path;
    protected String secretName;
    protected String secretValue;

    public AbstractRestClient() { }

    public AbstractRestClient(String target, String path, String secretName, String secretValue) {
        this.target = target;
        this.path = path;
        this.secretName = secretName;
        this.secretValue = secretValue;
    }
}