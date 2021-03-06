package eu.unipv.epsilon.scells

import swing._, event._

class Spreadsheet(val height: Int, val width: Int) extends ScrollPane {

  val cellModel = new Model(height, width)
  import cellModel._

  val table = new Table(height, width) {

    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.Off
    showGrid = true
    gridColor = new java.awt.Color(150, 150, 150)

    override def rendererComponent(isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component =
      if (hasFocus) new TextField(userData(row, column))
      else new Label(cells(row)(column).toString) {
        xAlignment = Alignment.Right
      }

    // Retrieves the content of the table at the given row and column
    // If null, returns an empty string.
    def userData(row: Int, column: Int): String = {
      val v = this(row, column) // Not constructor invocation but apply()!
      if (v == null) "" else v.toString
    }

    reactions += {
      case TableUpdated(table, rows, column) =>
        for (row <- rows) cells(row)(column).formula = FormulaParsers.parse(userData(row, column))
      case ValueChanged(cell) =>
        updateCell(cell.row, cell.column)
    }

    // Listen to all cells for value changes
    for (row <- cells; cell <- row) listenTo(cell)

  }

  val rowHeader = new ListView((0 until height) map (_.toString)) {
    fixedCellWidth = 30
    fixedCellHeight = table.rowHeight
  }

  viewportView = table
  rowHeaderView = rowHeader

}
