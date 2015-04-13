package eu.unipv.epsilon.scells

import scala.util.parsing.combinator.RegexParsers

object FormulaParsers extends RegexParsers {

  // Each identifier starts with a letter on an underscore, this is followed by an arbitrary number
  // of word chars represented by '\w' which recognizes letters, digits and underscores.
  def ident: Parser[String] = """[a-zA-Z_]\w*""".r

  // Decimal numbers, we have an optional minus sign, one or more digits ('\d') and an
  // optional decimal part consisting of a period followed by zero or more digits.
  def decimal: Parser[String] = """-?\d+(\.\d*)?""".r

  // The cell parser recognized the coordinates of a cell, such as C11 or B12.
  def cell: Parser[Coord] = """[A-Za-z]\d+""".r ^^ { s =>
    // This implementation only recognizes a single letter for column (max. 26 cols)
    // I may generalize this if I have time...
    val column = s.charAt(0).toUpper - 'A'
    val row = s.substring(1).toInt
    Coord(row, column)
  }

  // The Range parser recognizes a range of cells, such a range is
  // composed of two cell coordinates with a colon in between.
  def range: Parser[Range] = cell~":"~cell ^^ { case c1~":"~c2 => Range(c1, c2) }

  // The Number parser recognizes a decimal number, then converted to a double and wrapped in Number.
  def number: Parser[Number] = decimal ^^ (d => Number(d.toDouble))

  // The Application parser recognizes a function application.
  // It is composed of an identifier followed by a list of argument expressions in parentheses.
  def application: Parser[Application] = ident~"("~repsep(expr, ",")~")" ^^ {
    case f~"("~ps~")" => Application(f, ps)
  }

  // The expr parser recognizes a Formula expression, either a top-level formula following an '=' or
  // a function argument. It is defined to be a cell, a range of cells, a number or an application.
  def expr: Parser[Formula] = range | cell | number | application

  // The textual parser recognizes an arbitrary input string, as long as it does not start with
  // an equals sign (recall that strings that start with '=' are considered to be formulas).
  def textual: Parser[Textual] = """[^=].*""".r ^^ Textual

  // The Formula parser recognizes all kinds of legal inputs into a cell.
  // A formula is either a number, or a textual entry, or a formula starting with an equal sign.
  def formula: Parser[Formula] = number | textual | "="~>expr

  /** Parses a string to a [[Formula]] tree using the defined [[formula]] parser.
    *
    * If that succeeds, the resulting formula is returned.
    * If it fails, a [[Textual]] object with an error message is returned instead.
    */
  def parse(input: String): Formula = parseAll(formula, input) match {
    case Success(e, _) => e
    case f: NoSuccess => Textual("[" + f.msg + "]")
  }

}
