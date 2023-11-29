package devolution

import devolution.*
import devolution.helpers.*

import scala.collection.mutable.Map

/** The class `Timeline` represents eras in a text adventure game world. A game world
  * consists of eras. In general, an "era" can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An era also has a name and a description.
  * @param name         the name of the era
*/

class Timeline(val name: String) extends Zone[Timeline]:
  private val areas = Vector[Area]()

  def setAreas(exists: Vector[Area]) =
    this.areas ++ exists
  
  def getMap = this.areas

  def description = s"You are in ${this.name}."

  override def toString = s"${this.name}."
end Timeline