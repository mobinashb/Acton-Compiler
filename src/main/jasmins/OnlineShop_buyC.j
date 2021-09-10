.class public OnlineShop_buyC
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
putfield OnlineShop_buyC/receiver LOnlineShop;
aload_0
aload_2
putfield OnlineShop_buyC/sender LActor;
aload_0
iload 3
putfield OnlineShop_buyC/num I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop_buyC/receiver LOnlineShop;
aload_0
getfield OnlineShop_buyC/sender LActor;
aload_0
getfield OnlineShop_buyC/num I
invokevirtual OnlineShop/buyC(LActor;I)V
return
.end method
