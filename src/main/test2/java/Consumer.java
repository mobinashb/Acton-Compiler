package main.test2.java;

public class Consumer extends Actor {
    BufferManager buffer;

    public Consumer(int queueSize){
        super(queueSize);
    }

    public void initial(){
        this.send_beginConsume(this);
    }

    public void setKnownActors(BufferManager buffer){
        this.buffer = buffer;
    }

    @Override
    public void send_consume(Actor sender){
        this.send(new Consumer_consume(this, sender));
    }

    public void send_beginConsume(Actor sender){
        this.send(new Consumer_beginConsume(this, sender));
    }

    public void consume(Actor sender) {
        System.out.println("consumer is consuming");
        buffer.send_ackConsume(this);
        this.send_beginConsume(this);
    }

    public void beginConsume(Actor sender) {
        buffer.send_giveMeNextConsume(this);
    }
}