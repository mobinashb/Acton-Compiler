.class public Main
.super java/lang/Object

.method public <init>()V
.limit stack 1
.limit locals 1
0: aload_0
1: invokespecial java/lang/Object/<init>()V
4: return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 32
new A
dup
ldc 5
invokespecial A/<init>(I)V
astore 1
new B
dup
ldc 2
invokespecial B/<init>(I)V
astore 2
aload 1
aload 2
invokevirtual A/setKnownActors(LB;)V
aload 2
aload 1
invokevirtual B/setKnownActors(LA;)V
aload 1
ldc 10
invokevirtual A/initial(I)V
aload 1
invokevirtual A/start()V
aload 2
invokevirtual B/start()V
return
.end method
