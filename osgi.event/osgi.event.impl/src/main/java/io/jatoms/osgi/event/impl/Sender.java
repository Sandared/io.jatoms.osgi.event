package io.jatoms.osgi.event.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

@Component
public class Sender {

    @Reference
    EventAdmin ea;

    @Activate
    void activate(){
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("thread", Thread.currentThread().getId());

        ea.sendEvent(new Event("io/jatoms/osgi/event/fancyevent", eventData));
        ea.postEvent(new Event("io/jatoms/osgi/event/fancyevent", eventData));
    }

}
