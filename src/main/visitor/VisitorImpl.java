package main.visitor;
import com.sun.jdi.IntegerType;
import main.ast.node.*;
import main.ast.node.Program;
import main.ast.node.declaration.*;
import main.ast.node.declaration.handler.*;
import main.ast.node.declaration.VarDeclaration;
import main.ast.node.expression.*;
import main.ast.node.expression.operators.BinaryOperator;
import main.ast.node.expression.operators.UnaryOperator;
import main.ast.node.expression.values.BooleanValue;
import main.ast.node.expression.values.IntValue;
import main.ast.node.expression.values.StringValue;
import main.ast.node.statement.*;
import main.ast.type.Type;
import main.ast.type.arrayType.ArrayType;
import main.ast.type.noType.NoType;
import main.ast.type.primitiveType.BooleanType;
import main.ast.type.primitiveType.IntType;
import main.symbolTable.*;
import main.symbolTable.symbolTableVariableItem.*;
import main.symbolTable.itemException.*;
import main.ast.type.primitiveType.StringType;
import main.visitorPre.nameAnalyser.TypeCheck;


import java.awt.*;
import java.io.FileWriter;
import java.security.cert.TrustAnchor;
import java.util.*;

public class VisitorImpl implements Visitor {

    private HashMap<String, Integer> handlerVars = new HashMap<String, Integer>();
    private SymbolTableConstructor symbolTableConstructor;
    private String curActor = "";
    private int label = 0;
    private int coLabel = 0;
    private boolean init = false;
    private boolean forcond = false;
    private Stack<String> continueL = new Stack<String>();
    private Stack<String> breakL = new Stack<String>();
    private int mainJLevel = 0;
    private ArrayList<ActorDeclaration> acts = new ArrayList<ActorDeclaration>();
    private HashMap<String, Integer> actInsts = new HashMap<String, Integer>();
    private String jPAth = "src/main/jasmins/";
    private ArrayList<HandlerDeclaration> handlers = new ArrayList<HandlerDeclaration>();
    private String arrayAlloc = "";

    public VisitorImpl(){
        genJforActor();
        genJForMesseage();
        symbolTableConstructor = new SymbolTableConstructor();
    }

    private void genJForMesseage(){
        try {
            FileWriter fw = new FileWriter(jPAth + "Message.j");
            String code = ".class public abstract Message\n";
            code += ".super java/lang/Object\n";
            code += ".method public <init>()V\n";
            code += ".limit stack 1\n.limit locals 1\n";
            code += "0: aload_0\n1: invokespecial java/lang/Object/<init>()V\n4: return\n";
            code += ".end method\n";
            code += ".method public abstract execute()V\n";
            code += ".end method\n";
            fw.write(code);
            fw.close();
        }
        catch (Exception e1){
            System.out.println("Can not write to Message.j file");
        }
    }

    private void genJforActor(){
        try {
            FileWriter fw = new FileWriter(jPAth + "Actor.j");
            String code = ".class public Actor\n.super DefaultActor\n\n.field private queue Ljava/util/ArrayList;\n";
            code +=".signature " + "\"Ljava/util/ArrayList<LMessage;>;\"\n.end field\n.field private lock Ljava/util/concurrent/locks/ReentrantLock;\n";
            code += ".end field\n.field queueSize I\n.end field\n\n";
            code += ".method public <init>(I)V\n.limit stack 3\n.limit locals 2\naload_0\ninvokespecial DefaultActor/<init>()V\naload_0\nnew java/util/ArrayList\n";
            code += "dup\ninvokespecial java/util/ArrayList/<init>()V\nputfield Actor/queue Ljava/util/ArrayList;\naload_0\n";
            code += "new java/util/concurrent/locks/ReentrantLock\ndup\ninvokespecial java/util/concurrent/locks/ReentrantLock/<init>()V\n";
            code += "putfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\naload_0\niload_1\nputfield Actor/queueSize I\nreturn\n.end method\n\n";
            code += ".method public run()V\n.limit stack 2\n.limit locals 2\nLabel0:\naload_0\ngetfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n";
            code += "invokevirtual java/util/concurrent/locks/ReentrantLock/lock()V\naload_0\ngetfield Actor/queue Ljava/util/ArrayList;\n";
            code += "invokevirtual java/util/ArrayList/isEmpty()Z\nifne Label31\naload_0\ngetfield Actor/queue Ljava/util/ArrayList;\niconst_0\n";
            code += "invokevirtual java/util/ArrayList/remove(I)Ljava/lang/Object;\ncheckcast Message\nastore 1\naload_0\n";
            code += "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\ninvokevirtual java/util/concurrent/locks/ReentrantLock/unlock()V\naload_1\n";
            code += "invokevirtual Message/execute()V\ngoto Label0\nLabel31:\naload_0\ngetfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n";
            code += "invokevirtual java/util/concurrent/locks/ReentrantLock/unlock()V\ngoto Label0\n.end method\n\n";
            code += ".method public send(LMessage;)V\n.limit stack 2\n.limit locals 2\naload_0\ngetfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\n";
            code += "invokevirtual java/util/concurrent/locks/ReentrantLock/lock()V\naload_0\ngetfield Actor/queue Ljava/util/ArrayList;\n";
            code += "invokevirtual java/util/ArrayList/size()I\naload_0\ngetfield Actor/queueSize I\nif_icmpge Label30\naload_0\n";
            code += "getfield Actor/queue Ljava/util/ArrayList;\naload_1\ninvokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\npop\nLabel30:\naload_0\n";
            code += "getfield Actor/lock Ljava/util/concurrent/locks/ReentrantLock;\ninvokevirtual java/util/concurrent/locks/ReentrantLock/unlock()V\nreturn\n.end method\n";
            fw.write(code);
            fw.close();
        }
        catch (Exception e1){
            System.out.println("Can not write to Actor.j file");
        }
    }

