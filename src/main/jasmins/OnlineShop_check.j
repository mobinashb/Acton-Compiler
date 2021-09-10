.class public OnlineShop_check
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
putfield OnlineShop_check/receiver LOnlineShop;
aload_0
aload_2
putfield OnlineShop_check/sender LActor;
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop_check/receiver LOnlineShop;
aload_0
getfield OnlineShop_check/sender LActor;
invokevirtual OnlineShop/check(LActor;)V
return
.end method
