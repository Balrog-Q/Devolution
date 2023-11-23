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

  // Initialize the areas in the game
  private val bonus = Timeline(D.areas("bonus"))
  private val bigBang = Timeline(D.areas("bb"))
  private val primordialSoup = Timeline(D.areas("ps"))
  private val originOfLife = Timeline(D.areas("ol"))
  private val prehistory = Timeline(D.areas("ph"))
  private val middleAges = Timeline(D.areas("ma"))
  private val scientificRevo = Timeline(D.areas("sr"))
  private val globalization = Timeline(D.areas("ge"))
  private val endOfLife = Timeline(D.areas("el"))
  private val headDead = Timeline(D.areas("hd"))

  private val overflow = Area(D.areas("bonus"), bonus)
  private val bbVacuum = Area(D.areas("vacuum"), bigBang)
  private val psCenter = Area(D.areas("voidCenter"), primordialSoup)
  private val olOcean1 = Area(D.areas("ocean1"), originOfLife)
  private val phClearing = Area(D.areas("clearing"), prehistory)
  private val maPath = Area(D.areas("path"), middleAges)
  private val srRoom = Area(D.areas("room"), scientificRevo)
  private val geWhiteRoom = Area(D.areas("start"),globalization)
  private val elTower = Area(D.areas("tower"), endOfLife)
  private val hdVacuum = Area(D.areas("vacuum"), headDead)

  overflow    .setNeighbors(Vector(                       "future" -> bbVacuum))
  bbVacuum    .setNeighbors(Vector("past" -> overflow,    "future" -> psCenter))
  bbVacuum.setDeadly()
  psCenter    .setNeighbors(Vector("past" -> bbVacuum,    "future" -> olOcean1, "outside this" -> overflow))
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


  private val psPeriphery = Area(D.areas("closeVoid"), primordialSoup)
  private val psVoid = Area(D.areas("vacuum"), primordialSoup)
  psVoid.setDeadly()

  psCenter.setNeighbors(Vector(D.movements("away") -> psPeriphery))
  psPeriphery.setNeighbors(Vector(D.movements("back") -> psCenter, D.movements("forward") -> psVoid))

  //future celestial bodies
  psCenter.setInteractables(Vector(
    "dust1" -> Element(D.interactables("dust1"), 1),
    "dust2" -> Element(D.interactables("dust2"), 3),
    "dust3" -> Element(D.interactables("dust3"), 7)
  ))
  psPeriphery.setInteractables(Vector(
    "dust4" -> Element(D.interactables("dust4"), 1),
    "dust5" -> Element(D.interactables("dust5"), 3)
  ))
  geWhiteRoom.foundAbility = D.possibleAbilities("curious")

  private val olOcean2 = Area(D.areas("ocean2"), originOfLife)
  private val olOcean3 = Area(D.areas("ocean3"), originOfLife)
  private val olOcean4 = Area(D.areas("ocean4"), originOfLife)
  private val olThermal = Area(D.areas("thermal"), originOfLife)
  private val olSurface = Area(D.areas("surface"), originOfLife)
  private val olVolcano2 = Area(D.areas("volcano2"), originOfLife)
  private val olLava = Area(D.areas("lava"), originOfLife)
  olVolcano2.setDeadly()
  olLava.setDeadly()

  olOcean1   .setNeighbors(Vector(D.movements("forward") -> olSurface, D.movements("down") -> olOcean2))
  olOcean2   .setNeighbors(Vector(D.movements("up") -> olOcean1, D.movements("down") -> olOcean3))
  olOcean3   .setNeighbors(Vector(D.movements("up") -> olOcean2, D.movements("down") -> olOcean4))
  olOcean4   .setNeighbors(Vector(D.movements("up") -> olOcean4, D.movements("down") -> olVolcano2, D.movements("forward") -> olThermal))
  olThermal  .setNeighbors(Vector(D.movements("up") -> olOcean3, D.movements("down") -> olVolcano2, D.movements("back") -> olOcean4))
  olSurface  .setNeighbors(Vector(D.movements("back") -> olOcean1, D.movements("forward") -> olLava))


  private var tierlist = Vector("cell1", "cell2", "plankton", "mollusk", "fish1", "fish2")
  private var progression = 0
  olThermal.setInteractables(Vector(
    "thermalRock" -> Element(D.interactables("thermalRock"))
  ))

  olSurface.setInteractables(Vector(
    "rock" -> Element(D.interactables("rock"))
  ))

  def showOlInteractables() =
    olOcean1.setInteractables(Vector(
      "cell2" -> Element(D.interactables("cell2")),
      "plankton" -> Element(D.interactables("plankton")),
      "mollusk" -> Element(D.interactables("mollusk")),
      "fish1" -> Element(D.interactables("fish1"))
    ))

    olOcean2.setInteractables(Vector(
      "mollusk" -> Element(D.interactables("mollusk")),
      "plankton" -> Element(D.interactables("plankton")),
      "fish1" -> Element(D.interactables("fish1")),
      "cell1" -> Element(D.interactables("fish1")),
    ))

    olOcean3.setInteractables(Vector(
      "fish2" -> Element(D.interactables("fish2")),
      "fish1" -> Element(D.interactables("fish1")),
      "cell1" -> Element(D.interactables("fish1")),
    ))

    olOcean4.setInteractables(Vector(
      "fish2" -> Element(D.interactables("fish2")),
      "mollusk" -> Element(D.interactables("mollusk")),
      "cell1" -> Element(D.interactables("fish1")),
    ))

    olThermal.setInteractables(Vector(
      "thermalRock" -> Element(D.interactables("thermalRock")),
      "cell2" -> Element(D.interactables("cell2"))
    ))


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


  phJungle.setInteractables(Vector(
    "step" -> Element(D.interactables("step"), 4),
    "tree" -> Element(D.interactables("tree"), -1)
  ))
  phJungle.foundAbility = D.possibleAbilities("hear")
  phHill.setInteractables(Vector(
    "horn" -> Element(D.interactables("horn")),
    "escrement" -> Element(D.interactables("escrement"))
  ))
  phHill.foundAbility = D.possibleAbilities("vision")
  phVolcano.setInteractables(Vector(
    "egg" -> Element(D.interactables("egg")),
    "rock" -> Element(D.interactables("rock"))
  ))
  phVolcano.foundAbility = D.possibleAbilities("proprio")

  phSwamp.setInteractables(Vector(
    "sand" -> Element(D.interactables("sand"), -1)
  ))
  phSwamp.foundAbility = D.possibleAbilities("proprio")

  phCave2.setInteractables(Vector(
    "droplet" -> Element(D.interactables("droplet"), -1)
  ))
  phCave2.foundAbility = D.possibleAbilities("hear")


  private val maCastle1 = Area(D.areas("castle1"), middleAges)
  private val maCastle2 = Area(D.areas("castle2"), middleAges)
  private val maField = Area(D.areas("field"), middleAges)
  private val maVillage = Area(D.areas("village"), middleAges)
  private val maHouse = Area(D.areas("house"), middleAges)
  private val maStream = Area(D.areas("stream"), middleAges)
  private val maGrave = Area(D.areas("grave"), middleAges)
  maCastle2.setDeadly()
  maPath    .setNeighbors(Vector(D.movements("s") -> maField, D.movements("e") -> maVillage, D.movements("w") -> maCastle1))
  maCastle1 .setNeighbors(Vector(D.movements("w") -> maCastle2, D.movements("e") -> maPath, D.movements("s") -> maField))
  maVillage  .setNeighbors(Vector(D.movements("n") -> maGrave, D.movements("s") -> maHouse, D.movements("e") -> maStream, D.movements("w") -> maPath))
  maHouse  .setNeighbors(Vector(D.movements("back") -> maVillage))
  maGrave   .setNeighbors(Vector(D.movements("s") -> maVillage, D.movements("w") -> maPath, D.movements("e") -> maStream))
  maStream  .setNeighbors(Vector(D.movements("s") -> maField, D.movements("w") -> maVillage))
  maField   .setNeighbors(Vector(D.movements("n") -> maPath, D.movements("w") -> maCastle1))

  maCastle1.setInteractables(Vector(
    "guard" -> Element(D.interactables("guard"))
  ))
  maCastle1.foundAbility = D.possibleAbilities("fear")

  maHouse.setInteractables(Vector(
    "ill" -> Element(D.interactables("ill"))
  ))
  maHouse.foundAbility = D.possibleAbilities("sad")

  maGrave.setInteractables(Vector(
    "corpse" -> Element(D.interactables("corpse"))
  ))
  maGrave.foundAbility = D.possibleAbilities("sad")

  maStream.setInteractables(Vector(
    "rat" -> Element(D.interactables("rat"))
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")

  maVillage.setInteractables(Vector(
    "strappado" -> Element(D.interactables("strappado"))
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")


  private val geSwitch = Area(D.areas("switch"), globalization)
  private val geServer = Area(D.areas("server"), globalization)
  private val gePC = Area(D.areas("pc"), globalization)
  private val geFirewall = Area(D.areas("firewall"), globalization)
  //private val geWeb = Area(D.areas("web"), D.areas("ge"))

  geSwitch   .setNeighbors(Vector(D.movements("up") -> gePC, D.movements("down") -> geServer, D.movements("forward") -> geFirewall))
  geServer   .setNeighbors(Vector(D.movements("up") -> geSwitch))
  gePC       .setNeighbors(Vector(D.movements("forward") -> geSwitch))
  geFirewall .setNeighbors(Vector(D.movements("back") -> geSwitch))

  gePC.setInteractables(Vector(
    "setting1" -> Element(D.interactables("setting1")),
    "setting2" -> Element(D.interactables("setting2")),
    "setting3" -> Element(D.interactables("setting3"))
  ))
  geServer.setInteractables(Vector(
    "peripheral1" -> Element(D.interactables("peripheral1")),
    "peripheral2" -> Element(D.interactables("peripheral2")),
    "peripheral3" -> Element(D.interactables("peripheral3"))
  ))
  geFirewall.setInteractables(Vector(
    "memory1" -> Element(D.interactables("memory1")),
    "memory2" -> Element(D.interactables("memory2")),
    "memory3" -> Element(D.interactables("memory3"))
  ))

  private val elTerrace = Area(D.areas("terrace"), endOfLife)
  elTerrace.setDeadly()

  elTower   .setNeighbors(Vector(D.movements("forward") -> elTerrace))


  hdVacuum.setInteractables(Vector(
    "particle" -> Element(D.interactables("particle"))
  ))
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
    commandSuccess = true

    //bonus cheat mode
    if player.location == overflow then
      player.invincible = action.verb == D.misc("accept")
      return D.misc("accepted")
    //game start
    //D.zones.map((k,v) => v.question).zipWithIndex.filter((v,k) => v == input)
    if player.location.isDeadly then
      return player.die()

    //if action.verb == D.action("evolve") || action.verb == D.action("devolve") then
    //  timelineDiscovered = false

    //if player.phase == 1 then outcome = D.ge.answer

    //check for a timeline question guess
    //the following if will take care of the return output
    if isQuestionRight(input, D.zones(player.location.timeline.name).question)
      && this.isInRightTimeline then
      player.setLocation(Some(this.entryPoints(player.phase)))
      player.phase += 1
      //return D.zones.get(lastExpectedTimeline).answer

    //if player.phase == 1 && !player.remembers then
      //outcome += player.learn(D.possibleAbilities("memory"))

    else if player.phase == Endgame then //&& player.has(D.possibleAbilities("tought")) then
      //outcome += D.zones(player.location.timeline).tought
      if isQuestionRight(input, D.zones(player.location.timeline.name).word) then
        return outcome + D.zones(player.location.timeline.name).realization
      if isQuestionRight(input, D.misc("finalQuestion")) then
        //player.setLocation(Some(gePC))
        return "final question yey you won" //TO-DO



    //+ "\n" + player.learn(D.possibleAbilities("curious"))

    //println(entryPoints(player.phase-1).timeline)
    //show old answer as a hint
    if player.location.timeline.name == lastExpectedTimeline /*this showed old answers player.isInCompletedTimeline*/ || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse("")) then
      //println(D.zones("Globalization era").answer + " " + D.areas(entryPoints.get(player.phase - 1).map(_.timeline).getOrElse("")))
      //remove player from actual area to hide useless decriptions
      player.timelineChosen = false
      outcome += D.zones(lastExpectedTimeline).answer //player.location.timeline.name).answer
    if player.phase == 1 && player.location == geWhiteRoom && !player.has(D.possibleAbilities("curious")) then
      return outcome + "\n" + D.ge.misc("learn")

    if isInRightTimeline && player.timelineChosen then
      player.phase match
        case 0 =>
          //print a random word from the question if the user is trying without success
          if action.commandText.contains("?") then
            progression += 1
            if progression > 3 && progression % 2 == 0 then
              //commandSuccess = true
              return Random.shuffle(D.ge.question.split(" ")).head

          if player.remembers then
            this.commandSuccess = false
            return D.ge("intro2")
          else
            this.commandSuccess = false
            return D.ge("intro") + "\n" + D.ge("intro2")

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
          //println(":::"+player.location.interactable.values.map(_.completed).mkString(","))
          //if player.location.interactable.values.headOption.exists(_.completed) then //&& action.verb == D.ps("leave") then
            //player.location.interactable = player.location.interactable.tail
            //D.ps("left")

          //println("?"+player.location.interactable.values.count(_.completed))

          this.commandSuccess = false
          if player.location == psCenter then
            player.location.interactable.values.count(_.completed) match
                case 0 =>
                  return D.ps("intro")

                //shows hint only if the first cluster just ot created
                case 1 if player.location.interactable.values.count(e => e.interactions != 0 && !e.completed) == 0 =>
                  return D.ps("star1")
                case 2 if player.location.interactable.values.exists(e => e.interactions > 2 && !e.completed) =>
                  return D.ps("more2")
                case 2 if psPeriphery.interactable.count(_._2.completed) < 2 =>
                  return D.ps("move")
                case 3 =>
                  return D.ps("right")
                case _ =>
          if player.location == psPeriphery then
            println(player.location.interactable.values)
            println(player.location.interactable.values.count(_.completed))
            player.location.interactable.values.count(_.completed) match
              case 1 =>
                return D.ps("more1")
              case 2 =>
                return D.ps("big")
              case _ => 

          //if player.timeline == D.areas("bb") && action.verb == D.actions("go") then
            //timelineDiscovered = true
          /*if player.timeline == D.areas("bb") && isQuestionRight(input, D.ps.question)  then
            outcome = D.ps.answer
            player.phase += 1
              commandSuccess = true*/
        case 2 =>
          //if player.location == hdVacuum then
          outcome += D.hd("intro")
          if action.verb == D.action("go") then
            return Random.shuffle(D.hd.question.split(" ")).head
          if hdVacuum.interactable.exists(_._2.completed) then
            return D.hd("annihilated")
          else
            return outcome + "\n\n" + D.hd("particle")

        case 3 =>
          //spawns random pieces of commands (2 letters)
          if action.verb == D.action("go") then
            return Random.shuffle(((D.action("fear") + D.action("sad")).sliding(2,2).toVector)).head

        case 4 =>
          //spawns random pieces of commands (3 letters)
          if action.verb == D.action("go") then
            return Random.shuffle(((D.action("see") + "." + D.action("hear") + D.action("touch")).sliding(3,3).toVector)).head
          if phJungle.interactable("step").completed then
            player.die()

        case 5 =>
          //if the player got the body, spawn all other creatures
          if olThermal.interactable("thermalRock").completed && player.progression == -1 then
            showOlInteractables()
            player.progression += 1
            return D.ol("body")
          if action.verb == D.action("eat") && action.modifiers.nonEmpty then
            // interactables.get(action.modifiers)
            val focusedElement = player.location.searchInteractables(action.modifiers, action.verb).map(_._2.name).getOrElse("")
            if tierlist.indexOf(focusedElement) > -1 then
              if tierlist.indexOf(focusedElement) == player.progression then
                return player.die()
              else
                player.progression += 1

          if player.progression == tierlist.size-1 then
            player.progression += 1
            return D.ol.misc("done")

          /*if player.timeline == D.areas("hd") && isQuestionRight(input, D.hd.question)  then
            outcome = D.hd.answer
            player.phase += 1
              commandSuccess = true*/
        case 6 =>
          /*if player.timeline == middleAges && isQuestionRight(input, D.ma.question) then
            outcome = D.ma.answer
            player.phase += 1
            commandSuccess = true*/


        case _ =>
          //D.debug("noPhase")
        //ALL THE LOGIC ABOUT GAME PROGRESSION GOES HERE
    this.commandSuccess = !outcome.isBlank
    outcome

  /**
    * Checks if the input correspond to the question/word that the user has to write
    * @param input
    * @param question
    * @return
    */
  def isQuestionRight(input: String, question: String) =
    !input.isBlank && question.toLowerCase == input.toLowerCase

  /**
    * Check if user went back to the last completed timeline.
    * Allows to write the end-timeline hint message again as reminder.
    * @return
    */
  def lastExpectedTimeline = entryPoints.get(player.phase-1).map(_.timeline.name).getOrElse("")

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
    if !player.invincible then
      this.areas.foreach(_.interactable.foreach(_._2.interactions = 0))


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
      storyReport + {if storyReport.nonEmpty then "\n" else ""} + outcomeReport + "\nDEBUG: " + Action("$").execute(this.player).getOrElse("No desc?")

end Game