    protected String visitStatement( Statement stat )
    {
        if( stat == null )
            return " ";
        else if( stat instanceof MsgHandlerCall )
            return(this.visit( ( MsgHandlerCall ) stat ));
        else if( stat instanceof Block )
            return(this.visit( ( Block ) stat ));
        else if( stat instanceof Conditional )
            return(this.visit( ( Conditional ) stat ));
        else if( stat instanceof For )
            return(this.visit( ( For ) stat ));
        else if( stat instanceof Break )
            return(this.visit( ( Break ) stat ));
        else if( stat instanceof Continue )
            return(this.visit( ( Continue ) stat ));
        else if( stat instanceof Print )
            return(this.visit( ( Print ) stat ));
        else if( stat instanceof Assign )
            return(this.visit( ( Assign ) stat ));
        return "";
    }

    protected String visitExpr( Expression expr )
    {
        if( expr == null )
            return "";
        else if( expr instanceof UnaryExpression )
            return(this.visit( ( UnaryExpression ) expr ));
        else if( expr instanceof BinaryExpression )
            return(this.visit( ( BinaryExpression ) expr ));
        else if( expr instanceof ArrayCall )
            return(this.visit( ( ArrayCall ) expr ));
        else if( expr instanceof ActorVarAccess )
            return(this.visit( ( ActorVarAccess ) expr ));
        else if( expr instanceof Identifier )
            return(this.visit( ( Identifier ) expr ));
        else if( expr instanceof Self )
            return(this.visit( ( Self ) expr ));
        else if( expr instanceof Sender )
            return(this.visit( ( Sender ) expr ));
        else if( expr instanceof BooleanValue )
            return(this.visit( ( BooleanValue ) expr ));
        else if( expr instanceof IntValue )
            return(this.visit( ( IntValue ) expr ));
        else if( expr instanceof StringValue )
            return(this.visit( ( StringValue ) expr ));
        return "";
    }

    private Boolean classField(Identifier identifier){
        try {
            SymbolTableVariableItem temp = (SymbolTableVariableItem) SymbolTable.top.getInCurrentScope(SymbolTableVariableItem.STARTKEY + identifier.getName());
            return false;

        }
        catch (ItemNotFoundException e1){
            try {
                SymbolTableVariableItem temp1 = (SymbolTableVariableItem) SymbolTable.top.get(SymbolTableVariableItem.STARTKEY + identifier.getName());
                return true;
            }
            catch (ItemNotFoundException e2){}
        }
        return false;
    }

    private ActorDeclaration findActor(String name){
        for (ActorDeclaration actDec : acts){
            if (actDec.getName().getName().equals(name))
                return actDec;
        }
        return null;
    }

