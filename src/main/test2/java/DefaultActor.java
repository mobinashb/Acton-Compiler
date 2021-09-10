package main.test2.java;

public class DefaultActor extends Thread {
	public void send_giveMeNextProduce(Actor sender){
        System.out.println("there is no msghandler named giveMeNextProduce in sender");
    }

    public void send_giveMeNextConsume(Actor sender){
        System.out.println("there is no msghandler named giveMeNextConsume in sender");
    }

    public void send_ackProduce(Actor sender){
        System.out.println("there is no msghandler named ackProduce in sender");
    }

    public void send_ackConsume(Actor sender){
        System.out.println("there is no msghandler named ackConsume in sender");
    }

    public void send_produce(Actor sender){
        System.out.println("there is no msghandler named produce in sender");
    }

    public void send_beginProduce(Actor sender){
        System.out.println("there is no msghandler named beginProduce in sender");
    }

    public void send_consume(Actor sender){
        System.out.println("there is no msghandler named consume in sender");
    }

    public void send_beginConsume(Actor sender){
        System.out.println("there is no msghandler named beginConsume in sender");
    }
}