package io.jatoms.osgi.event.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.propertytypes.EventTopics;

@Component(immediate = true)
@EventTopics("io/jatoms/osgi/event/fancyevent")
public class Receiver implements EventHandler{

	@Override
	public void handleEvent(Event event) {
        long threadID = (long) event.getProperty("thread");
        System.out.println("Sender thread ID is " + threadID);
        System.out.println("Receiver thread ID is " + Thread.currentThread().getId());
	}
}