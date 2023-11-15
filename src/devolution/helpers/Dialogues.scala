package devolution.helpers

/**
  * This object allows to centralize every single text in the game to keep track and easily modify them.
  */
object Dialogues:

  //dialogues that does not fall under any other category
  val misc = Map[String, String]

  val abilities = Map[String, String]("Flow of time" -> "", "Memory" -> "", "Critical thinking" -> "", "Curiosity" -> "", "Vision" -> "", "Sadness" -> "", "Fear" -> "", "Hearing" -> "", "Proprioception" -> "")



  //---- Zones ----

  //big bang dialogues map
  val bb = ZoneDialogues(
    Vector("Everything here. Unfortunately, -", "Every interesting thing is here. If it wasn’t for -", "It’s not through memory that you get here. But -", "You can’t find something that survives if you don’t do it yourse-", "Everything, but not what you just did. Pretty unexpected to -"),
    Vector("Just black, no light to be seen, aside from one singular peculiar point", "Nothing physical is here, but it will soon be", "Not much to hear here", "Time is going to start soon", "You already know what is going to hap-"))

  //primordial soup dialogues map
  val ps = ZoneDialogues(
    Vector("Nothing interesting here", "Seems interesting", "Memory is not yet a thing here", "What you experienced had no effect here", "The place where interesting things just happened", "X Is no danger here, only opportunities", "X Some say this is also a peak, but you are looking for a less abstract one"),
    Vector("Nothing to see really. There is still no source of light", "You miss a floor between your feets", "Sound travels through air, and there isn’t much here", "At these scales, time almost feel frozen", "The butterfly effect at its finest. Put some dust together now and who knows what could happen in a few billion years"))

  //...


  //allows to access every possible zone's dialogues though the unique zone name
  def zones = Map[String, ZoneDialogues]("Big Bang" -> bb, "Primordial Soup" -> ps)


/**
  * Helper to set and access dialogues of timelines or areas
 */
class ZoneDialogues(private val phaseDescriptions: Vector[String], private val abilityDescriptions: Vector[String]):
  def phaseDesc(i: Int) = phaseDescriptions.lift(i).getOrElse("")
  val visualDesc = abilityDescriptions.lift(1).getOrElse("")
  val soundDesc = abilityDescriptions.lift(1).getOrElse("")
  val physicalDesc = abilityDescriptions.lift(2).getOrElse("")
  val timeDesc = abilityDescriptions.lift(2).getOrElse("")