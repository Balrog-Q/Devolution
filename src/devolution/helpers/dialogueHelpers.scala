package devolution.helpers


class KnowledgeDialogues(val misc: Map[String, String], val abilitiesDesc: Vector[AbilityDialogues]):
  def desc(name: String) = this.abilitiesDesc.map(_.name).find(_ == name).getOrElse(misc("invalidAbility"))
  def apply(key: String) = this.misc(key)

class AbilityDialogues(val name: String, val desc: String)

/**
  * Text-related info of a timeline to easily access dialogues of it and its areas.
  */
class TimelineDialogues(val word: String, val question: String, val answer: String, val areaDialogues: Map[String, AreaDialogues]):

  var misc = Map[String, String]()

  def area(name: String) = areaDialogues(name)

  def apply(key: String) = misc.getOrElse(key, "")

/**
  * Text-related info of an area to easily access dialogues of it.
 */
class AreaDialogues(phaseDescriptions: Vector[String], abilityDescriptions: Map[String, String]):
  /**
    * Return the right phase-description if existent,
    * otherwise the phase 0 description if existent,
    * otherwise the "no area description" error text.
    * @param i The phase number.
    */
  def phaseDesc(i: Int) =
    phaseDescriptions.lift(i).find(_ != "").getOrElse(this.toString)

  def abilityDesc(abilityName: String) = abilityDescriptions.getOrElse(abilityName, "")


  /**
    * Feelings don't need alternative texts if not defined.
    */
  //val visualDesc = abilityDescriptions.headOption.getOrElse("")
  //val soundDesc = abilityDescriptions.lift(1).getOrElse("")
  //val physicalDesc = abilityDescriptions.lift(2).getOrElse("")

  override def toString = phaseDescriptions.find(_ != "").getOrElse(Dialogues.debug("noAreaDesc"))

//val timeDesc = abilityDescriptions.lift(2).getOrElse("")

class ElementDialogues(val name: String, val action: String, val output: String)