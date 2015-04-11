package eu.unipv.epsilon.scells

import javax.swing.UIManager

import swing._

object Main extends SimpleSwingApplication {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  def top = new MainFrame {
    title = "Spreadsheet"
    contents = new Spreadsheet(100, 26)
  }

}
