.class public A_binaryTest
.super Message

.field private e1 I
.field private e2 I
.field private e3 I
.field private w1 Z
.field private w2 Z
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;IIIZZ)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_binaryTest/receiver LA;
aload_0
aload_2
putfield A_binaryTest/sender LActor;
aload_0
iload 3
putfield A_binaryTest/e1 I
aload_0
iload 3
putfield A_binaryTest/e2 I
aload_0
iload 3
putfield A_binaryTest/e3 I
aload_0
iload 3
putfield A_binaryTest/w1 Z
aload_0
iload 3
putfield A_binaryTest/w2 Z
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_binaryTest/receiver LA;
aload_0
getfield A_binaryTest/sender LActor;
aload_0
getfield A_binaryTest/e1 I
aload_0
getfield A_binaryTest/e2 I
aload_0
getfield A_binaryTest/e3 I
aload_0
getfield A_binaryTest/w1 Z
aload_0
getfield A_binaryTest/w2 Z
invokevirtual A/binaryTest(LActor;IIIZZ)V
return
.end method
