.class public BufferManager
.super Actor

.field producer LProducer;
.field consumer LConsumer;
.field empty Z
.field full Z
.field producerWaiting Z
.field consumerWaiting Z
.field bufferlenght I
.field nextProduce I
.field nextConsume I

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LProducer;LConsumer;)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield BufferManager/producer LProducer;
aload_0
aload_2
putfield BufferManager/consumer LConsumer;
return
.end method

.method public initial()V
.limit stack 32
.limit locals 32
aload_0
ldc 2
putfield BufferManager/bufferlenght I
aload_0
ldc 1
putfield BufferManager/empty Z
aload_0
ldc 0
putfield BufferManager/full Z
aload_0
ldc 0
putfield BufferManager/producerWaiting Z
aload_0
ldc 0
putfield BufferManager/consumerWaiting Z
aload_0
ldc 0
putfield BufferManager/nextProduce I
aload_0
ldc 0
putfield BufferManager/nextConsume I
return
.end method

.method public send_giveMeNextProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_giveMeNextProduce
dup
aload_0
aload_1
invokespecial BufferManager_giveMeNextProduce/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public giveMeNextProduce(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "give me next produce begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
getfield BufferManager/full Z
ifne Label1
iconst_1
goto Label2
Label1:
iconst_0
Label2:
aload_0
getfield BufferManager/producer LProducer;
aload_0
invokevirtual Producer/send_produce(LActor;)V
goto Label3
Label0:
aload_0
ldc 1
putfield BufferManager/producerWaiting Z
Label3:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "give me next produce ends"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

.method public send_giveMeNextConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_giveMeNextConsume
dup
aload_0
aload_1
invokespecial BufferManager_giveMeNextConsume/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public giveMeNextConsume(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "give me next consume begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
getfield BufferManager/empty Z
ifne Label5
iconst_1
goto Label6
Label5:
iconst_0
Label6:
aload_0
getfield BufferManager/consumer LConsumer;
aload_0
invokevirtual Consumer/send_consume(LActor;)V
goto Label7
Label4:
aload_0
ldc 1
putfield BufferManager/consumerWaiting Z
Label7:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "give me next consume ends"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

.method public send_ackProduce(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_ackProduce
dup
aload_0
aload_1
invokespecial BufferManager_ackProduce/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public ackProduce(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "ack produce begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
aload_0
getfield BufferManager/nextProduce I
ldc 1
iadd
aload_0
getfield BufferManager/bufferlenght I
irem
putfield BufferManager/nextProduce I
aload_0
getfield BufferManager/nextProduce I
aload_0
getfield BufferManager/nextConsume I
if_icmpne Label8
aload_0
ldc 1
putfield BufferManager/full Z
Label8:
aload_0
ldc 0
putfield BufferManager/empty Z
aload_0
getfield BufferManager/consumerWaiting Z
aload_0
getfield BufferManager/consumer LConsumer;
aload_0
invokevirtual Consumer/send_consume(LActor;)V
aload_0
ldc 0
putfield BufferManager/consumerWaiting Z
Label9:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "ack produce begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

.method public send_ackConsume(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new BufferManager_ackConsume
dup
aload_0
aload_1
invokespecial BufferManager_ackConsume/<init>(LBufferManager;LActor;)V
invokevirtual BufferManager/send(LMessage;)V
return
.end method

.method public ackConsume(LActor;)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "ack consume begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload_0
aload_0
getfield BufferManager/nextConsume I
ldc 1
iadd
aload_0
getfield BufferManager/bufferlenght I
irem
putfield BufferManager/nextConsume I
aload_0
getfield BufferManager/nextConsume I
aload_0
getfield BufferManager/nextProduce I
if_icmpne Label10
aload_0
ldc 1
putfield BufferManager/empty Z
Label10:
aload_0
ldc 0
putfield BufferManager/full Z
aload_0
getfield BufferManager/producerWaiting Z
aload_0
getfield BufferManager/producer LProducer;
aload_0
invokevirtual Producer/send_produce(LActor;)V
aload_0
ldc 0
putfield BufferManager/producerWaiting Z
Label11:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "ack consume begins"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

