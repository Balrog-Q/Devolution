package devolution.helpers

/**
  * This object allows to centralize every single text in the game to keep track and easily modify them.
  */
object Dialogues:

  //dialogues that does not fall under any other category
  val misc = Map[String, String](
    "quote" -> "Devolution\n\nOne general law, leading to the advancement of all organic beings, is multiply\nTherein, let the strongest live, and the weakest die\nEventually, a species can become complacent\nAt that point, a de-evolution can occur\nA spiral downwards, signaling an end for that lifeform",
    "intro1" -> "You are here. But where?\nBut something else is also. But what?\nDespite your best efforts, you can’t come to any better conclusion.\nKnowledge gave you life, but now it seems that it all went missing.\nMaybe, if you could think of one precise single question, you would be able to focus…",
    "intro2" -> "You are here. But where?\nAnd is someone else also?",
    "unknownCommand" -> "It’s not the right time to ",
    "unknownParameter" -> "It doesn’t sound like a good idea to ")

  //val story = Map[String, String]("")

  val abilities = Map[String, String]("Flow of time" -> "", "Memory" -> "", "Critical thinking" -> "", "Curiosity" -> "", "Vision" -> "", "Sadness" -> "", "Fear" -> "", "Hearing" -> "", "Proprioception" -> "")

  val areas = Map[String, String]("bonus" -> "Overflow", "bb" -> "Big Bang", "vacuum" -> "Vacuum", "ps" -> "Primordial Soup", "closeVoid" -> "Periphery of cluster", "voidCenter" -> "Center of cluster", "ol" -> "Origin of life", "ocean1" -> "Ocean surface", "volcano" -> "Volcanic area", "ocean2" -> "Twilight zone", "ocean3" -> "Midnight zone", "ocean4" -> "Hadal zone", "thermal" -> "Thermal source", "lava" -> "Lava river", "volcano2" -> "Underwater volcano", "ph" -> "Prehistory", "jungle" -> "Jungle", "clearing" -> "Jungle clearing", "hill" -> "Hill", "swamp" -> "Swamp", "nest" -> "Dinosaur nest", "cave1" -> "Cave entrance", "cave2" -> "Cave depth", "ma" -> "Middle Ages", "path" -> "Pathway", "castle1" -> "Castle entrance", "castle2" -> "Castle private courtyard", "field" -> "Open field", "house1" -> "House entrance", "grave" -> "Mass grave", "stream" -> "Stream", "house2" -> "House", "sr" -> "Scientific revolution", "room" -> "Meeting room", "terrace" -> "Terrace", "ge" -> "Globalization era", "start" -> "White room", "switch" -> "Switch", "firewall" -> "Firewall", "pc" -> "PC", "server" -> "Servers", "web" -> "Deep web", "el" -> "End of life", "tower" -> "Tower top", "hd" -> "Heat death")

  //---- Zones ----

  //overflow dialogues
  val bonus = TimelineDialogues("", "", "", Map(
    areas("bonus") -> AreaDialogues(
    Vector(""),
    Vector("")
    )
  ))

  //big bang dialogues
  val bb = TimelineDialogues("", "", "", Map(
    areas("vacuum") -> AreaDialogues(
    Vector("Everything here. Unfortunately, -", "Every interesting thing is here. If it wasn’t for -", "It’s not through memory that you get here. But -", "You can’t find something that survives if you don’t do it yourse-", "Everything, but not what you just did. Pretty unexpected to -"),
    Vector("Just black, no light to be seen, aside from one singular peculiar point", "Nothing physical is here, but it will soon be", "Not much to hear here", "Time is going to start soon", "You already know what is going to hap-")
    )
  ))

  //primordial soup dialogues
  val ps = TimelineDialogues("from", "What is the point of this?", "", Map(
    areas("voidCenter") -> AreaDialogues(
    Vector("Nothing interesting here", "Seems interesting", "Memory is not yet a thing here", "What you experienced had no effect here", "The place where interesting things just happened", "X Is no danger here, only opportunities", "X Some say this is also a peak, but you are looking for a less abstract one"),
    Vector("Nothing to see really. There is still no source of light", "You miss a floor between your feets", "Sound travels through air, and there isn’t much here", "At these scales, time almost feel frozen", "The butterfly effect at its finest. Put some dust together now and who knows what could happen in a few billion years")),
    areas("closeVoid") -> AreaDialogues(
      Vector(""),
      Vector("")
    ))
  )

  val ol = TimelineDialogues("eating", "Is there a peak in this chain?", "", Map(
    areas("ocean1") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("volcano") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("ocean2") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("ocean3") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("ocean4") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("thermal") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("lava") -> AreaDialogues(
      Vector(""),
      Vector("")
    )
  ))

  val ph = TimelineDialogues("the", "Why should I escape them?", "", Map(
    areas("clearing") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("volcano") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("jungle") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("hill") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("swamp") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("nest") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("cave1") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("cave2") -> AreaDialogues(
      Vector(""),
      Vector("")
    )
  ))

  val ma = TimelineDialogues("straight", "How could someone survive this?", "", Map(
    areas("path") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("castle1") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("field") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("house1") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("house2") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("grave") -> AreaDialogues(
      Vector(""),
      Vector("")
    ),
    areas("stream") -> AreaDialogues(
      Vector(""),
      Vector("")
    )
  ))

  //globalization era
  val ge = TimelineDialogues("you", "Am I alone?",
    "Are you alone? Probably yes.\nAnd probably you are all posing the same question.\nTo find something that is also never alone, you’ll need to… devolve.",
    Map(
      areas("start") -> AreaDialogues(
        Vector(""),
        Vector("")
      )
    )
  )

  //scientific revolution
  val sr = TimelineDialogues("", "",
    "",
    Map(
      areas("room") -> AreaDialogues(
        Vector(""),
        Vector("")
      )
    )
  )

  //end of life
  val el = TimelineDialogues("", "",
    "",
    Map(
      areas("tower") -> AreaDialogues(
        Vector(""),
        Vector("")
      )
    )
  )

  //heat death
  val hd = TimelineDialogues("", "",
    "",
    Map(
      areas("vacuum") -> AreaDialogues(
        Vector(""),
        Vector("")
      )
    )
  )

  /*val ge = TimelineDialogues("", "",
    "",
    Map(
      "" -> AreaDialogues(
        Vector(""),
        Vector("")
      )
    )
  )*/

  //...


  //allows to access every possible zone's dialogues though the unique zone name
  def zones = Map[String, TimelineDialogues](areas("bonus") -> bonus, areas("bb") -> bb, areas("ps") -> ps, areas("ol") -> ol, areas("ph") -> ph, areas("ma") -> ma, areas("sr") -> sr, areas("ge") -> ge, areas("el") -> el, areas("hd") -> hd)

class TimelineDialogues(val word: String, val question: String, val answer: String, val areaDialogues: Map[String, AreaDialogues]):
  def area(name: String) = areaDialogues(name)
/**
  * Helper to set and access dialogues of timelines or areas
 */
class AreaDialogues(phaseDescriptions: Vector[String], abilityDescriptions: Vector[String]):
  def phaseDesc(i: Int) = phaseDescriptions.lift(i).getOrElse("")
  val visualDesc = abilityDescriptions.lift(1).getOrElse("")
  val soundDesc = abilityDescriptions.lift(1).getOrElse("")
  val physicalDesc = abilityDescriptions.lift(2).getOrElse("")
  //val timeDesc = abilityDescriptions.lift(2).getOrElse("")