    private void addToHandlers(HandlerDeclaration handlerDeclaration){
        if(handlers.size() == 0){
            handlers.add(handlerDeclaration);
            return;
        }
        for(HandlerDeclaration hndlrDec : handlers){
            if(hndlrDec.getName().getName().equals(handlerDeclaration.getName().getName())){
                ArrayList<VarDeclaration> inArgs = handlerDeclaration.getArgs();
                if(inArgs.size() != hndlrDec.getArgs().size())
                    continue;
                int i = 0;
                Boolean eq = true;
                for(VarDeclaration args : hndlrDec.getArgs()){
                    if(inArgs.get(i).getType().equals(args.getType()) == false){
                        eq = false;
                        break;
                    }
                    i++;
                }
                if(eq == true)
                    return;
            }
        }
        handlers.add(handlerDeclaration);
    }

    private String typeInJasmine(Type type){
        if(type.toString() == "int")
            return "I";
        else if(type.toString() == "boolean")
            return "Z";
        else if(type.toString() == "string")
            return "Ljava/lang/String;";
        else if(type.toString() == "int[]")
            return "[I";
        return "L" + type.toString() + ";";
    }


    private String getPrimaryActDec(ActorDeclaration actorDeclaration){
        String actData = ".class public " + actorDeclaration.getName().getName() + "\n" + ".super Actor";

        String fields = "";
        for(VarDeclaration knownAcs : actorDeclaration.getKnownActors())
            fields += ".field " + knownAcs.getIdentifier().getName() + " L" + knownAcs.getType() + ";\n";

        for(VarDeclaration actVars : actorDeclaration.getActorVars()) {
            fields += ".field " + actVars.getIdentifier().getName() + " " + typeInJasmine(actVars.getType()) + "\n";
        }

        for(VarDeclaration var : actorDeclaration.getActorVars()){
            Type temp = var.getType();
            if(temp instanceof ArrayType){
                arrayAlloc += "aload_0\nbipush " + Integer.toString(((ArrayType) temp).getSize()) +
                        "\n" + "newarray int\nputfield " + curActor + "/" + var.getIdentifier().getName() + " [I\n";
            }
        }

        String constructor = ".method public <init>(I)V\n" + ".limit stack 32\n" + ".limit locals 32\n" +
                arrayAlloc + "aload_0\n" + "iload_1\n" + "invokespecial Actor/<init>(I)V\n" + "return\n" + ".end method";
        arrayAlloc = "";

        return  actData + "\n\n" + fields + "\n" + constructor + "\n\n";
    }

    private String getKnownActors(ActorDeclaration actorDeclaration){
        String name = ".method public setKnownActors";
        String Args = "(";
        for(VarDeclaration knownActs : actorDeclaration.getKnownActors())
            Args += "L" + knownActs.getType() + ";";
        Args += ")";
        String returnType = "V";
        String limits = ".limit stack 32\n.limit locals 32";

        String body = "";

        int j = 1;
        for(VarDeclaration knownActs : actorDeclaration.getKnownActors()) {
            String iter = "aload_0\n";
            iter += "aload_" + Integer.toString(j) + "\n";
            iter += "putfield " + actorDeclaration.getName().getName() + "/" +knownActs.getIdentifier().getName() + " L" + knownActs.getType() + ";\n";
            body += iter;
            j++;
        }
        String ending = "return\n.end method";

        return name + Args + returnType + "\n" + limits + "\n" + body + ending + "\n";
    }

    private String getPrimaryActIns(){
        String code = ".class public Main\n.super java/lang/Object\n\n.method public <init>()V\n.limit stack 1\n.limit locals 1\n" +
        "0: aload_0\n1: invokespecial java/lang/Object/<init>()V\n4: return\n.end method\n\n";
        code += ".method public static main([Ljava/lang/String;)V\n.limit stack 32\n.limit locals 32\n";
        return code;
    }

