.class public A_forTest
.super Message

.field private k I
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;I)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_forTest/receiver LA;
aload_0
aload_2
putfield A_forTest/sender LActor;
aload_0
iload 3
putfield A_forTest/k I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_forTest/receiver LA;
aload_0
getfield A_forTest/sender LActor;
aload_0
getfield A_forTest/k I
invokevirtual A/forTest(LActor;I)V
return
.end method
