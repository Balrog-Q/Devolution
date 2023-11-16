package devolution

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Game:

  /** the name of the game */
  val title = "A Forest Adventure"
  private val bb = Area("Big Bang")
  /*private val middle      = Area("Forest", "You are somewhere in the forest. There are a lot of trees here.\nBirds are singing.")
  private val northForest = Area("Forest", "You are somewhere in the forest. A tangle of bushes blocks further passage north.\nBirds are singing.")
  private val southForest = Area("Forest", "The forest just goes on and on.")
  private val clearing    = Area("Forest Clearing", "You are at a small clearing in the middle of forest.\nNearly invisible, twisted paths lead in many directions.")
  private val tangle      = Area("Tangle of Bushes", "You are in a dense tangle of bushes. It's hard to see exactly where you're going.")
  private val home        = Area("Home", "Home sweet home! Now the only thing you need is a working remote control.")
  middle     .setNeighbors(Vector("north" -> northForest, "east" -> tangle, "south" -> southForest, "west" -> clearing   ))
  northForest.setNeighbors(Vector(                        "east" -> tangle, "south" -> middle,      "west" -> clearing   ))
  southForest.setNeighbors(Vector("north" -> middle,      "east" -> tangle, "south" -> southForest, "west" -> clearing   ))
  clearing   .setNeighbors(Vector("north" -> northForest, "east" -> middle, "south" -> southForest, "west" -> northForest))
  tangle     .setNeighbors(Vector("north" -> northForest, "east" -> home,   "south" -> southForest, "west" -> northForest))
  home       .setNeighbors(Vector(                                                                  "west" -> tangle     ))
  clearing.addAbility(Ability("battery", "It's a small battery cell. Looks new."))
  southForest.addAbility(Ability("remote", "It's the remote control for your TV.\nWhat it was doing in the forest, you have no idea.\nProblem is, there's no battery."))
  *//** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40

  /** The phase the player is currently in. It allows to select the right dialogues. */
  val phase = 0

  /** Determines if the adventure is complete, that is, if the player has won. */
  private val destination = bb
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(bb)
  def isComplete =
    this.player.location == this.destination && this.player.has("battery") && this.player.has("remote")
  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit
  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "You are lost in the woods. Find your way back home.\n\nBetter hurry, 'cause Scalatut elämät is on real soon now. And you can't miss Scalkkarit, right?"
  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "Home at last... and phew, just in time! Well done!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else  // game over due to player quitting
      "Quitter!"
  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
end Game