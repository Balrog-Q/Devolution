package devolution

/** The class `Ability` represents abilities in a text adventure game. Each ability has a name
  * and a longer description.
  *
  * @param name         the ability's name
  * @param description  the ability's description
  */
class Ability(val name: String, val description: String):

  /** Returns a short textual representation of the ability (its name, that is). */
  override def toString = this.name

end Ability

