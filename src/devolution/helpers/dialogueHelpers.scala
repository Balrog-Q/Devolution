package devolution.helpers

class KnowledgeDialogues(val misc: Map[String, String], val abilitiesDesc: Map[String, String]):
  def desc(name: String) = this.abilitiesDesc.find(_._1.toLowerCase == name.toLowerCase).map(_._2).getOrElse(misc("invalidAbility"))
  def apply(key: String) = this.misc(key)

class AbilityDialogues(val name: String, val desc: String)

/**
  * Text-related info of a timeline to easily access dialogues of it and its areas.
  */
class TimelineDialogues(val word: String, val question: String, val answer: String, val thought: String, val realization: String, val areaDialogues: Map[String, AreaDialogues]):

  var misc = Map[String, String]()

  def area(name: String) = areaDialogues(name)

  def apply(key: String) = misc.getOrElse(key, "")

/**
  * Text-related info of an area to easily access dialogues of it.
 */
class AreaDialogues(phaseDescriptions: Vector[String], abilityDescriptions: Map[String, String] = Map[String, String]()):
  /**
    * Return the right phase-description if existent
    * @param i The phase number.
    */
  def phaseDesc(i: Int) =
    phaseDescriptions.lift(i).getOrElse(this.getNonEmpty)

  def abilityDesc(abilityName: String) =
    abilityDescriptions.getOrElse(abilityName, "")

  def description(phase: Int, canSee: Boolean) =
    if canSee then
      abilityDesc(D.possibleAbilities("vision"))
    else
      this.phaseDesc(phase)

  /**
    * Search for a non-empty phase description if existent,
    * otherwise returns the "no area description" text.
    * @return
    */
  def getNonEmpty = phaseDescriptions.find(_ != "").getOrElse(D("noAreaDesc"))


class InteractableDialogues(val name: String, val action: String, val output: String):
  /**
    * The value is set to lowercase to match the possible user input
    * @return
    */
  def lowerName = this.name.toLowerCase