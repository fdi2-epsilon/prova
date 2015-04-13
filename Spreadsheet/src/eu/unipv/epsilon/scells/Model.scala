package eu.unipv.epsilon.scells

import swing._

class Model(val height: Int, val width: Int) extends Evaluator with Arithmetic {

  case class Cell(row: Int, column: Int) extends Publisher {

    override def toString = formula match {
      case Textual(s) => s
      case _ => value.toString // Show evaluation result
    }

    // The 'value' setter does the publishing...
    private var v: Double = 0
    def value: Double = v
    def value_=(w: Double): Unit = {
      if (!(v == w || v.isNaN && w.isNaN)) {
        // NaN needs to be treated separately since NaN != NaN
        v = w
        publish(ValueChanged(this))
      }
    }

    // ...and the 'formula' setter the subscribing.
    private var f: Formula = Empty
    def formula: Formula = f
    def formula_=(f: Formula): Unit = {
      for (c <- references(formula)) deafTo(c)    // Unsubscribe from previous formula refs
      this.f = f            // Assign the new formula
      for (c <- references(formula)) listenTo(c)  // Subscribe to the refs of the new formula
      value = evaluate(f)   // Recompute value with the new formula
    }

    reactions += {
      case ValueChanged(cell: Cell) => value = evaluate(formula)
    }

  }

  case class ValueChanged(cell: Cell) extends event.Event

  val cells = Array.ofDim[Cell](height, width)

  for (i <- 0 until height; j <- 0 until width)
    cells(i)(j) = new Cell(i, j)

}
