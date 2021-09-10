package main.test2.java;

public class BufferManager_ackProduce extends Message {
    private BufferManager receiver;
    private Actor sender;

    public BufferManager_ackProduce(BufferManager receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.ackProduce(sender);
    }
}
