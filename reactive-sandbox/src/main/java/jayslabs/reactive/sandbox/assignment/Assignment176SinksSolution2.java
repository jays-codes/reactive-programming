package jayslabs.reactive.sandbox.assignment;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

class SlackMember {
    private final String name;
    private Consumer<String> msgConsumer;
    private static final Logger log = LoggerFactory.getLogger(SlackMember.class);

    public SlackMember(String name) {
        this.name = name;
    }

    public void send(String msg){
        this.msgConsumer.accept(msg);
    }

    void setMsgConsumer(Consumer<String> msgConsumer){
        this.msgConsumer = msgConsumer;
    }

    void receives(String msg){
        log.info(msg);
    }

    public String getName(){
        return name;
    }

}

record SlackMessage(String sender, String msg){
    private static final String MSG_TEMPLATE = "[%s -> %s] : %s";

    public String formatForDelivery(String receiver){
        return MSG_TEMPLATE.formatted(sender, receiver, msg);
    }
}

class ChannelRoom {
    private final String name;
    private final Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;

    private static final Logger log = LoggerFactory.getLogger(ChannelRoom.class);

    public ChannelRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = this.sink.asFlux();
    }

    //user subscribes to the flux and receives (formats) messages
    public void join(SlackMember member) {
        log.info("{} joined the room {}", member.getName(), this.name);

        this.flux
        .filter(sm -> !sm.sender().equals(member.getName()))
        .map(sm -> sm.formatForDelivery(member.getName()))
        .subscribe(member::receives);

        //user sets msgConsumer to emit messages to the sink
        member.setMsgConsumer(msg -> send(member.getName(), msg));
    }

    public void send(String sender, String msg){
        sink.tryEmitNext(new SlackMessage(sender, msg));
    }
}

public class Assignment176SinksSolution2 {

    public static void main(String[] args) {
        var room = new ChannelRoom("ostania");

        var anya = new SlackMember("anya");
        var becky = new SlackMember("becky");
        var bondo = new SlackMember("bondo");

        room.join(anya);
        room.join(becky);

        anya.send("hello");
        Util.sleepSeconds(4);

        becky.send("hi anya");
        anya.send("hello becky");
        Util.sleepSeconds(4);

        room.join(bondo);
        bondo.send("arf arf");

    }
}
