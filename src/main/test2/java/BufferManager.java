package main.test2.java;

public class BufferManager extends Actor {
    Producer producer;
    Consumer consumer;

    boolean empty;
    boolean full;
    boolean producerWaiting;
    boolean consumerWaiting;
    int bufferLength;
    int nextProduce;
    int nextConsume;

    public BufferManager(int queueSize){
        super(queueSize);
    }

    public void initial(){
        bufferLength = 5;
        empty = true;
        full = false;
        producerWaiting = false;
        consumerWaiting = false;
        nextProduce = 0;
        nextConsume = 0;
    }

    public void setKnownActors(Producer producer, Consumer consumer){
        this.producer = producer;
        this.consumer = consumer;
    }

    @Override
    public void send_giveMeNextProduce(Actor sender){
        this.send(new BufferManager_giveMeNextProduce(this, sender));
    }

    public void send_giveMeNextConsume(Actor sender){
        this.send(new BufferManager_giveMeNextConsume(this, sender));
    }

    public void send_ackProduce(Actor sender){
        this.send(new BufferManager_ackProduce(this, sender));
    }

    public void send_ackConsume(Actor sender){
        this.send(new BufferManager_ackConsume(this, sender));
    }

    public void giveMeNextProduce(Actor sender){
        System.out.println("give me next produce begins");
        if (!full)  {
            producer.send_produce(this);
        }
        else {
            producerWaiting = true;
        }
        System.out.println("give me next produce ends");
    }

    public void giveMeNextConsume(Actor sender) {
        System.out.println("give me next consume begins");
        if (!empty) {
            consumer.send_consume(this);
        } 
        else {
            consumerWaiting = true;
        }
        System.out.println("give me next consume ends");
    }
    
    public void ackProduce(Actor sender) {
        System.out.println("ack produce begins");
        nextProduce = (nextProduce + 1) % bufferLength;
        if (nextProduce == nextConsume) {
            full = true;
        }
        empty = false;
        if (consumerWaiting) {
            consumer.send_consume(this);
            consumerWaiting = false;
        }
        System.out.println("ack produce ends");
    }

    public void ackConsume(Actor sender) {
        System.out.println("ack consume begins");
        nextConsume = (nextConsume + 1) % bufferLength;
        if (nextConsume == nextProduce) {
            empty = true;
        }
        full = false;
        if (producerWaiting) {
            producer.send_produce(this);
            producerWaiting = false;
        }
        System.out.println("ack consume ends");
    }

}
