.class public Bank
.super Actor

.field o1 LOwner;
.field o2 LOwner;
.field tcash I

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LOwner;LOwner;)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield Bank/o1 LOwner;
aload_0
aload_2
putfield Bank/o2 LOwner;
return
.end method

.method public initial()V
.limit stack 32
.limit locals 32
aload_0
ldc 0
putfield Bank/tcash I
aload_0
aload_0
invokevirtual Bank/send_com(LActor;)V
return
.end method

.method public send_com(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new Bank_com
dup
aload_0
aload_1
invokespecial Bank_com/<init>(LBank;LActor;)V
invokevirtual Bank/send(LMessage;)V
return
.end method

.method public com(LActor;)V
.limit stack 32
.limit locals 32
ldc 54648456
istore 2
 Label35:
iload 2
ldc 66235634
imul
ldc 123423
ldc 3242
irem
iadd
istore 2
aload_0
getfield Bank/o1 LOwner;
aload_0
iload 2
ldc 23657
irem
invokevirtual Owner/send_pay(LActor;I)V
aload_0
aload_0
getfield Bank/tcash I
iload 2
ldc 23657
irem
iadd
putfield Bank/tcash I
iload 2
ldc 66235634
imul
ldc 123423
ldc 3242
irem
iadd
istore 2
aload_0
getfield Bank/o2 LOwner;
aload_0
iload 2
ldc 23657
irem
invokevirtual Owner/send_pay(LActor;I)V
aload_0
aload_0
getfield Bank/tcash I
iload 2
ldc 23657
irem
iadd
putfield Bank/tcash I
LabelUpdate0:
 goto Label35
Label36:
return
.end method

