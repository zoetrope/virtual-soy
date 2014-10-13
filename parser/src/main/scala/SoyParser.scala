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

  def PARAMETER_REF = "$" ~ IDENTIFIER

  def EMPTY_ARRAY_LITERAL: Parser[String] = "[]"

  def EMPTY_OBJECT_LITERAL: Parser[String] = "[:]"

  //TODO
  def CAPTURED_FUNCTION_IDENTIFIER = IDENTIFIER

  // parser
  def special_char = " " | "null" | "\r" | "\n" | "\t" | "{" | "}"

  def expr_list: Parser[Any] = repsep(expr, COMMA)

  def key_value: Parser[Any] = string_literal ~ COLON ~ expr

  def key_value_list: Parser[Any] = repsep(string_literal ~ COLON ~ expr, COMMA)

  def expr: Parser[Any] =
  //    EMPTY_ARRAY_LITERAL |
  //      EMPTY_OBJECT_LITERAL |
  //      CAPTURED_FUNCTION_IDENTIFIER ~ LPAREN ~ expr_list ~ RPAREN |
//    expr ~ dot_reference |
      //      null_safe_reference |
      //    expr ~ LPAREN ~ expr_list ~ RPAREN |
      //    expr ~ LPAREN ~ RPAREN |
      //    literal_value |
      //    NULL_LITERAL |
      //    NOT ~ expr |
      //    expr ~ binary_operator ~ expr |
      //    LPAREN ~ expr ~ RPAREN |
      //    expr ~ ELVIS ~ expr |
      //    expr ~ QUESTION ~ expr ~ COLON ~ expr |
      PARAMETER_REF |
      CAPTURED_IDENTIFIER

  def literal_value = MINUS ~ INTEGER_LITERAL |
    INTEGER_LITERAL |
    MINUS ~ FLOATING_POINT_LITERAL |
    FLOATING_POINT_LITERAL |
    string_literal |
    BOOLEAN_LITERAL |
    LBRACK ~ expr_list ~ RBRACK |
    LBRACK ~ key_value_list ~ RBRACK

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

  def expr_l = PARAMETER_REF | dot_reference_l
  def dot_reference_l = chainl1(expr,IDENTIFIER,  "." ^^{q=>(a:Any,b:Any)=>(q,a,b)})
  def dot_reference = DOT ~ CAPTURED_IDENTIFIER |
    DOT ~ INTEGER_LITERAL |
    LBRACK ~ expr ~ RBRACK

  def index_reference = expr ~ LBRACK ~ expr ~ RBRACK

  def null_safe_reference = chainl1(expr, CAPTURED_IDENTIFIER | INTEGER_LITERAL, QUESTION_DOT ^^ { o => (a: Any, b: Any) => (o, a, b)})

  def namespace_ident: Parser[Any] = CAPTURED_IDENTIFIER |
    NAMESPACE_IDENTIFIER |
    namespace_ident ~ DOT ~ CAPTURED_IDENTIFIER

  def template_ident: Parser[Any] = DOT ~ CAPTURED_IDENTIFIER |
    TEMPLATE_IDENTIFIER |
    DELTEMPLATE_IDENTIFIER |
    template_ident ~ DOT ~ CAPTURED_IDENTIFIER


}
