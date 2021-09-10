package main.visitorPre.nameAnalyser;

import main.ast.node.*;
import main.ast.node.Program;
import main.ast.node.declaration.*;
import main.ast.node.declaration.handler.HandlerDeclaration;
import main.ast.node.declaration.handler.InitHandlerDeclaration;
import main.ast.node.declaration.handler.MsgHandlerDeclaration;
import main.ast.node.expression.operators.BinaryOperator;
import main.ast.node.expression.operators.UnaryOperator;
import main.ast.node.statement.*;
import main.ast.node.expression.*;
import main.ast.node.expression.values.*;
import main.ast.type.Type;
import main.ast.type.actorType.ActorType;
import main.ast.type.arrayType.ArrayType;
import main.ast.type.noType.NoType;
import main.ast.type.primitiveType.BooleanType;
import main.ast.type.primitiveType.IntType;
import main.ast.type.primitiveType.StringType;
import main.symbolTable.*;
import main.symbolTable.symbolTableVariableItem.*;
import main.symbolTable.itemException.*;
import main.visitor.VisitorImpl;

import java.util.*;

public class TypeCheck extends  VisitorImpl{
    private SymbolTableConstructor symbolTableConstructor;
    private int inLoop;
    private ArrayList<ActorDeclaration> actors;
    private ArrayList<ActorDeclaration> actorsRT;
    private boolean inMain;

    public TypeCheck()
    {
        symbolTableConstructor = new SymbolTableConstructor();
        inLoop = 0;
        actors = new ArrayList<>();
        actorsRT = new ArrayList<>();
        inMain = false;

    }


    @Override
    public String visit(Program program){

        symbolTableConstructor.constructProgramSymbolTable();
        actors = new ArrayList<>(program.getActors());
        for(ActorDeclaration actorDeclaration : program.getActors())
            actorDeclaration.accept(this);
        program.getMain().accept(this);
        return "";
    }

    @Override
    public String visit(ActorDeclaration actorDeclaration) {
        symbolTableConstructor.construct(actorDeclaration);
        actorsRT.add(actorDeclaration);
        if (actorDeclaration.getParentName() != null) {
            String parentName = actorDeclaration.getParentName().getName();
            if (getDeclaration(parentName) == null) {
                String errorMessage = "Line:" + actorDeclaration.getLine() + ":" +
                        "actor " + parentName + " is not declared";
                System.out.println(errorMessage);
            }
        }
        for (VarDeclaration varDeclaration: actorDeclaration.getKnownActors()) {
            ActorType type = (ActorType)varDeclaration.getType();
            Identifier name = type.getName();
            if (getDeclaration(name.getName()) == null) {
                String errorMessage = "Line:" + varDeclaration.getLine() + ":" +
                        "actor " + name.getName() + " is not declared";
                System.out.println(errorMessage);
            }
            varDeclaration.getIdentifier().setType(type);
        }
        for (VarDeclaration varDeclaration: actorDeclaration.getActorVars()) {
            varDeclaration.accept(this);
        }
        if (actorDeclaration.getInitHandler() != null)
            actorDeclaration.getInitHandler().accept(this);
        for (MsgHandlerDeclaration msgHandlerDeclaration: actorDeclaration.getMsgHandlers())
            msgHandlerDeclaration.accept(this);
        SymbolTable.pop();
        return "";
    }

    @Override
    public String visit(HandlerDeclaration handlerDeclaration) {
        if (handlerDeclaration == null)
            return "";
        symbolTableConstructor.construct(handlerDeclaration);
        for (VarDeclaration argDeclaration : handlerDeclaration.getArgs())
            argDeclaration.accept(this);
        for (VarDeclaration localVariable : handlerDeclaration.getLocalVars())
            localVariable.accept(this);
        for (Statement statement : handlerDeclaration.getBody()) {
            if (handlerDeclaration instanceof InitHandlerDeclaration) {
                if (statement instanceof MsgHandlerCall &&
                        ((MsgHandlerCall) statement).getInstance() instanceof Sender) {
                    String errorMessage = "Line:" + statement.getLine() + ":" +
                            "no sender in initial msghandler";
                    System.out.println(errorMessage);
                }
                if (statement instanceof Assign) {
                    Expression left = ((Assign) statement).getlValue();
                    Expression right = ((Assign) statement).getrValue();
                    if (left instanceof Sender || right instanceof Sender) {
                        String errorMessage = "Line:" + statement.getLine() + ":" +
                                "no sender in initial msghandler";
                        System.out.println(errorMessage);
                    }
                }
            }
            visitStatement(statement);
        }
        SymbolTable.pop();
        return "";
    }