    private void genJForHandlerDec(HandlerDeclaration handlerDeclaration){
        String name = handlerDeclaration.getName().getName();
        String className = curActor + "_" + name;
        try{
            FileWriter fwMsg = new FileWriter(jPAth + className + ".j");
            String code = ".class public " + className + "\n.super Message\n\n";
            for(VarDeclaration arg : handlerDeclaration.getArgs())
                code += ".field private " + arg.getIdentifier().getName() + " " + typeInJasmine(arg.getType()) + "\n";

            code += ".field private receiver L" + curActor + ";\n";
            code += ".field private sender LActor;\n\n";

            code += ".method public <init>(L" + curActor + ";LActor;";
            for(VarDeclaration arg : handlerDeclaration.getArgs())
                code += typeInJasmine(arg.getType());
            code += ")V\n.limit stack 32\n.limit locals 32\n";
            code += "aload_0\ninvokespecial Message/<init>()V\n";
            code += "aload_0\naload_1\nputfield " + className + "/receiver L" + curActor + ";\n";
            code += "aload_0\naload_2\nputfield " + className + "/sender LActor;\n";
            int cnt = 3;
            for(VarDeclaration arg : handlerDeclaration.getArgs()){
                code += "aload_0\n";
                if(arg.getType() instanceof IntType || arg.getType() instanceof BooleanType)
                    code += "iload " + Integer.toString(cnt) + "\n";
                else
                    code += "aload " + Integer.toString(cnt) + "\n";
                code += "putfield " + className + "/" + arg.getIdentifier().getName() + " " + typeInJasmine(arg.getType()) + "\n";
            }
            code += "return\n.end method\n\n";

            code += ".method public execute()V\n.limit stack 32\n.limit locals 32\n";
            code += "aload_0\ngetfield " + className + "/receiver L" + curActor + ";\naload_0\n";
            code += "getfield " + className + "/sender LActor;\n";

            for(VarDeclaration arg : handlerDeclaration.getArgs()){
                code += "aload_0\n";
                code += "getfield " + className + "/" + arg.getIdentifier().getName() + " " + typeInJasmine(arg.getType()) + "\n";
            }

            code += "invokevirtual " + curActor + "/" + name + "(LActor;";

            for(VarDeclaration arg : handlerDeclaration.getArgs())
                code += typeInJasmine(arg.getType());
            code += ")V\n";

            code += "return\n.end method\n";

            fwMsg.write(code);
            fwMsg.close();
        }
        catch (Exception e3){
            System.out.println("Can't write to " + name + ".j file");
        }
    }


    @Override
    public String visit(Program program) {
        symbolTableConstructor.constructProgramSymbolTable();

        for(ActorDeclaration actorDeclaration : program.getActors())
            acts.add(actorDeclaration);

        for(ActorDeclaration actorDeclaration : program.getActors())
            actorDeclaration.accept(this);

        try{
            FileWriter fwDefualtActor = new FileWriter(jPAth + "DefaultActor.j");
            String code = ".class public DefaultActor\n.super java/lang/Thread\n\n.method public <init>()V\n.limit stack 1\n.limit locals 1\n";
            code += "aload_0\ninvokespecial java/lang/Thread/<init>()V\nreturn\n.end method\n\n";

            for(HandlerDeclaration hndlr : handlers){
                code += ".method public " + "send_" + hndlr.getName().getName() + "(LActor;";
                for(VarDeclaration var : hndlr.getArgs())
                    code += typeInJasmine(var.getType());
                code += ")V\n.limit stack 32\n.limit locals 32\ngetstatic java/lang/System/out Ljava/io/PrintStream;\n";
                code += "ldc \"there is no msghandler named " + hndlr.getName().getName() + " in sender\"\n";
                code += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\nreturn\n.end method\n\n";
            }
            fwDefualtActor.write(code);
            fwDefualtActor.close();

        }
        catch (Exception err){
            System.out.println("Can't write to file DefualtActor.j");
        }

        program.getMain().accept(this);
        return " ";
    }

    @Override
    public String visit(ActorDeclaration actorDeclaration) {
        curActor = actorDeclaration.getName().getName();
        symbolTableConstructor.construct(actorDeclaration);
        try {
            FileWriter fw = new FileWriter(jPAth + actorDeclaration.getName().getName() + ".j");
            String priDec = getPrimaryActDec(actorDeclaration);
            String knownActs = getKnownActors(actorDeclaration);
            String initial = "";

            if(actorDeclaration.getInitHandler() != null) {
                init = true;
                initial += visit(actorDeclaration.getInitHandler());
            }
            init = false;
            String Handlers = "";
            for(MsgHandlerDeclaration msgHandlerDeclaration: actorDeclaration.getMsgHandlers()) {
                genJForHandlerDec(msgHandlerDeclaration);
                String sendCode = addSendMsgCode(msgHandlerDeclaration);
                Handlers += sendCode + "\n" + visit(msgHandlerDeclaration);
            }
            fw.write(priDec + knownActs + "\n" + initial + Handlers);
            fw.close();
        }
        catch (Exception e1){System.out.println("Can't write to file " +actorDeclaration.getName().getName() + ".j");}

        SymbolTable.pop();
        return "";
    }

