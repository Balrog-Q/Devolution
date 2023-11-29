package devolution

import devolution.*
import devolution.helpers.*

/**
  * An interactable element found in an area.
  * Requires a specific action and return some text if acted on the right way.
  * @param dialogues
  * @param neededInteractions When it has value -1, the object never reaches completeness
  */
class Interactable(private val dialogues: InteractableDialogues, private val neededInteractions: Int = 1): // private val requiredAction: String, private val output: String):

  var interactions = 0

  def name = this.dialogues.lowerName

  def requiredAction = this.dialogues.action

  def execute(action: String): Option[String] =
    if action == requiredAction && !this.completed && this.dialogues.output.nonEmpty then
      interactions += 1
      //if the output is composed by more words, doesn't add decoration to it
      if this.dialogues.output.split(" ").length == 1 then
        Some(s"~${this.dialogues.output}~")
      else
        Some(this.dialogues.output)
    else
      None

  def completed = interactions == neededInteractions && neededInteractions > -1