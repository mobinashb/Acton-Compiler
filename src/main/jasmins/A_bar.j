.class public A_bar
.super Message

.field private i I
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;I)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_bar/receiver LA;
aload_0
aload_2
putfield A_bar/sender LActor;
aload_0
iload 3
putfield A_bar/i I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_bar/receiver LA;
aload_0
getfield A_bar/sender LActor;
aload_0
getfield A_bar/i I
invokevirtual A/bar(LActor;I)V
return
.end method
