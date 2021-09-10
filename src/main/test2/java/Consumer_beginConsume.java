package main.test2.java;

public class Consumer_beginConsume extends Message {
    private Consumer receiver;
    private Actor sender;

    public Consumer_beginConsume(Consumer receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.beginConsume(sender);
    }
}
