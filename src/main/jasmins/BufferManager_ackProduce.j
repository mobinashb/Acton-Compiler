.class public BufferManager_ackProduce
.super Message

.field private receiver LBufferManager;
.field private sender LActor;

.method public <init>(LBufferManager;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield BufferManager_ackProduce/receiver LBufferManager;
aload_0
aload_2
putfield BufferManager_ackProduce/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield BufferManager_ackProduce/receiver LBufferManager;
aload_0
getfield BufferManager_ackProduce/sender LActor;
invokevirtual BufferManager/ackProduce(LActor;)V
return
.end method
