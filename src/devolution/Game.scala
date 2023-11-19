package devolution

import devolution.*
import devolution.helpers.D
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
  val title = "Devolution"
  //private val timelines = Map[String, String]("Big Bang" -> "")
  //private val bb = Area("Big Bang", None)

  /*private val ps = Area("Primordial soup")
  private val psVacuum = Area("Vacuum")
  private val psPeriphery = Area("Perifery of cluster")*/

  private val overflow = Area(D.areas("bonus"), D.areas("bonus"))
  private val bbVacuum = Area(D.areas("vacuum"), D.areas("bb"))
  private val psCenter = Area(D.areas("voidCenter"), D.areas("ps"))
  private val olSurface = Area(D.areas("ocean1"), D.areas("ol"))
  private val phForest = Area(D.areas("clearing"), D.areas("ph"))
  private val maPath = Area(D.areas("path"), D.areas("ma"))
  private val srRoom = Area(D.areas("room"), D.areas("sr"))
  private val geWhiteRoom = Area(D.areas("start"), D.areas("ge"))
  private val elTower = Area(D.areas("tower"), D.areas("el"))
  private val hdVacuum = Area(D.areas("vacuum"), D.areas("hd"))

  bbVacuum    .setNeighbors(Vector("past" -> overflow,    "future" -> psCenter))
  psCenter    .setNeighbors(Vector("past" -> bbVacuum,    "future" -> olSurface))
  olSurface   .setNeighbors(Vector("past" -> psCenter,    "future" -> phForest))
  phForest    .setNeighbors(Vector("past" -> olSurface,   "future" -> maPath))
  maPath      .setNeighbors(Vector("past" -> phForest,    "future" -> srRoom))
  srRoom      .setNeighbors(Vector("past" -> maPath,      "future" -> geWhiteRoom))
  geWhiteRoom .setNeighbors(Vector("past" -> srRoom,      "future" -> elTower))
  elTower     .setNeighbors(Vector("past" -> geWhiteRoom, "future" -> hdVacuum))
  hdVacuum    .setNeighbors(Vector("past" -> elTower,     "future" -> hdVacuum))

  val entryPoints = Vector(bbVacuum, psCenter, hdVacuum, maPath, phForest, olSurface, elTower, srRoom, geWhiteRoom)

  // conditions to move between areas in that timeline
  /*bbVacuum    .setMovePhase(0)
  psCenter    .setMovePhase(1)
  olSurface   .setMovePhase(5)
  phForest    .setMovePhase(4)
  maPath      .setMovePhase(3)
  srRoom      .setMovePhase(7)
  geWhiteRoom .setMovePhase(9)
  elTower     .setMovePhase(6)
  hdVacuum    .setMovePhase(2)*/
  entryPoints.zipWithIndex.foreach(_.setMovePhase(_))

  private def startingPoint = geWhiteRoom
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
  //var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  //val timeLimit = 40

  /** Determines if the adventure is complete, that is, if the player has won. */
  //private val destination = bb
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(geWhiteRoom)
  def isComplete = false
    //this.player.location == this.destination && this.player.has("battery") && this.player.has("remote")
  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete //|| this.player.hasQuit || this.turnCount == this.timeLimit
  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = D.misc("quote")
  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = "TO-DO"
    /*if this.isComplete then
      "Home at last... and phew, just in time! Well done!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else  // game over due to player quitting
      "Quitter!"*/

  /**
    * Manage the story progression and conditions.
    * @param input The possible user command that will apport progression in the story
    */
  def parseStoryCommand(input: String) =
    var outcome = ""
    //game start
    D.zones.map((k,v) => v.question).zipWithIndex.filter((v,k) => v == input)
    player.phase match
      case 0 =>
        if player.location == startingPoint then
          outcome += D.misc("intro1")
          if !player.remembers then
            outcome += "\n" + D.misc("intro2")

          //first question guessed
          if input == D.ge.question then
            outcome = D.ge.answer
            player.phase += 1
      case 1 =>
        if player.location == psCenter && input == D.ps.question then
          outcome = D.ps.answer
          player.phase += 1
      case 2 =>
        if player.location == hdVacuum && input == D.hd.question then
          outcome = D.hd.answer
          player.phase += 1
      case 2 =>
        if player.location == hdVacuum && input == D.hd.question then
          outcome = D.hd.answer
          player.phase += 1


      case _ =>
        D.debug("noPhase")
    //ALL THE LOGIC ABOUT GAME PROGRESSION GOES HERE
    outcome

  /**
    * Handle the dead of the player and the reset options/state changes.
    */
  def reset() =
    player.phase = 0
    this.player.dead = false


  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    var outcomeReport = action.execute(this.player)
    if action.verb.isBlank then
      ""
    else
      //check if the verb could still be meaningfull
      if outcomeReport.isEmpty then
        if !action.verb.isBlank then
          outcomeReport = Some(parseStoryCommand(action.commandText))

      //default "unknown" output text
      if outcomeReport.isEmpty then
        if action.modifiers.isBlank then
          D.misc("unknownCommand") + action.verb
          //if !action.verb.isBlank then
          //else
          //outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")
        else
          D.misc("unknownParameter") + s"${action.verb} ${action.modifiers}"
      else //this.turnCount += 1
        outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")

end Game