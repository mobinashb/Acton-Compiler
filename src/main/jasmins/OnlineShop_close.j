.class public OnlineShop_close
.super Message

.field private receiver LOnlineShop;
.field private sender LActor;

.method public <init>(LOnlineShop;LActor;)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield OnlineShop_close/receiver LOnlineShop;
aload_0
aload_2
putfield OnlineShop_close/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop_close/receiver LOnlineShop;
aload_0
getfield OnlineShop_close/sender LActor;
invokevirtual OnlineShop/close(LActor;)V
return
.end method
