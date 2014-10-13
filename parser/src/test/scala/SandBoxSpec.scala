import org.scalatest._

class SandBoxSpec extends FlatSpec with Matchers{

  "sandbox" should "parsed" in {
    SoyParser.parseAll(SoyParser.dot_reference_l, "$foo.bar").get.toString should be("(.,($~foo),bar)")
    SoyParser.parseAll(SoyParser.dot_reference_l, "$foo.bar.boo").get.toString should be("(.,(.,($~foo),bar),boo)")
    SoyParser.parseAll(SoyParser.dot_reference_l, "$foo.bar[123].boo").get.toString should be("(.,($~foo),bar)")
  }
}
