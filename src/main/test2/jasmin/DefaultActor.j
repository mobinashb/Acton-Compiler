.class public DefaultActor
.super java/lang/Thread

.method public <init>()V
  .limit stack 1
  .limit locals 1
  aload_0
  invokespecial java/lang/Thread/<init>()V
  return
.end method

.method public send_giveMeNextProduce(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named giveMeNextProduce in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_giveMeNextConsume(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named giveMeNextConsume in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_ackProduce(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named ackProduce in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_ackConsume(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named ackConsume in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_produce(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named produce in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_beginProduce(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named beginProduce in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_consume(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named consume in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method

.method public send_beginConsume(LActor;)V
  .limit stack 2
  .limit locals 2
  getstatic java/lang/System/out Ljava/io/PrintStream;
  ldc "there is no msghandler named beginConsume in sender"
  invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
  return
.end method


