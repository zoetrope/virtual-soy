import org.scalatest._

class SoyParserSpec extends FlatSpec with Matchers {

  def testParser(parser: SoyParser.Parser[Any], input: String, output: Expr): Unit = {
    SoyParser.parseAll(parser, input).get should be(output)
  }

  "key_value_list" should "parsed" in {
    testParser(SoyParser.key_value, "\"a\":$abc", KeyValueLiteral("\"a\"", ParameterRef("abc")))

    testParser(SoyParser.key_value_list, "\"a\":$abc,\"bb\":$def",
      MapLiteral(List(
        KeyValueLiteral("\"a\"", ParameterRef("abc")),
        KeyValueLiteral("\"bb\"", ParameterRef("def"))
      )))
  }
}
