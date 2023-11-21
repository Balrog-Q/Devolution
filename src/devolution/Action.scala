package devolution

import devolution.helpers.D
/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  var success     = false
  val commandText = input.trim.toLowerCase
  val verb        = commandText.takeWhile( _ != ' ' )
  val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match
    case D.actionNames("go")        => Some(actor.go(this.modifiers))
    case D.actionNames("devolve")   => Some(actor.devolve())
    case D.actionNames("evolve")    => Some(actor.evolve())
    //case "knowledge" => Some(actor.knowledge)
    case D.actionNames("examine")   => Some(actor.examine(this.modifiers))
    case D.actionNames("quit")      => Some(actor.quit())
    case D.actionNames("explore")   => Some(actor.enterTimeline())
    //case D.actionNames("see")       => Some(actor.interact(this.modifiers))
    case "$"                        => Some("phase " + actor.phase + " " + actor.location.toString) // FOR DEBUG
    case "tp"                       => Some(actor.tp(this.modifiers))
    case other if !this.modifiers.isBlank => Some(actor.interact(this.verb, this.modifiers))
    case other                      => None
    //case "drop"      => Some(actor.drop(this.modifiers))
    //case "get"       => Some(actor.get(this.modifiers))
    //case "xyzzy"     => Some("The grue tastes yummy.")
    //case "rest"      => Some(actor.rest())
  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"
end Action
