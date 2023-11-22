package devolution

import devolution.*
import devolution.helpers.{D, Endgame, VisionUnlock}
import scala.util.Random
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
  private val olOcean1 = Area(D.areas("ocean1"), D.areas("ol"))
  private val phClearing = Area(D.areas("clearing"), D.areas("ph"))
  private val maPath = Area(D.areas("path"), D.areas("ma"))
  private val srRoom = Area(D.areas("room"), D.areas("sr"))
  private val geWhiteRoom = Area(D.areas("start"), D.areas("ge"))
  private val elTower = Area(D.areas("tower"), D.areas("el"))
  private val hdVacuum = Area(D.areas("vacuum"), D.areas("hd"))

  overflow    .setNeighbors(Vector(                       "future" -> bbVacuum))
  bbVacuum    .setNeighbors(Vector("past" -> overflow,    "future" -> psCenter))
  bbVacuum.deadly = true
  psCenter    .setNeighbors(Vector("past" -> bbVacuum,    "future" -> olOcean1))
  olOcean1   .setNeighbors(Vector("past" -> psCenter,    "future" -> phClearing))
  phClearing    .setNeighbors(Vector("past" -> olOcean1,   "future" -> maPath))
  maPath      .setNeighbors(Vector("past" -> phClearing,    "future" -> srRoom))
  srRoom      .setNeighbors(Vector("past" -> maPath,      "future" -> geWhiteRoom))
  geWhiteRoom .setNeighbors(Vector("past" -> srRoom,      "future" -> elTower))
  elTower     .setNeighbors(Vector("past" -> geWhiteRoom, "future" -> hdVacuum))
  hdVacuum    .setNeighbors(Vector("past" -> elTower,     "future" -> hdVacuum))

  val entryPoints = Vector(geWhiteRoom, psCenter, hdVacuum, maPath, phClearing, olOcean1, elTower, srRoom)
    .zipWithIndex.map(_.swap).toMap
  entryPoints.foreach((k,v) => v.setMovePhase(k))
  //assign the correct move phase based on the order the zone has been added to the entryPoint vector


  private val psPeriphery = Area(D.areas("closeVoid"), D.areas("ps"))
  private val psVoid = Area(D.areas("vacuum"), D.areas("ps"))
  psVoid.deadly = true

  psCenter.setNeighbors(Vector(D.movements("away") -> psPeriphery))
  psPeriphery.setNeighbors(Vector(D.movements("back") -> psCenter, D.movements("forward") -> psVoid))

  //future celestial bodies
  psCenter.interactables ++= Map(
    "dust1" -> Element(D.interactables("dust1"), 1),
    "dust2" -> Element(D.interactables("dust2"), 3),
    //"dust6" -> Element(D.interactables("dust6"), 1)
  )
  psPeriphery.interactables ++= Map(
    "dust3" -> Element(D.interactables("dust3"), 5),
    "dust4" -> Element(D.interactables("dust4"), 2),
    "dust5" -> Element(D.interactables("dust5"), 2),
    //"dust7" -> Element(D.interactables("dust7"), 1),
  )


  private val olOcean2 = Area(D.areas("ocean2"), D.areas("ol"))
  private val olOcean3 = Area(D.areas("ocean3"), D.areas("ol"))
  private val olOcean4 = Area(D.areas("ocean4"), D.areas("ol"))
  private val olThermal = Area(D.areas("thermal"), D.areas("ol"))
  private val olSurface = Area(D.areas("surface"), D.areas("ol"))
  private val olVolcano2 = Area(D.areas("volcano2"), D.areas("ol"))
  private val olLava = Area(D.areas("lava"), D.areas("ol"))
  olVolcano2.deadly = true
  olLava.deadly = true

  olOcean1   .setNeighbors(Vector(D.movements("forward") -> olSurface, D.movements("down") -> olOcean2))
  olOcean2   .setNeighbors(Vector(D.movements("up") -> olOcean1, D.movements("down") -> olOcean3))
  olOcean3   .setNeighbors(Vector(D.movements("up") -> olOcean2, D.movements("down") -> olOcean4))
  olOcean4   .setNeighbors(Vector(D.movements("up") -> olOcean4, D.movements("down") -> olVolcano2, D.movements("forward") -> olThermal))
  olThermal  .setNeighbors(Vector(D.movements("up") -> olOcean3, D.movements("down") -> olVolcano2, D.movements("back") -> olOcean4))
  olSurface  .setNeighbors(Vector(D.movements("back") -> olOcean1, D.movements("forward") -> olLava))


  private var tierlist = Vector("cell1", "cell2", "plankton", "mollusk", "fish1", "fish2")
  private var currentTier = 0
  olThermal.interactables ++= Map(
    "thermalRock" -> Element(D.interactables("thermalRock"))
  )

  olSurface.interactables ++= Map(
    "rock" -> Element(D.interactables("rock"))
  )

  def showOlInteractables() =
    olOcean1.interactables ++= Map(
      "cell2" -> Element(D.interactables("cell2")),
      "plankton" -> Element(D.interactables("plankton")),
      "mollusk" -> Element(D.interactables("mollusk")),
      "fish1" -> Element(D.interactables("fish1"))
    )

    olOcean2.interactables ++= Map(
      "mollusk" -> Element(D.interactables("mollusk")),
      "plankton" -> Element(D.interactables("plankton")),
      "fish1" -> Element(D.interactables("fish1")),
      "cell1" -> Element(D.interactables("fish1")),
    )

    olOcean3.interactables ++= Map(
      "fish2" -> Element(D.interactables("fish2")),
      "fish1" -> Element(D.interactables("fish1")),
      "cell1" -> Element(D.interactables("fish1")),
    )

    olOcean4.interactables ++= Map(
      "fish2" -> Element(D.interactables("fish2")),
      "mollusk" -> Element(D.interactables("mollusk")),
      "cell1" -> Element(D.interactables("fish1")),
    )

    olThermal.interactables ++= Map(
      "thermalRock" -> Element(D.interactables("thermalRock")),
      "cell2" -> Element(D.interactables("cell2"))
    )


  private val phJungle = Area(D.areas("jungle"), D.areas("ph"))
  private val phHill = Area(D.areas("hill"), D.areas("ph"))
  private val phSwamp = Area(D.areas("swamp"), D.areas("ph"))
  private val phCave1 = Area(D.areas("cave1"), D.areas("ph"))
  private val phCave2 = Area(D.areas("cave2"), D.areas("ph"))
  private val phVolcano = Area(D.areas("volcano"), D.areas("ph"))
  private val phNest = Area(D.areas("nest"), D.areas("ph"))
  private val phLava = Area(D.areas("lava"), D.areas("ph"))
  phLava.deadly = true
  phNest.deadly = true

  phClearing  .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phSwamp, D.movements("e") -> phJungle, D.movements("w") -> phHill))
  phJungle    .setNeighbors(Vector(D.movements("n") -> phCave1, D.movements("s") -> phClearing, D.movements("e") -> phHill, D.movements("w") -> phNest))
  phHill      .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phSwamp, D.movements("e") -> phClearing, D.movements("w") -> phVolcano))
  phVolcano   .setNeighbors(Vector(D.movements("n") -> phJungle, D.movements("s") -> phJungle, D.movements("e") -> phHill, D.movements("w") -> phLava))
  phSwamp     .setNeighbors(Vector(D.movements("n") -> phClearing, D.movements("s") -> phNest, D.movements("e") -> phHill, D.movements("w") -> phJungle))
  phCave1     .setNeighbors(Vector(D.movements("in") -> phCave2, D.movements("s") -> phJungle, D.movements("e") -> phJungle, D.movements("w") -> phJungle))
  phCave2     .setNeighbors(Vector(D.movements("back") -> phCave1,  D.movements("forward") -> phNest))


  phJungle.interactables ++= Map(
    "step" -> Element(D.interactables("step")),
    "tree" -> Element(D.interactables("tree"))
  )
  phJungle.foundAbility = D.possibleAbilities("hear")
  phHill.interactables ++= Map(
    "horn" -> Element(D.interactables("horn")),
    "escrement" -> Element(D.interactables("escrement"))
  )
  phHill.foundAbility = D.possibleAbilities("vision")
  phVolcano.interactables ++= Map(
    "egg" -> Element(D.interactables("egg")),
    "rock" -> Element(D.interactables("rock"))
  )
  phVolcano.foundAbility = D.possibleAbilities("proprio")

  phSwamp.interactables ++= Map(
    "sand" -> Element(D.interactables("sand"))
  )
  phSwamp.foundAbility = D.possibleAbilities("proprio")

  phCave2.interactables ++= Map(
    "droplet" -> Element(D.interactables("droplet"))
  )
  phCave2.foundAbility = D.possibleAbilities("hear")


  private val maCastle1 = Area(D.areas("castle1"), D.areas("ma"))
  private val maCastle2 = Area(D.areas("castle2"), D.areas("ma"))
  private val maField = Area(D.areas("field"), D.areas("ma"))
  private val maVillage = Area(D.areas("village"), D.areas("ma"))
  private val maHouse = Area(D.areas("house"), D.areas("ma"))
  private val maStream = Area(D.areas("stream"), D.areas("ma"))
  private val maGrave = Area(D.areas("grave"), D.areas("ma"))

  maCastle2.deadly = true
  maPath    .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maCastle1))
  maCastle1 .setNeighbors(Vector(D.movements("w") -> maCastle2, D.movements("e") -> maPath, D.movements("s") -> maField))
  maVillage  .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maField, D.movements("e") -> maStream, D.movements("w") -> maPath))
  maHouse  .setNeighbors(Vector(D.movements("back") -> maVillage))
  maGrave   .setNeighbors(Vector(D.movements("s") -> maVillage, D.movements("w") -> maPath, D.movements("e") -> maStream))
  maStream  .setNeighbors(Vector(D.movements("w") -> maVillage))
  maField   .setNeighbors(Vector(D.movements("n") -> maPath))

  maCastle1.interactables ++= Map(
    "guard" -> Element(D.interactables("guard"))
  )
  maCastle1.foundAbility = D.possibleAbilities("fear")

  maHouse.interactables ++= Map(
    "ill" -> Element(D.interactables("ill"))
  )
  maHouse.foundAbility = D.possibleAbilities("sad")

  maGrave.interactables ++= Map(
    "corpse" -> Element(D.interactables("corpse"))
  )
  maGrave.foundAbility = D.possibleAbilities("sad")

  maStream.interactables ++= Map(
    "rat" -> Element(D.interactables("rat"))
  )
  maVillage.interactables ++= Map(
    "strappado" -> Element(D.interactables("strappado"))
  )
  maVillage.foundAbility = D.possibleAbilities("fear")


  private val geSwitch = Area(D.areas("switch"), D.areas("ge"))
  private val geServer = Area(D.areas("server"), D.areas("ge"))
  private val gePC = Area(D.areas("pc"), D.areas("ge"))
  private val geFirewall = Area(D.areas("firewall"), D.areas("ge"))
  //private val geWeb = Area(D.areas("web"), D.areas("ge"))

  geSwitch   .setNeighbors(Vector(D.movements("up") -> gePC, D.movements("down") -> geServer, D.movements("forward") -> geFirewall))
  geServer   .setNeighbors(Vector(D.movements("up") -> geSwitch))
  gePC       .setNeighbors(Vector(D.movements("forward") -> geSwitch))
  geFirewall .setNeighbors(Vector(D.movements("back") -> geSwitch))

  gePC.interactables ++= Map(
    "setting1" -> Element(D.interactables("setting1")),
    "setting2" -> Element(D.interactables("setting2")),
    "setting3" -> Element(D.interactables("setting3"))
  )
  geServer.interactables ++= Map(
    "peripheral1" -> Element(D.interactables("peripheral1")),
    "peripheral2" -> Element(D.interactables("peripheral2")),
    "peripheral3" -> Element(D.interactables("peripheral3"))
  )
  geFirewall.interactables ++= Map(
    "memory1" -> Element(D.interactables("memory1")),
    "memory2" -> Element(D.interactables("memory2")),
    "memory3" -> Element(D.interactables("memory3"))
  )

  private val elTerrace = Area(D.areas("terrace"), D.areas("el"))
  elTerrace.deadly = true

  elTower   .setNeighbors(Vector(D.movements("forward") -> elTerrace))


  hdVacuum.interactables ++= Map(
    "particle" -> Element(D.interactables("particle"))
  )
  hdVacuum.foundAbility = D.possibleAbilities("memory")

  // conditions to move between areas in that timeline
  /*bbVacuum    .setMovePhase(0)
  psCenter    .setMovePhase(1)
  olOcean1   .setMovePhase(5)
  phClearing    .setMovePhase(4)
  maPath      .setMovePhase(3)
  srRoom      .setMovePhase(7)
  geWhiteRoom .setMovePhase(9)
  elTower     .setMovePhase(6)
  hdVacuum    .setMovePhase(2)*/

  val areas = Vector(
    overflow,
    bbVacuum,
    psCenter,psPeriphery,psVoid,
    olOcean1,olOcean2,olOcean3,olOcean4,olThermal,olSurface,olVolcano2,olLava,
    phClearing,phJungle,phHill,phVolcano,phSwamp,phCave1,phCave2,phLava,
    maPath,maCastle1,maVillage,maHouse,maGrave,maStream,maField,maCastle2,
    srRoom,
    geWhiteRoom,geSwitch,geServer,gePC,geFirewall,
    elTower,elTerrace,
    hdVacuum)


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
      this.commandSuccess = true
      return player.die()

    //if action.verb == D.actionNames("evolve") || action.verb == D.actionNames("devolve") then
    //  timelineDiscovered = false

    //if player.phase == 1 then outcome = D.ge.answer

    //check for a timeline question guess
    //the following if will take care of the return output
    if isQuestionRight(input, D.zones(player.location.timeline).question)
      && this.isInRightTimeline then
      player.phase += 1
      this.commandSuccess = true
      //return D.zones.get(lastExcpectedTimeline).answer

    if player.phase == 1 && !player.remembers then
      outcome += player.learn(D.possibleAbilities("memory"))
    else if player.phase == Endgame then //&& player.has(D.possibleAbilities("tought")) then
      //outcome += D.zones(player.location.timeline).tought
      if isQuestionRight(input, D.zones(player.location.timeline).word) then
        return outcome + D.zones(player.location.timeline).realization
      if isQuestionRight(input, D.misc("finalQuestion")) then
        //player.setLocation(Some(gePC))
        return "final question yey you won" //TO-DO



    //+ "\n" + player.learn(D.possibleAbilities("curious"))

    //println(entryPoints(player.phase-1).timeline)
    //show old answer as a hint
    if player.location.timeline == lastExcpectedTimeline || isQuestionRight(input, D.zones.get(lastExcpectedTimeline).map(_.question).getOrElse("")) then
      //println(D.zones("Globalization era").answer + " " + D.areas(entryPoints.get(player.phase - 1).map(_.timeline).getOrElse("")))
      this.commandSuccess = true
      //remove player from actual area to hide useless decriptions
      player.timelineChosen = false
      return outcome + D.zones(lastExcpectedTimeline).answer

    if isInRightTimeline && player.timelineChosen then
      player.phase match
        case 0 =>
          if player.remembers then
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
          //if player.location == geWhiteRoom then
          //  return D.ge.misc("learn")
          //if player.location == psCenter then
          //println(":::"+player.location.interactables.values.map(_.completed).mkString(","))
          //if player.location.interactables.values.headOption.exists(_.completed) then //&& action.verb == D.ps("leave") then
            //player.location.interactables = player.location.interactables.tail
            //D.ps("left")

          //println("?"+player.location.interactables.values.count(_.completed))

          if player.location == psCenter then
            player.location.interactables.values.count(_.completed) match
                case 0 => "hello?"+player.location.interactables
                //shows hint only if the first cluster just ot created
                case 1 if player.location.interactables.values.count(e => e.interactions == 0 && !e.completed) == 1 =>
                  this.commandSuccess = true
                  return D.ps("star1")
                case 2 =>
                  this.commandSuccess = true
                  return D.ps("move")
                case _ => ""
          if player.location == psPeriphery then
            println(player.location.interactables.values)
            println(player.location.interactables.values.count(_.completed))
            player.location.interactables.values.count(_.completed) match
              case 1 =>
                this.commandSuccess = true
                return D.ps("less")
              case 2 =>
                this.commandSuccess = true
                return D.ps("move")
              case _ => ""

          //if player.timeline == D.areas("bb") && action.verb == D.actions("go") then
            //timelineDiscovered = true
          /*if player.timeline == D.areas("bb") && isQuestionRight(input, D.ps.question)  then
            outcome = D.ps.answer
            player.phase += 1
              commandSuccess = true*/
        case 3 =>
          if player.location == hdVacuum then
            if action.verb == D.actionNames("go") then
              return Random.shuffle(D.hd.question.split(" ")).head
            return D.hd("intro")
        case 4 =>
          //if the player got the body, spawn all other creatures
          if olThermal.interactables("thermalRock").completed && player.currentTier == -1 then
            showOlInteractables()
            player.currentTier += 1
            return D.ol("body")
          if action.verb == D.actionNames("eat") && action.modifiers.nonEmpty then
            // interactables.get(action.modifiers)
            val focusedElement = player.location.searchInteractables(action.modifiers, action.verb).map(_._2.name).getOrElse("")
            if tierlist.indexOf(focusedElement) > -1 then
              if tierlist.indexOf(focusedElement) == player.currentTier then
                return player.die()
              else
                player.currentTier += 1

          if player.currentTier == tierlist.size-1 then
            player.currentTier += 1
            return D.ol.misc("done")

          /*if player.timeline == D.areas("hd") && isQuestionRight(input, D.hd.question)  then
            outcome = D.hd.answer
            player.phase += 1
              commandSuccess = true*/
        case 3 =>
          /*if player.timeline == D.areas("ma") && isQuestionRight(input, D.ma.question) then
            outcome = D.ma.answer
            player.phase += 1
            commandSuccess = true*/


        case _ =>
          D.debug("noPhase")
        //ALL THE LOGIC ABOUT GAME PROGRESSION GOES HERE
    outcome

  /**
    * Checks if the input correspond to the question/word that the user has to write
    * @param input
    * @param question
    * @return
    */
  def isQuestionRight(input: String, question: String) =
    !input.isBlank && question.toLowerCase.contains(input.toLowerCase())

  /**
    * Check if user went back to the last completed timeline.
    * Allows to write the end-timeline hint message again as reminder.
    * @return
    */
  def lastExcpectedTimeline = entryPoints.get(player.phase-1).map(_.timeline).getOrElse("")

  /**
    * Check is the player has something to do in this timeline.
    * @return
    */
  def isInRightTimeline = player.location.timeline == entryPoints(player.phase%entryPoints.size).timeline

  /**
    * Handle the dead of the player and the reset options/state changes.
    * The program freeze for some instants to let the player read the output, then automatically reset.
    */
  def reset() =
    player.phase = 0
    player.timelineChosen = true
    player.currentTier = -1
    this.areas.foreach(_.interactables.foreach(_._2.interactions = 0))


  var commandSuccess = false
  //var timelineDiscovered = false

  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    var outcomeReport = action.execute(this.player).getOrElse("")
    var storyReport = parseStoryCommand(action)

    if !this.commandSuccess && outcomeReport.isBlank && !action.verb.isBlank && action.verb != D.misc("deadCommand") then //&& outcomeReport.isBlank && storyReport.isBlank then // needs to hide "command not found" error during successful output
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