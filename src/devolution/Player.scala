package devolution

import scala.collection.mutable.Map
import devolution.helpers.*

import scala.collection.mutable.Buffer
import scala.collection.mutable.LinkedHashMap

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  * A player object's state is mutable: the player's location and abilities can change.
  * @param startingArea the player's initial location
  */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag

  /**
    * Container of all the abilities that the player has.
    * Starts with all the possible abilities in the right place as defined in the possibleAbilities dialogue vector.
    * This allows to show their descriptions in a nice order.
    */
  var abilitiesStatus = D.possibleAbilities.map((k,v) => v -> false)
  //a vector representation of only the current learned abilities
  def abilities = abilitiesStatus.filter(_._2).keys.toVector

  var dead = false
  def isDead = this.dead

  var lastEntryPoint = startingArea
  var timelineChosen = true

  /**
    * Multifunctional variable used to keep track of various timeline progress status
    * Gets reset at every change of timeline.
    */
  var progression = -1

  /* The phase the player is currently in. It allows to select the right dialogues and story logic. */
  var phase = Globalization

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player's current location. */
  def location = this.currentLocation
  /** Safely changes the location */
  def setLocation(destination: Option[Area]) = this.currentLocation = destination.getOrElse(this.currentLocation)

  /** Returns the player's current timeline. */
  def timeline = this.currentLocation.timeline

  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name.
    */
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
    //reminds the need to access the timeline before being able to move in it
    else
      D("reminder")


  /** Signals that the player wants to quit the game. */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name + " with last EP " + this.lastEntryPoint

  /**
    * Add an ability to the knowledge of the user.
    * @param abilityName  the name of ability
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
      D.knowledge("noAbilityHere")

  /** Determines whether the player is carrying an ability of the given name. */
  def has(abilityName: String) = this.abilities.contains(abilityName)

  //helpers to easily get information about some learned abilities
  def remembers = this.has(D.possibleAbilities("memory"))
  def canSee = this.has(D.possibleAbilities("vision"))
  def canThink = this.has(D.possibleAbilities("thought"))
  def feelsSurroundings = this.has(D.possibleAbilities("proprio"))
  def isInvincible = this.has(D.possibleAbilities("invincible"))

  /**
    * Allows to survey the selected direction.
    * This returns the visual description of it, under certain conditions.
    * @param direction
    * @return
    */
  def examine(direction: String) =
    //only if proprioception and vision are acquired, and the direction is unrelated to time
    if this.feelsSurroundings && this.canSee && !D.specialDirection.contains(direction) then
      s"${D("survey")} $direction...\n${this.location.neighbor(direction).map(_.shortDescription(this.abilities, this.canSee, this.phase)).getOrElse(D("noArea"))}"
    else
      D.knowledge("notFeeling")

  /**
    * Tries to execute an action on some object.
    * @param action The name of action
    * @param name The name of the interactable. If the player can't see, this name won't matter.
    * @return
    */
  def interact(action: String, name: String): String =
    //set the first element in the interactable list as default if the player can't see its name
    var effectiveName = name
    if !this.canSee then
      //automatically finds the name of the object that correspond only to the tried action, if it exists
      effectiveName = this.location.interactable.values.toVector.headOption.map(_.name).getOrElse("")

    //first filters objects with right name, then with right action required
    this.location.searchInteractables(effectiveName, action).map(_._2.execute(action)).filter(_.isDefined).map(_.getOrElse(D("wrongAction") + name)).getOrElse("").trim

  /**
    * Lists the acquired abilities
    */
  def knowledge =
    if this.abilities.isEmpty then
      D.knowledge("noAbility")
    else
      this.abilities.map(name => s"$name: ${D.knowledge.desc(name)}".capitalize).mkString(s"\n${D.knowledge("knowledgeIntro")}\n- ", ".\n- ", ".")

  /**
    * Completed timelines are the ones in previous phases.
    * @return
    */
  def isInCompletedTimeline = this.phase > this.lastEntryPoint.getMovePhase // entryPoints.get(player.phase-1).map(_.timeline.name).getOrElse("")


  /**
    * Triggers a time travel toward the past
    * @return
    */
  def devolve() =
    timeTravelTo(D.specialDirection("past"))

  /**
    * Trigger a time travel toward the future
    * @return
    */
  def evolve() =
    timeTravelTo(D.specialDirection("future"))

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
      val outcome = this.go(direction)
      this.lastEntryPoint = this.currentLocation
      s"${D.misc(direction)}\n$outcome"
    else
      D.knowledge("notFeeling")

  /**
    * Allows the user to enter a certain timeline.
    * This is required to explore it.
    * @return
    */
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
    else //if the timeline belongs to a later part of the game, the user dies
      this.die()

  /**
    * Triggers the feel of fear
    * @param interactable
    * @return
    */
  def fear(interactable: String) =
    this.feel("fear", interactable)
  /**
    * Triggers the feel of sadness
    * @param interactable
    * @return
    */
  def grieve(interactable: String) =
    this.feel("sad", interactable)

  /**
    * Check the conditions to interact with an object
    * @param ability  The ability that player intend to use for the object
    * @param interactable The interactable of the object
    * @return
    */
  def feel(ability: String, interactable: String) =
    if this.timelineChosen && this.has(D.possibleAbilities(ability)) || this.phase == MiddleAges then
      this.interact(D.action(ability), interactable)
    else
      D.knowledge("notFeeling")

  /**
    * Handle the death of the user, by respawining it at the beginning.
    * It can be a common event in this game.
    * @return
    */
  def die() =
    if !this.isInvincible then
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
      this.currentLocation = this.lastEntryPoint
      D("saved")

  /**
    * This function only flags the main program to show and keep showing the thought.
    * This allows to keep the message visible despite the user's multiple tries.
    * @return
    */
  def contemplate() =
    if this.canThink && this.phase > Endgame then
      this.progression += 1
    ""


  //DEBUG FUNCTION TO QUICKLY MOVE AROUND PHASES
  //uncomment to speed around different part of the game, test endings, etc.
  /*def tp(phase: String): String =
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
    ""*/



end Player
