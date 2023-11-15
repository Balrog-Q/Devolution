package devolution

import devolution.*
import devolution.helpers.D

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
*/
class Area(var name: String):

  val description = D.zones(name).phaseDesc(0)
  private val neighbors = Map[String, Area]()
  private val abilities = Map[String, Ability]()

  val foundWord = ""
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
  def fullDescription =
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    val abilityList = "\nYou see here: " + this.abilities.keys.mkString(" ")
    if this.abilities.nonEmpty then
      this.description + abilityList + exitList
    else
      this.description + exitList
  def addAbility(ability: Ability) = this.abilities(ability.name) = ability
  def contains(abilityName: String) = this.abilities.contains(abilityName)
  def removeAbility(abilityName: String) = this.abilities.remove(abilityName)
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)
end Area