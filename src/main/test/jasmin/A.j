.class public A
.super Actor

.field b LB;
.field i I

.method public <init>(I)V
.limit stack 2
.limit locals 2
aload_0
iload_1
invokespecial Actor/<init>(I)V
return
.end method

.method public setKnownActors(LB)V
.limit stack 32
.limit locals 32
aload_0
aload_1
putfield A/b LB;
return
.end method

.method public initial(LActor)V
.limit stack 32
.limit locals 32
aload_0
ldc 2
putfield A/i I
aload_0
getfield A/b LB
aload_0
aload_0
getfield A/i I
invokevirtual B/send_foo(LActor;I)V
return
.end method

.method public send_bar(LActor;I)V
.limit stack 32
.limit locals 32
aload_0
new A_bar
dup
aload_0
aload_1
iload 2
invokespecial A_bar/<init>(LA;LActor;I)V
invokevirtual A/send(LMessage;)V
return
.end method

.method public bar(LActor;I)V
.limit stack 32
.limit locals 32
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 2
invokevirtual java/io/PrintStream/println(I)V
aload_1
aload_0
iload 2
ldc 1
iadd
invokevirtual Actor/send_foo(LActor;I)V
return
.end method