    private String addSendMsgCode(HandlerDeclaration handlerDeclaration){
        addToHandlers(handlerDeclaration);
        String name = handlerDeclaration.getName().getName();


        String code = ".method public send_" + name + "(LActor;";
        for(VarDeclaration arg : handlerDeclaration.getArgs())
            code += typeInJasmine(arg.getType());
        code += ")V\n";
        code += ".limit stack 32\n.limit locals 32\n";
        code += "aload_0\nnew " + curActor + "_" + name + "\ndup\naload_0\naload_1\n";
        int cnt = 2;
        for(VarDeclaration arg : handlerDeclaration.getArgs()) {
            if(arg.getType() instanceof IntType || arg.getType() instanceof BooleanType)
                code += "iload " + Integer.toString(cnt) + "\n";
            else
                code += "aload " + Integer.toString(cnt) + "\n";
            cnt++;
        }

        code += "invokespecial " + curActor + "_" + name + "/<init>(L" + curActor + ";LActor;";
        for(VarDeclaration arg : handlerDeclaration.getArgs())
            code += typeInJasmine(arg.getType());
        code += ")V\n";

        code += "invokevirtual " + curActor + "/send(LMessage;)V\n";
        code += "return\n.end method\n";

        return code;
    }

    @Override
    public String visit(HandlerDeclaration handlerDeclaration) {

        symbolTableConstructor.construct(handlerDeclaration);
        String name = ".method public " + handlerDeclaration.getName().getName();

        String Args = "(" + (init ? "" : "LActor;");
        int i = init ? 0 : 1;
        for(VarDeclaration args : handlerDeclaration.getArgs()) {
            handlerVars.put(args.getIdentifier().getName(), i);
            i++;
            Args += visit(args);
        }
        Args += ")";

        String returnType = "V";
        String limits = ".limit stack 32\n.limit locals 32";

        String body = "";

        for(VarDeclaration vars : handlerDeclaration.getLocalVars()){
            handlerVars.put(vars.getIdentifier().getName(), i);
            i++;
            Type temp = vars.getType();
            if(temp instanceof ArrayType){
                arrayAlloc += "aload_0\nbipush " + Integer.toString(((ArrayType) temp).getSize()) +
                        "\n" + "newarray int\nastore " + Integer.toString(i) + "\n";
            }
        }

        for(Statement stat : handlerDeclaration.getBody()){
            body += visitStatement(stat);
        }
        handlerVars.clear();
        String ending = "return\n.end method";
        SymbolTable.pop();
        return name + Args + returnType + "\n" + limits + "\n" + body + ending + "\n\n";
    }

    @Override
    public String visit(VarDeclaration varDeclaration) {
        return typeInJasmine(varDeclaration.getType());
    }

    @Override
    public String visit(Main mainActors) {
        symbolTableConstructor.construct(mainActors);
        int j = 1;
        for (ActorInstantiation mainAcs : mainActors.getMainActors()) {
            actInsts.put(mainAcs.getIdentifier().getName(), j);
            j++;
        }
        try{
            FileWriter fw = new FileWriter(jPAth + "Main.j");
            String code = getPrimaryActIns();
            for(mainJLevel = 0; mainJLevel < 4; mainJLevel++) {
                for (ActorInstantiation mainAcs : mainActors.getMainActors())
                    code += visit(mainAcs);
            }
            code += "return\n.end method\n";
            fw.write(code);
            fw.close();
        }
        catch (Exception e1){
            System.out.println("Can't write to Main.j");
        }

        SymbolTable.pop();
        return "";
    }

