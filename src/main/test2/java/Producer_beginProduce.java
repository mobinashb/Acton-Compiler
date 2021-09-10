package main.test2.java;

public class Producer_beginProduce extends Message {
    private Producer receiver;
    private Actor sender;

    public Producer_beginProduce(Producer receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.beginProduce(sender);
    }
}
