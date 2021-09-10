package main.test2.java;

public class BufferManager_giveMeNextConsume extends Message {
    private BufferManager receiver;
    private Actor sender;

    public BufferManager_giveMeNextConsume(BufferManager receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.giveMeNextConsume(sender);
    }
}
