package main.test2.java;

public class Producer_produce extends Message {
    private Producer receiver;
    private Actor sender;

    public Producer_produce(Producer receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.produce(sender);
    }
}
