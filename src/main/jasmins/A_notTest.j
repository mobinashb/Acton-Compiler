.class public A_notTest
.super Message

.field private o1 Z
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;Z)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_notTest/receiver LA;
aload_0
aload_2
putfield A_notTest/sender LActor;
aload_0
iload 3
putfield A_notTest/o1 Z
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_notTest/receiver LA;
aload_0
getfield A_notTest/sender LActor;
aload_0
getfield A_notTest/o1 Z
invokevirtual A/notTest(LActor;Z)V
return
.end method
