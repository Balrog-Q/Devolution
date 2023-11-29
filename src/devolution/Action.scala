package devolution

import devolution.helpers.*
/** The class Action represents actions that a player may take in a text adventure game.
  * Action objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input A textual in-game command such as “go east” or “rest” */
class Action(input: String):
  val commandText = input.trim.toLowerCase
  val verb        = commandText.takeWhile(_ != ' ')
  val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an Option
    * wrapper; if the command was not recognized, None is returned. */
  def execute(actor: Player) =
    this.verb match
      case D.action("go")        => Some(actor.go(this.modifiers))
      case D.action("examine")   => Some(actor.examine(this.modifiers))
      case D.action("devolve")   => Some(actor.devolve())
      case D.action("evolve")    => Some(actor.evolve())
      case D.action("quit")      => Some(actor.quit())
      case D.action("knowledge") => Some(actor.knowledge)
      case D.action("explore")   => Some(actor.exploreTimeline())
      case D.action("fear")      => Some(actor.fear(this.modifiers))
      case D.action("sad")       => Some(actor.grieve(this.modifiers))
      //Following two are randomly ignored by the match statement if implemented normally (without condition).
      //The problem has possibly to do with the interal logic of the match-case...
      case other if this.verb == D.action("learn")     => Some(actor.learn(this.modifiers))
      case other if this.verb == D.action("thought")   => Some(actor.contemplate())
      //case "tp"                       => Some(actor.tp(this.modifiers)) //uncomment to unlock a "phase teleportation" command
      //unique commands specifics for some interactables
      case other if !this.modifiers.isBlank => Some(actor.interact(this.verb, this.modifiers))
      case other                      => None

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"
end Action
