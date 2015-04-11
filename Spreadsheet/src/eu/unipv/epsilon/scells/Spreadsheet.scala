package eu.unipv.epsilon.scells

import scala.swing._

class Spreadsheet(val height: Int, val width: Int) extends ScrollPane {

  val cellModel = new Model(height, width)
  import cellModel._

  val table = new Table(height, width) {
    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.Off
    showGrid = true
    gridColor = new java.awt.Color(150, 150, 150)

    override def rendererComponent(isSelected: Boolean, focused: Boolean, row: Int, column: Int): Component =
      if (focused) new TextField(userData(row, column))
      else new Label(cells(row)(column).toString) {
        xAlignment = Alignment.Right
      }

    // Retrieves the content of the table at the given row and column
    // If null, returns an empty string.
    def userData(row: Int, column: Int): String = {
      val v = this(row, column) // Not constructor invocation but apply()!
      if (v == null) "" else v.toString
    }
  }

  val rowHeader = new ListView((0 until height) map (_.toString)) {
    fixedCellWidth = 30
    fixedCellHeight = table.rowHeight
  }

  viewportView = table
  rowHeaderView = rowHeader

}
