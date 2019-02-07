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
        String threadID = (String) event.getProperty("thread");
        System.out.println("Receiver thread ID is " + threadID);
	}
}