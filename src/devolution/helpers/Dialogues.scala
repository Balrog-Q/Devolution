package devolution.helpers

/**
  * This object allows to centralize every single text in the game to keep track and easily modify them.
  */
object Dialogues:

  //dialogues that does not fall under any other category
  val misc = Map[String, String](
    "quote" -> "Devolution\n\nOne general law, leading to the advancement of all organic beings, is multiply\nTherein, let the strongest live, and the weakest die\nEventually, a species can become complacent\nAt that point, a de-evolution can occur\nA spiral downwards, signaling an end for that lifeform",
    "intro1" -> "But where?\nBut something else is also. But what?\nDespite your best efforts, you can’t come to any better conclusion.\nKnowledge gave you life, but now it seems that it all went missing.\nMaybe, if you could think of one precise single question, you would be able to focus…",
    "intro2" -> "You are here. But where?\nAnd is someone else also?",
    "unknownCommand" -> "It’s not the right time to ",
    "unknownParameter" -> "It doesn’t sound like a good idea to ",
    "undefinedArea" -> "You can't distinguish anything useful",
    "noArea" -> "There is nothing particularly interesting in that direction"
  )

  val actions = Map[String, String](
    "evolveAction" -> "You wait",
    "devolveAction" -> "You make a hard choice",
  )

  val debug = Map[String, String](
    "noOutput" -> "Wow, you certainly did something by trying to ",
    "noAreaDesc" -> "Wow, you ceraintly are somewhere now",
    "noPhase" -> "Wow, you certainly progressed until some point",
    "noAbility" -> "Wow, you certainly got some broken unknown ability")

  //val story = Map[String, String]("")


  //---- Knowledge ----

  //list of abilities to associate easy names to in-game names
  val possibleAbilities = Map[String, String]("memory" -> "Memory",
    "thought" -> "Critical thinking",
    "curious" -> "Curiosity",
    "vision" -> "Vision",
    "sad" -> "Sadness",
    "fear" -> "Fear",
    "hear" -> "Hearing",
    "proprio" -> "Proprioception"
  )

  //associate in-game names to descriptions
  val abilitiesDesc = Vector[AbilityDialogues](
    //"timeflow" -> "You can understand the flow of time",
    AbilityDialogues(possibleAbilities("memory"), "What happened, now is and stays clearer in your mind"),
    AbilityDialogues(possibleAbilities("thought"), "You can contemplate places and events and thus reach to new conclusions"),
    AbilityDialogues(possibleAbilities("curious"), "The desire of discovery push you in any direction you may want to go"),
    AbilityDialogues(possibleAbilities("vision"), "Your immediate surroundings give you way to interpret reality"),
    AbilityDialogues(possibleAbilities("sad"), "Lets you grieve on objects and events"),
    AbilityDialogues(possibleAbilities("fear"), "Allows you to safeguard yourself by feeling fear"),
    AbilityDialogues(possibleAbilities("hear"), "Reveals a new dimension of reality"),
    AbilityDialogues(possibleAbilities("proprio"), "You can survery every direction as if it was a part of your own body"))

  val abilityMisc = Map[String, String](
    "desc" -> "What you acquired until now:\n",
    "noAbility" -> "You haven't learned anything yet",
    "missingAbility" -> "You still lack the knowledge to do that",
    "new" -> "A new light sparks in you: ",
    "knowledgeIntro" -> "You reacquired the following:",
    "invalidAbility" -> debug("noAbility")
  )


  val knowledge = KnowledgeDialogues(abilityMisc, abilitiesDesc)






  //---- Zones ----
  val areas = Map[String, String]("bonus" -> "Overflow", "bb" -> "Big Bang", "vacuum" -> "Vacuum", "ps" -> "Primordial Soup", "closeVoid" -> "Periphery of cluster", "voidCenter" -> "Center of cluster", "ol" -> "Origin of life", "ocean1" -> "Ocean surface", "volcano" -> "Volcanic area", "ocean2" -> "Twilight zone", "ocean3" -> "Midnight zone", "ocean4" -> "Hadal zone", "thermal" -> "Thermal source", "lava" -> "Lava river", "volcano2" -> "Underwater volcano", "ph" -> "Prehistory", "jungle" -> "Jungle", "clearing" -> "Jungle clearing", "hill" -> "Hill", "swamp" -> "Swamp", "nest" -> "Dinosaur nest", "cave1" -> "Cave entrance", "cave2" -> "Cave depth", "ma" -> "Middle Ages", "path" -> "Pathway", "castle1" -> "Castle entrance", "castle2" -> "Castle private courtyard", "field" -> "Open field", "house1" -> "House entrance", "grave" -> "Mass grave", "stream" -> "Stream", "house2" -> "House", "sr" -> "Scientific revolution", "room" -> "Meeting room", "terrace" -> "Terrace", "ge" -> "Globalization era", "start" -> "White room", "switch" -> "Switch", "firewall" -> "Firewall", "pc" -> "PC", "server" -> "Servers", "web" -> "Deep web", "el" -> "End of life", "tower" -> "Tower top", "hd" -> "Heat death")

  //overflow dialogues
  val bonus = TimelineDialogues("", "", "", Map(
    areas("bonus") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //big bang dialogues
  val bb = TimelineDialogues("", "", "", Map(
    areas("vacuum") -> AreaDialogues(
      Vector("Everything here. Unfortunately, -", "Every interesting thing is here. If it wasn’t for -", "It’s not through memory that you get here. But -", "You can’t find something that survives if you don’t do it yourse-", "Everything, but not what you just did. Pretty unexpected to -"),
      Map[String, String](
        possibleAbilities("vision") -> "Just black, no light to be seen, aside from one singular peculiar point",
        possibleAbilities("proprio") -> "Nothing physical is here, but it will soon be",
        possibleAbilities("hear") -> "Not much to hear here",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "You already know what is going to hap-",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //primordial soup dialogues
  val ps = TimelineDialogues("from", "What is the point of this?", "", Map(
    areas("voidCenter") -> AreaDialogues(
      Vector("Nothing interesting here", "Seems interesting", "Memory is not yet a thing here", "What you experienced had no effect here", "The place where interesting things just happened", "X Is no danger here, only opportunities", "X Some say this is also a peak, but you are looking for a less abstract one"),
      Map[String, String](
        possibleAbilities("vision") -> "Nothing to see really. There is still no source of light",
        possibleAbilities("proprio") -> "You miss a floor between your feets",
        possibleAbilities("hear") -> "Sound travels through air, and there isn’t much here",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("closeVoid") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  val ol = TimelineDialogues("eating", "Is there a peak in this chain?", "", Map(
    areas("ocean1") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("volcano") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean2") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean3") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean4") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("thermal") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("lava") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  val ph = TimelineDialogues("the", "Why should I escape them?", "", Map(
    areas("clearing") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("volcano") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("jungle") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("hill") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("swamp") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("nest") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("cave1") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("cave2") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  val ma = TimelineDialogues("straight", "How could someone survive this?", "", Map(
    areas("path") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("castle1") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("field") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("house1") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("house2") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("grave") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("stream") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //globalization era
  val ge = TimelineDialogues("you", "Am I alone?",
    "Are you alone? Probably yes.\nAnd probably you are all posing the same question.\nTo find something that is also never alone, you’ll need to… devolve.", Map(
    areas("start") -> AreaDialogues(
      Vector("You are here.", "Not interesting yet", "They have a different concept of memory here", "You can’t explain what you feel being here"),
      Map[String, String](
        possibleAbilities("vision") -> "A completely empty, white room",
        possibleAbilities("proprio") -> "Despite apparently missing, the ground is solid",
        possibleAbilities("hear") -> "A subtle static noise fill the space",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //scientific revolution
  val sr = TimelineDialogues("", "", "", Map(
    areas("room") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //end of life
  val el = TimelineDialogues("", "", "", Map(
    areas("tower") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

  //heat death
  val hd = TimelineDialogues("", "", "", Map(
    areas("vacuum") -> AreaDialogues(
      Vector("Nothing interesting here",""),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))

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
