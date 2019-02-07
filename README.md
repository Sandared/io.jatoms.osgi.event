# OSGi Event Starter

This repository is a showcase on how simple it is to send and receive events in OSGi. As usual it should you take 5 minutes or less to get it up and running... depending on how fast Maven is able to download the internet ;)

![GitpodEvents](https://github.com/Sandared/io.jatoms.osgi.event/blob/master/GitpodEvents.PNG)

## Run the application
* [![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/io.jatoms.osgi.event/blob/master/osgi.event/osgi.event.impl/src/main/java/io/jatoms/osgi/event/impl/Sender.java)
* Wait until Maven has finished downloading the internet
* type `run osgi.event.app/target/osgi.event.app.jar` in the terminal and watch the awesome console output ;)

## What's going on?
In OSGi it's super simple to create event publisher and consumer that eiter communicate synchonously or asynchronously without knowing each other. All you have to do is using `EventAdmin` within the sender and implement `EventHandler` within the receiver.
This is shown in `Sender` and `Receiver`.

### Sender 
`Sender` is a (immediate) component (annotated with `@Component`) and has a reference to the `EventAdmin` service that is provided and injected by the OSGi runtime, so you don't have to care where it comes from (maaagic). (Immediate) Components are instantiated by OSGi automatically and then their `@Activate` annotated method is called. Within this method we use the injected `EventAdmin` to send two `Event`s with the same data. The first String given to an `Event` is the topic under which it is published. Those topics are hierarchically organized. Actually instead of `io/jatoms/osgi/event/fancyevent` we also could have used just something simple as `test`.

### Receiver
`Receiver` is a (delayed) component too, implements the `EventHandler` interface and is annotated with `@EventTopics` which defines that this handler only wants events that are published under this topic. The `EventHandler` interface is also the "service" under which the receiver component is registered in the OSGi registry. This is done automatically for all `@Component` annotated classes that implement an interface. In the background the `EventAdmin` tells the OSGi registry to notify him for any `EventHandler` that is added to the registry. By this mechanism `EventAdmin` gets a reference to our implementation without the need for us to declare this anywhere explicitly. `EventAdmin` then calls our `handle` method each time an `Event` is published that we are interested in. The `handle` method is rather self-explanatory. 

### sendEvent vs. postEvent
In the console output should be something like
```
Sender thread ID is 1
Receiver thread ID is 1
Sender thread ID is 1
Receiver thread ID is 22
```
The numbers in your case might be different but show the same effect. The first Sender-Receiver combination results from the method call `sendEvent()` within `Sender`. This is the synchronous way to send messages to possible receivers. This means that when using `sendEvent` your thread will be used to do all the logic within the `handleEvent` methods of all handlers that are listening to your events. This might take quite some time in the worst case, but might be necessary for temporal ordering. 
`postEvent` instead is a fire and forget method that uses another thread to actually deliver the events to possible handlers. This might be fine when you don't care when events are delivered to your receivers.

### How to reproduce
To be honest I cheated a little bit when I said "OSGi events in 5 minutes or less". You didn't had to write any code to make this example work, aside from the `run osgi.event.app/target/osgi.event.app.jar` command which shouldn't have taken you too long to type ;)
So what would actually be necessary to reproduce this example in an empty workspace?
Well, assuming you have a workspace like in the [OSGi base example](https://github.com/Sandared/io.jatoms.osgi.base) where some bash aliases are defined for your workspace all you have to do is:
* open an empty OSGi base Gitpod workspace (maybe just fork the OSGi base example)
* type `projectbare` and fill out the information about groupId and artifactId and so on
* cd into your newly created project
* type `ds` and also fill out the information about groupId and artifactId
* type `app` and provide all the information needed
* Write the classes `Sender` and `Receiver` as they are written here.
* cd into your `ds` project
* type `mvn package`
* cd into your root project
* type `resolve <artifactId of your app project>`
* From there on you should be able to run the application as described above.
* (Instead of using `projectbare` and creating `ds` and `app` by yourself you could also just type `project`, but that will not ask you for the names of `ds` and `app`, but will name them `impl` and `app`)

## Further Reading
If you are now intrigued by the simplicity of sending/receiving events within OSGi and want to learn even more about this topic, then I highly recommend you to go to [Dirk Fauth's blog entry](http://blog.vogella.com/2017/05/16/osgi-event-admin-publish-subscribe/) about OSGi's EventAdmin.
If that's not enough information for then have a look at [the official specification](https://osgi.org/specification/osgi.cmpn/7.0.0/service.event.html)
