.class public Owner_recv
.super Message

.field private income I
.field private receiver LOwner;
.field private sender LActor;

.method public <init>(LOwner;LActor;I)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield Owner_recv/receiver LOwner;
aload_0
aload_2
putfield Owner_recv/sender LActor;
aload_0
iload 3
putfield Owner_recv/income I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield Owner_recv/receiver LOwner;
aload_0
getfield Owner_recv/sender LActor;
aload_0
getfield Owner_recv/income I
invokevirtual Owner/recv(LActor;I)V
return
.end method
