package devolution

import scala.collection.mutable.Map
import devolution.helpers.*

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
  var abilities = Vector[String]()     // container of all the abilities that the player has
  var dead = false
  var lastEntryPoint = startingArea
  var invincible = false
  var timelineChosen = true
  //used to keep track of the right order of eating in the Origin of Life timeline
  var currentTier = -1

  /* The phase the player is currently in. It allows to select the right dialogues. */
  var phase = 1
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  /** Returns the player's current location. */
  def location = this.currentLocation
  def timeline = this.currentLocation.timeline
  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    if this.timelineChosen || direction == D.movements("past") || direction == D.movements("future") then
      val destination = this.location.neighbor(direction)
      if this.location.getMovePhase <= this.phase || direction == D.movements("past") || direction == D.movements("future") then
        setLocation(destination)
        D.misc("moved") + direction
        //if this.location.isDeadly then
        //  this.die()
      else
        ""
    else
      D.misc("reminder")

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
  def learn(abilityName: String) =
    if this.currentLocation.offers(abilityName) then
      this.abilities = this.abilities :+ abilityName
      D.knowledge("new") + abilityName + ".\n" + D.knowledge.desc(abilityName)
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
  def remember = this.has(D.possibleAbilities("memory"))
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
    if abilities.contains(D.possibleAbilities("proprio")) then
      s"\n\nYou survey $direction: " + this.location.neighbor(direction).map(_.shortDescription(this.abilities, this.phase)).getOrElse(D.misc("noArea"))
    else
      D.knowledge("missingAbility")

  def interact(action: String, name: String): String =
    //this.location.interactables.find(t => t._1.contains(name) && !t._2.completed).map(_._2.execute(action)).getOrElse(D.misc("wrongAction") + name)
    //first filters objects with right name, then with right action required
    this.location.searchInteractables(name, action).map(_._2.execute(action)).filter(_.isDefined).map(_.getOrElse(D.misc("wrongAction") + name)).getOrElse("")
  /** Causes the player to list what they are carrying. Returns a listing of the player's
    * abilities or a statement indicating that the player is carrying nothing. The return
    * value has the form "You are carrying:\nABILITIES ON SEPARATE LINES" or "You are empty-handed."
    * The abilities are listed in an arbitrary order. */
  def knowledge =
    if this.abilities.isEmpty then
      D.knowledge("noAbility")
    else
      D.knowledge("knowledgeIntro") + "\n" + this.abilities.map(name => s"$name: ${D.knowledge(name)}").mkString("\n")

  def devolve() =
    timeTravelTo("past")

  def evolve() =
    timeTravelTo("future")

  def timeTravelTo(direction: String) =
    if this.phase > 0 then
      timelineChosen = false
      this.currentLocation = this.lastEntryPoint
      this.go(direction)
      this.lastEntryPoint = this.currentLocation
      D.actions(direction)
    else
      ""

  def enterTimeline() =
    if this.location.getMovePhase <= this.phase then
      this.timelineChosen = true
      D.zones(this.location.timeline.name)("intro")
    else
      this.die()

  def die() =
    if !this.invincible then
      this.dead = true
      this.phase = 0
      this.currentLocation = startingArea
      this.lastEntryPoint = startingArea
      D.misc("denied")
    else
      ""

  def contemplate() =
    if this.has(D.possibleAbilities("thought")) && this.phase == Endgame then
      D.zones(this.location.timeline.name).tought
    else
      ""

  def isDead = this.dead

  // DEBUG FUNCTION TO QUICKLY MOVE AROUND PHASES
  def tp(phase: String): String =
    this.phase = phase.toIntOption.getOrElse(0)
    ""
    //this.currentLocation =



end Player
