package jayslabs.reactive.sandbox.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jayslabs.reactive.sandbox.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;



class SlackUser {
    private String name;
    public static Sinks.Many<String> sink;
    

    public SlackUser(String name) {
        this.name = name;
    }

    public void send(String msg){
        sink.tryEmitNext(msg);
    }

    public Flux<String> getFlux(){
        return sink.asFlux();
    }

    public String getName(){
        return name;
    }

}

record SlackRoom (String name){

    public static Sinks.Many<String> sink;
    private static final Logger log = LoggerFactory.getLogger(SlackRoom.class);

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
    }

    public void join(SlackUser user) {
        this.sink.asFlux().subscribe(Util.subscriber(user.getName()));
        user.sink = this.sink;
        log.info("{} joined the room {}", user.getName(), this.name);
        //user.getFlux().subscribe(Util.subscriber(user.getName()));
    }

    public void send(String msg){
        sink.tryEmitNext(msg);
    }
}

public class Assignment176Sinks {

    public static void main(String[] args) {
        var room = new SlackRoom("ostania");

        var anya = new SlackUser("anya");
        var becky = new SlackUser("becky");
        var bondo = new SlackUser("bondo");

        room.join(anya);
        room.join(becky);

        anya.send("hello");

        Util.sleepSeconds(4);

        anya.send("hello becky");
        becky.send("hi anya");
        
        Util.sleepSeconds(4);

        room.join(bondo);

        bondo.send("arf arf");

    }
}
