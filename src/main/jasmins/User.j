.class public User
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
putfield User/shop LOnlineShop;
return
.end method

.method public initial(I)V
.limit stack 32
.limit locals 32
aload_0
iload 1
putfield User/tcash I
aload_0
aload_0
invokevirtual User/send_buy(LActor;)V
return
.end method

.method public send_buy(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new User_buy
dup
aload_0
aload_1
invokespecial User_buy/<init>(LUser;LActor;)V
invokevirtual User/send(LMessage;)V
return
.end method

.method public buy(LActor;)V
.limit stack 32
.limit locals 32
ldc 546546546
istore 2
 Label37:
iload 2
ldc 4534534
imul
iload 2
ldc 435
irem
ldc 4
imul
iadd
istore 2
iload 2
ldc 3
irem
ldc 0
if_icmpne Label39
aload_0
getfield User/shop LOnlineShop;
aload_0
iload 2
ldc 3
irem
invokevirtual OnlineShop/send_buyA(LActor;I)V
aload_0
aload_0
getfield User/tcash I
ldc 1200
isub
putfield User/tcash I
goto Label40
Label39:
iload 2
ldc 3
irem
ldc 1
if_icmpne Label41
aload_0
getfield User/shop LOnlineShop;
aload_0
iload 2
ldc 4
irem
invokevirtual OnlineShop/send_buyB(LActor;I)V
aload_0
aload_0
getfield User/tcash I
ldc 2300
isub
putfield User/tcash I
goto Label42
Label41:
aload_0
getfield User/shop LOnlineShop;
aload_0
iload 2
ldc 2
irem
invokevirtual OnlineShop/send_buyC(LActor;I)V
aload_0
aload_0
getfield User/tcash I
ldc 4600
isub
putfield User/tcash I
Label42:
Label40:
aload_0
getfield User/tcash I
ldc 0
if_icmpge Label43
goto Label38
Label43:
LabelUpdate1:
 goto Label37
Label38:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "bye"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

