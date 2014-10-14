sealed trait Expr


case class Identifier(id: String) extends Expr
case class TemplateIdentifier(id: String) extends Expr

case class IntegerLiteral(num: Integer) extends Expr
case class FloatingLiteral(num: Double) extends Expr
case class StringLiteral(str: String) extends Expr
case class BooleanLiteral(b: Boolean) extends Expr
case class ArrayLiteral(l: List[Expr]) extends Expr
case class MapLiteral(l: List[KeyValueLiteral]) extends Expr
case class KeyValueLiteral(k: String,v:Expr) extends Expr
case object NullLiteral extends Expr

case class ParameterRef(p: String) extends Expr;

case object EmptyArrayLiteral extends Expr
case object EmptyObjectLiteral extends Expr

case class DotReference(left:Expr, right:String) extends Expr
case class IndexReference(left:Expr, right:Expr) extends Expr
case class NullSafeReference(left:Expr, right:String) extends Expr
case class InvokeFunction(func: String, args:List[Expr]) extends Expr

case class NotOp(ex:Expr) extends Expr
