.class public OnlineShop_buyB
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
putfield OnlineShop_buyB/receiver LOnlineShop;
aload_0
aload_2
putfield OnlineShop_buyB/sender LActor;
aload_0
iload 3
putfield OnlineShop_buyB/num I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop_buyB/receiver LOnlineShop;
aload_0
getfield OnlineShop_buyB/sender LActor;
aload_0
getfield OnlineShop_buyB/num I
invokevirtual OnlineShop/buyB(LActor;I)V
return
.end method
