package devolution

import devolution.*
import devolution.helpers.*

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
*/
class Area(val name: String, val timeline: String):

  val desc = D.zones(timeline).area(name)
  val neighbors = Map[String, Area]()
  var deadly = false
  var interactables = Map[String, Element]()

  /**
    * The minimum game phase required to move around from here.
    */
  var movePhase = 0

  val foundWord = D.zones(timeline).area(name)
  val finalQuestion = ""

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and abilities. If there are no
    * abilities present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more abilities present, the return
    * value has the form "DESCRIPTION\nYou see here: ABILITIES SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The abilities and directions are listed in an arbitrary order. */
  def fullDescription(knowledge: Vector[String], phase: Int): String =
    //this.shortDescription(knowledge) +

    //show a death message only if the user can see where he went
    if this.isDeadly then
      if knowledge.contains(D.knowledge("vision")) then
        return D.misc("dead") + this.name
      else
        return ""

    var placeDesc = knowledge.map(desc.abilityDesc(_)).mkString("\n")
    if placeDesc.trim.isEmpty then
      placeDesc = desc.phaseDesc(phase)
    //if abilities.contains(D.abilities("proprioception") = "\n\nExits available: " + this.neighbors.keys.mkString(", ")
    //val abilityList = "\nYou see here: " + this.abilities.keys.mkString(" ")
    /*if this.knowledge.nonEmpty then
      this.description + abilityList + exitList
    else
      this.description + exitList*/
    placeDesc

  def shortDescription(knowledge: Vector[String]) =
    if knowledge.contains(D.knowledge("vision")) then
      this.desc.abilityDesc(D.knowledge("vision"))
    else
      D.misc("undefinedArea")

  //def learn(ability: Ability) = this.abilities(ability.name) = ability
  //def contains(abilityName: String) = this.abilities.contains(abilityName)
  //def removeAbility(abilityName: String) = this.abilities.remove(abilityName)
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.timeline + ", " + D.movements.map((k,m) => k + " " + this.neighbor(m).map(_.name)).mkString(" ")// + ", " + this.neighbor("west").name + ", " + this.neighbor("up").name + ", " + this.neighbor("down").name + ", " + this.neighbor("back").name + ", " + this.neighbor("forward").name + " " + this.neighbor("past").map(_.name) + " " + this.neighbor("future").map(_.name)

  /**
    * Changes the default minimum move phase.
    * Only the entry points have different move phases:
    * they are the only areas where movement is constrained,
    * but once that restriction is overcome, the movement becomes free in that timeline.
    */
  def setMovePhase(phase: Int) = this.movePhase = phase

  def isDeadly = this.deadly

end Area

/**
  * An interactable element found in an area.
  * Requires a specific action and return some text if acted on the right way.
  * @param dialogues
  * @param neededInteractions
  */
class Element(private val dialogues: ElementDialogues, private val neededInteractions: Int = 1): // private val requiredAction: String, private val output: String):

  var interactions = 0

  def execute(action: String) =
    if action == this.dialogues.action && !this.completed then
      interactions += 1
      this.dialogues.output
    else
      ""

  def completed = interactions == neededInteractions