    @Override
    public String visit(ActorInstantiation actorInstantiation) {
        String code = "";
        String type = actorInstantiation.getType().toString();
        String name = actorInstantiation.getIdentifier().getName();

        if(mainJLevel == 0){
            code += "new " + type + "\n";
            code += "dup\n";
            code += "ldc " + Integer.toString(findActor(type).getQueueSize()) + "\n";
            code += "invokespecial " + type + "/<init>(I)V\n";
            code += "astore " + actInsts.get(name) + "\n";
        }
        else if(mainJLevel == 1){
            code += "aload " + actInsts.get(name) + "\n";
            for(Identifier knownActs : actorInstantiation.getKnownActors())
                code += "aload " + actInsts.get(knownActs.getName()) + "\n";

            code += "invokevirtual " + type + "/setKnownActors(";
            for(Identifier knownActs : actorInstantiation.getKnownActors())
                code += "L" + knownActs.getType().toString() + ";";
            code += ")V\n";
        }
        else if(mainJLevel == 2){
            if(findActor(type).getInitHandler() != null) {
                code += "aload " + actInsts.get(name) + "\n";
                for(Expression args : actorInstantiation.getInitArgs())
                    code += visitExpr(args);
                code += "invokevirtual " + type + "/initial(";
                for(Expression args : actorInstantiation.getInitArgs())
                    code += typeInJasmine(args.getType());
                code += ")V\n";
            }
        }
        else if(mainJLevel == 3){
            code += "aload " + actInsts.get(name) + "\n";
            code += "invokevirtual " + type + "/start()V\n";
        }
        return code;
    }

    @Override
    public String visit(UnaryExpression unaryExpression) {
        String code = "";
        UnaryOperator oper = unaryExpression.getUnaryOperator();

        if(oper == UnaryOperator.postinc || oper == UnaryOperator.postdec) {
            try {
                int pos = handlerVars.get(((Identifier) unaryExpression.getOperand()).getName()) + 1;
                code += visitExpr(unaryExpression.getOperand());
                code += "iinc " + Integer.toString(pos);
                code += (oper == UnaryOperator.postinc) ? " 1\n" : " -1\n";
            }
            catch (Exception e1) {
                code += "aload_0\ndup\ngetfiled " + curActor + "/" + ((Identifier) unaryExpression.getOperand()).getName() +
                        " I\n" + "dup_x1\niconst_1\n";
                code += (oper == UnaryOperator.postinc) ? "iadd\n" : "isub\n";
                code += "putfield " + curActor + "/" + ((Identifier) unaryExpression.getOperand()).getName() +" I\n" ;
            }
        }

        else if(oper == UnaryOperator.preinc || oper == UnaryOperator.predec){
            try{
                int pos = handlerVars.get(((Identifier) unaryExpression.getOperand()).getName()) + 1;
                code += "iinc " + Integer.toString(pos);
                code += (oper == UnaryOperator.preinc) ? " 1\n" : " -1\n";
                code += visitExpr(unaryExpression.getOperand());
            }
            catch (Exception e2){
                code += "aload_0\ndup\ngetfiled " + curActor + "/" + ((Identifier) unaryExpression.getOperand()).getName() +
                        " I\n" + "iconst_1\n";
                code += (oper == UnaryOperator.preinc) ? "iadd\n" : "isub\n";
                code += "dup_x1\n";
                code += "putfield " + curActor + "/" + ((Identifier) unaryExpression.getOperand()).getName() +" I\n" ;
            }

        }
        else if(oper == UnaryOperator.minus){
            code += visitExpr(unaryExpression.getOperand());
            code += "ineg\n";
        }
        else if(oper == UnaryOperator.not){
            String label1 = "Label" + Integer.toString(label++);
            String label2 = "Label" + Integer.toString(label++);

            code += visitExpr(unaryExpression.getOperand());
            code += "ifne " + label1 + "\n" + "iconst_1\n";
            code += "goto " + label2 + "\n";
            code += label1 + ":\n" + "iconst_0\n" + label2 + ":\n";
        }
        return code;
    }

