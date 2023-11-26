package devolution

import devolution.helpers.*
/** The class Action represents actions that a player may take in a text adventure game.
  * Action objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  var success     = false
  val commandText = input.trim.toLowerCase
  val verb        = commandText.takeWhile( _ != ' ' )
  val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”). The description is returned in an Option
    * wrapper; if the command was not recognized, None is returned. */
  def execute(actor: Player) =
    //println(D.action("learn")+ " "+ this.verb + " " + (D.action("learn") == this.verb)+ " " + (D.action("thought") ==this.verb))
    /*this.verb match
    //doesn't work
      case D.action("learn") => println("1")
      case D.action("thought") => println("2")
      case _ => println("3")
    this.verb match
    //doesn't work
      case D.a("learn") => println("1")
      case D.a("thought") => println("2")
      case _ => println("3")
    //work
    this.verb match
      case D.b => println("1")
      case D.c => println("2")
      case _ => println("3")*/

    /*val TestMap = Map[String, String](
      "thought" -> "contemplate",
      "learn" -> "acquire")
    val TestConst = "contemplate"*/

    this.verb match
      /*case D.action("go")        => Some("works fine")
      case D.action("examine")   => Some("works fine")

      case D.action("thought")   => Some("never works")
      case D.action("learn")     => Some("never works")
      case TestMap("thought")         => Some("never works")
      case `TestMap`("learn")           => Some("never works")

      case TestConst                  => Some("works fine")
      case other if this.verb == TestMap("thought") => Some("works fine")*/

      //case "contemplate"
      //case D.action("eat")       => Some("no")
      case D.action("go")        => Some(actor.go(this.modifiers))
      case D.action("examine")   => Some(actor.examine(this.modifiers))

      case D.action("devolve")   => Some(actor.devolve())
      case D.action("evolve")    => Some(actor.evolve())
      //case "knowledge" => Some(actor.knowledge)
      case D.action("quit")      => Some(actor.quit())
      case D.action("knowledge") => Some(actor.knowledge)
      case D.action("explore")   => Some(actor.exploreTimeline())
      case D.action("fear")      => Some(actor.fear(this.modifiers))
      case D.action("sad")       => Some(actor.grieve(this.modifiers))
      //case D.action("feel")      => Some(actor.feel(this.modifiers))
      //case D.action("see")       => Some(actor.interact(this.modifiers))

      //these two are arbitrairly ignored by the match statement if implemented normally.
      case other if this.verb == D.action("learn")     => Some(actor.learn(this.modifiers))
      case other if this.verb == D.action("thought")   => Some(actor.contemplate())
      case "tp"                       => Some(actor.tp(this.modifiers))
      case other if !this.modifiers.isBlank => Some(actor.interact(this.verb, this.modifiers))
      case "$"                        => Some("phase " + actor.phase + " " + actor.location.toString) // FOR DEBUG
      case other                      => None
    //case "drop"      => Some(actor.drop(this.modifiers))
    //case "get"       => Some(actor.get(this.modifiers))
    //case "xyzzy"     => Some("The grue tastes yummy.")
    //case "rest"      => Some(actor.rest())
  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"
end Action
