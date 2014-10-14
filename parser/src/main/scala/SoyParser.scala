import scala.util.parsing.combinator._

object App {
  def main(args: Array[String]): Unit = {
    SoyParser("$hoge")
    SoyParser("[]")
    SoyParser("[:]")
    SoyParser("func()")
    //SoyParser("\"hoge\"")
  }
}

object SoyParser extends JavaTokenParsers {
  def apply(s: String): Unit = {
    parseAll(expr, s) match {
      case Success(v, _) => println("Success: " + v)
      case x => println("Failure: " + x)
    }
  }

  // token
  def DOT: Parser[String] = "."

  def QUESTION_DOT: Parser[String] = "?."

  def LPAREN: Parser[String] = "("

  def RPAREN: Parser[String] = ")"

  def LBRACK: Parser[String] = "["

  def RBRACK: Parser[String] = "]"

  def PLUS: Parser[String] = "+"

  def MINUS: Parser[String] = "-"

  def MULT: Parser[String] = "*"

  def DIV: Parser[String] = "/"

  def MOD: Parser[String] = "%"

  def EQEQ: Parser[String] = "=="

  def NOTEQ: Parser[String] = "!="

  def LT: Parser[String] = "<"

  def LTEQ: Parser[String] = "<="

  def GT: Parser[String] = ">"

  def GTEQ: Parser[String] = ">="

  def AND: Parser[String] = "and"

  def OR: Parser[String] = "or"

  def NULL_LITERAL: Parser[String] = "null"

  def NOT: Parser[String] = "not"

  def ELVIS: Parser[String] = "?:"

  def QUESTION: Parser[String] = "?"

  def COLON: Parser[String] = ":"

  def COMMA: Parser[String] = ","

  //TODO need parameterRef?
  def STRING_LITERAL = stringLiteral

  //TODO (need unicode literal)
  def STRING_LITERAL_ESCAPE = "\b" | "\t" | "\n" | "\f" | "\r" | "\"" | "\'" | "\\"

  //TODO
  def BRACE_IN_STRING = "{" ~ STRING_LITERAL ~ "}"

  def STRING_LITERAL_BEGIN = "\"" | "'"

  def STRING_LITERAL_END = "\"" | "'"


  def IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9]*".r

  //TODO (need to remove reserved keyword?)
  def CAPTURED_IDENTIFIER = IDENTIFIER

  def TEMPLATE_IDENTIFIER = "call" ~ "[^a-zA-Z0-9_]".r

  def DELTEMPLATE_IDENTIFIER = "delcall" ~ "[^a-zA-Z0-9_]".r

  def NAMESPACE_IDENTIFIER = repsep(IDENTIFIER, ".")

  def INTEGER_LITERAL = decimalNumber

  def FLOATING_POINT_LITERAL = floatingPointNumber

  def BOOLEAN_LITERAL = "true" | "false"

  def PARAMETER_REF = "$" ~> IDENTIFIER

  def EMPTY_ARRAY_LITERAL: Parser[String] = "[]"

  def EMPTY_OBJECT_LITERAL: Parser[String] = "[:]"

  //TODO
  def CAPTURED_FUNCTION_IDENTIFIER = IDENTIFIER

  // parser
  def special_char = " " | "null" | "\r" | "\n" | "\t" | "{" | "}"

  def expr_list: Parser[List[Expr]] = repsep(expr, COMMA)

  def key_value: Parser[KeyValueLiteral] = string_literal ~ COLON ~ expr ^^ {
    case k ~ _ ~ v => KeyValueLiteral(k, v)
  }

  def key_value_list: Parser[MapLiteral] = repsep(key_value, COMMA) ^^ {
    l => MapLiteral(l)
  }

  def expr: Parser[Expr] =
    PARAMETER_REF ^^ { p => ParameterRef(p)} |
      //      CAPTURED_IDENTIFIER |
      EMPTY_ARRAY_LITERAL ^^ { _ => EmptyArrayLiteral} |
      EMPTY_OBJECT_LITERAL ^^ { _ => EmptyObjectLiteral} |
      CAPTURED_FUNCTION_IDENTIFIER ~ LPAREN ~ expr_list ~ RPAREN ^^ {
        case f ~ _ ~ args ~ _ => InvokeFunction(f, args)
      } |
      dot_reference |
      index_reference |
      null_safe_reference |
      //    expr ~ LPAREN ~ expr_list ~ RPAREN |
      //    expr ~ LPAREN ~ RPAREN |
      literal_value |
      NULL_LITERAL ^^ { _ => NullLiteral} |
      NOT ~> expr ^^ { ex => NotOp(ex)}

  //    expr ~ binary_operator ~ expr |
  //    LPAREN ~ expr ~ RPAREN |
  //    expr ~ ELVIS ~ expr |
  //    expr ~ QUESTION ~ expr ~ COLON ~ expr |

  def literal_value: Parser[Expr] =
    MINUS ~> INTEGER_LITERAL ^^ { n => IntegerLiteral(-1 * n.toInt)} |
      INTEGER_LITERAL ^^ { n => IntegerLiteral(n.toInt)} |
      MINUS ~> FLOATING_POINT_LITERAL ^^ { n => FloatingLiteral(-1 * n.toDouble)} |
      FLOATING_POINT_LITERAL ^^ { n => FloatingLiteral(-1 * n.toDouble)} |
      string_literal ^^ { s => StringLiteral(s)} |
      BOOLEAN_LITERAL ^^ { b => BooleanLiteral(b.toBoolean)} |
      LBRACK ~> expr_list <~ RBRACK ^^ { l => ArrayLiteral(l)} |
      LBRACK ~> key_value_list <~ RBRACK

  def string_literal = stringLiteral

  /*
  def string_literal = STRING_LITERAL_BEGIN ~ string_literal_buf ~ STRING_LITERAL_END |
    STRING_LITERAL_BEGIN ~ STRING_LITERAL_END

  def string_literal_buf: Parser[Any] = rep(STRING_LITERAL | STRING_LITERAL_ESCAPE | BRACE_IN_STRING)
  */

  def binary_operator = PLUS |
    MINUS |
    MULT |
    DIV |
    MOD |
    EQEQ |
    NOTEQ |
    LT |
    LTEQ |
    GT |
    GTEQ |
    AND |
    OR

  def dot_reference = chainl1(expr, CAPTURED_IDENTIFIER | INTEGER_LITERAL, "." ^^ {
    _ => (a: Expr, b: String) => DotReference(a, b)
  })

  def index_reference = chainl1(expr, expr <~ RBRACK, "[" ^^ {
    _ => (a: Expr, b: Expr) => IndexReference(a, b)
  })

  def null_safe_reference = chainl1(expr, CAPTURED_IDENTIFIER | INTEGER_LITERAL, QUESTION_DOT ^^ {
    _ => (a: Expr, b: String) => NullSafeReference(a, b)
  })

  def namespace_ident: Parser[Any] = CAPTURED_IDENTIFIER |
    NAMESPACE_IDENTIFIER |
    namespace_ident ~ DOT ~ CAPTURED_IDENTIFIER

  def template_ident: Parser[Any] = DOT ~ CAPTURED_IDENTIFIER |
    TEMPLATE_IDENTIFIER |
    DELTEMPLATE_IDENTIFIER |
    template_ident ~ DOT ~ CAPTURED_IDENTIFIER


}
