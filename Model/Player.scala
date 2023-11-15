package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private val possession = Map[String, Item]()

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() =
    "You rest for a while. Better get a move on, though."


  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  def inventory =
    var itemList = this.possession.keys.mkString("\n")
    if this.possession.nonEmpty then
      "You are carrying:\n" + itemList
    else
      "You are empty-handed."

  def drop(itemName: String) =
    this.possession.remove(itemName) match
      case None => "You don't have that"
      case Some(item) =>
        this.location.addItem(item)
        "You drop the " + item.name

  def examine(itemName: String) =
    this.possession.get(itemName) match
      case None => "If you want to examine something, you need to pick it up first."
      case Some(item) => "You look closely at the " + item.name + ".\n" + item.description

  def get(itemName: String) =
    this.location.removeItem(itemName) match
      case None => "There is no " + itemName + " here to pick up."
      case Some(item) =>
        this.possession(item.name) = item
        "You pick up the " + item.name + "."

  def has(itemName: String) = this.possession.contains(itemName)

  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

end Player

