package devolution.helpers

/**
  * This object allows to centralize every single text in the game to keep track and easily modify them.
  */
object Dialogues:

  //dialogues that does not fall under any other category
  val misc = Map[String, String](
    "quote" -> "Devolution\n\nOne general law, leading to the advancement of all organic beings, is multiply\nTherein, let the strongest live, and the weakest die\nEventually, a species can become complacent\nAt that point, a de-evolution can occur\nA spiral downwards, signaling an end for that lifeform",
    "unknownCommand" -> "It's not the right time to",
    "unknownParameter" -> "It doesn't sound like a good idea to",
    "wrongQuestion" -> "That surely something to think about, but not quite what you are looking for...",
    "undefinedArea" -> "You can't distinguish anything useful",
    "noArea" -> "There is nothing particularly interesting in that direction",
    "dead" -> "You took one step too close to the ",
    "moved" -> "You tried to move ",
    "accept" -> "yes",
    "accepted" -> "Relizing your condition, from now on you refuse to die",
    "reminder" -> "Before moving around, you need to be determinated to... explore",
    "denied" -> "You are overwhelmed by what is going on in this place. Even though you still can't fully understand it, you feel like you shouldn't be here. And by the time you realize this, you also remember that-",
    "finalQuestion" -> "Are you still eating straight from the paw?",
    "aroundYou" -> "You feel particulary attracted by: ",
    "deadCommand" -> "-",
    "wrongAction" -> "A questionable idea to "
  )

  val actionNames = Map[String, String](
    "go" -> "go",
    "evolve" -> "evolve",
    "devolve" -> "devolve",
    "examine" -> "examine",
    "see" -> "watch",
    "hear" -> "listen",
    "fear" -> "fear",
    "sad" -> "grieve",
    "explore" -> "explore",
    "quit" -> "quit",
    "thought" -> "contemplate",
    "learn" -> "acquire",
    "eat" -> "eat"
  )
  /*val a = Map[String, String](
    "thought" -> "contemplate",
    "learn" -> "acquire")
  val b = "contemplate"
  val c = "acquire"*/

  val actions = Map[String, String](
    "future" -> "You wait",
    "past" -> "You make a hard choice",
  )

  val movements = Map[String, String](
    "n" -> "north",
    "s" -> "south",
    "e" -> "east",
    "w" -> "west",
    "up" -> "up",
    "down" -> "down",
    "forward" -> "forward",
    "away" -> "away",
    "back" -> "back",
    "in" -> "in",
    "past" -> "past",
    "future" -> "future"
  )

  val debug = Map[String, String](
    "noOutput" -> "Wow, you certainly did something by trying to ",
    "noAreaDesc" -> "Wow, you ceraintly are somewhere now",
    "noPhase" -> "Wow, you certainly progressed until some point",
    "noAbility" -> "Wow, you certainly got some broken unknown ability",
    "noElement" -> "Wow, you certainly acted on something")

  //val story = Map[String, String]("")


  //---- Knowledge ----

  //list of abilities to associate easy names to in-game names
  val possibleAbilities = Map[String, String](
    "memory" -> "Memory",
    "thought" -> "Critical thinking",
    "curious" -> "Curiosity",
    "vision" -> "Vision",
    "sad" -> "Sadness",
    "fear" -> "Fear",
    "hear" -> "Hearing",
    "proprio" -> "Proprioception"
  )

  //associate in-game names to descriptions
  val abilitiesDesc = Map[String, String](
    //"timeflow" -> "You can understand the flow of time",
    possibleAbilities("memory") -> "What happened, now is and stays clearer in your mind",
    possibleAbilities("thought") -> "You can contemplate places and events and thus reach to new conclusions",
    possibleAbilities("curious") -> "The desire of discovery push you in any direction you may want to go",
    possibleAbilities("vision") -> "Your immediate surroundings give you way to interpret reality",
    possibleAbilities("sad") -> "Lets you grieve on objects and events",
    possibleAbilities("fear") -> "Allows you to safeguard yourself by feeling fear",
    possibleAbilities("hear") -> "Reveals a new dimension of reality",
    possibleAbilities("proprio") -> "You can survery every direction as if it was a part of your own body"
  )

  val abilityMisc = Map[String, String](
    "desc" -> "What you acquired until now:\n",
    "noAbility" -> "You haven't learned anything yet",
    "missingAbility" -> "You still lack the knowledge to do that",
    "new" -> "A new light sparks in you: ",
    "knowledgeIntro" -> "You reacquired the following:",
    "invalidAbility" -> debug("noAbility")
  )


  val knowledge = KnowledgeDialogues(abilityMisc, abilitiesDesc)

  val interactables = Map[String, ElementDialogues](
    "dust1" -> ElementDialogues("Dust", "collect", "Point"),
    "dust2" -> ElementDialogues("Dust", "collect", "This"),
    "dust3" -> ElementDialogues("Dust", "collect", "What"),
    "dust4" -> ElementDialogues("Dust", "collect", "Of"),
    "dust5" -> ElementDialogues("Dust", "collect", "Is"),
    //"dust6" -> ElementDialogues("Dust", "leave", "-"),

    "cell1" -> ElementDialogues("Prokaryote", "eat", "Is"),
    "cell2" -> ElementDialogues("Eukaryote", "eat", "There"),
    "rock" -> ElementDialogues("Rock","watch", "A"),
    "mollusk" -> ElementDialogues("Mollusk", "eat", "Peak"),
    "plankton" -> ElementDialogues("Mollusk", "eat", "In"),
    "fish1" -> ElementDialogues("Small fish", "eat", "This"),
    "fish2" -> ElementDialogues("Big fish", "eat", "Chain"),
    "thermalRock" -> ElementDialogues("Porous rock", "settle", "Chain"),

    "rock" -> ElementDialogues("Rock", "touch", "I"),
    "escrement" -> ElementDialogues("Escrements", "touch", "why"),
    "sand" -> ElementDialogues("Quicksand", "touch", "*proprioception*"),
    "step" -> ElementDialogues("Heavy steps", "hear", "them"),
    "tree" -> ElementDialogues("Tall tree", "watch", "*vision*"),
    "egg" -> ElementDialogues("Giant egg", "eat", "escape"),
    "horn" -> ElementDialogues("Broken horn", "watch", "should"),
    "droplet" -> ElementDialogues("Droplet", "hear", "*hearing*"),

    "guard" -> ElementDialogues("Armed guard", "fear", "how"),
    "corpse" -> ElementDialogues("Corpses", "grieve", "survive"),
    "rat" -> ElementDialogues("Rat", "fear", "could"),
    "ill" -> ElementDialogues("Ill child", "grieve", "someone"),
    "strappado" -> ElementDialogues("Strappado", "fear", "this"),

    "scientist1" -> ElementDialogues("Scientist", "hear", "I"),
    "priest" -> ElementDialogues("Priest", "watch", "am"),
    "bird" -> ElementDialogues("Dear bird", "grieve", "through"),
    "clock" -> ElementDialogues("Clock", "fear", "time"),
    "cat" -> ElementDialogues("Cat", "touch", "traveling"),

    "setting1" -> ElementDialogues("Setting", "fear", ""),
    "setting2" -> ElementDialogues("Setting", "grieve", ""),
    "setting3" -> ElementDialogues("Setting", "analyze", ""),
    "peripheral1" -> ElementDialogues("Peripheral", "fear", ""),
    "peripheral2" -> ElementDialogues("Peripheral", "grieve", ""),
    "peripheral3" -> ElementDialogues("Peripheral", "analyze", ""),
    "memory1" -> ElementDialogues("RAM", "fear", ""),
    "memory2" -> ElementDialogues("RAM", "grieve", ""),
    "memory3" -> ElementDialogues("RAM", "analyze", ""),

    "" -> ElementDialogues("", "watch", "Why"),
    "" -> ElementDialogues("", "", "Is"),
    "" -> ElementDialogues("", "", "This"),
    "" -> ElementDialogues("", "", "So"),
    "" -> ElementDialogues("", "", "Different"),

    "particle" -> ElementDialogues("Particle", "collect", "*memory*"),
    "" -> ElementDialogues("", "", "")
  )




  //---- Zones ----
  val areas = Map[String, String]("bonus" -> "Overflow", "bb" -> "Big Bang", "vacuum" -> "Vacuum", "ps" -> "Primordial Soup", "closeVoid" -> "Periphery of cluster", "voidCenter" -> "Center of cluster", "ol" -> "Origin of life", "ocean1" -> "Ocean surface", "surface" -> "Volcanic area", "ocean2" -> "Twilight zone", "ocean3" -> "Midnight zone", "ocean4" -> "Hadal zone", "thermal" -> "Thermal source", "lava" -> "Lava river", "volcano2" -> "Underwater volcano", "volcano" -> "Volcano", "ph" -> "Prehistory", "jungle" -> "Jungle", "clearing" -> "Jungle clearing", "hill" -> "Hill", "swamp" -> "Swamp", "nest" -> "Dinosaur nest", "cave1" -> "Cave entrance", "cave2" -> "Cave depth", "ma" -> "Middle Ages", "path" -> "Pathway", "castle1" -> "Castle entrance", "castle2" -> "Castle private courtyard", "field" -> "Open field", "village" -> "House entrance", "grave" -> "Mass grave", "stream" -> "Stream", "house" -> "House", "sr" -> "Scientific revolution", "room" -> "Meeting room", "terrace" -> "Terrace", "ge" -> "Globalization era", "start" -> "White room", "switch" -> "Switch", "firewall" -> "Firewall", "pc" -> "PC", "server" -> "Servers", "web" -> "Deep web", "el" -> "End of life", "tower" -> "Tower top", "hd" -> "Heat death")

  //overflow dialogues
  val bonus = TimelineDialogues("", "", "", "", "",
    Map(
    areas("bonus") -> AreaDialogues(
      Vector("No, I'm not eating from a paw anymore. No matter which kind of being or entity I am, I'm now free. But do I really desire freedom?"),
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
  val bb = TimelineDialogues("", "", "", "", "", Map(
    areas("vacuum") -> AreaDialogues(
      Vector("", "Everything here. Unfortunately, -", "Every interesting thing is here. If it wasn't for -", "It's not through memory that you get here. But -", "You can't find something that survives if you don't do it yourse-", "Everything, but not what you just did. Pretty unexpected to -"),
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
  val ps = TimelineDialogues("from", "What is the point of this?",
    "Did this have any actual utility? The only clear thing is that something happened, and many things will still happen. Where could you go to discover what will be?",
    "The butterfly effect at its finest. From just dust to a working universe, in just few billion years. From the center to the very edge, things here were free from any kind of purpose.",
    "We all come from here. But where are headed? What is the arrival point?",
    Map(
    areas("voidCenter") -> AreaDialogues(
      Vector("", "Seems interesting", "Memory is not yet a thing here", "What you experienced had no effect here", "The place where interesting things just happened", "There is no danger here, only opportunities", "Some say this is also a peak, but you are looking for a less abstract one"),
      Map[String, String](
        possibleAbilities("vision") -> "Nothing to see really. There is still no source of light",
        possibleAbilities("proprio") -> "You miss a floor between your feets",
        possibleAbilities("hear") -> "Sound travels through air, and there isn't much here",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("closeVoid") -> AreaDialogues(
      Vector("", "Everything here is alone"),
      Map[String, String](
        possibleAbilities("vision") -> "Nothing but darkness",
        possibleAbilities("proprio") -> "You feel a strange pull from were you arrived, as if something is trying to swallow you whole",
        possibleAbilities("hear") -> "You hear nothing but silence",
        possibleAbilities("fear") -> "The fear of complete void hold you from getting further",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("vacuum") -> AreaDialogues(
      Vector(""),
      Map[String, String]()
    )
  ))

  ps.misc = Map[String, String](
    "intro" -> "An uncountable number things, here, are alone. For the moment, they are just… dust. If only there was a way to bring them closer to... collect it...",
    "star1" -> "Something happened. But you will need to focus on another question to understand. For now it's time to leave this cluster",
    "move" -> "You seem also able to go away from here. But feel free to go back if you are loosing your way",
    "less" -> "Another thing happened. This place stars to have a structure",
    "more1" -> "More dust is here. It is starting to interact",
    "more2" -> "Even more dust. Something is happening",
    "leave" -> "leave",
    //"left" -> "You moved away from the new cluster",
  )

  val ol = TimelineDialogues("eating", "Is there a peak in this chain?",
    "This endless cycle is probably bound to repeat itself again and again, unless... where could it lead?",
    "Consuming and eating was the only point in this uncertain world. By the logic of killed or be killed, a structured food chain is formed as the bigger eat the smaller.",
    "Will the simple act of eating again and again be somewhat useful? What if something becomes unstoppable at starts at consuming everything around it?",
    Map(
    areas("ocean1") -> AreaDialogues(
      Vector("", "Nothing interesting here", "It's too chaotic to keep track of things here", "Something, but still not interesting", "Here lives the origin of the problem. But you had already enough of it"),
      Map[String, String](
        possibleAbilities("vision") -> "A sunny day in this gray and blue world",
        possibleAbilities("proprio") -> "Earthquakes shake the ground and lava river flows in great numbers. The water it's full of energy",
        possibleAbilities("hear") -> "You hear the gentle splash of the waves, the rhythmic pulse of the tide",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You wonder what lies beneath the waves..."
      )
    ),
    areas("surface") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Clouds of smoke and ash rise from the craters of this volcanic area",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "The rumbling of the earth and the hissing of the steam are increasingly deafening",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean2") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Darkness envelops you like a thick blanket",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "You feel a surge of fear as you go deeper. You don't know what dangers lurk in the depths. You hesitate, unsure if you should risk it.",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean3") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "The darkness can't get any worse than this",
        possibleAbilities("proprio") -> "Bubbles of air raise to the surface",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "Every part of you screams in panic. You're not supposed to be here. Better go away",
        possibleAbilities("sad") -> "You feel a pang of sadness as you realize how lonely this place is",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean4") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Mysterious glimpse of light create intricate patterns",
        possibleAbilities("proprio") -> "The mass of water around you gets pushed around by some unexplainable current",
        possibleAbilities("hear") -> "A distant rumble echoes through the water",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("thermal") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "There is a strange formation growing from the seafloor. It looks like a volcanic vent, spewing hot water and minerals",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You are attracted by the welcoming warmth produced"
      )
    ),
    areas("lava") -> AreaDialogues(
      Vector(""),
      Map[String, String]()
    ),
    areas("volcano2") -> AreaDialogues(
      Vector(""),
      Map[String, String]()
    )
  ))
  ol.misc = Map[String, String](
    "intro" -> "There is nothing to escape from, here... but not for long. On place will make your fears real, by giving you a reason to escape. Find it.",
    "body" -> "Like you, many other entities had the same idea, and they too developed a physical form.\nNavigate wisely these uncertain waters and eat as much as you can. Follow their same path, and reach the top",
    "done" -> "You are now the strongest. Do this help focus on something?"
  )

  val ph = TimelineDialogues("the", "Why should I escape them?",
    "You felt all kind of emotions by surviving here. But in contrast to the other living being, you had no reason to fear for your safety. Where should you go to become like them?",
    "The forests, the oceans and even the skies are ruled by monsters. But the true reason to move forward is the unexplainable instinct that resides somewhere in the minds of every living being.",
    "The thing that everything is trying to preserve is their life. But losing oneself’s body in favor of someone else could benefit both species.",
    Map(
    areas("clearing") -> AreaDialogues(
      Vector("", "Nothing interesting here", "Something, but still not interesting", "Things happen too quick here to remember them", "A place where something could survive"),
      Map[String, String](
        possibleAbilities("vision") -> "A green and dense panorama all around",
        possibleAbilities("proprio") -> "Everything seems to be ready to ambush you. Not even the ground under you feet wants to stay still.",
        possibleAbilities("hear") -> "Low rumbles and high pitch cries fill the area",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "A misplaced step is enough to face death here. In this kind of wildness, mistakes are not forgiven. Better pay attention."
      )
    ),
    areas("volcano") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "The red glow of lava contrasts with the dark rocks and the green vegetation",
        possibleAbilities("proprio") -> "The magma lurking under the ground makes it feel alive",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "A false step could mean diving directly into some lava"
      )
    ),
    areas("jungle") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Nothing but shades of green in this dense jungle. Light hardly reaches the ground",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "You can hear the rustling of leaves and the roaring of creatures",
        possibleAbilities("sad") -> "Cruel and unforgiving, you have won’t find any mercy in here",
        possibleAbilities("curious") -> "Something is always spying through the shadows"
      )
    ),
    areas("hill") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "The rocks of a recent landslide reflects the sun over this steep hill",
        possibleAbilities("proprio") -> "You can feel the warmth of the sun and the freshness of the air",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You wonder what resides on top"
      )
    ),
    areas("swamp") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "The rocks of a recent landslide reflects the sun over this steep hill",
        possibleAbilities("proprio") -> "The air is humid and heavy, buzzing with insects and the cries of unknown animals",
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
    ),
  ))
  ph.misc = Map[String, String](
    "intro" -> "Wherever you may be, things around here understood why they feel emotions. watch, touch and hear, Become like them, and learn their capabilities",
    "" -> ""
  )

  val ma = TimelineDialogues("straight", "How could someone survive this?",
    "It seems impossible for anything to thrive in this environment. Is there something, somewhere, that doesn't care about dead?",
    "Life here was pretty straightforward, as long as you didn’t try anything possibly unwanted by some inviolable high institution. But during hard times, when any flicker of hope was lost and laws were no more, people were straight up inventing some pseudo-science to solve their problems.",
    "If everything is kept straight and stopped from evolving, the only possible outcome is annihilation, either by external or internal factors.",
    Map(
    areas("path") -> AreaDialogues(
      Vector("", "Nothing interesting here", "Something, but still not interesting","Memory is valued more than any other thing here","The place where you learned through empathy"),
      Map[String, String](
        possibleAbilities("vision") -> "A cloudy afternoon in an almost empty village. Rats are everywhere",
        possibleAbilities("proprio") -> "The mud don’t affect villagers. They are all locked up in their houses or... worse",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "The pathways are spookily quiet. Crows are singing diabolically",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("castle1") -> AreaDialogues(
      Vector("Something imponent blocks your way. But some other hostile entity don’t you to go any further."),
      Map[String, String](
        possibleAbilities("vision") -> "An imposing castle guarded by a massive door",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "Probably, the people in there still live opulent lives, despite the misery out here",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("field") -> AreaDialogues(
      Vector("A sense of emptiness fills you"),
      Map[String, String](
        possibleAbilities("vision") -> "The field seems going on forever",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "The sound of birdsong fills your ears as you walk across the grass",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "This is the only peaceful relief around here",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("village") -> AreaDialogues(
      Vector("This is a maze of rock and misery"),
      Map[String, String](
        possibleAbilities("vision") -> "You stumble across a rural village. The green of plants wrap around every stone grey building",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "You hear multiple faint cries coming from all around",
        possibleAbilities("curious") -> "Nature is not the only one who took over. The state of neglet in tangible"
      )
    ),
    areas("house") -> AreaDialogues(
      Vector("This place has lost its hospitality and now exudes only hopelessness and tears"),
      Map[String, String](
        possibleAbilities("vision") -> "You see the remains of furniture, clothes, toys, books, and other personal belongings",
        possibleAbilities("proprio") -> "The air is stale and dusty. You cough as you inhale the smell of decay and rot",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "You realize that this was once a happy home, full of love and laughter",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("grave") -> AreaDialogues(
      Vector("The striking cold awareness of horror hits you as soon as you arrive here"),
      Map[String, String](
        possibleAbilities("vision") -> "The rotting corpses piled up like firewood reveal a mass grave",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "You feel a chill run down your spine as you imagine the horrors that might have befallen this village",
        possibleAbilities("fear") -> "A sense of danger makes you shiver",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("stream") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "A small, brown, stream blocks your way. Its waters reveals a multitude of colors... but blue is missing",
        possibleAbilities("proprio") -> "Water is not the reason why you decide to stop",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "You feel a pang of sadness as you realize that the slow water is also filled with dead bodies",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("castle2") -> AreaDialogues(
      Vector(""),
      Map[String, String]()
    )
  ))
  ma.misc = Map[String, String](
    "intro" -> "Memory awakes ancestral instinct in every entity that possess it, and here that is especially true. But which kind of instincts? You'll need to get closer to them to find out",
    "" -> ""
  )

  //scientific revolution
  val sr = TimelineDialogues("are", "Am I traveling through time?",
    "This is not right. One should not... I am not allowed to wander through time... but where could someone be granted this ability? Where did I receive it?",
    "Big things are going to happen from here. People are realizing the potential of science. People are thinking in new ways. They are using their minds to discover which  opportunities are still hidden. They are finally, truly, aware of themselves.",
    "Humanity is acquiring awareness. But no matter their efforts, they are still constrained by the flow of time. Shouldn’t everyone be? How could someone be allowed to travel through it?",
    Map(
    areas("room") -> AreaDialogues(
      Vector("", "Nothing interesting here","Something, but still not interesting","They don't focus on the past here","Here, they are on the right track to overcome the problem… but they are not quite there yet"),
      Map[String, String](
        possibleAbilities("vision") -> "A neat room with a cozy light. A big, rectangular table awaits for guests",
        possibleAbilities("proprio") -> "You are not alone",
        possibleAbilities("hear") -> "It’s so quiet that you can hear the wind blowing and the birds singing",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))
  sr.misc = Map[String, String](
    "intro" -> "It hasn't always been like that. Advancement was once pursued with good intentions. Discover something, and shout it to the world...!",
    "" -> ""
  )


  //globalization era
  val ge = TimelineDialogues("you", "Am I alone?",
    "Are you alone? Probably yes.\nAnd probably you are all posing the same question.\nTo find something that is also never alone, you'll need to… devolve, and explore.\n\nSomething is already changing in you. And you desire more. Could this be... *curiosity*? To earn it, now, you only need to acquire it",
    "???\n?? ?\n???????????????\n? ? ? ?\n?? ??\n??? ? ? ? ???\nI can't focus on anything.\n??? ? ? ? ???\nI don't find any word anymore...\n?\nBut maybe I only need to focus on\n?\none single\n?\nelement\n?\nof my thoughts, one\n?\nfor every time I've been in. Just one element\n?\nBut what could it be?\n?????????????????????????????????????",
    "Right. This works.\nMy understanding of every time in history is now complete. What I still don't understand is me. I only need to go back everywhere and focus some more. One word for each time should be enough.\nI must return here with all those pieces.",
    Map(
    areas("start") -> AreaDialogues(
      Vector("You are here.", "You are here again", "Not interesting yet", "They have a different concept of memory here", "You can't explain what you feel being here"),
      Map[String, String](
        possibleAbilities("vision") -> "A completely empty, white room",
        possibleAbilities("proprio") -> "Despite apparently missing, the ground is solid",
        possibleAbilities("hear") -> "A subtle static noise fills the space",
        possibleAbilities("fear") -> "You have mixed feelings about this",
        possibleAbilities("sad") -> "You have mixed feelings about this",
        possibleAbilities("curious") -> "You have mixed feelings about this"
      )
    ),
    areas("switch") -> AreaDialogues(
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
    areas("server") -> AreaDialogues(
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
    areas("pc") -> AreaDialogues(
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
    areas("firewall") -> AreaDialogues(
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
  ge.misc = Map[String, String](
    "intro" -> "But where?\nBut something else is also. But what?\nDespite your best efforts, you can't come to any better conclusion.\nKnowledge gave you life, but now it seems that it all went missing.\nMaybe, if you could think of one precise single question, you would be able to focus…",
    "intro2" -> "You are here. But where?\nAnd is someone else also?",
    "learn" -> "Something is already changing in you. And you desire more. Could this be... *curiosity*? To earn it, now, you only need to acquire it"
  )

  //end of life
  val el = TimelineDialogues("paw", "Why is this so different?",
    "", "", "", Map(
    areas("tower") -> AreaDialogues(
      Vector("", "Nothing interesting here", "Something, but still not interesting","Here, memory is meaningless by now","No one will survive here anyway"),
      Map[String, String](
        possibleAbilities("vision") -> "",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("terrace") -> AreaDialogues(
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
  el.misc = Map[String, String](
    "intro" -> "You may have found the peak. But it's not what you expected. Analyze it by surveying you surroundings.",
    "" -> ""
  )

  //heat death
  val hd = TimelineDialogues("", "", "", "", "", Map(
    areas("vacuum") -> AreaDialogues(
      Vector("", "Nothing interesting here","The opposite of what you did","Here, memory is meaningless by now","No one will survive here anyway"),
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
  hd.misc = Map[String, String](
    "intro" -> "Find the answer. Find the point of what you did, in this place or in its surroundings.",
    "" -> ""
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
  def zones = 
    Map[String, TimelineDialogues](areas("bonus") -> bonus, 
                                   areas("bb") -> bb, 
                                   areas("ps") -> ps, 
                                   areas("ol") -> ol, 
                                   areas("ph") -> ph, 
                                   areas("ma") -> ma, 
                                   areas("sr") -> sr, 
                                   areas("ge") -> ge, 
                                   areas("el") -> el, 
                                   areas("hd") -> hd)
