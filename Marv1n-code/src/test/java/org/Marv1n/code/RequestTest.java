package org.Marv1n.code;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class RequestTest {

    private Request request;
    private Request aDifferentRequest;
    private int NUMBER_OF_SEATS_NEEDED = 5;
    private int PRIORITY = 2;
    private UUID REQUESTER_UUID = UUID.randomUUID();

    @Before
    public void init(){
        request = new Request(NUMBER_OF_SEATS_NEEDED,PRIORITY,REQUESTER_UUID);
    }

    @Test
    public void givenRequest_WhenComparedToIdenticalRequest_ThenReturnTrue(){
        assertTrue(request.equals(request));
    }

    @Test
    public void givenRequest_WhenComparedToDifferentRequest_ThenReturnFalse(){
        aDifferentRequest = new Request(NUMBER_OF_SEATS_NEEDED,PRIORITY,UUID.randomUUID());
        assertFalse(request.equals(aDifferentRequest));
    }

    @Test
    public void givenRequest_WhenComparedToDifferentObject_ThenReturnFalse(){
        Integer aDifferentObject = new Integer(25);
        assertFalse(request.equals(aDifferentObject));
    }

    @Test
    public void givenRequest_WhenComparedToNull_ThenReturnFalse(){
        assertFalse(request.equals(null));
    }

}