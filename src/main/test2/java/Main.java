package main.test2.java;

public class Main {
    public static void main(String[] args){
        BufferManager buffermanager = new BufferManager(4);
        Producer producer = new Producer(2);
        Consumer consumer = new Consumer(2);

        buffermanager.setKnownActors(producer, consumer);
        producer.setKnownActors(buffermanager);
        consumer.setKnownActors(buffermanager);

        buffermanager.initial();
    	producer.initial();
    	consumer.initial();

        buffermanager.start();
        producer.start();
        consumer.start();
    }
}
