package devolution

import devolution.*
import devolution.helpers.*

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. What different areas have in common is that players
  * can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name the name of the area
  * @param timeline timeline in which the area is located
*/
class Area(val name: String, val timeline: Timeline) extends Zone[Area]:

  /** All the text available to an area */
  private val descriptions = D.zones(timeline.name).area(name)

  /** Items found in this area */
  private val interactables = Map[String, Interactable]()
  def interactable = this.interactables
  def setInteractables(interacts: Vector[(String, Interactable)]) =
    this.interactables ++= interacts

  /** The minimum game phase required to move around from here. */
  private var movePhase = Globalization
  def getMovePhase = this.movePhase
  /**
    * Changes the default minimum move phase.
    * Only the entry points have different move phases:
    * they are the only areas where movement is constrained,
    * but once that restriction is overcome, the movement becomes free in that timeline.
    */
  def setMovePhase(phase: Int) = this.movePhase = phase

  private var deadly = false
  def setDeadly() = this.deadly = true
  def isDeadly = this.deadly

  /** A single possible ability that could be unlocked */
  var foundAbility = ""


  /** Returns a multi-line description of the area as a player sees it.
    * Description changes during the game: initially, they are based on the phase.
    * Later, they correspond to a maximum of one string for each ability. */
  def fullDescription(knowledge: Vector[String], canSee: Boolean, phase: Int): String =
    //this.shortDescription(knowledge) +

    var placeDesc = knowledge.map(this.descriptions.abilityDesc(_)).filter(_.nonEmpty).mkString("", ".\n", "...").trim
    if placeDesc.length < 4 then
      placeDesc = descriptions.phaseDesc(phase)
    if this.interactables.nonEmpty then
      placeDesc += "\n\n" + D("aroundYou") + this.interactables.values.filterNot(_.completed).map(e => {
          if canSee then e.name else D("unknownObject")
        }).mkString("... ")
    placeDesc

  /**
    * Return the place visual description, if vision is available.
    * In case of deadly areas, return the only default text they have.
    * @param knowledge
    * @param canSee
    * @param phase
    * @return
    */
  def shortDescription(knowledge: Vector[String], canSee: Boolean, phase: Int) =
    if this.isDeadly && canSee then
      this.descriptions.phaseDesc(0)
    else
      this.descriptions.description(phase, canSee) + "..."

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.timeline + ", " + D.direction.filter((k,m) => this.neighbor(m).map(_.name).nonEmpty).map((k,m) => k + " " + this.neighbor(m).map(_.name)).mkString(" ")// + ", " + this.neighbor("west").name + ", " + this.neighbor("up").name + ", " + this.neighbor("down").name + ", " + this.neighbor("back").name + ", " + this.neighbor("forward").name + " " + this.neighbor("past").map(_.name) + " " + this.neighbor("future").map(_.name)

  /**
    * Search if there exists in this area an interactable not yet completed,
    * with the requested name and the tried action.
    * @param name
    * @param action
    * @return
    */
  def searchInteractables(name: String, action: String) =
    interactables.filter(t => t._2.name.toLowerCase == name.toLowerCase && !t._2.completed)
      .find(_._2.requiredAction == action)

  def offers(abilityName: String) = this.foundAbility == abilityName
end Area