    @Override
    public String visit(BinaryExpression binaryExpression) {
        String code = "";
        String left = visitExpr(binaryExpression.getLeft());
        String right = visitExpr(binaryExpression.getRight());

        BinaryOperator oper = binaryExpression.getBinaryOperator();

        if(oper == BinaryOperator.add)
            code = left + right + "iadd\n";
        else if(oper == BinaryOperator.sub)
            code = left + right + "isub\n";
        else if(oper == BinaryOperator.mult)
            code = left + right + "imul\n";
        else if(oper == BinaryOperator.div)
            code = left + right + "idiv\n";
        else if(oper == BinaryOperator.mod)
            code = left + right + "irem\n";
        else if(oper == BinaryOperator.eq || oper == BinaryOperator.neq || oper == BinaryOperator.gt || oper == BinaryOperator.lt){
            if(binaryExpression.getLeft().getType() instanceof IntType || binaryExpression.getLeft().getType() instanceof BooleanType)
                code += left + right + "if_icmp";
            else
                code += left + right + "if_acmp";
            if(oper == BinaryOperator.eq)
                code += "ne ";
            else if(oper == BinaryOperator.neq)
                code += "eq ";
            else if(oper == BinaryOperator.gt)
                code += "le ";
            else if(oper == BinaryOperator.lt)
                code += "ge ";

            if(forcond)
                code += "Label" + Integer.toString(label-1) + "\n";
            else {
                code += "Label" + Integer.toString(label) + "\n" + "iconst_1\n";
                code += "goto Label" + Integer.toString(label + 1) + "\n";
                code += "Label" + Integer.toString(label) + ":\n";
                code += "iconst_0\n";
                code += "Label" + Integer.toString(label + 1) + ":\n";
                label += 2;
            }
            forcond = false;
        }
        else if(oper == BinaryOperator.and){
            String label1 = "Label" + Integer.toString(label++);
            String label2 = "Label" + Integer.toString(label++);
            code += visitExpr(binaryExpression.getLeft());
            code += "ifeq " + label1 + "\n";
            code += visitExpr(binaryExpression.getRight());
            code += "ifeq " + label1 + "\n" + "iconst_1\n" + "goto " + label2 + "\n" + label1 + ":\n" + "iconst_0\n" + label2 + ":\n";
        }
        else if(oper == BinaryOperator.or){
            String label1 = "Label" + Integer.toString(label++);
            String label2 = "Label" + Integer.toString(label++);
            code += visitExpr(binaryExpression.getLeft());
            code += "ifne " + label1 + "\n";
            code += visitExpr(binaryExpression.getRight());
            code += "ifne " + label1 + "\n" + "iconst_0\n" + "goto " + label2 + "\n" + label1 + ":\n" + "iconst_1\n" + label2 + ":\n";
        }

        return code;
    }

    @Override
    public String visit(ArrayCall arrayCall) {
        String code = visitExpr(arrayCall.getArrayInstance());
        code += visitExpr(arrayCall.getIndex());
        code += "iaload\n";
        return code;
    }

    @Override
    public String visit(ActorVarAccess actorVarAccess) {
        return "aload_0\ngetfield " + curActor + "/" + actorVarAccess.getVariable().getName() +
                " " + typeInJasmine(actorVarAccess.getVariable().getType()) + "\n";
    }

    @Override
    public String visit(Identifier identifier) {
        String code = "";

        try {
            SymbolTableVariableItem temp = (SymbolTableVariableItem) SymbolTable.top.getInCurrentScope(SymbolTableVariableItem.STARTKEY + identifier.getName());
            Type idType = identifier.getType();
            if(idType instanceof StringType || idType instanceof ArrayType)
                code += "aload " + Integer.toString(handlerVars.get(identifier.getName())+1) + "\n";
            else
                code += "iload " + Integer.toString(handlerVars.get(identifier.getName())+1) + "\n";
        }
        catch (ItemNotFoundException e1){
            try {
                SymbolTableVariableItem temp1 = (SymbolTableVariableItem) SymbolTable.top.get(SymbolTableVariableItem.STARTKEY + identifier.getName());
                code += "aload_0\n";
                code += "getfield " + curActor + "/" + identifier.getName() + " " + typeInJasmine(temp1.getType()) + "\n";
            }
            catch (ItemNotFoundException e2){}
        }

        return code;
    }

    @Override
    public String visit(Self self) {
        return "aload_0\n";
    }

    @Override
    public String visit(Sender sender) {
        return "aload_1\n";
    }

    @Override
    public String visit(BooleanValue value) {
        int boolToInt = value.getConstant() ? 1 : 0;
        String code = "ldc " + boolToInt + "\n";
        return code;
    }

    @Override
    public String visit(IntValue value) {
        String code = "ldc " + Integer.toString(value.getConstant()) + "\n";
        return code;
    }

    @Override
    public String visit(StringValue value) {
        String code = "ldc " + value.getConstant() + "\n";
        return code;
    }

    @Override
    public String visit(Block block) {
        String code = "";

        for(Statement body : block.getStatements())
            code += visitStatement(body);

        return code;
    }

