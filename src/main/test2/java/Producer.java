package main.test2.java;

public class Producer extends Actor {
    BufferManager buffer;

    public Producer(int queueSize){
        super(queueSize);
    }

    public void initial(){
        this.send_beginProduce(this);
    }

    public void setKnownActors(BufferManager buffer){
        this.buffer = buffer;
    }

    @Override
    public void send_produce(Actor sender){
        this.send(new Producer_produce(this, sender));
    }

    public void send_beginProduce(Actor sender){
        this.send(new Producer_beginProduce(this, sender));
    }

    public void produce(Actor sender) {
        System.out.println("producer is producing");
        buffer.send_ackProduce(this);
        this.send_beginProduce(this);
    }

    public void beginProduce(Actor sender) {
        buffer.send_giveMeNextProduce(this);
    }
}