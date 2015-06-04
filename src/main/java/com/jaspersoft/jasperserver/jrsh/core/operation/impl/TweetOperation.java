package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import lombok.Data;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.SUCCESS;

@Data
@Master(name = "tweet")
public class TweetOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "tweet", values =
    @Value(tokenAlias = "TMES", tail = true))
    private String message;

    @Override
    public OperationResult eval(Session session) {
        //
        // Create builder
        //
        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey("1x10eRM9ksS26cEUIhkf8kC0b")
                .setOAuthConsumerSecret("hmrxfCzBdHZXvPxB0PzuzqZWKh2FPKWAMHu5YptzDYxIxTXYXr")
                .setOAuthAccessToken("3308360651-SvrWvqfLdoCNOxMtKbiw3d3opXYV05R4Ne2UiMA")
                .setOAuthAccessTokenSecret("uZoWCo57EjT5gSztXvDVfiOKn9ypZUXMZ7VrJIFrhnlsW");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Status status = null;
        try {
            status = twitter.updateStatus(message);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return new OperationResult("Successfully updated the status to [" + status.getText() + "].", SUCCESS, this, null);
    }
}
