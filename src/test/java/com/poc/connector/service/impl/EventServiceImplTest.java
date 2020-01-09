package com.poc.connector.service.impl;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.model.request.EventRequest;
import com.poc.connector.model.response.Actor;
import com.poc.connector.model.response.Event;
import com.poc.connector.model.response.LoginSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

    @Mock
    private OktaConfiguration oktaConfiguration;

    @InjectMocks
    @Spy
    private EventServiceImpl eventService;

    private Event getEventMock(String userEmail) {
        Actor target = new Actor("", "", userEmail, "", "");
        return new Event("", "", "", ZonedDateTime.now(), null, null, Arrays.asList(target));
    }

    private List<Event> getEventsMockData() {
        List<Event> events = new ArrayList<>();
        events.add(getEventMock("testuser1@domain.com"));
        events.add(getEventMock("testuser2@domain.com"));
        events.add(getEventMock("testuser3@domain.com"));
        events.add(getEventMock("testuser1@domain.com"));
        events.add(getEventMock("testuser2@domain.com"));
        events.add(getEventMock("testuser1@domain.com"));
        events.add(getEventMock("testuser1@domain.com"));
        return events;
    }

    @Test
    public void getLoginSuccessSummary() {
        try {
            Mockito.doReturn(getEventsMockData()).when(eventService).getEvents(isA(EventRequest.class));
            List<LoginSummary> loginSummaries = eventService.getLoginSuccessSummary(new EventRequest());
            System.out.println(loginSummaries);
            Assert.assertEquals(loginSummaries.size(), 3);
            Long testUser2Count = loginSummaries.stream().filter(loginSummary -> loginSummary.getEmail().equalsIgnoreCase("testuser2@domain.com")).findFirst().get().getCount();
            Assert.assertEquals(testUser2Count.longValue(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}