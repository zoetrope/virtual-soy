import org.scalatest._

class SoyParserSpec extends FlatSpec with Matchers{

  "expr" should "parsed" in {
    SoyParser.parse_expr("[]") should be("[]")
    //SoyParser("func()") should be("func~(~)")
  }
}
