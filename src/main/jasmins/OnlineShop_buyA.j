.class public OnlineShop_buyA
.super Message

.field private num I
.field private receiver LOnlineShop;
.field private sender LActor;

.method public <init>(LOnlineShop;LActor;I)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield OnlineShop_buyA/receiver LOnlineShop;
aload_0
aload_2
putfield OnlineShop_buyA/sender LActor;
aload_0
iload 3
putfield OnlineShop_buyA/num I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop_buyA/receiver LOnlineShop;
aload_0
getfield OnlineShop_buyA/sender LActor;
aload_0
getfield OnlineShop_buyA/num I
invokevirtual OnlineShop/buyA(LActor;I)V
return
.end method
