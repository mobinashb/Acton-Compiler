.class public Owner_pay
.super Message

.field private tcash I
.field private receiver LOwner;
.field private sender LActor;

.method public <init>(LOwner;LActor;I)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield Owner_pay/receiver LOwner;
aload_0
aload_2
putfield Owner_pay/sender LActor;
aload_0
iload 3
putfield Owner_pay/tcash I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield Owner_pay/receiver LOwner;
aload_0
getfield Owner_pay/sender LActor;
aload_0
getfield Owner_pay/tcash I
invokevirtual Owner/pay(LActor;I)V
return
.end method
