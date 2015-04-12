package eu.unipv.epsilon.scells

trait Formula

// For cell coordinates such as A3
case class Coord(row: Int, column: Int) extends Formula {
  override def toString: String = ('A' + column).toChar.toString + row
}

// For cell ranges such as A3:B17
case class Range(c1: Coord, c2: Coord) extends Formula {
  override def toString: String = c1.toString + ':' + c2.toString
}

// For floating-point numbers such as 3.1415
case class Number(value: Double) extends Formula {
  override def toString: String = value.toString
}

// For textual labels such as 'Deprecation'
case class Textual(value: String) extends Formula {
  override def toString: String = value
}

// For function applications such as sum(A1, A2)
case class Application(function: String, arguments: List[Formula]) extends Formula {
  override def toString: String = function + arguments.mkString("(", ",", ")")
}

object Empty extends Textual("")
