.class public A_decIncTest
.super Message

.field private i I
.field private j I
.field private receiver LA;
.field private sender LActor;

.method public <init>(LA;LActor;II)V
.limit stack 32
.limit locals 32
aload_0
invokespecial Message/<init>()V
aload_0
aload_1
putfield A_decIncTest/receiver LA;
aload_0
aload_2
putfield A_decIncTest/sender LActor;
aload_0
iload 3
putfield A_decIncTest/i I
aload_0
iload 3
putfield A_decIncTest/j I
return
.end method

.method public execute()V
.limit stack 32
.limit locals 32
aload_0
getfield A_decIncTest/receiver LA;
aload_0
getfield A_decIncTest/sender LActor;
aload_0
getfield A_decIncTest/i I
aload_0
getfield A_decIncTest/j I
invokevirtual A/decIncTest(LActor;II)V
return
.end method
