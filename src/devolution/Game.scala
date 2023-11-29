package devolution

import devolution.*
import devolution.helpers.*
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
  //private val realWorld = Timeline(D.areas("rw"))

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
  psCenter    .setNeighbors(Vector("past" -> bbVacuum,    "future" -> olOcean1, "outside" -> overflow))
  olOcean1   .setNeighbors(Vector("past" -> psCenter,    "future" -> phClearing))
  phClearing    .setNeighbors(Vector("past" -> olOcean1,   "future" -> maPath))
  maPath      .setNeighbors(Vector("past" -> phClearing,    "future" -> srRoom))
  srRoom      .setNeighbors(Vector("past" -> maPath,      "future" -> geWhiteRoom))
  geWhiteRoom .setNeighbors(Vector("past" -> srRoom,      "future" -> elTower))
  elTower     .setNeighbors(Vector("past" -> geWhiteRoom, "future" -> hdVacuum))
  hdVacuum    .setNeighbors(Vector("past" -> elTower,     "future" -> hdVacuum))

  val entryPoints = Vector(geWhiteRoom, psCenter, hdVacuum, maPath, phClearing, olOcean1, elTower, srRoom, geWhiteRoom)
    .zipWithIndex.map(_.swap).toMap
  entryPoints.foreach((k,v) => v.setMovePhase(k))
  //assign the correct move phase based on the order the zone has been added to the entryPoint vector


  private val psPeriphery = Area(D.areas("closeVoid"), primordialSoup)
  private val psVoid = Area(D.areas("vacuum"), primordialSoup)
  psVoid.setDeadly()

  psCenter.setNeighbors(Vector(D.direction("away") -> psPeriphery))
  psPeriphery.setNeighbors(Vector(D.direction("back") -> psCenter, D.direction("away") -> psVoid))

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

  olOcean1   .setNeighbors(Vector(D.direction("away") -> olSurface, D.direction("down") -> olOcean2))
  olOcean2   .setNeighbors(Vector(D.direction("up") -> olOcean1, D.direction("down") -> olOcean3))
  olOcean3   .setNeighbors(Vector(D.direction("up") -> olOcean2, D.direction("down") -> olOcean4))
  olOcean4   .setNeighbors(Vector(D.direction("up") -> olOcean3, D.direction("down") -> olVolcano2, D.direction("forward") -> olThermal))
  olThermal  .setNeighbors(Vector(D.direction("up") -> olOcean3, D.direction("down") -> olVolcano2, D.direction("back") -> olOcean4))
  olSurface  .setNeighbors(Vector(D.direction("back") -> olOcean1, D.direction("forward") -> olLava))


  private var tierlist = Vector(D.interactables("cell1").lowerName, D.interactables("cell2").lowerName, D.interactables("plankton").lowerName, D.interactables("mollusk").lowerName, D.interactables("fish1").lowerName, D.interactables("fish2").lowerName)
  //private var progression = 0
  olThermal.setInteractables(Vector(
    "thermalRock" -> Element(D.interactables("thermalRock"))
  ))

  olSurface.setInteractables(Vector(
    "rock" -> Element(D.interactables("rock"))
  ))

  def showOlInteractables() =
    olOcean1.setInteractables(Vector(
      "cell2" -> Element(D.interactables("cell2"), -1),
      "plankton" -> Element(D.interactables("plankton"), -1),
      "fish1" -> Element(D.interactables("fish1"), -1)
    ))

    olOcean2.setInteractables(Vector(
      "mollusk" -> Element(D.interactables("mollusk"), -1),
      "plankton" -> Element(D.interactables("plankton"), -1),
      "fish1" -> Element(D.interactables("fish1"), -1),
      "cell1" -> Element(D.interactables("cell1"), -1),
    ))

    olOcean3.setInteractables(Vector(
      "fish2" -> Element(D.interactables("fish2"), -1),
      "fish1" -> Element(D.interactables("fish1"), -1),
      "cell1" -> Element(D.interactables("fish1"), -1),
    ))

    olOcean4.setInteractables(Vector(
      "fish2" -> Element(D.interactables("fish2"), -1),
      "mollusk" -> Element(D.interactables("mollusk"), -1),
      "cell1" -> Element(D.interactables("fish1"), -1),
    ))

    olThermal.setInteractables(Vector(
      "cell1" -> Element(D.interactables("cell1"), -1)
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

  phClearing  .setNeighbors(Vector(D.direction("n") -> phJungle, D.direction("s") -> phSwamp, D.direction("e") -> phJungle, D.direction("w") -> phHill))
  phJungle    .setNeighbors(Vector(D.direction("n") -> phCave1, D.direction("s") -> phClearing, D.direction("w") -> phHill, D.direction("e") -> phNest))
  phHill      .setNeighbors(Vector(D.direction("n") -> phJungle, D.direction("s") -> phSwamp, D.direction("e") -> phClearing, D.direction("w") -> phVolcano))
  phVolcano   .setNeighbors(Vector(D.direction("n") -> phJungle, D.direction("s") -> phJungle, D.direction("e") -> phHill, D.direction("w") -> phLava))
  phSwamp     .setNeighbors(Vector(D.direction("n") -> phClearing, D.direction("s") -> phNest, D.direction("w") -> phHill, D.direction("e") -> phJungle))
  phCave1     .setNeighbors(Vector(D.direction("down") -> phCave2, D.direction("s") -> phJungle, D.direction("e") -> phJungle, D.direction("w") -> phJungle))
  phCave2     .setNeighbors(Vector(D.direction("back") -> phCave1,  D.direction("down") -> phNest))


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
  maPath    .setNeighbors(Vector(D.direction("s") -> maField, D.direction("e") -> maVillage, D.direction("w") -> maCastle1))
  maCastle1 .setNeighbors(Vector(D.direction("w") -> maCastle2, D.direction("e") -> maPath, D.direction("s") -> maField))
  maVillage  .setNeighbors(Vector(D.direction("n") -> maGrave, D.direction("s") -> maHouse, D.direction("e") -> maStream, D.direction("w") -> maPath))
  maHouse  .setNeighbors(Vector(D.direction("back") -> maVillage))
  maGrave   .setNeighbors(Vector(D.direction("s") -> maVillage, D.direction("w") -> maPath, D.direction("e") -> maStream))
  maStream  .setNeighbors(Vector(D.direction("s") -> maField, D.direction("w") -> maVillage))
  maField   .setNeighbors(Vector(D.direction("n") -> maPath, D.direction("w") -> maCastle1))

  maCastle1.setInteractables(Vector(
    "guard" -> Element(D.interactables("guard"), -1)
  ))
  maCastle1.foundAbility = D.possibleAbilities("fear")

  maHouse.setInteractables(Vector(
    "ill" -> Element(D.interactables("ill"), -1)
  ))
  maHouse.foundAbility = D.possibleAbilities("sad")

  maGrave.setInteractables(Vector(
    "corpse" -> Element(D.interactables("corpse"), -1)
  ))
  maGrave.foundAbility = D.possibleAbilities("sad")

  maStream.setInteractables(Vector(
    "rat" -> Element(D.interactables("rat"), -1)
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")

  maVillage.setInteractables(Vector(
    "strappado" -> Element(D.interactables("strappado"), -1)
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")


  srRoom.setInteractables(Vector(
    "scientist" -> Element(D.interactables("scientist"), -1),
    "priest" -> Element(D.interactables("priest"), -1),
    "mouse" -> Element(D.interactables("mouse")),
    "clock" -> Element(D.interactables("clock"))
  ))
  srRoom.foundAbility = D.possibleAbilities("thought")


  private val geSwitch = Area(D.areas("switch"), globalization)
  private val geServer = Area(D.areas("server"), globalization)
  private val gePC = Area(D.areas("pc"), globalization)
  private val geFirewall = Area(D.areas("firewall"), globalization)
  //private val geWeb = Area(D.areas("web"), D.areas("ge"))

  geSwitch   .setNeighbors(Vector(D.direction("up") -> gePC, D.direction("down") -> geServer, D.direction("forward") -> geFirewall))
  geServer   .setNeighbors(Vector(D.direction("back") -> geSwitch))
  gePC       .setNeighbors(Vector(D.direction("back") -> geSwitch))
  geFirewall .setNeighbors(Vector(D.direction("back") -> geSwitch))

  gePC.setInteractables(Vector(
    "peripheral1" -> Element(D.interactables("peripheral1")),
    "peripheral2" -> Element(D.interactables("peripheral2")),
    "peripheral3" -> Element(D.interactables("peripheral3"))
  ))
  geServer.setInteractables(Vector(
    "memory1" -> Element(D.interactables("memory1")),
    "memory2" -> Element(D.interactables("memory2")),
    "memory3" -> Element(D.interactables("memory3"))
  ))
  geFirewall.setInteractables(Vector(
    "setting1" -> Element(D.interactables("setting1")),
    "setting2" -> Element(D.interactables("setting2")),
    "setting3" -> Element(D.interactables("setting3"))
  ))

  private val elNorth = Area(D.areas("north"), endOfLife)
  private val elSouth = Area(D.areas("south"), endOfLife)
  private val elEast = Area(D.areas("east"), endOfLife)
  private val elWest = Area(D.areas("west"), endOfLife)
  private val elDown = Area(D.areas("down"), endOfLife)
  elNorth.setDeadly()
  elSouth.setDeadly()
  elEast.setDeadly()
  elWest.setDeadly()
  elDown.setDeadly()

  elTower   .setNeighbors(Vector(D.direction("n") -> elNorth))
  elTower   .setNeighbors(Vector(D.direction("s") -> elSouth))
  elTower   .setNeighbors(Vector(D.direction("e") -> elEast))
  elTower   .setNeighbors(Vector(D.direction("w") -> elWest))
  elTower   .setNeighbors(Vector(D.direction("down") -> elDown))


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
    elTower,elNorth,elSouth,elEast,elWest,elDown,
    hdVacuum)


  private def startingPoint = geWhiteRoom

  /** Determines if the adventure is complete, that is, if the player has won. */
  //private val destination = bb
  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(startingPoint)
  //player.tp("5")

  def isComplete = (geServer.interactable.values ++ gePC.interactable.values ++ geFirewall.interactable.values).exists(_.completed)
    //this.player.location == this.destination && this.player.has("battery") && this.player.has("remote")
  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete //|| this.player.hasQuit || this.turnCount == this.timeLimit
  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = D("quote")
  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = D("end")
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
      player.timelineChosen = true
    //game start
    if player.isDead then
      return ""//D("dead") + player.location.name//player.die()


    /* Timeline question guess check */

    //the following "if"s will take care of the return output
    if isQuestionRight(input, D.zones(player.location.timeline.name).question)
      && this.isInRightTimeline then
      player.setLocation(Some(player.lastEntryPoint))//this.entryPoints.getOrElse(player.phase, player.lastEntryPoint)))
      player.phase += 1
      player.progression = -1
    else if player.phase > Endgame then //&& player.has(D.possibleAbilities("tought")) then
      //outcome += D.zones(player.location.timeline).tought
      if player.location != overflow then
        if isQuestionRight(input, D("finalQuestion")) then //final question found
          //player.setLocation(Some(gePC))
          player.setLocation(Some(overflow))
          //return D.bonus.question
        if player.progression > -1 && player.canThink then //keep showing contemplate message
          outcome += D.zones(player.location.timeline.name).thought + "\n"

        if isQuestionRight(input, D.zones(player.location.timeline.name).word) then //&& player.progression == -1 then //special timeline word found. True only if there was no previous progression
          player.phase += 1
          //player.progression == 0
          return outcome + D.zones(player.location.timeline.name).realization

      /*if isQuestionRight(input, D.zones(player.location.timeline.name).word) then //&& player.progression == -1 then //special timeline word found. True only if there was no previous progression
        player.phase += 1
        //player.progression == 0
        outcome += D.zones(player.location.timeline.name).thought + "\n" + D.zones(player.location.timeline.name).realization + "\n"
      else if player.location != overflow then
        if isQuestionRight(input, D("finalQuestion")) then //final question found
          //player.setLocation(Some(gePC))
          player.setLocation(Some(overflow))
          //return D.bonus.question
        if player.progression > -1 && player.canThink then //keep showing contemplate message
          return /*outcome +=*/ D.zones(player.location.timeline.name).thought*/

      if player.location == overflow && !player.invincible then
        if action.verb == D("accept") then
          player.progression += 1
          player.invincible = true
          player.setLocation(Some(geSwitch))
          outcome = D.bonus.realization
        else if action.verb == D("refuse") then
          player.setLocation(Some(player.lastEntryPoint))
        else
          return D.bonus.question


    /* Answer showing logic */

    //Only useful before endgame
    if player.location.timeline.name == lastExpectedTimeline && (player.phase <= Endgame && !player.canThink || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse(""))) then
      //success if the guess has just been made or if there are no previous tries (that is, the timeline was already completed)
      this.commandSuccess = player.progression == -1 || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse(""))
      //remove player from actual area to hide useless decriptions, but only if it's not trying to enter the timeline
      player.timelineChosen = player.progression == -1 && (action.verb == D.action("explore") || action.verb == D.action("go"))

      player.progression += 1
      outcome += "\n" + this.changeSubject(D.zones(lastExpectedTimeline).question.capitalize) + " " + D.zones(lastExpectedTimeline).answer //player.location.timeline.name).answer


    /* Tmeline specific final conditions */

    //hint to learn critical thinking
    if player.phase == Endgame && player.location == srRoom && !player.canThink then //missing critical think ability
      return outcome + "\n\n" + D.sr("hint")
    //hint to learn the curiosity ability
    else if player.phase == PrimoridalSoup && player.location == geWhiteRoom then
      if !player.has(D.possibleAbilities("curious")) then
        return outcome + "\n" + D.ge.misc("learn")
      else
        this.commandSuccess = false
        return outcome
    else if player.location.timeline.name == lastExpectedTimeline && !this.commandSuccess then
      return outcome

    /* Main timeline logic (if the player is in the right place at the right moment */
    if (isInRightTimeline || player.phase > Endgame) && player.timelineChosen then
      player.phase match
        case Globalization =>
          //print a random word from the question if the user is trying without success
          if action.commandText.contains("?") then
            player.progression += 1
            if player.progression > 2 && player.progression % 2 == 0 then
              //commandSuccess = true
              return outcome + this.getHint(D.ge.question.split(" "))

          if player.remembers then
            this.commandSuccess = false
            return D.ge("intro2")
          else
            this.commandSuccess = player.progression == -1
            player.progression = 0
            return D.ge("intro") + "\n" + D.ge("intro2")

        case PrimoridalSoup =>

          this.commandSuccess = false

          //hint on psCenter
          if player.location == psCenter then
            player.location.interactable.values.count(_.completed) match
                case 0 =>
                  return D.ps("intro")
                case 1 if player.location.interactable.values.count(e => e.interactions != 0 && !e.completed) == 0 =>
                  return D.ps("star1")
                case 2 if player.location.interactable.values.exists(e => e.interactions > 2 && !e.completed) =>
                  return D.ps("more2")
                case 2 if psPeriphery.interactable.count(_._2.completed) < 2 =>
                  return D.ps("move")
                case 3 =>
                  return D.ps("right")
                case _ =>

          //hint is psPeriphery
          if player.location == psPeriphery then
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
        case HeatDead =>
          //the only success in this timeline is achieved by trying to move
          this.commandSuccess = action.verb == D.action("go") && action.modifiers.nonEmpty

          //spawns random words of the solution-question
          if this.commandSuccess then
            outcome += this.getHint(D.hd.question.split(" ")) + "\n\n"

          //handle the only interactable text
          if hdVacuum.interactable.exists(_._2.completed) then
            return outcome + D.hd("annihilated")
          else
            //this.commandSuccess = false
            return outcome + D.hd("particle")

        case MiddleAges =>
          //spawns random pieces of commands (2 letters) if the direction could be valid
          if action.verb == D.action("go") && D.direction.exists(_._2 == action.modifiers) then
            player.progression = 0
            return this.getHint(D.action("fear") + D.action("sad"), 2)

          //if the player guessed the command in the right area, shows the name of the ability to learn
          else if action.verb == D.action("sad") || action.verb == D.action("fear") then
            val correspondingAbility = D.possibleAbilities(D.action.find(_._2 == action.verb).map(_._1).getOrElse(""))
            if !player.has(correspondingAbility) && player.location.offers(correspondingAbility) then
              return s"*${correspondingAbility}*"

          if player.progression == -1 then
            return D.ma("intro")

        case Prehistory =>
          this.commandSuccess = action.verb == D.action("go") && D.direction.exists(_._2 == action.modifiers)

          //spawns random pieces of commands (3 letters)
          if this.commandSuccess then
            player.progression = 0
            return this.getHint(D.action("see") + "." + D.action("hear") + D.action("touch"), 3)

          if player.progression == -1 then
            return D.ph("intro")

          //listening to the steps of an approaching creature for too long can be dangerous
          if phJungle.interactable("step").completed then
            player.die()

        case OriginOfLife =>
          //if the player got the body, spawn all other creatures
          if olThermal.interactable("thermalRock").completed then
            if player.progression == -1 then
              showOlInteractables()
              player.progression += 1
              return D.ol("body")
          else if action.modifiers == D.interactables("thermalRock").name.toLowerCase then
             return this.getHint(D.action("colonize"), 2)
          else if player.location == olOcean1 then
            return D.ol("intro")

          if action.verb == D.action("eat") && action.modifiers.nonEmpty then
            val elementTier = tierlist.indexOf(player.location.searchInteractables(action.modifiers, action.verb).map(_._2.name.toLowerCase).getOrElse(""))
            //println(focusedElement)
            if elementTier == player.progression then
              player.progression += 1
            else if elementTier >= player.progression then
              return player.die()

          //all eaten
          if player.progression == tierlist.size then
            player.progression += 1
            return D.ol.misc("done")

        case EndOfLife =>
          //trying to move kills the player in this timeline
          if action.verb == D.action("go") then
            return player.die()
          else if action.verb == D.action("examine") then
            player.progression = 0

          if player.progression == -1 then
            this.commandSuccess = false
            return D.el("intro")

        case ScientificRevo =>
          if action.verb == D.action("hear") then
            if Math.abs(player.progression) % 2 == 1 && action.modifiers == srRoom.interactable("scientist").name then
              player.progression += 1
              return D.srConversarion(player.progression % D.srConversarion.size)
                + "\n" +this.getHint(D.sr.question.split(" "))

            else if player.progression % 2 == 0 && action.modifiers == srRoom.interactable("priest").name then
              player.progression += 1
              return D.srConversarion(player.progression % D.srConversarion.size)
                + "\n" + this.getHint(D.sr.question.split(" "))
            return D.sr.misc("other")

          if player.progression == -1 && !player.location.interactable.exists(_._2.completed) then
            this.commandSuccess = false
            return D.sr("intro")

        case Endgame =>
          this.commandSuccess = player.progression == 0
          //keeps showing the second tutorial
          if action.verb == D.ge("firstAnswer") then
            player.progression = -1
            //player.phase += 1
            player.phase += 1
            this.commandSuccess = true
            return D.ge("tutorial2")
          if player.canThink then
            return D.ge("tutorial1")

        case anyPhase if anyPhase > Endgame =>
          if player.invincible && player.location == geSwitch then
            //flag to the thought part not to activate
            player.progression = -1
            //if player.location != geSwitch then player.setLocation(Some(geSwitch))
            return D.ge.misc("finalTutorial")

          //final game message
          if this.isComplete then
            return D.ge.areaDialogues(player.location.name).abilityDesc(D.possibleAbilities(D.action.find(_._2 == action.verb).map(_._1).getOrElse(""))) + "." + outcome

          //keeps showing previous tutorial
          if player.progression == -1 && player.location == geWhiteRoom then
            this.commandSuccess = false
            return D.ge("tutorial2")


        case _ =>
          this.commandSuccess = false
          //D.debug("noPhase")
    this.commandSuccess = outcome.nonEmpty
    outcome

  /**
    * Checks if the input correspond to the question/word that the user has to write
    * @param input  The input for the question
    * @param question The question
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
    * If it's in the endgame, it has something to do everywhere.
    * @return
    */
  def isInRightTimeline = player.location == overflow || player.phase < entryPoints.size && player.location.timeline == entryPoints(player.phase%entryPoints.size).timeline

  /**
    * Handle the dead of the player and the reset options/state changes.
    * The program freeze for some instants to let the player read the output, then automatically reset.
    */
  def reset() =
    if !player.invincible then
      this.areas.foreach(_.interactable.foreach(_._2.interactions = 0))
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

    if this.isComplete then
      return storyReport + "\n" + outcomeReport

    if !this.commandSuccess && outcomeReport.isBlank && !action.verb.isBlank && !player.isDead then// && action.verb != D("deathCommand") then //&& outcomeReport.isBlank && storyReport.isBlank then // needs to hide "command not found" error during successful output
      //check if the verb could still be meaningful
      //if outcomeReport.isEmpty then
        //if !action.verb.isBlank then
        //  outcomeReport = Some(parseStoryCommand(action.commandText))

      //default "unknown" output text
      //if action.verb.isEmpty then
      if action.commandText.endsWith("?") then
        s"$storyReport\n\n${this.changeSubject(action.commandText)} ${D("wrongQuestion")}".trim
      else if action.modifiers.isBlank then
        s"$storyReport\n\n${D("unknownCommand")} ${action.verb}!".trim
        //if !action.verb.isBlank then
        //else
        //outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")
      else
        s"$storyReport\n\n${D("unknownParameter")} ${action.verb} ${action.modifiers}...".trim
      //else //this.turnCount += 1
      //  outcomeReport.getOrElse(s"""${D.debug("noOutput")} "$command"""")
    else
      (storyReport + {if storyReport.nonEmpty then "\n" else ""} + outcomeReport).trim + "\nDEBUG: " + "phase " + player.phase //+ " " +player.timelineChosen + " " + player.location.toString).trim

  /**
    * Executes a substitution of some first person-related words
    * to transform simple questions in a second person form.
    * The pattern "\\b[word]\\b" replaces [word] only if it's
    * between spaces, punctation, or at the end/begin of a string.
    * @param question the "second person" version of the question (e.g "am I..." -> "are you...")
    * @return
    */
  def changeSubject(question: String) =
    question.toLowerCase
      .replaceAll("\\bi\\b", "you")
      .replaceAll("\\bam\\b", "are")
      .replaceAll("\\bme\\b", "you").capitalize

  /**
    * Chooses the logic of the hint and the letters to be given to the user
    * and adds some decoration to it.
    * @param hints The number of letters to slide. If 0, shuffle the entire input.
    * @return Some letters based on randomness and the logic applied.
    */
  def getHint(hints: String, letters: Int): String =
    this.getHint(hints.sliding(letters,letters).toArray)

  /**
    * Chooses the logic of the hint and the letters to be given to the user
    * and adds some decoration to it.
    * @param hints The number of letters to slide. If 0, shuffle the entire input.
    * @return Some letters based on randomness and the logic applied.
    */
  def getHint(hints: Array[String]): String =
    s"\n~${Random.shuffle(hints).head}~"

end Game