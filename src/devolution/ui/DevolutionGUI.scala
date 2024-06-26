package devolution.ui

import scala.swing.*
import scala.swing.event.*
import javax.swing.{JFrame, JLabel, ImageIcon, UIManager}
//import javax.swing.*
import devolution.Game

import java.awt.{Dimension, Insets, Point, Color, Image}
import java.awt.Robot
import java.awt.event.KeyEvent
import scala.language.adhocExtensions
import devolution.helpers.D

/** The singleton object `AdventureGUI` represents a GUI-based version of the Adventure
  * game application. The object serves as a entry point for the game app, and can
  * be run to start up a user interface that operates in a separate window. The GUI reads
  * its input from a text field and displays information about the game world in uneditable
  * text areas.
  */
object DevolutionGUI extends SimpleSwingApplication:
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  def top = new MainFrame:

    // Access to the application's internal logic:
    val game = Game()
    val player = game.player
    val robot = new Robot()

    // Components:
    val timelineInfo = new TextArea(1, 80):
      editable = false
      wordWrap = false
      lineWrap = false
    val locationInfo = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val turnOutput = new TextArea(6, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val input = new TextField(40):
      minimumSize = preferredSize
    this.listenTo(input.keys)
    val turnCounter = Label()

    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isComplete then
          val command = this.input.text.trim
          if command.nonEmpty then
            this.input.text = ""
            this.playTurn(command)
            if game.player.isDead then
              game.reset()
              game.player.dead = false
    }

    // Layout:

    this.contents = new GridBagPanel:
      import scala.swing.GridBagPanel.Anchor.*
      import scala.swing.GridBagPanel.Fill
      layout += Label("Timeline:") -> Constraints(0, 0, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Location:") -> Constraints(0, 1, 1, 1, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Command:")  -> Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Events:")   -> Constraints(0, 3, 1, 1, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter        -> Constraints(0, 4, 2, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += timelineInfo       -> Constraints(1, 0, 1, 1, 1, 0, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += locationInfo       -> Constraints(1, 1, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += input              -> Constraints(1, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput         -> Constraints(1, 3, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)

    // Menu:
    this.menuBar = new MenuBar:
      contents += new Menu("Program"):
        val quitAction = Action("Quit")( dispose() )
        contents += MenuItem(quitAction)

    // Set up the GUI's initial state:
    this.title = game.title
    //this.updateInfo(this.game.welcomeMessage)
    this.turnOutput.text = D("begin")
    this.locationInfo.text = this.game.welcomeMessage
    this.timelineInfo.text = ""
    this.location = Point(200, 200)
    this.minimumSize = Dimension(500, 500)
    this.preferredSize = Dimension(800, 800)
    this.pack()
    this.input.requestFocusInWindow()


    def playTurn(command: String) =
      val turnReport = this.game.playTurn(command)
      if this.player.hasQuit then
        this.dispose()
      else
        this.updateInfo(turnReport)
        this.input.enabled = !this.game.isComplete


    def updateInfo(info: String) =
      if !this.game.isComplete then
        this.turnOutput.text = info
      else
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
      if player.timelineChosen then
        this.locationInfo.text = this.game.locationFullDescription
      else
        this.locationInfo.text = this.player.location.shortDescription(player.abilities, player.canSee, player.phase)
      this.timelineInfo.text = this.game.timelineDescription

  end top

  // Enable this code to work even under the -language:strictEquality compiler option:
  private given CanEqual[Component, Component] = CanEqual.derived
  private given CanEqual[Key.Value, Key.Value] = CanEqual.derived

end DevolutionGUI