    @Override
    public String visit(VarDeclaration varDeclaration) {
        if(varDeclaration == null)
            return "";
        visitExpr(varDeclaration.getIdentifier());
        return "";
    }

    @Override
    public String visit(Main programMain) {
        if(programMain == null)
            return "";
        symbolTableConstructor.construct(programMain);
        inMain = true;
        for(ActorInstantiation mainActor : programMain.getMainActors())
            mainActor.accept(this);
        SymbolTable.pop();
        inMain = false;
        return "";
    }

    ActorDeclaration getDeclaration (String name)
    {
        for (ActorDeclaration actorDec : actors) {
            Identifier actorName = actorDec.getName();
            if (name.equals(actorName.getName())) {
                return actorDec;
            }
        }
        return null;
    }

    @Override
    public String visit(ActorInstantiation actorInstantiation) {
        if(actorInstantiation == null)
            return "";
        ActorType type = (ActorType)actorInstantiation.getType();
        String name = type.getName().getName();
        ActorDeclaration actorDec = getDeclaration(name);
        if (actorDec == null) {
            String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                    "actor " + name + " is not declared";
            System.out.println(errorMessage);
            return "";
        }
        ArrayList<VarDeclaration> refKnownActors = actorDec.getKnownActors();
        ArrayList<Identifier> knownActors = actorInstantiation.getKnownActors();
        int i = 0;
        boolean notMatched = (knownActors.size() != refKnownActors.size());
        for (Identifier knownActor : knownActors) {
            visitExpr(knownActor);
            if (knownActor.getType() instanceof NoType ||
                    refKnownActors.get(i).getType() instanceof NoType) {
                i++;
                continue;
            }
            if (!notMatched) {
                ActorType knownActorType = (ActorType) knownActor.getType();
                ActorType refKnownActorType = (ActorType) refKnownActors.get(i).getType();
                if (!isSubtypeOf(refKnownActorType, knownActorType)){
                    String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                            "knownactors do not match with definition";
                    System.out.println(errorMessage);
                    break;
                }
            }
            i++;
        }
        ArrayList<Expression> initArgs = actorInstantiation.getInitArgs();
        for (Expression initArg : initArgs)
            visitExpr(initArg);
        if (actorDec.getInitHandler() == null && initArgs.size() > 0) {
            String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                    "arguments do not match with definition";
            System.out.println(errorMessage);
        }
        else if (actorDec.getInitHandler() != null) {
            ArrayList<VarDeclaration> args = actorDec.getInitHandler().getArgs();
            if (args.size() != initArgs.size()) {
                String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                        "arguments do not match with definition";
                System.out.println(errorMessage);
            }
            else if (!(args.size() == 0)) {
                int j = 0;
                for (Expression initArg : initArgs) { //this should be printed first or the known actors should?
                    if (!(initArg.getType() instanceof NoType)) {
                        if (!(initArg.getType().getClass().getName().equals(args.get(j).getType().getClass().getName()))) {
                            String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                                    "arguments do not match with definition";
                            System.out.println(errorMessage);
                            break;
                        }
                    }
                    j++;
                }
            }
        }
        if (notMatched) {
            String errorMessage = "Line:" + actorInstantiation.getLine() + ":" +
                    "knownactors do not match with definition";
            System.out.println(errorMessage);
        }
        return "";
    }

