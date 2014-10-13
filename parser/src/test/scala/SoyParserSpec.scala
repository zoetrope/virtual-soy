import org.scalatest._

class SoyParserSpec extends FlatSpec with Matchers {

  "expr" should "parsed" in {
    SoyParser.parseAll(SoyParser.expr, "[]").get.toString should be("[]")
    SoyParser.parseAll(SoyParser.expr, "[:]").get.toString should be("[:]")
    //    SoyParser.parseAll(SoyParser.expr, "func()").get.toString should be("(((func~()~List())~))")
    //    SoyParser.parseAll(SoyParser.expr, "func($a,$b,$c)").get.toString should be("(((func~()~List(($~a), ($~b), ($~c)))~))")
    SoyParser.parseAll(SoyParser.expr, "$foo.bar").get.toString should be("[]")
    //    SoyParser.parseAll(SoyParser.expr, "$foo?.bar").get.toString should be("[]")
  }

  "key_value_list" should "parsed" in {
    SoyParser.parseAll(SoyParser.key_value, "\"a\":$abc").get.toString should be("((\"a\"~:)~($~abc))")
    SoyParser.parseAll(SoyParser.key_value_list, "\"a\":$abc,\"bb\":$def").get.toString should be("List(((\"a\"~:)~($~abc)), ((\"bb\"~:)~($~def)))")
  }

  "exprlist" should "parsed" in {
    SoyParser.parseAll(SoyParser.expr_list, "foo bar").get.toString should be("((func~()~))")

    SoyParser.parseAll(SoyParser.expr_list, "$foo $bar").get.toString should be("((func~()~))")
  }

  "literal_value" should "parsed" in {
    SoyParser.parseAll(SoyParser.literal_value, "123").get.toString should be("123")
    SoyParser.parseAll(SoyParser.literal_value, "-456").get.toString should be("(-~456)")
    SoyParser.parseAll(SoyParser.literal_value, "0.001").get.toString should be("0.001")
    SoyParser.parseAll(SoyParser.literal_value, "-123.00456").get.toString should be("(-~123.00456)")
    SoyParser.parseAll(SoyParser.literal_value, "true").get.toString should be("true")
    SoyParser.parseAll(SoyParser.literal_value, "false").get.toString should be("false")
    SoyParser.parseAll(SoyParser.literal_value, "[$a,$b,$c]").get.toString should be("(([~List(($~a), ($~b), ($~c)))~])")
    SoyParser.parseAll(SoyParser.literal_value, "[a:123,b:456]").get.toString should be("false")
  }

  "string_literal" should "parsed" in {
    SoyParser.parseAll(SoyParser.string_literal, "\"abc\"").get.toString should be("\"abc\"")
    //    SoyParser.parseAll(SoyParser.string_literal_buf, "a").get.toString should be("")
  }

  "dot_reference" should "parsed" in {
    SoyParser.parseAll(SoyParser.dot_reference, "$bar. hoge").get.toString should be("(.,($~bar),hoge)")
    SoyParser.parseAll(SoyParser.dot_reference, "$foo.123").get.toString should be("(.,($~foo),123)")
  }
  "index_reference" should "parsed" in {
    SoyParser.parseAll(SoyParser.index_reference, "$fuga[$bar]").get.toString should be("(((($~fuga)~[)~($~bar))~])")
  }
  "null_safe_reference" should "parsed" in {
    SoyParser.parseAll(SoyParser.null_safe_reference, "$hoge?.hoge").get.toString should be("(?.,($~hoge),hoge)")
    SoyParser.parseAll(SoyParser.null_safe_reference, "$hoge?.123").get.toString should be("(?.,($~hoge),123)")
  }
}
