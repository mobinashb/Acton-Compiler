package main.visitor;

import main.ast.node.*;
import main.ast.node.Node;
import main.ast.node.Program;
import main.ast.node.declaration.*;
import main.ast.node.declaration.handler.HandlerDeclaration;
import main.ast.node.declaration.handler.MsgHandlerDeclaration;
import main.ast.node.declaration.VarDeclaration;
import main.ast.node.declaration.handler.InitHandlerDeclaration;
import main.ast.node.expression.*;
import main.ast.node.expression.values.BooleanValue;
import main.ast.node.expression.values.IntValue;
import main.ast.node.expression.values.StringValue;
import main.ast.node.statement.*;

public interface Visitor {

    String visit (Program program);

    //Declarations
    String visit (ActorDeclaration actorDeclaration);
    String visit (HandlerDeclaration handlerDeclaration);
    String visit (VarDeclaration varDeclaration);

    //main
    String visit(Main mainActors);
    String visit(ActorInstantiation actorInstantiation);

    //Expressions
    String visit(UnaryExpression unaryExpression);
    String visit(BinaryExpression binaryExpression);
    String visit(ArrayCall arrayCall);
    String visit(ActorVarAccess actorVarAccess);
    String visit(Identifier identifier);
    String visit(Self self);
    String visit(Sender sender);
    String visit(BooleanValue value);
    String visit(IntValue value);
    String visit(StringValue value);

    //Statements
    String visit(Block block);
    String visit(Conditional conditional);
    String visit(For loop);
    String visit(Break breakLoop);
    String visit(Continue continueLoop);
    String visit(MsgHandlerCall msgHandlerCall);
    String visit(Print print);
    String visit(Assign assign);
}