    @Override
    public String visit(UnaryExpression unaryExpression) {
        if (unaryExpression == null)
            return "";
        Expression operand = unaryExpression.getOperand();
        visitExpr(operand);
        UnaryOperator op = unaryExpression.getUnaryOperator();
        Type expressionType = operand.getType();
        if (op == UnaryOperator.minus ||
                op == UnaryOperator.predec ||
                op == UnaryOperator.postdec ||
                op == UnaryOperator.preinc ||
                op == UnaryOperator.postinc)
        {
            String operator;
            if (op == UnaryOperator.predec || op == UnaryOperator.postdec) operator = "decrement";
            else if (op == UnaryOperator.minus) operator = "minus";
            else operator = "increment";
            if (op != UnaryOperator.minus && !(operand instanceof ArrayCall || operand instanceof Identifier || operand instanceof ActorVarAccess)) {
                String errorMessage = "Line:" + operand.getLine() + ":" +
                        "lvalue required as " + operator + " operand";
                System.out.println(errorMessage);
                unaryExpression.setType(new NoType());
            }
            if (expressionType instanceof NoType)
                unaryExpression.setType(new NoType());
            else if (!(expressionType instanceof IntType)) {
                unaryExpression.setType(new NoType());
                String errorMessage = "Line:" + operand.getLine() + ":" +
                        "unsupported operand type for " + operator;
                System.out.println(errorMessage);
                return "";
            }
            else unaryExpression.setType(new IntType());
        }
        else if (op == UnaryOperator.not)
        {
            if (!(operand instanceof Identifier || operand instanceof ActorVarAccess)) {
                String errorMessage = "Line:" + operand.getLine() + ":" +
                        "lvalue required as " + op.name() + " operand";
                System.out.println(errorMessage);
                unaryExpression.setType(new NoType());
            }
            if (expressionType instanceof NoType)
                unaryExpression.setType(new NoType());
            else if (!(expressionType instanceof BooleanType)) {
                unaryExpression.setType(new NoType());
                String errorMessage = "Line:" + operand.getLine() + ":" +
                        "unsupported operand type for " + unaryExpression.getUnaryOperator().name();
                System.out.println(errorMessage);
                return "";
            }
            else unaryExpression.setType(new BooleanType());
        }
        else {
            unaryExpression.setType(new NoType());
            String errorMessage = "Line:" + operand.getLine() + ":" +
                    "unsupported operand type for " + unaryExpression.getUnaryOperator().name();
            System.out.println(errorMessage);
        }
        return "";
    }

