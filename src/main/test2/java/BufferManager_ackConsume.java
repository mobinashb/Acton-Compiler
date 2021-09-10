package main.test2.java;

public class BufferManager_ackConsume extends Message {
    private BufferManager receiver;
    private Actor sender;

    public BufferManager_ackConsume(BufferManager receiver, Actor sender){
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public void execute() {
        receiver.ackConsume(sender);
    }
}