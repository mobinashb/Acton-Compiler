package main.test2.java;

public class BufferManager_giveMeNextProduce extends Message {
    private BufferManager receiver;
    private Actor sender;

    public BufferManager_giveMeNextProduce(BufferManager receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.giveMeNextProduce(sender);
    }
}