    boolean isSubtypeOf (ActorType left, ActorType right)
    {
        ArrayList<ActorDeclaration> rightAncestors = new ArrayList<>();
        String leftName = left.getName().getName();
        String rightName = right.getName().getName();
        rightAncestors.add(getDeclaration(rightName));
        rightAncestors = union(rightAncestors, getAncestors(getDeclaration(rightName)));
        for (ActorDeclaration act : rightAncestors) {
            if (act.getName().getName().equals(leftName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String visit(BinaryExpression binaryExpression) {
        if(binaryExpression == null)
            return "";
        BinaryOperator operator = binaryExpression.getBinaryOperator();
        Expression left = binaryExpression.getLeft();
        Expression right = binaryExpression.getRight();
        visitExpr(left);
        visitExpr(right);
        if(operator == BinaryOperator.mult || operator == BinaryOperator.div ||
                operator == BinaryOperator.add || operator == BinaryOperator.sub ||
                operator == BinaryOperator.mod){
            if((!(left.getType() instanceof IntType) && !(left.getType() instanceof  NoType)) ||
                    ((!(right.getType() instanceof IntType) && !(right.getType() instanceof  NoType)))) {
                binaryExpression.setType(new NoType());
                String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                        "unsupported operand type for " + operator.name();
                System.out.println(errorMessage);
                return "";
            }
            else if((left.getType() instanceof  NoType) || (right.getType() instanceof  NoType))
                binaryExpression.setType(new NoType());
            else
                binaryExpression.setType(new IntType());
            return "";
        }
        else if(operator == BinaryOperator.gt || operator == BinaryOperator.lt){
            if((!(left.getType() instanceof IntType) && !(left.getType() instanceof  NoType)) ||
                    ((!(right.getType() instanceof IntType) && !(right.getType() instanceof  NoType)))) {
                binaryExpression.setType(new NoType());
                String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                        "unsupported operand type for " + operator.name();
                System.out.println(errorMessage);
                return "";
            }
            else if((left.getType() instanceof  NoType) || (right.getType() instanceof  NoType))
                binaryExpression.setType(new NoType());
            else
                binaryExpression.setType(new BooleanType());
            return "";
        }
        else if(operator == BinaryOperator.and || operator == BinaryOperator.or) {
            if((!(left.getType() instanceof BooleanType) && !(left.getType() instanceof  NoType)) ||
                    ((!(right.getType() instanceof BooleanType) && !(right.getType() instanceof  NoType)))) {
                binaryExpression.setType(new NoType());
                String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                        "unsupported operand type for " + operator.name();
                System.out.println(errorMessage);
                return "";
            }
            else if((left.getType() instanceof  NoType) || (right.getType() instanceof  NoType))
                binaryExpression.setType(new NoType());
            else
                binaryExpression.setType(new BooleanType());
            return "";
        }
        else if(operator == BinaryOperator.eq || operator == BinaryOperator.neq ||
                operator == BinaryOperator.assign) {
            if(left.getType() instanceof NoType || right.getType() instanceof NoType) {
                binaryExpression.setType((new NoType()));
            }
            else if (left.getType() instanceof ActorType && right.getType() instanceof ActorType) {
                if (!isSubtypeOf((ActorType)left.getType(), (ActorType)right.getType())) {
                    binaryExpression.setType(new NoType());
                    String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                            "left and right type not matched for " + operator.name();
                    System.out.println(errorMessage);
                }
            }
            else if(!(left.getType().getClass().equals(right.getType().getClass()))) {
                binaryExpression.setType(new NoType());
                String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                        "left and right type not matched for " + operator.name();
                System.out.println(errorMessage);
                return "";
            }

            else if((left.getType() instanceof ArrayType && right.getType() instanceof ArrayType)) {
                if(((ArrayType) left.getType()).getSize() != ((ArrayType) right.getType()).getSize()) {
                    binaryExpression.setType(new NoType());
                    String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                            "operation " + operator.name() + " requires equal array sizes";
                    System.out.println(errorMessage);
                    return "";
                }
            }
            if (operator == BinaryOperator.assign) {
                if (!(left instanceof Identifier || left instanceof ActorVarAccess || left instanceof ArrayCall)) {
                    binaryExpression.setType(new NoType());
                    String errorMessage = "Line:" + binaryExpression.getLine() + ":" +
                            "left side of assignment must be a valid lvalue";
                    System.out.println(errorMessage);
                    return "";
                }
            }
            binaryExpression.setType(new BooleanType());
        }
        return "";
    }


    @Override
    public String visit(ArrayCall arrayCall) {
        visitExpr(arrayCall.getArrayInstance());
        visitExpr(arrayCall.getIndex());
        arrayCall.setType(new IntType());
        return "";
    }

    @Override
    public String visit(ActorVarAccess actorVarAccess) {
        if(actorVarAccess == null)
            return "";
        visitExpr(actorVarAccess.getSelf());
        if (!(actorVarAccess.getSelf().getType() instanceof NoType))
            visitExpr(actorVarAccess.getVariable());
        else actorVarAccess.getVariable().setType(new NoType());
        actorVarAccess.setType(actorVarAccess.getVariable().getType());
        return "";
    }

    @Override
    public String visit(Identifier identifier) {
        if(identifier == null)
            return "";
        try {
            SymbolTableVariableItem temp = (SymbolTableVariableItem) SymbolTable.top.get(SymbolTableVariableItem.STARTKEY + identifier.getName());
            identifier.setType(temp.getType());
        }
        catch (ItemNotFoundException e1){
            identifier.setType(new NoType());
            String errorMessage = "Line:" + identifier.getLine() + ":" +
                    "variable " + identifier.getName() + " is not declared";
            System.out.println(errorMessage);
        }
        return "";
    }

    @Override
    public String visit(Self self) {
        if (inMain) {
            self.setType(new NoType());
            String errorMessage = "Line:" + self.getLine() + ":" +
                    "self doesn't refer to any actor";
            System.out.println(errorMessage);
            return "";
        }
        for (int i = 0; i < actorsRT.size(); i++) {
            ActorDeclaration actorDec = actorsRT.get(i);
            Identifier actorName = actorDec.getName();
            self.setType(new ActorType(actorName));
        }
        return "";
    }

    @Override
    public String visit(Sender sender) {
        return "";
    }

    @Override
    public String visit(BooleanValue value) {
        return "";
    }

    @Override
    public String visit(IntValue value) {
        return "";
    }

    @Override
    public String visit(StringValue value) {
        return "";
    }

    ArrayList<ActorDeclaration> union (ArrayList<ActorDeclaration> A, ArrayList<ActorDeclaration> B) {
        ArrayList<ActorDeclaration> unionArray = new ArrayList<ActorDeclaration>();
        if (A == null && B != null) {
            return B;
        }
        else if (A != null && B == null) {
            return A;
        }
        else if (A == null) {
            return null;
        }
        for (ActorDeclaration i : A)
        {
            if (!unionArray.contains(i))
            {
                unionArray.add(i);
            }
        }
        for (ActorDeclaration j : B)
        {
            if (!unionArray.contains(j))
            {
                unionArray.add(j);
            }
        }
        return unionArray;
    }

    ArrayList<ActorDeclaration> getAncestors(ActorDeclaration act)
    {
        if (act == null) return null;
        Identifier parent = act.getParentName();
        if (parent == null) {
            return null;
        }
        ArrayList<ActorDeclaration> ancestors = new ArrayList<ActorDeclaration>();
        for (ActorDeclaration actorDec : actors) {
            Identifier actorName = actorDec.getName();
            if (actorName.getName().equals(parent.getName())) {
                ancestors.add(actorDec);
                return union(ancestors, getAncestors(actorDec));
            }
        }
        return null;
    }

    MsgHandlerDeclaration findMsghandler (Identifier name, ActorDeclaration act)
    {
        for (MsgHandlerDeclaration msgDec : act.getMsgHandlers()) {
            if (msgDec.getName().getName().equals(name.getName())) {
                return msgDec;
            }
        }
        ArrayList<ActorDeclaration> ancestors =  getAncestors(act);
        if (ancestors == null) {
            return null;
        }
        for (ActorDeclaration dec : ancestors) {
            ArrayList<MsgHandlerDeclaration> msgDecs = dec.getMsgHandlers();
            if (msgDecs == null) {
                continue;
            }
            for (MsgHandlerDeclaration msgDec : msgDecs) {
                if (msgDec.getName().getName().equals(name.getName())) {
                    return msgDec;
                }
            }
        }
        return null;
    }


    @Override
    public String visit(MsgHandlerCall msgHandlerCall) {
        if(msgHandlerCall == null) {
            return "";
        }
        MsgHandlerDeclaration foundMsgHandler = null;
        try {
            Expression instance = msgHandlerCall.getInstance();
            visitExpr(instance);
            String instanceName = "";
            if (instance instanceof Identifier) {
                instanceName = ((Identifier)instance).getType().toString();
            }
            Identifier name = msgHandlerCall.getMsgHandlerName();
            if (instance instanceof Self) {
                visitExpr(instance);
                instanceName = instance.getType().toString();
            }
            Identifier actorName = null;
            boolean isCallable = false;
            for (ActorDeclaration actorDec : actors) {
                actorName = actorDec.getName();
                if (instanceName.equals(actorName.getName())) {
                    isCallable = true;
                    foundMsgHandler = findMsghandler(name, actorDec);
                    break;
                }
            }
            if (instance instanceof Self) {
                isCallable = true;
            }
            if ((instance instanceof Self || instance instanceof Identifier) &&
                    foundMsgHandler == null && actorName != null &&
                    isCallable) {
                String errorMessage = "Line:" + msgHandlerCall.getLine() + ":" +
                        "there is no msghandler named " + name.getName() + " in actor " + actorName.getName();
                System.out.println(errorMessage);
                return "";
            }
            if (instance instanceof Sender) {
                isCallable = true;
            }
            if (!isCallable) {
                String errorMessage = "Line:" + msgHandlerCall.getLine() + ":" +
                        "variable " + ((Identifier)instance).getName() + " is not callable";
                System.out.println(errorMessage);
                return "";
            }
            if (foundMsgHandler == null) {
                return "";
            }
            ArrayList<VarDeclaration> args = foundMsgHandler.getArgs();
            if (args == null && msgHandlerCall.getArgs() == null) {
                return "";
            }
            if (args == null || msgHandlerCall.getArgs() == null) {
                String errorMessage = "Line:" + msgHandlerCall.getLine() + ":" +
                        "arguments do not match with definition";
                System.out.println(errorMessage);
                return "";
            }
            int i = 0;
            ArrayList<Expression> arguments = msgHandlerCall.getArgs();
            boolean notMatched = (args.size() != arguments.size());
            for (Expression argument : arguments)
                visitExpr(argument);
            for (Expression argument : arguments) {
                if (!notMatched) {
                    if (args.get(i).getType().equals(argument.getType())) {
                        System.out.println(args.get(i).getType() + "==" + argument.getType());
                        if (!(argument.getType() instanceof NoType)) {
                            String errorMessage = "Line:" + msgHandlerCall.getLine() + ":" +
                                    "arguments do not match with definition";
                            System.out.println(errorMessage);
                            return "";
                        }
                    }
                    i++;
                }
            }
            if (notMatched) {
                String errorMessage = "Line:" + msgHandlerCall.getLine() + ":" +
                        "arguments do not match with definition";
                System.out.println(errorMessage);
                return "";
            }
        }
        catch(NullPointerException npe) {
            System.out.println("null pointer exception occurred");
        }
        return "";
    }

    @Override
    public String visit(Block block) {
        if(block == null)
            return "";
        for(Statement statement : block.getStatements())
            visitStatement(statement);
        return "";
    }

    @Override
    public String visit(Conditional conditional) {
        Expression condition = conditional.getExpression();
        visitExpr(condition);
        Type conditionType = condition.getType();
        if (!(conditionType instanceof BooleanType || conditionType instanceof NoType)) {
            String errorMessage = "Line:" + conditional.getLine() + ":" +
                    "condition type must be boolean";
            System.out.println(errorMessage);
        }
        visitStatement(conditional.getThenBody());
        visitStatement(conditional.getElseBody());
        return "";
    }

    @Override
    public String visit(For loop) {
        visitStatement(loop.getInitialize());
        Expression condition = loop.getCondition();
        if (condition != null) {
            visitExpr(condition);
            Type conditionType = condition.getType();
            if (!(conditionType instanceof BooleanType || conditionType instanceof NoType)) {
                String errorMessage = "Line:" + loop.getLine() + ":" +
                        "condition type must be Boolean";
                System.out.println(errorMessage);
            }
        }
        visitStatement(loop.getUpdate());
        inLoop++;
        visitStatement(loop.getBody());
        inLoop--;
        return "";
    }

    @Override
    public String visit(Break b) {
        if (inLoop < 1) {
            String errorMessage = "Line:" + b.getLine() + ":" +
                    "break statement not within loop";
            System.out.println(errorMessage);
        }
        return "";
    }

    @Override
    public String visit(Continue c) {
        if (inLoop < 1) {
            String errorMessage = "Line:" + c.getLine() + ":" +
                    "continue statement not within loop";
            System.out.println(errorMessage);
        }
        return "";
    }

    @Override
    public String visit(Print print) {
        if(print == null)
            return "";
        Expression arg = print.getArg();
        visitExpr(arg);
        Type argType = arg.getType();
        if (!(argType instanceof IntType || argType instanceof StringType
                || argType instanceof BooleanType || argType instanceof ArrayType)) {
            String errorMessage = "Line:" + print.getLine() + ":" +
                    "unsupported type for print";
            System.out.println(errorMessage);
        }
        return "";
    }

    @Override
    public String visit(Assign assign) {
        Expression left = assign.getlValue();
        Expression right = assign.getrValue();
        BinaryExpression temp = new BinaryExpression(left, right, BinaryOperator.assign);
        temp.setLine(assign.getLine());
        visitExpr(temp);
        return "";
    }
}
