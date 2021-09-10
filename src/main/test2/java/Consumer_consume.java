package main.test2.java;

public class Consumer_consume extends Message {
    private Consumer receiver;
    private Actor sender;

    public Consumer_consume(Consumer receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.consume(sender);
    }
}
