.class public Bank_com
.super Message

.field private receiver LBank;
.field private sender LActor;

.method public <init>(LBank;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield Bank_com/receiver LBank;
aload_0
aload_2
putfield Bank_com/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield Bank_com/receiver LBank;
aload_0
getfield Bank_com/sender LActor;
invokevirtual Bank/com(LActor;)V
return
.end method
