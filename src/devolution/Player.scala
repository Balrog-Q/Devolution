package devolution

import scala.collection.mutable.Map
import devolution.helpers.*

import scala.collection.mutable.Buffer
import scala.collection.mutable.LinkedHashMap

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object's state is mutable: the player's location and abilities can change,
  * for instance.
  *
  * @param startingArea  the player's initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  //private var possibleAbilities = D.possibleAbilities.values.toVector
  /**
    * Container of all the abilities that the player has.
    * Starts with fixed length to put abilities in the right place (as defined in the possibleAbilities dialogue vector).
    * This allows to show their descriptions in a nice order.
    */
  var abilitiesStatus = D.possibleAbilities.map((k,v) => v -> false)
  var dead = false
  var lastEntryPoint = startingArea
  var invincible = false
  var timelineChosen = true

  /**
    * Multifunctional variable used to keep track of various timeline progress status
    * Must be reset at every change of timeline
    */
  var progression = -1

  /* The phase the player is currently in. It allows to select the right dialogues. */
  var phase = Globalization
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  /** Returns the player's current location. */
  def location = this.currentLocation
  def timeline = this.currentLocation.timeline
  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    if this.timelineChosen || D.specialDirection.values.toVector.contains(direction) then
      val destination = this.location.neighbor(direction)
      if this.location.getMovePhase <= this.phase || D.specialDirection.values.toVector.contains(direction) then
        setLocation(destination)
        if this.location.isDeadly then
          this.die()
        else
          D("moved") + direction
      else
        ""
    else
      D("reminder")

  def abilities = abilitiesStatus.filter(_._2).keys.toVector
  def setLocation(destination: Option[Area]) = this.currentLocation = destination.getOrElse(this.currentLocation)
    //if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  //def rest() = "You rest for a while. Better get a move on, though."
  /** Signals that the player wants to quit the game. Returns a description of what happened
    * within the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name + " with last EP " + this.lastEntryPoint
  /** Tries to pick up an ability of the given name. This is successful if such an ability is
    * located in the player's current location. If so, the ability is added to the player's
    * knowledge and removed from the location. Returns a description of the result:
    * "You pick up the ABILITY." or "There is no ABILITY here to pick up." */
  /*def get(abilityName: String) =
    val received = this.location.removeAbility(abilityName)
    for newAbility <- received do
      this.abilities.put(newAbility.name, newAbility)
    if received.isDefined then
      "You pick up the " + abilityName + "."
    else
      "There is no " + abilityName + " here to pick up."*/

  /**
    * Add an ability to the knowledge of the user.
    * @param abilityName
    * @return
    */
  def learn(abilityName: String) =
    if this.has(abilityName) then //returned independently of if the ability it's present in this area
      D.knowledge("alreadyLearned")
    else if this.currentLocation.offers(abilityName) then
        //abilities are placed in specific spots, to show their description in a nice order
        this.abilitiesStatus.update(abilityName, true)
        s"\n${D.knowledge("new")} $abilityName.\n${D.knowledge.desc(abilityName)}."
    else
      ""

  /** Determines whether the player is carrying an ability of the given name. */
  def has(abilityName: String) = this.abilities.contains(abilityName)
  /** Tries to drop an ability of the given name. This is successful if such an ability is
    * currently in the player's possession. If so, the ability is removed from the
    * player's knowledge and placed in the area. Returns a description of the result
    * of the attempt: "You drop the ABILITY." or "You don't have that!". */
  /*def drop(abilityName: String) =
    val removed = this.abilities.remove(abilityName)
    for oldAbility <- removed do
      this.location.addAbility(oldAbility)
    if removed.isDefined then "You drop the " + abilityName + "." else "You don't have that!"
  */
  def remembers = this.has(D.possibleAbilities("memory"))
  def canSee = this.has(D.possibleAbilities("vision"))
  def canThink = this.has(D.possibleAbilities("thought"))
  def feelsSurroundings = this.has(D.possibleAbilities("proprio"))

  /** Causes the player to examine the ability of the given name. This is successful if such
    * an ability is currently in the player's possession. Returns a description of the result,
    * which, if the attempt is successful, includes a description of the ability. The description
    * has the form: "You look closely at the ABILITY.\nDESCRIPTION" or "If you want
    * to examine something, you need to pick it up first." */
  def examine(direction: String) =
    //def lookText(ability: Ability) = "You look closely at the " + ability.name + ".\n" + ability.description
    //val failText = "If you want to examine something, you need to pick it up first."
    //this.abilities.get(abilityName).map(lookText).getOrElse(D.ability.misc("missingAbility"))
    //this.abilities.get(abilityName).map(D.ability("desc")).getOrElse(D.ability.misc("missingAbility"))
    if this.feelsSurroundings && this.canSee && !D.specialDirection.contains(direction) then
      s"${D("survey")} $direction...\n${this.location.neighbor(direction).map(_.shortDescription(this.abilities, this.canSee, this.phase)).getOrElse(D("noArea"))}"
    else
      D.knowledge("notFeeling")

  /**
    * Tries to execute an action on some object.
    * @param action
    * @param name The name of the interactable.
    *             If the player can't see, this name won't matter.
    * @return
    */
  def interact(action: String, name: String): String =
    //this.location.interactables.find(t => t._1.contains(name) && !t._2.completed).map(_._2.execute(action)).getOrElse(D("wrongAction") + name)

    //set the first element in the interactable list as default if the player can't see its name
    var effectiveName = name
    if !this.canSee then
      effectiveName = this.location.interactable.values.toVector.headOption.map(_.name).getOrElse("")

    //first filters objects with right name, then with right action required
    this.location.searchInteractables(effectiveName, action).map(_._2.execute(action)).filter(_.isDefined).map(_.getOrElse(D("wrongAction") + name)).getOrElse("").trim
  /** Causes the player to list what they are carrying. Returns a listing of the player's
    * abilities or a statement indicating that the player is carrying nothing. The return
    * value has the form "You are carrying:\nABILITIES ON SEPARATE LINES" or "You are empty-handed."
    * The abilities are listed in an arbitrary order. */
  def knowledge =
    if this.abilities.isEmpty then
      D.knowledge("noAbility")
    else
      this.abilities.map(name => s"$name: ${D.knowledge.desc(name)}".capitalize).mkString(s"\n${D.knowledge("knowledgeIntro")}\n- ", ".\n- ", ".")


  def isInCompletedTimeline = this.phase > this.lastEntryPoint.getMovePhase // entryPoints.get(player.phase-1).map(_.timeline.name).getOrElse("")


  def devolve() =
    timeTravelTo("past")

  def evolve() =
    timeTravelTo("future")

  /**
    * Handle every variable that must be updated at timeline change.
    * Available at certain conditions.
    * @param direction The travel direction (only past or future here)
    * @return
    */
  def timeTravelTo(direction: String) =
    //only after the first successful question
    //and if he learned the ability to do it
    if this.phase > 0 && this.has(D.possibleAbilities("curious")) then
      this.timelineChosen = false
      this.progression = -1
      this.currentLocation = this.lastEntryPoint
      this.go(direction)
      this.lastEntryPoint = this.currentLocation
      D.misc(direction)
    else
      D.knowledge("notFeeling")

  def exploreTimeline() =
    if this.location.getMovePhase <= this.phase then
      if !this.timelineChosen then
        this.timelineChosen = true
        this.progression = -1
        D("welcome") + {
          if this.canThink then
            this.location.timeline.name.toLowerCase
          else if this.canSee then
            this.location.name.toLowerCase
          else
            D("unknownTimeline")
        }
      else
        ""
        //D.zones(this.location.timeline.name)("intro") //shows the intro when not needed; after re-entering the same just completed timeline
    else
      this.die()

  def fear(interactable: String) =
    this.feel("fear", interactable)

  def grieve(interactable: String) =
    this.feel("sad", interactable)

  /**
    * Check the conditions to interact with an object
    * @param ability
    * @param interactable
    * @return
    */
  def feel(ability: String, interactable: String) =
    if this.timelineChosen && this.has(D.possibleAbilities(ability)) || this.phase == MiddleAges then
      this.interact(D.action(ability), interactable)
    else
      D.knowledge("notFeeling")

  def die() =
    if !this.invincible then
      if !this.remembers then
        this.abilitiesStatus.foreach((k,v) => this.abilitiesStatus.update(k, false))
      this.dead = true
      this.phase = Globalization
      this.timelineChosen = true
      this.progression = -1
      this.currentLocation = startingArea
      this.lastEntryPoint = startingArea
      D("denied")
    else
      ""

  /**
    * This function only flags the main program to show and keep showing the thought.
    * This allows to keep the message visible despite the user's multiple tries.
    * @return
    */
  def contemplate() =
    if this.canThink && this.phase > Endgame then
      this.progression += 1
    ""

  def isDead = this.dead

  // DEBUG FUNCTION TO QUICKLY MOVE AROUND PHASES
  def tp(phase: String): String =
    this.phase = phase.toIntOption.getOrElse(Globalization)
    this.abilitiesStatus.update(D.possibleAbilities("curious").toLowerCase, true)
    this.abilitiesStatus.update(D.possibleAbilities("memory").toLowerCase, true)
    if phase.toIntOption.getOrElse(0) > 3 then
      this.abilitiesStatus.update(D.possibleAbilities("fear").toLowerCase, true)
      this.abilitiesStatus.update(D.possibleAbilities("sad").toLowerCase, true)
    if phase.toIntOption.getOrElse(0) > 4 then
      this.abilitiesStatus.update(D.possibleAbilities("vision").toLowerCase, true)
      this.abilitiesStatus.update(D.possibleAbilities("hear").toLowerCase, true)
      this.abilitiesStatus.update(D.possibleAbilities("proprio").toLowerCase, true)
    if phase.toIntOption.getOrElse(0) > 7 then
      this.abilitiesStatus.update(D.possibleAbilities("thought").toLowerCase, true)
    ""
    //this.currentLocation =



end Player
