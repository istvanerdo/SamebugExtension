package org.igorski;

import org.igorski.clients.SamebugClient;
import org.igorski.model.CrashResponse;
import org.igorski.model.SamebugRequest;
import org.igorski.services.SamebugRequestFactory;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.UriBuilder;

public class SamebugExtensionTest {

    private static final String ENDPOINT = "https://nightly.samebug.com/rest";

    @Test
    public void shouldPrintException() {

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget webTarget = client.target(UriBuilder.fromPath(ENDPOINT));
        SamebugClient samebugClient = webTarget.proxy(SamebugClient.class);

        try {
            throw new Exception("New exception");
        } catch (Exception e) {
            SamebugRequestFactory samebugRequestFactory = new SamebugRequestFactory();
            SamebugRequest samebugRequest = samebugRequestFactory.createInstance(e);

            CrashResponse crashResponse = samebugClient.getCrashReport(samebugRequest);
            System.out.println(crashResponse.getData().getId());
        }
    }
}