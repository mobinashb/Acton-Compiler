.class public Owner
.super Actor

.field shop LOnlineShop;
.field tcash I

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LOnlineShop;)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield Owner/shop LOnlineShop;
return
.end method

.method public send_recv(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new Owner_recv
dup
aload_0
aload_1
iload 2
invokespecial Owner_recv/<init>(LOwner;LActor;I)V
invokevirtual Owner/send(LMessage;)V
return
.end method

.method public recv(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
iload 2
aload_0
getfield Owner/tcash I
iadd
putfield Owner/tcash I
aload_0
getfield Owner/shop LOnlineShop;
aload_0
invokevirtual OnlineShop/send_close(LActor;)V
return
.end method

.method public send_pay(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new Owner_pay
dup
aload_0
aload_1
iload 2
invokespecial Owner_pay/<init>(LOwner;LActor;I)V
invokevirtual Owner/send(LMessage;)V
return
.end method

.method public pay(LActor;I)V
.limit stack 32
.limit locals 32
iload 2
ldc 0
if_icmple Label34
aload_0
aload_0
getfield Owner/tcash I
iload 2
isub
putfield Owner/tcash I
Label34:
return
.end method