    @Override
    public String visit(Conditional conditional) {
        String code = "";

        String elseLabel = "Label" + Integer.toString(label++);
        String afterLabel = "";

        forcond = true;
        code += visitExpr(conditional.getExpression());
//        code += "ifeq " + elseLabel + "\n";
        code += visitStatement(conditional.getThenBody());
        boolean hasElse = (conditional.getElseBody() != null);
        if(hasElse)
            afterLabel = "Label" + Integer.toString(label++);
        if(hasElse)
            code += "goto " + afterLabel + "\n";

        code += elseLabel + ":\n";

        if(hasElse) {
            code += visitStatement(conditional.getElseBody());
            code += afterLabel + ":\n";
        }

        return code;
    }

    @Override
    public String visit(For loop) {
        String code = "";

        String startLabel = "Label" + Integer.toString(label++);
        String afterLabel = "Label" + Integer.toString(label++);
        String updateLabel = "LabelUpdate" + Integer.toString(coLabel++);

        continueL.push(updateLabel);
        breakL.push(afterLabel);

        code += visitStatement(loop.getInitialize());
        code += startLabel + ":\n";
        forcond = true;
        code += visitExpr(loop.getCondition());
        code += visitStatement(loop.getBody());
        code += updateLabel + ":\n";
        code += visitStatement(loop.getUpdate());
        code += "goto " + startLabel + "\n";
        code += afterLabel + ":\n";

        continueL.pop();
        breakL.pop();
        return code;
    }

    @Override
    public String visit(Break breakLoop) {
        return  "goto " + breakL.peek() + "\n";
    }

    @Override
    public String visit(Continue continueLoop) {
        return "goto " + continueL.peek() + "\n";
    }

    @Override
    public String visit(MsgHandlerCall msgHandlerCall) {
        String code = visitExpr(msgHandlerCall.getInstance());

        code += "aload_0\n";

        for(Expression args : msgHandlerCall.getArgs())
            code += visitExpr(args);

        if(msgHandlerCall.getInstance().getType() != null)
            code += "invokevirtual " + msgHandlerCall.getInstance().getType().toString() + "/send_" + msgHandlerCall.getMsgHandlerName().getName() + "(LActor;";
        else
            code += "invokevirtual " + "Actor/send_" + msgHandlerCall.getMsgHandlerName().getName() + "(LActor;";

        for(Expression arg : msgHandlerCall.getArgs()) {
            TypeCheck t1 = new TypeCheck();
            t1.visitExpr(arg);
            code += typeInJasmine(arg.getType());
        }

        code += ")V\n";
        return code;
    }

    @Override
    public String visit(Print print) {
        String code = "getstatic java/lang/System/out Ljava/io/PrintStream;\n";
        Expression printArg = print.getArg();
        code += visitExpr(printArg);
        code += "invokevirtual java/io/PrintStream/println";

        if(printArg.getType() instanceof IntType)
            code += "(I)V\n";
        else if(printArg.getType() instanceof StringType)
            code += "(Ljava/lang/String;)V\n";
        else if(printArg.getType() instanceof BooleanType)
            code += "(Z)V\n";
        else if(printArg.getType() instanceof ArrayType)
            code += "(Ljava/lang/Object;)V\n";

        return code;
    }

    @Override
    public String visit(Assign assign) {
        String code = "";
        Expression lval = assign.getlValue();
        Expression rval = assign.getrValue();

        String right = visitExpr(rval);

        if(lval instanceof Identifier) {
            Boolean CF = classField((Identifier) lval);
            if (CF == true) {
                code += "aload_0\n";
                code += right;
                code += "putfield " + curActor + "/" + ((Identifier) lval).getName() + " " + typeInJasmine(rval.getType()) + "\n";
            } else {
                code += right;
                if (lval.getType() instanceof ArrayType || lval.getType() instanceof StringType)
                    code += "astore " + Integer.toString(handlerVars.get(((Identifier) lval).getName())+1) + "\n";
                else
                    code += "istore " + Integer.toString(handlerVars.get(((Identifier) lval).getName())+1) + "\n";
            }
        }
        else if(lval instanceof ArrayCall){
            code += visitExpr(((ArrayCall) lval).getArrayInstance());
            code += visitExpr(((ArrayCall) lval).getIndex());
            code += right;
            code += "iastore\n";
        }
        else if(lval instanceof ActorVarAccess){
            code += "aload_0\n";
            code += right;
            code += "putfield " + curActor + "/" + ((ActorVarAccess) lval).getVariable().getName()
                    + " " + typeInJasmine(rval.getType()) + "\n";
        }

        return code;
    }
}
