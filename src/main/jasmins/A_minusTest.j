.class public A_minusTest
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
putfield A_minusTest/receiver LA;
aload_0
aload_2
putfield A_minusTest/sender LActor;
aload_0
iload 3
putfield A_minusTest/k I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_minusTest/receiver LA;
aload_0
getfield A_minusTest/sender LActor;
aload_0
getfield A_minusTest/k I
invokevirtual A/minusTest(LActor;I)V
return
.end method
