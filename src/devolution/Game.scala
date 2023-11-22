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

  // Initialize the areas in the game
  private val bonus = Timeline("bonus")
  private val bigBang = Timeline("Big Bang")
  private val primordialSoup = Timeline("Primordial Soup")
  private val originOfLife = Timeline("Origin of Life")
  private val prehistory = Timeline("Prehistory")
  private val middleAges = Timeline("Middle Ages")
  private val scientificRevo = Timeline("Scientific Revolution")
  private val globalization = Timeline("Globalization Era")
  private val endOfLife = Timeline("End of Life")
  private val headDead = Timeline("Head Dead")

  private val overflow = Area(D.areas("bonus"), bonus)
  private val bbVacuum = Area(D.areas("vacuum"), bigBang)
  private val psCenter = Area(D.areas("voidCenter"), primordialSoup)
  private val olSurface = Area(D.areas("ocean1"), originOfLife)
  private val phClearing = Area(D.areas("clearing"), prehistory)
  private val maPath = Area(D.areas("path"), middleAges)
  private val srRoom = Area(D.areas("room"), scientificRevo)
  private val geWhiteRoom = Area(D.areas("start"),globalization)
  private val elTower = Area(D.areas("tower"), endOfLife)
  private val hdVacuum = Area(D.areas("vacuum"), headDead)

  overflow    .setNeighbors(Vector(                       "future" -> bbVacuum))
  bbVacuum    .setNeighbors(Vector("past" -> overflow,    "future" -> psCenter))
  bbVacuum.setDeadly()
  psCenter    .setNeighbors(Vector("past" -> bbVacuum,    "future" -> olSurface))
  olSurface   .setNeighbors(Vector("past" -> psCenter,    "future" -> phClearing))
  phClearing    .setNeighbors(Vector("past" -> olSurface,   "future" -> maPath))
  maPath      .setNeighbors(Vector("past" -> phClearing,    "future" -> srRoom))
  srRoom      .setNeighbors(Vector("past" -> maPath,      "future" -> geWhiteRoom))
  geWhiteRoom .setNeighbors(Vector("past" -> srRoom,      "future" -> elTower))
  elTower     .setNeighbors(Vector("past" -> geWhiteRoom, "future" -> hdVacuum))
  hdVacuum    .setNeighbors(Vector("past" -> elTower,     "future" -> hdVacuum))

  val entryPoints = Vector(geWhiteRoom, psCenter, hdVacuum, maPath, phClearing, olSurface, elTower, srRoom)
    .zipWithIndex.map(_.swap).toMap
  entryPoints.foreach((k,v) => v.setMovePhase(k))
  //assign the correct move phase based on the order the zone has been added to the entryPoint vector


  private val psPeriphery = Area(D.areas("closeVoid"), primordialSoup)
  private val psVoid = Area(D.areas("vacuum"), primordialSoup)
  psVoid.setDeadly()

  psCenter.setNeighbors(Vector(D.movements("forward") -> psPeriphery))
  psPeriphery.setNeighbors(Vector(D.movements("back") -> psCenter, D.movements("forward") -> psVoid))

  //future celestial bodies
  psCenter.setInteractables(Vector(
    "dust1" -> Element(D.interactables("dust1"), 1),
    "dust2" -> Element(D.interactables("dust2"), 3)
  ))
  psPeriphery.setInteractables(Vector(
    "dust3" -> Element(D.interactables("dust3"), 5),
    "dust4" -> Element(D.interactables("dust4"), 2),
    "dust5" -> Element(D.interactables("dust5"), 2)
  ))


  private val olOcean2 = Area(D.areas("ocean2"), originOfLife)
  private val olOcean3 = Area(D.areas("ocean3"), originOfLife)
  private val olOcean4 = Area(D.areas("ocean4"), originOfLife)
  private val olThermal = Area(D.areas("thermal"), originOfLife)
  private val olVolcano = Area(D.areas("volcano"), originOfLife)
  private val olVolcano2 = Area(D.areas("volcano2"), originOfLife)
  private val olLava = Area(D.areas("lava"), originOfLife)
  olVolcano2.setDeadly()
  olLava.setDeadly()

  olSurface   .setNeighbors(Vector(D.movements("forward") -> olVolcano, D.movements("down") -> olOcean2))
  olOcean2   .setNeighbors(Vector(D.movements("up") -> olSurface, D.movements("down") -> olOcean3))
  olOcean3   .setNeighbors(Vector(D.movements("up") -> olOcean2, D.movements("down") -> olOcean4))
  olOcean4   .setNeighbors(Vector(D.movements("up") -> olOcean4, D.movements("down") -> olVolcano2, D.movements("forward") -> olThermal))
  olThermal  .setNeighbors(Vector(D.movements("up") -> olOcean3, D.movements("down") -> olVolcano2, D.movements("back") -> olOcean4))
  olVolcano  .setNeighbors(Vector(D.movements("back") -> olSurface, D.movements("forward") -> olLava))

  private val phJungle = Area(D.areas("jungle"), prehistory)
  private val phHill = Area(D.areas("hill"), prehistory)
  private val phSwamp = Area(D.areas("swamp"), prehistory)
  private val phCave1 = Area(D.areas("cave1"), prehistory)
  private val phCave2 = Area(D.areas("cave2"), prehistory)
  private val phVolcano = Area(D.areas("volcano"), prehistory)
  private val phNest = Area(D.areas("nest"), prehistory)
  private val phLava = Area(D.areas("lava"), prehistory)
  phLava.setDeadly()
  phNest.setDeadly()

  phClearing  .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phSwamp, D.movements("e") -> phJungle, D.movements("w") -> phHill))
  phJungle    .setNeighbors(Vector(D.movements("n") -> phCave1, D.movements("s") -> phClearing, D.movements("e") -> phHill, D.movements("w") -> phNest))
  phHill      .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phSwamp, D.movements("e") -> phClearing, D.movements("w") -> phVolcano))
  phVolcano   .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phJungle, D.movements("e") -> phHill, D.movements("w") -> phLava))
  phSwamp     .setNeighbors(Vector(D.movements("n") -> phClearing, D.movements("s") -> phNest, D.movements("e") -> phHill, D.movements("w") -> phJungle))
  phCave1     .setNeighbors(Vector(D.movements("in") -> phCave2, D.movements("s") -> phJungle, D.movements("e") -> phJungle, D.movements("w") -> phJungle))
  phCave2     .setNeighbors(Vector(D.movements("back") -> phCave1,  D.movements("forward") -> phNest))



  private val maCastle1 = Area(D.areas("castle1"), middleAges)
  private val maCastle2 = Area(D.areas("castle2"), middleAges)
  private val maField = Area(D.areas("field"), middleAges)
  private val maHouse1 = Area(D.areas("house1"), middleAges)
  private val maHouse2 = Area(D.areas("house2"), middleAges)
  private val maStream = Area(D.areas("stream"), middleAges)
  private val maGrave = Area(D.areas("grave"), middleAges)
  maCastle2.setDeadly()
  maPath    .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maCastle1))
  maCastle1 .setNeighbors(Vector(D.movements("w") -> maCastle2, D.movements("e") -> maPath, D.movements("s") -> maField))
  maHouse1  .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maPath, D.movements("in") -> maHouse2))
  maHouse2  .setNeighbors(Vector(D.movements("back") -> maHouse1))
  maGrave   .setNeighbors(Vector(D.movements("s") -> maHouse1, D.movements("w") -> maPath, D.movements("e") -> maStream))
  maStream  .setNeighbors(Vector(D.movements("w") -> maHouse1))
  maField   .setNeighbors(Vector(D.movements("n") -> maPath))



  /*private val geSwitch = Area(D.areas("switch"), D.areas("ge"))
  private val geServer = Area(D.areas("server"), D.areas("ge"))
  private val gePC = Area(D.areas("pc"), D.areas("ge"))
  private val geFirewall = Area(D.areas("firewall"), D.areas("ge"))
  //private val geWeb = Area(D.areas("web"), D.areas("ge"))

  geSwitch   .setNeighbors(Vector(D.movements("up") -> gePC, D.movements("down") -> geServer, D.movements("forward") -> geFirewall))
  geServer   .setNeighbors(Vector(D.movements("up") -> geSwitch))
  gePC       .setNeighbors(Vector(D.movements("forward") -> geSwitch))
  geFirewall .setNeighbors(Vector(D.movements("back") -> geSwitch))*/


  private val elTerrace = Area(D.areas("terrace"), endOfLife)
  elTerrace.setDeadly()

  elTower   .setNeighbors(Vector(D.movements("forward") -> elTerrace))

  // conditions to move between areas in that timeline
  /*bbVacuum    .setMovePhase(0)
  psCenter    .setMovePhase(1)
  olSurface   .setMovePhase(5)
  phClearing    .setMovePhase(4)
  maPath      .setMovePhase(3)
  srRoom      .setMovePhase(7)
  geWhiteRoom .setMovePhase(9)
  elTower     .setMovePhase(6)
  hdVacuum    .setMovePhase(2)*/


  private def startingPoint = geWhiteRoom

  /** Determines if the adventure is complete, that is, if the player has won. */
  //private val destination = bb
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(psCenter)
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

    //bonus cheat mode
    if player.location == overflow then
      player.invincible = action.verb == D.misc("accept")
      return D.misc("accepted")
    //game start
    //D.zones.map((k,v) => v.question).zipWithIndex.filter((v,k) => v == input)
    if player.location.isDeadly then
      player.die()
      return player.location.description.phaseDesc(0)

    //if action.verb == D.actionNames("evolve") || action.verb == D.actionNames("devolve") then
    //  timelineDiscovered = false

    //if player.phase == 1 then outcome = D.ge.answer

    //check for a timeline question guess
    //the following if will take care of the return output
    if isQuestionRight(input, D.zones(player.location.timeline.name).question)
      && this.isInRightTimeline then
      player.phase += 1
      this.commandSuccess = true
      //return D.zones.get(lastExcpectedTimeline).answer

    if player.phase == 1 && !player.remember then
      outcome += player.learn(D.possibleAbilities("memory"))
    else if player.phase == 8 then
      outcome += D.zones(player.location.timeline.name).tought
      if isQuestionRight(input, D.zones(player.location.timeline.name).word) then
        return outcome + D.zones(player.location.timeline.name).realization
      if isQuestionRight(input, D.misc("finalQuestion")) then
        //player.setLocation(Some(gePC))
        return "final question yey you won" //TO-DO



    //+ "\n" + player.learn(D.possibleAbilities("curious"))

    //println(entryPoints(player.phase-1).timeline)
    //show old answer as a hint
    if player.location.timeline.name == lastExpectedTimeline || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse("")) then
      //println(D.zones("Globalization era").answer + " " + D.areas(entryPoints.get(player.phase - 1).map(_.timeline).getOrElse("")))
      this.commandSuccess = true
      return outcome + D.zones(lastExpectedTimeline).answer

    if isInRightTimeline && player.timelineChosen then
      player.phase match
        case 0 =>
          if player.remember then
            return D.ge("intro2")
          else
            return D.ge("intro")
          //if player.location == startingPoint then
            /*if isQuestionRight(input, D.ge.question) then
              player.phase += 1
              outcome = D.ge.answer
              commandSuccess = true
            else
              outcome += D.misc("intro1")
              if !player.remembers then
                outcome += "\n" + D.misc("intro2")*/

            //first question guessed
        case 1 =>
          //if player.location == psCenter then
          //println(":::"+player.location.interactables.values.map(_.completed).mkString(","))
          if player.location.interactable.values.headOption.exists(_.completed) then //&& action.verb == D.ps("leave") then
            //player.location.interactables = player.location.interactables.tail
            //D.ps("left")

          //println("?"+player.location.interactables.values.count(_.completed))
          player.location.interactable.values.count(_.completed) match
            case 0 => player.location.interactable
            case 1 =>
              this.commandSuccess = true
              return D.ps("star1")
            case 2 =>
              this.commandSuccess = true
              return D.ps("move")
            case _ => 
          if player.location == psPeriphery then
            println(player.location.interactable.values)
            println(player.location.interactable.values.count(_.completed))
            player.location.interactable.values.count(_.completed) match
              case 1 =>
                this.commandSuccess = true
                return D.ps("less")
              case 2 =>
                this.commandSuccess = true
                return D.ps("move")
              case _ => 

          //if player.timeline == D.areas("bb") && action.verb == D.actions("go") then
            //timelineDiscovered = true
          /*if player.timeline == D.areas("bb") && isQuestionRight(input, D.ps.question)  then
            outcome = D.ps.answer
            player.phase += 1
              commandSuccess = true*/
        case 2 =>
          /*if player.timeline == D.areas("hd") && isQuestionRight(input, D.hd.question)  then
            outcome = D.hd.answer
            player.phase += 1
              commandSuccess = true*/
        case 3 =>
          /*if player.timeline == middleAges && isQuestionRight(input, D.ma.question) then
            outcome = D.ma.answer
            player.phase += 1
            commandSuccess = true*/


        case _ =>
          D.debug("noPhase")
        //ALL THE LOGIC ABOUT GAME PROGRESSION GOES HERE
    outcome

  def isQuestionRight(input: String, question: String) =
    !input.isBlank && input.contains("?") && question.toLowerCase.contains(input.toLowerCase())

  def lastExpectedTimeline = entryPoints.get( player.phase-1 ).map( _.timeline.name ).getOrElse("")

  def isInRightTimeline = player.location.timeline == entryPoints(player.phase).timeline

  /**
    * Handle the dead of the player and the reset options/state changes.
    * The program freeze for some instants to let the player read the output, then automatically reset.
    */
  def reset() =
    player.phase = 0
    //timelineDiscovered = false
    //this.playTurn("")
    Thread.sleep(1000)
    this.player.dead = false

  var commandSuccess = false
  //var timelineDiscovered = false

  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    var outcomeReport = action.execute(this.player).getOrElse("")
    var storyReport = parseStoryCommand(action)

    if !this.commandSuccess && outcomeReport.isBlank && !action.verb.isBlank && !player.isDead then //&& outcomeReport.isBlank && storyReport.isBlank then // needs to hide "command not found" error during successful output
      //check if the verb could still be meaningfull
      //if outcomeReport.isEmpty then
        //if !action.verb.isBlank then
        //  outcomeReport = Some(parseStoryCommand(action.commandText))

      //default "unknown" output text
      //if action.verb.isEmpty then
      if action.commandText.endsWith("?") then
        s"$storyReport\n\n${action.commandText} ${D.misc("wrongQuestion")}"
      else if action.modifiers.isBlank then
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