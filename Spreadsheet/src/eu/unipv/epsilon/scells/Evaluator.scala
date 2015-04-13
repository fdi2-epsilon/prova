package eu.unipv.epsilon.scells

trait Evaluator { this: Model =>

  type Op = List[Double] => Double

  val operations = new collection.mutable.HashMap[String, Op]

  def evaluate(e: Formula): Double = try {
    e match {
      case Coord(row, column) =>
        cells(row)(column).value
      case Number(v) =>
        v
      case Textual(_) =>
        0
      case Application(function, arguments) =>
        val argvals = arguments flatMap evalList
        operations(function)(argvals)
    }
  } catch {
    case ex: Exception => Double.NaN
  }

  // Evaluation of arguments is different from the evaluation of top-level formulas.
  // Args may be lists whereas top-level formulas may not. For example A1:A3 in sum(A1:A3)
  // returns the values of cells A1, A2, A3 in a list, passed to the sum operation.
  // It is also possible to mix, e.g. sum(A1:A3, 1.0, C7) would sum up five values.

  // evalList can handle arguments that can evaluate to lists for the motivation explained above
  private def evalList(e: Formula): List[Double] = e match {
    case Range(_, _) => references(e) map (_.value)
    case _ => List(evaluate(e))
  }

  // Creates a list of Cells referenced by a Formula
  def references(e: Formula): List[Cell] = e match {
    case Coord(row, column) =>
      List(cells(row)(column))
    case Range(Coord(r1, c1), Coord(r2, c2)) =>
      for (row <- (r1 to r2).toList; column <- c1 to c2) yield cells(row)(column)
    case Application(function, arguments) =>
      arguments flatMap references
    case _ => // Textual and Number
      List() // Nil?
  }

}
