# OSGi Event Starter

This repository is a showcase on how simple it is to send and receive events in OSGi. As usual it should you take 5 minutes or less to get it up and running... depending on how fast Maven is able to download the internet ;)

![GitpodEvents](TODO)

## Run the application
* [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/io.jatoms.osgi.event)
* Wait until Maven has finished downloading the internet
* type `run osgi.event.app/target/osgi.event.app.jar` in the terminal and watch the console output

## What's going on?
In OSGi it's super simple to create event publisher and consumer that eiter communicate synchonously or asynchronously without knowing each other. All you have to do is using `EventAdmin` within the sender and implement `EventHandler` within the receiver.
This is shown in `Sender` and `Receiver`.

### Sender 
`Sender` is a (immediate) component (annotated with `@Component`) and has a reference to the `EventAdmin` service that is provided and injected by the OSGi runtime, so you don't have to care where it comes from (maaagic). (Immediate) Components are instantiated by OSGi automatically and then their `@Activate` annotated method is called. Within this method we use the injected `EventAdmin` to send two `Event`s with the same data. The first String given to an `Event` is the topic under which it is published. Those topics are hierarchically organized. Actually instead of `io/jatoms/osgi/event/fancyevent` we also could have used just something simple as `test`.

### Receiver
`Receiver` is a (delayed) component too, implements the `EventHandler` interface and is annotated with `@EventTopics` which defines that this handler only wants events that are published under this topic. The `EventHandler` interface is also the "service" under which the receiver component is registered in the OSGi registry. This is done automatically for all `@Component` annotated classes that implement an interface. In the background the `EventAdmin` tells the OSGi registry to notify him for any `EventHandler` that is added to the registry. By this mechanism `EventAdmin` gets a reference to our implementation without the need for us to declare this anywhere explicitly. `EventAdmin` then calls our `handle` method each time an `Event` is published that we are interested in. The `handle` method is rather self-explanatory. 

### sendEvent vs. postEvent

