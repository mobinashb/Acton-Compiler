.class public B
.super Actor

.field a LA;
.field j I

.method public <init>(I)V
.limit stack 32
.limit locals 32
aload_0
bipush 10
newarray int
astore 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LA;)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield B/a LA;
return
.end method

.method public send_foo(LActor;[I)V
.limit stack 32
.limit locals 32
aload_0
new B_foo
dup
aload_0
aload_1
aload 2
invokespecial B_foo/<init>(LB;LActor;[I)V
invokevirtual B/send(LMessage;)V
return
.end method

.method public foo(LActor;[I)V
.limit stack 32
.limit locals 32
aload_0
ldc 0
putfield B/j I
Label2:
aload_0
getfield B/j I
ldc 10
if_icmpge Label3
getstatic java/lang/System/out Ljava/io/PrintStream;
aload 2
aload_0
getfield B/j I
iaload
invokevirtual java/io/PrintStream/println(I)V
LabelUpdate1:
aload_0
aload_0
getfield B/j I
ldc 1
iadd
putfield B/j I
goto Label2
Label3:
return
.end method

