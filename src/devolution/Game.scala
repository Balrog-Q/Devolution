package devolution

import devolution.*
import devolution.helpers.{AreaDialogues, D}
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

  val entryPoints = Vector(geWhiteRoom, psCenter, hdVacuum, maPath, phForest, olSurface, elTower, srRoom)
    .zipWithIndex.map(_.swap).toMap
  entryPoints.foreach((k,v) => v.setMovePhase(k))
  //assign the correct move phase based on the order the zone has been added to the entryPoint vector


  private val psPeriphery = Area(D.areas("closeVoid"), D.areas("ps"))
  private val psVoid = Area(D.areas("vacuum"), D.areas("ps"))
  psVoid.deadly = true
  psCenter.setNeighbors(Vector(D.movements("forward") -> psPeriphery))
  psPeriphery.setNeighbors(Vector(D.movements("back") -> psCenter, D.movements("forward") -> psVoid))

  private val maCastle1 = Area(D.areas("castle1"), D.areas("ma"))
  private val maCastle2 = Area(D.areas("castle2"), D.areas("ma"))
  private val maField = Area(D.areas("field"), D.areas("ma"))
  private val maHouse1 = Area(D.areas("house1"), D.areas("ma"))
  private val maHouse2 = Area(D.areas("house2"), D.areas("ma"))
  private val maStream = Area(D.areas("stream"), D.areas("ma"))
  private val maGrave = Area(D.areas("grave"), D.areas("ma"))
  maCastle2.deadly = true
  maPath    .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maCastle1))
  maCastle1 .setNeighbors(Vector(D.movements("w") -> maCastle2, D.movements("e") -> maPath, D.movements("s") -> maField))
  maHouse1  .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maPath, D.movements("in") -> maHouse2))
  maHouse2  .setNeighbors(Vector(D.movements("back") -> maHouse1))
  maGrave .setNeighbors(Vector(D.movements("s") -> maHouse1, D.movements("w") -> maPath, D.movements("e") -> maStream))
  maStream  .setNeighbors(Vector(D.movements("w") -> maHouse1))
  maField .setNeighbors(Vector(D.movements("n") -> maPath))

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
    * @param action The possible user command that will apport progression in the story
    */
  def parseStoryCommand(action: Action): String =
    val input = action.commandText
    var outcome = ""
    commandSuccess = false
    //game start
    //D.zones.map((k,v) => v.question).zipWithIndex.filter((v,k) => v == input)
    if player.location.isDeadly || player.location == bbVacuum then
      player.die()

      //show a death message only if the user can see where he went
      if player.abilities.contains(D.knowledge("vision")) then
        return D.misc("dead") + player.location.name
      else
        return ""

    //if action.verb == D.actionNames("evolve") || action.verb == D.actionNames("devolve") then
    //  timelineDiscovered = false

    //if player.phase == 1 then outcome = D.ge.answer
    //show old answer as a hint
    println(entryPoints.get(player.phase-1).map(_.timeline).getOrElse(""))
    if player.location.timeline == entryPoints.get(player.phase-1).map(_.timeline).getOrElse("") then
      //println(D.zones("Globalization era").answer + " " + D.areas(entryPoints.get(player.phase - 1).map(_.timeline).getOrElse("")))
      return D.zones.get(entryPoints.get(player.phase-1).map(_.timeline).getOrElse("")).map(_.answer).getOrElse("non!")

    player.phase match
      case 0 =>
        if player.location == startingPoint then
          if isQuestionRight(input, D.ge.question) then
            player.phase += 1
            commandSuccess = true
          else
            outcome += D.misc("intro1")
            if !player.remembers then
              outcome += "\n" + D.misc("intro2")

          //first question guessed
      case 1 =>
        //if player.timeline == D.areas("bb") && action.verb == D.actions("go") then
          //timelineDiscovered = true
        if player.timeline == D.areas("bb") && isQuestionRight(input, D.ps.question)  then
          outcome = D.ps.answer
          player.phase += 1
            commandSuccess = true
      case 2 =>
        if player.timeline == D.areas("hd") && isQuestionRight(input, D.hd.question)  then
          outcome = D.hd.answer
          player.phase += 1
            commandSuccess = true
      case 3 =>
        if player.timeline == D.areas("ma") && isQuestionRight(input, D.ma.question) then
          outcome = D.ma.answer
          player.phase += 1
          commandSuccess = true


      case _ =>
        D.debug("noPhase")
      //ALL THE LOGIC ABOUT GAME PROGRESSION GOES HERE
    outcome

  def isQuestionRight(input: String, question: String) =
    !input.isBlank && input.contains("?") && question.toLowerCase().contains(input.toLowerCase())

  /**
    * Handle the dead of the player and the reset options/state changes.
    * The program freeze for some instants to let the player read the output, then automatically reset.
    */
  def reset() =
    player.phase = 0
    //timelineDiscovered = false
    this.playTurn("")
    Thread.sleep(1000)
    this.player.dead = false

  var commandSuccess = false
  //var timelineDiscovered = false

  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    var storyReport = parseStoryCommand(action)
    var outcomeReport = action.execute(this.player).getOrElse("")

    if !this.commandSuccess && outcomeReport.isBlank && !action.verb.isBlank && !player.isDead then //&& outcomeReport.isBlank && storyReport.isBlank then // needs to hide "command not found" error during successful output
      //check if the verb could still be meaningfull
      //if outcomeReport.isEmpty then
        //if !action.verb.isBlank then
        //  outcomeReport = Some(parseStoryCommand(action.commandText))

      //default "unknown" output text
      //if action.verb.isEmpty then
      if action.modifiers.isBlank then
        s"$storyReport\n\n${D.misc("unknownCommand")} ${action.verb}!"
        //if !action.verb.isBlank then
        //else
        //outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")
      else
        s"$storyReport\n\n${D.misc("unknownParameter")} ${action.verb} ${action.modifiers}..."
      //else //this.turnCount += 1
      //  outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")
    else
      storyReport + outcomeReport + "\nDEBUG: " + Action("$").execute(this.player).getOrElse("No desc?")

end Game