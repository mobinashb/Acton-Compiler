.class public User_buy
.super Message

.field private receiver LUser;
.field private sender LActor;

.method public <init>(LUser;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield User_buy/receiver LUser;
aload_0
aload_2
putfield User_buy/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield User_buy/receiver LUser;
aload_0
getfield User_buy/sender LActor;
invokevirtual User/buy(LActor;)V
return
.end method
