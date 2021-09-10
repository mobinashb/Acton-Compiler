.class public OnlineShop
.super Actor

.field owner LOwner;
.field income I
.field A I
.field B I
.field C I
.field index I
.field reqs [I
.field open Z

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
bipush 100
newarray int
putfield OnlineShop/reqs [I
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LOwner;)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield OnlineShop/owner LOwner;
return
.end method

.method public initial(III)V
.limit stack 32
.limit locals 32
aload_0
iload 1
putfield OnlineShop/A I
aload_0
iload 2
putfield OnlineShop/B I
aload_0
iload 3
putfield OnlineShop/C I
aload_0
ldc 0
putfield OnlineShop/income I
aload_0
ldc 0
putfield OnlineShop/open Z
return
.end method

.method public send_buyA(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new OnlineShop_buyA
dup
aload_0
aload_1
iload 2
invokespecial OnlineShop_buyA/<init>(LOnlineShop;LActor;I)V
invokevirtual OnlineShop/send(LMessage;)V
return
.end method

.method public buyA(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop/open Z
iload 2
aload_0
getfield OnlineShop/A I
if_icmpge Label1
aload_0
aload_0
getfield OnlineShop/income I
iload 2
ldc 1200
imul
iadd
putfield OnlineShop/income I
aload_0
aload_0
getfield OnlineShop/A I
iload 2
isub
putfield OnlineShop/A I
aload_0
aload_0
invokevirtual OnlineShop/send_check(LActor;)V
goto Label2
Label1:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "not available"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
Label2:
Label0:
return
.end method

.method public send_buyB(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new OnlineShop_buyB
dup
aload_0
aload_1
iload 2
invokespecial OnlineShop_buyB/<init>(LOnlineShop;LActor;I)V
invokevirtual OnlineShop/send(LMessage;)V
return
.end method

.method public buyB(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop/open Z
iload 2
aload_0
getfield OnlineShop/B I
if_icmpge Label4
aload_0
aload_0
getfield OnlineShop/income I
iload 2
ldc 2300
imul
iadd
putfield OnlineShop/income I
aload_0
aload_0
getfield OnlineShop/B I
iload 2
isub
putfield OnlineShop/B I
aload_0
aload_0
invokevirtual OnlineShop/send_check(LActor;)V
goto Label5
Label4:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "not available"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
Label5:
Label3:
return
.end method

.method public send_buyC(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new OnlineShop_buyC
dup
aload_0
aload_1
iload 2
invokespecial OnlineShop_buyC/<init>(LOnlineShop;LActor;I)V
invokevirtual OnlineShop/send(LMessage;)V
return
.end method

.method public buyC(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop/open Z
iload 2
aload_0
getfield OnlineShop/C I
if_icmpge Label7
aload_0
aload_0
getfield OnlineShop/income I
iload 2
ldc 4600
imul
iadd
putfield OnlineShop/income I
aload_0
aload_0
getfield OnlineShop/C I
iload 2
isub
putfield OnlineShop/C I
aload_0
aload_0
invokevirtual OnlineShop/send_check(LActor;)V
goto Label8
Label7:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "not available"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
Label8:
Label6:
return
.end method

.method public send_check(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new OnlineShop_check
dup
aload_0
aload_1
invokespecial OnlineShop_check/<init>(LOnlineShop;LActor;)V
invokevirtual OnlineShop/send(LMessage;)V
return
.end method

.method public check(LActor;)V
.limit stack 32
.limit locals 32
aload_0
getfield OnlineShop/A I
ldc 0
if_icmpne Label28
iconst_1
goto Label29
Label28:
iconst_0
Label29:
ifeq Label26
aload_0
getfield OnlineShop/B I
ldc 0
if_icmpne Label30
iconst_1
goto Label31
Label30:
iconst_0
Label31:
ifeq Label26
iconst_1
goto Label27
Label26:
iconst_0
Label27:
ifeq Label20
aload_0
getfield OnlineShop/C I
ldc 0
if_icmpne Label32
iconst_1
goto Label33
Label32:
iconst_0
Label33:
ifeq Label20
iconst_1
goto Label21
Label20:
iconst_0
Label21:
aload_0
getfield OnlineShop/owner LOwner;
aload_0
aload_0
getfield OnlineShop/income I
invokevirtual Owner/send_recv(LActor;I)V
Label9:
return
.end method

.method public send_close(LActor;)V
.limit stack 32
.limit locals 32
aload_0
new OnlineShop_close
dup
aload_0
aload_1
invokespecial OnlineShop_close/<init>(LOnlineShop;LActor;)V
invokevirtual OnlineShop/send(LMessage;)V
return
.end method

.method public close(LActor;)V
.limit stack 32
.limit locals 32
aload_0
ldc 0
putfield OnlineShop/open Z
return
.end method

