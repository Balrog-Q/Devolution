package devolution

import devolution.*
import devolution.helpers.*
import scala.util.Random
/** The class `Game` represents the adventure game. It consists of a player and
  * a number of areas and timelines that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  */
class Game:

  /** the name of the game */
  val title = "Devolution"


  //initialize the timelines in the game
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

  //initialize the entry points that represents their timelines
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

  //basic movement between timelines
  overflow    .setNeighbors(Vector(                                           D.specialDirection("future") -> bbVacuum))
  bbVacuum    .setNeighbors(Vector(D.specialDirection("past") -> overflow,    D.specialDirection("future") -> psCenter))
  bbVacuum.setDeadly()
  psCenter    .setNeighbors(Vector(D.specialDirection("past") -> bbVacuum,    D.specialDirection("future") -> olOcean1, D.specialDirection("outside") -> overflow))
  olOcean1    .setNeighbors(Vector(D.specialDirection("past") -> psCenter,    D.specialDirection("future") -> phClearing))
  phClearing  .setNeighbors(Vector(D.specialDirection("past") -> olOcean1,    D.specialDirection("future") -> maPath))
  maPath      .setNeighbors(Vector(D.specialDirection("past") -> phClearing,  D.specialDirection("future") -> srRoom))
  srRoom      .setNeighbors(Vector(D.specialDirection("past") -> maPath,      D.specialDirection("future") -> geWhiteRoom))
  geWhiteRoom .setNeighbors(Vector(D.specialDirection("past") -> srRoom,      D.specialDirection("future") -> elTower))
  elTower     .setNeighbors(Vector(D.specialDirection("past") -> geWhiteRoom, D.specialDirection("future") -> hdVacuum))
  hdVacuum    .setNeighbors(Vector(D.specialDirection("past") -> elTower,     D.specialDirection("future") -> hdVacuum))

  //assign the correct move phase based on the order the zone has been added to the entryPoint vector
  val entryPoints = Vector(geWhiteRoom, psCenter, hdVacuum, maPath, phClearing, olOcean1, elTower, srRoom, geWhiteRoom)
    .zipWithIndex.map(_.swap).toMap
  entryPoints.foreach((k,v) => v.setMovePhase(k))

  /** Areas connections, interactables and death initialization */

  overflow.foundAbility = D.possibleAbilities("invincible")


  private val psPeriphery = Area(D.areas("closeVoid"), primordialSoup)
  private val psVoid = Area(D.areas("vacuum"), primordialSoup)
  psVoid.setDeadly()

  psCenter    .setNeighbors(Vector(D.direction("away") -> psPeriphery))
  psPeriphery .setNeighbors(Vector(D.direction("back") -> psCenter, D.direction("away") -> psVoid))

  //future celestial bodies
  psCenter.setInteractables(Vector(
    "dust1" -> Interactable(D.interactables("dust1"), 1),
    "dust2" -> Interactable(D.interactables("dust2"), 3),
    "dust3" -> Interactable(D.interactables("dust3"), 7)
  ))
  psPeriphery.setInteractables(Vector(
    "dust4" -> Interactable(D.interactables("dust4"), 1),
    "dust5" -> Interactable(D.interactables("dust5"), 3)
  ))


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
  olSurface  .setNeighbors(Vector(D.direction("back") -> olOcean1, D.direction("away") -> olLava))

  private var tierlist = Vector(D.interactables("cell1").lowerName, D.interactables("cell2").lowerName, D.interactables("plankton").lowerName, D.interactables("mollusk").lowerName, D.interactables("fish1").lowerName, D.interactables("fish2").lowerName)

  olThermal.setInteractables(Vector(
    "thermalRock" -> Interactable(D.interactables("thermalRock"))
  ))

  olSurface.setInteractables(Vector(
    "rock" -> Interactable(D.interactables("rock"))
  ))

  def showOlInteractables() =
    olOcean1.setInteractables(Vector(
      "cell2" -> Interactable(D.interactables("cell2"), -1),
      "plankton" -> Interactable(D.interactables("plankton"), -1),
      "fish1" -> Interactable(D.interactables("fish1"), -1)
    ))
    olOcean2.setInteractables(Vector(
      "mollusk" -> Interactable(D.interactables("mollusk"), -1),
      "plankton" -> Interactable(D.interactables("plankton"), -1),
      "fish1" -> Interactable(D.interactables("fish1"), -1),
      "cell1" -> Interactable(D.interactables("cell1"), -1),
    ))
    olOcean3.setInteractables(Vector(
      "fish2" -> Interactable(D.interactables("fish2"), -1),
      "fish1" -> Interactable(D.interactables("fish1"), -1),
      "cell1" -> Interactable(D.interactables("fish1"), -1),
    ))
    olOcean4.setInteractables(Vector(
      "fish2" -> Interactable(D.interactables("fish2"), -1),
      "mollusk" -> Interactable(D.interactables("mollusk"), -1),
      "cell1" -> Interactable(D.interactables("fish1"), -1),
    ))
    olThermal.setInteractables(Vector(
      "cell1" -> Interactable(D.interactables("cell1"), -1)
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
  phHill      .setNeighbors(Vector(D.direction("n") -> phJungle, D.direction("s") -> phClearing, D.direction("e") -> phJungle, D.direction("w") -> phVolcano))
  phVolcano   .setNeighbors(Vector(D.direction("n") -> phJungle, D.direction("s") -> phJungle, D.direction("e") -> phHill, D.direction("w") -> phLava))
  phSwamp     .setNeighbors(Vector(D.direction("n") -> phClearing, D.direction("s") -> phNest, D.direction("w") -> phHill, D.direction("e") -> phJungle))
  phCave1     .setNeighbors(Vector(D.direction("down") -> phCave2, D.direction("s") -> phJungle, D.direction("e") -> phJungle, D.direction("w") -> phJungle))
  phCave2     .setNeighbors(Vector(D.direction("back") -> phCave1,  D.direction("down") -> phNest))

  phJungle.setInteractables(Vector(
    "step" -> Interactable(D.interactables("step"), 4),
    "tree" -> Interactable(D.interactables("tree"), -1)
  ))
  phJungle.foundAbility = D.possibleAbilities("hear")
  phHill.setInteractables(Vector(
    "horn" -> Interactable(D.interactables("horn")),
    "escrement" -> Interactable(D.interactables("escrement"))
  ))
  phHill.foundAbility = D.possibleAbilities("vision")
  phVolcano.setInteractables(Vector(
    "egg" -> Interactable(D.interactables("egg")),
    "rock" -> Interactable(D.interactables("rock"))
  ))
  phVolcano.foundAbility = D.possibleAbilities("proprio")
  phSwamp.setInteractables(Vector(
    "sand" -> Interactable(D.interactables("sand"), -1)
  ))
  phSwamp.foundAbility = D.possibleAbilities("proprio")
  phCave2.setInteractables(Vector(
    "droplet" -> Interactable(D.interactables("droplet"), -1)
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
  maHouse  .setNeighbors(Vector(D.direction("s") -> maField, D.direction("back") -> maVillage))
  maGrave   .setNeighbors(Vector(D.direction("s") -> maVillage, D.direction("w") -> maPath, D.direction("e") -> maStream))
  maStream  .setNeighbors(Vector(D.direction("s") -> maField, D.direction("w") -> maVillage))
  maField   .setNeighbors(Vector(D.direction("n") -> maPath, D.direction("w") -> maCastle1, D.direction("e") -> maStream))

  maCastle1.setInteractables(Vector(
    "guard" -> Interactable(D.interactables("guard"), -1)
  ))
  maCastle1.foundAbility = D.possibleAbilities("fear")
  maHouse.setInteractables(Vector(
    "ill" -> Interactable(D.interactables("ill"), -1)
  ))
  maHouse.foundAbility = D.possibleAbilities("sad")
  maGrave.setInteractables(Vector(
    "corpse" -> Interactable(D.interactables("corpse"), -1)
  ))
  maGrave.foundAbility = D.possibleAbilities("sad")
  maStream.setInteractables(Vector(
    "rat" -> Interactable(D.interactables("rat"), -1)
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")
  maVillage.setInteractables(Vector(
    "strappado" -> Interactable(D.interactables("strappado"), -1)
  ))
  maVillage.foundAbility = D.possibleAbilities("fear")


  srRoom.setInteractables(Vector(
    "scientist" -> Interactable(D.interactables("scientist"), -1),
    "priest" -> Interactable(D.interactables("priest"), -1),
    "mouse" -> Interactable(D.interactables("mouse")),
    "clock" -> Interactable(D.interactables("clock"))
  ))
  srRoom.foundAbility = D.possibleAbilities("thought")


  private val geSwitch = Area(D.areas("switch"), globalization)
  private val geServer = Area(D.areas("server"), globalization)
  private val gePC = Area(D.areas("pc"), globalization)
  private val geFirewall = Area(D.areas("firewall"), globalization)

  geSwitch   .setNeighbors(Vector(D.direction("up") -> gePC, D.direction("down") -> geServer, D.direction("forward") -> geFirewall))
  geServer   .setNeighbors(Vector(D.direction("back") -> geSwitch))
  gePC       .setNeighbors(Vector(D.direction("back") -> geSwitch))
  geFirewall .setNeighbors(Vector(D.direction("back") -> geSwitch))

  geWhiteRoom.foundAbility = D.possibleAbilities("curious")

  gePC.setInteractables(Vector(
    "peripheral1" -> Interactable(D.interactables("peripheral1")),
    "peripheral2" -> Interactable(D.interactables("peripheral2")),
    "peripheral3" -> Interactable(D.interactables("peripheral3"))
  ))
  geServer.setInteractables(Vector(
    "memory1" -> Interactable(D.interactables("memory1")),
    "memory2" -> Interactable(D.interactables("memory2")),
    "memory3" -> Interactable(D.interactables("memory3"))
  ))
  geFirewall.setInteractables(Vector(
    "setting1" -> Interactable(D.interactables("setting1")),
    "setting2" -> Interactable(D.interactables("setting2")),
    "setting3" -> Interactable(D.interactables("setting3"))
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
    "particle" -> Interactable(D.interactables("particle"))
  ))
  hdVacuum.foundAbility = D.possibleAbilities("memory")


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

  /** The character who is the protagonist of the adventure and whom the real-life player controls. */
  val player = Player(startingPoint)

  /** Determines if the adventure is complete, that is, if the player has got and ending. */
  def isComplete = (geServer.interactable.values ++ gePC.interactable.values ++ geFirewall.interactable.values).exists(_.completed)

  /** When false, triggers the "wrong input" logic */
  var commandSuccess = false

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = D("quote")

  /** Returns a message that is to be displayed to the player at the end of the game.
    * It is also used as quit message.
    */
  def goodbyeMessage = D("end")

  /**
    * Manage the story progression and conditions.
    * @param action The possible user command that will apport progression in the story
    */
  def parseStoryCommand(action: Action): String =
    val input = action.commandText
    var outcome = ""
    this.commandSuccess = true

    if player.isDead then
      return ""

    /** The cheat mode is triggered in any case during the final phase of the game.
      It is unlocked if the overflow area is accessed for the first time or efter the endgame. */
    if player.location == overflow then
      if (!player.isInvincible || player.phase > Endgame) then
        if action.verb == D("accept") then
          player.progression += 1

          outcome = player.learn(D.possibleAbilities("invincible"))

          //moves the player to the real world if the phase is right
          if player.phase > Endgame then
            player.progression = -1
            player.setLocation(Some(geSwitch))
          else
            player.setLocation(Some(player.lastEntryPoint))
            return outcome
        else if action.verb == D("refuse") then
          player.setLocation(Some(player.lastEntryPoint))
        else
          return D.bonus.question


    /* Timeline question guess check */

    //the following "if"s will take care of the return output
    if isQuestionRight(input, D.zones(player.location.timeline.name).question)
      && this.isInRightTimeline then
      player.setLocation(Some(player.lastEntryPoint))//this.entryPoints.getOrElse(player.phase, player.lastEntryPoint)))
      player.phase += 1
      player.progression = -1
    else if player.phase > Endgame then
      if player.location != overflow then
        if isQuestionRight(input, D("finalQuestion")) then //final question found
          player.setLocation(Some(overflow))
          return D.bonus.question
        if player.progression > -1 && player.canThink then //keep showing contemplate message
          outcome += "\n" + D.zones(player.location.timeline.name).thought + "\n"

        //special timeline word check
        if isQuestionRight(input, D.zones(player.location.timeline.name).word) then
          player.phase += 1
          return outcome + "\n" + D.zones(player.location.timeline.name).realization


    /* Answer showing logic */

    //before-endgame question guess check
    if player.location.timeline.name == lastExpectedTimeline && (player.phase <= Endgame && !player.canThink || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse(""))) then
      //success if the guess has just been made or if there are no previous tries (that is, the timeline was already completed)
      this.commandSuccess = player.progression == -1 || isQuestionRight(input, D.zones.get(lastExpectedTimeline).map(_.question).getOrElse(""))
      //remove player from actual area to hide useless decriptions, but only if it's not trying to enter the timeline
      player.timelineChosen = player.progression == -1 && (action.verb == D.action("explore") || action.verb == D.action("go"))
      player.progression += 1
      outcome += "\n" + this.changeSubject(D.zones(lastExpectedTimeline).question.capitalize) + " " + D.zones(lastExpectedTimeline).answer //player.location.timeline.name).answer


    /* Tmeline specific final conditions */

    //hints to learn critical thinking
    if player.phase == Endgame && player.location == srRoom && !player.canThink then //missing critical think ability
      //if the player guess the word without this ability, he's not left outside the timeline
      this.player.timelineChosen = true
      return outcome + "\n\n" + D.sr("hint")
    //hints to learn the curiosity ability
    else if player.phase == PrimoridalSoup && player.location == geWhiteRoom then
      if !player.has(D.possibleAbilities("curious")) then
        return outcome + "\n" + D.ge.misc("learn")
      else
        this.commandSuccess = false
        return outcome
    //random commands in the previous timeline
    else if player.location.timeline.name == lastExpectedTimeline && !this.commandSuccess then
      return outcome

    /* Main timeline logic.
      Triggered if the player is in the right place at the right moment,
       or after the endgame
     */
    if (isInRightTimeline || player.phase > Endgame) && player.timelineChosen then
      player.phase match
        case Globalization =>
          //print a random word from the question if the user is trying without success
          if action.commandText.contains("?") then
            player.progression += 1
            if player.progression > 2 && player.progression % 2 == 0 then
              return outcome + this.getHint(D.ge.question.split(" "))

          //doesn't show the full message if the player has progressed enough in the game
          if player.remembers then
            this.commandSuccess = false
            return D.ge("intro2")
          else
            this.commandSuccess = player.progression == -1
            player.progression = 0
            return D.ge("intro") + "\n" + D.ge("intro2")

        case PrimoridalSoup =>
          this.commandSuccess = false
          //hint in psCenter
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
          //hints is psPeriphery
          if player.location == psPeriphery then
            player.location.interactable.values.count(_.completed) match
              case 1 =>
                return D.ps("more1")
              case 2 =>
                return D.ps("big")
              case _ =>

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

          //listening to the steps of an approaching creature for too long can be dangerous!
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

            if elementTier == player.progression then
              player.progression += 1
            else if elementTier >= player.progression then
              return player.die()

          //all creatures in the list eaten
          if player.progression == tierlist.size then
            player.progression += 1
            return D.ol.misc("done")

        case EndOfLife =>
          //trying to move anywhere is deadly in this timeline
          if action.verb == D.action("go") then
            return player.die()
          else if action.verb == D.action("examine") then
            player.progression = 0

          if player.progression == -1 then
            this.commandSuccess = false
            return D.el("intro")

        case ScientificRevo =>
          this.commandSuccess = action.verb == D.action("hear")

          //dialogue logic
          if this.commandSuccess then
            if Math.abs(player.progression) % 2 == 1 && action.modifiers == srRoom.interactable("scientist").name
                      || player.progression % 2 == 0 && action.modifiers == srRoom.interactable("priest").name then
              player.progression += 1
              return D.srConversarion(player.progression % D.srConversarion.size)
                + "\n" +this.getHint(D.sr.question.split(" "))
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
          this.commandSuccess = player.progression != 0 || this.isComplete

          //final game message
          if this.isComplete then
            return D.ge.areaDialogues(player.location.name).abilityDesc(D.possibleAbilities(D.action.find(_._2 == action.verb).map(_._1).getOrElse(""))) + "." //+ outcome

          //keeps printing the final hint in each of the final areas
          if player.isInvincible && (player.location == geSwitch || player.location == geServer || player.location == geFirewall || player.location == gePC) then
            //flag to the thought part not to activate
            player.progression = 0
            return D.ge.misc("finalTutorial")

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
    if !player.isInvincible then
      this.areas.foreach(_.interactable.foreach(_._2.interactions = 0))
      this.player.dead = false

  /** Plays a turn by executing the given in-game command
    * and it checks if the command is relevant to the story progression.
    * Additionally, it prettifies the output or adds a "wrong input" part */
  def playTurn(command: String): String =
    val action = Action(command)
    var outcomeReport = action.execute(this.player).getOrElse("")
    var storyReport = parseStoryCommand(action)

    if this.isComplete then
      return storyReport + "\n" + outcomeReport

    //handle if and how the "wrong input" has to be printed
    if !this.commandSuccess && outcomeReport.isBlank && !action.verb.isBlank && !player.isDead then
      //unknown question
      if action.commandText.endsWith("?") then
        s"$storyReport\n\n${this.changeSubject(action.commandText)} ${D("wrongQuestion")}".trim
      //unknown action
      else if action.modifiers.isBlank then
        s"$storyReport\n\n${D("unknownCommand")} ${action.verb}!".trim
      //unknown parameters
      else
        s"$storyReport\n\n${D("unknownParameter")} ${action.verb} ${action.modifiers}...".trim
    else //default output
      (storyReport + {if storyReport.nonEmpty then "\n" else ""} + outcomeReport).trim

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
    * Selects a hint of the correct length based on the input string
    * @param hint
    * @return A string of the correct length
    */
  def getHint(hint: String, letters: Int): String =
    this.getHint(hint.sliding(letters,letters).toArray)

  /**
    * Select a random hint and adds some decoration to it.
    * @param hints
    * @return Some letters based on randomness and the logic applied.
    */
  def getHint(hints: Array[String]): String =
    s"\n~${Random.shuffle(hints).head}~"

  /**
    * Handle the description of the timeline based on current player abilities.
    * @return
    */
  def timelineDescription =
    D("timelineDesc") + " " + {
      if player.feelsSurroundings then
        this.player.timeline.description
      else
        D("unknownTimeline")
    }

  /**
    * Handle the description of the area based on current player abilities.
    * @return
    */
  def locationFullDescription =
    if player.isDead then
      //show a death message only if the user can see where he went
      D("dead") + { if player.canSee then player.location.name else D("unknownTimeline") }
    else
      this.player.location.fullDescription(player.abilities, player.canSee, player.phase)

end Game