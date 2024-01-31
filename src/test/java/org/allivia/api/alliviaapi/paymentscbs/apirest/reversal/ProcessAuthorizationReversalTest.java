package org.allivia.api.alliviaapi.paymentscbs.apirest.reversal;

import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import org.junit.Test;

public class ProcessAuthorizationReversalTest {
    @Test
    public void testRun() throws ConfigException, ApiException {
        ProcessAuthorizationReversal authorizationReversal=new ProcessAuthorizationReversal("1624472819952","6244728257726878404006","3.0","BOB7",null);

        authorizationReversal.start();
    }
}
