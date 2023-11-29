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
class Area(val name: String, val timeline: Timeline) extends Zone[Area]:

  private val descriptions = D.zones(timeline.name).area(name)
  private val interactables = Map[String, Element]()
  private val finalQuestion = ""

  private var deadly = false
  var foundAbility = ""

  /** * The minimum game phase required to move around from here. */
  private var movePhase = Globalization

  def interactable = this.interactables

  def setInteractables(interacts: Vector[(String, Element)]) =
    this.interactables ++= interacts


  def getMovePhase = this.movePhase

  /**
    * Changes the default minimum move phase.
    * Only the entry points have different move phases:
    * they are the only areas where movement is constrained,
    * but once that restriction is overcome, the movement becomes free in that timeline.
    */
  def setMovePhase(phase: Int) = this.movePhase = phase

  def setDeadly() = this.deadly = true

  def isDeadly = this.deadly

  def description = this.descriptions

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and abilities. If there are no
    * abilities present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more abilities present, the return
    * value has the form "DESCRIPTION\nYou see here: ABILITIES SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The abilities and directions are listed in an arbitrary order. */
  def fullDescription(knowledge: Vector[String], canSee: Boolean, phase: Int): String =
    //this.shortDescription(knowledge) +

    //show a death message only if the user can see where he went
    if this.isDeadly then
        return D("dead") + { if canSee then this.name else D("unknownTimeline") }

    var placeDesc = knowledge.map(this.descriptions.abilityDesc(_)).filter(_.nonEmpty).mkString("", ".\n", "...").trim
    if placeDesc.length < 4 then
      placeDesc = descriptions.phaseDesc(phase)
    if this.interactables.nonEmpty then
      placeDesc += "\n\n" + D("aroundYou")
        + this.interactables.values.filterNot(_.completed).map(e => {
          if canSee then e.name else D("unknownObject")
        }).mkString("... ")
    else
      D("noObjects")
    //if abilities.contains(D.abilities("proprioception") = "\n\nExits available: " + this.neighbors.keys.mkString(", ")
    //val abilityList = "\nYou see here: " + this.abilities.keys.mkString(" ")
    /*if this.knowledge.nonEmpty then
      this.description + abilityList + exitList
    else
      this.description + exitList*/
    placeDesc

  /**
    * Return the place visual description, if vision is available.
    * In case of deadly areas, return the default text.
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
    //if knowledge.contains(D.knowledge("vision")) || phase < VisionUnlock then
    //else
    //  D("undefinedArea")

  //def learn(ability: Ability) = this.abilities(ability.name) = ability
  //def contains(abilityName: String) = this.abilities.contains(abilityName)
  //def removeAbility(abilityName: String) = this.abilities.remove(abilityName)
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.timeline + ", " + D.direction.filter((k,m) => this.neighbor(m).map(_.name).nonEmpty).map((k,m) => k + " " + this.neighbor(m).map(_.name)).mkString(" ")// + ", " + this.neighbor("west").name + ", " + this.neighbor("up").name + ", " + this.neighbor("down").name + ", " + this.neighbor("back").name + ", " + this.neighbor("forward").name + " " + this.neighbor("past").map(_.name) + " " + this.neighbor("future").map(_.name)

  def searchInteractables(name: String, action: String) =
    interactables.filter(t => t._2.name.toLowerCase == name.toLowerCase && !t._2.completed)
      .find(_._2.requiredAction == action)

  def offers(abilityName: String) = this.foundAbility == abilityName
end Area

/**
  * An interactable element found in an area.
  * Requires a specific action and return some text if acted on the right way.
  * @param dialogues
  * @param neededInteractions When has value -1, the object never reaches completeness
  */
class Element(private val dialogues: ElementDialogues, private val neededInteractions: Int = 1): // private val requiredAction: String, private val output: String):

  var interactions = 0

  def name = this.dialogues.lowerName

  def requiredAction = this.dialogues.action

  private var description = ""

  def setSpecialDescription(desc: String) = this.description = desc

  def execute(action: String): Option[String] =
    if action == requiredAction && !this.completed && this.dialogues.output.nonEmpty then
      interactions += 1
      Some("\n" + this.description + "\n" + {
        if this.dialogues.output.split(" ").length == 1 then
          s"~${this.dialogues.output}~"
        else
          this.dialogues.output
      })
    else
      None

  def completed = interactions == neededInteractions && neededInteractions > -1