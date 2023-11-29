package devolution.helpers

import scala.collection.mutable.LinkedHashMap

/**
  * This object allows to centralize every single text in the game to keep track and easily modify them.
  */
object Dialogues:

  //dialogues that does not fall under any other category
  val misc = Map[String, String](
    "quote" -> "--Devolution--\n\n\"One general law, leading to the advancement of all organic beings, is multiply\nTherein, let the strongest live, and the weakest die\nEventually, a species can become complacent\nAt that point, a de-evolution can occur\nA spiral downwards, signaling an end for that lifeform.\"",
    "begin" -> "Wake up",
    "unknownCommand" -> "It's not the right time to",
    "unknownParameter" -> "It doesn't sound like a good idea to",
    "wrongQuestion" -> "That is surely something to think about, but not quite what you are looking for, here and now...",
    "undefinedArea" -> "You can't distinguish anything useful",
    "noArea" -> "Nothing particularly interesting in that direction",
    "noObjects" -> "Nothing catch your attention you here",
    "noAreaDesc" -> "You can't get any information from this place",
    "dead" -> "You took one step too close to the ",
    "moved" -> "\nYou tried to move ",
    "accept" -> "yes",
    "refuse" -> "no",
    //"accepted" -> "Realizing your condition, from now on you refuse to die",
    "reminder" -> "\nBefore moving around, you need to be determined to... explore",
    "denied" -> "You are overwhelmed by what is going on in this place. Even though you still can't fully understand it, you feel like you shouldn't be here. And by the time you realize this, you also remember that-",
    "finalQuestion" -> "Are you still eating straight from the paw?",
    "aroundYou" -> "You feel particularly attracted by: ",
    "deathCommand" -> "-",
    "wrongAction" -> "A questionable idea to ",
    "unknownObject" -> "???",
    "welcome" -> "\nYou begin to explore the ",
    "survey" -> "You examine",
    "unknownTimeline" -> "...whatever this is",
    "future" -> "You waited",
    "past" -> "You make a hard choice",
    "end" -> "This was my choice. I'm ready to face its consequences."
  )

  def apply(key: String) = misc(key)

  val action = Map[String, String](
    "go" -> "go",
    "evolve" -> "evolve",
    "devolve" -> "devolve",
    "examine" -> "examine",
    "see" -> "watch",
    "hear" -> "listen",
    "touch" -> "touch",
    "fear" -> "fear",
    "sad" -> "grieve",
    "curious" -> "browse",
    "explore" -> "explore",
    "quit" -> "quit",
    "thought" -> "contemplate",
    "learn" -> "acquire",
    "eat" -> "eat",
    "knowledge" -> "knowledge",
    "collect" -> "collect",
    "colonize" -> "colonize"
  )
  /*val a = Map[String, String](
    "thought" -> "contemplate",
    "learn" -> "acquire")
  val b = "contemplate"
  val c = "acquire"*/

  /*val actions = Map[String, String](
    "future" -> "You waited",
    "past" -> "You make a hard choice",
  )*/

  val direction = Map[String, String](
    "n" -> "north",
    "s" -> "south",
    "e" -> "east",
    "w" -> "west",
    "up" -> "up",
    "down" -> "down",
    "forward" -> "forward",
    "away" -> "away",
    "back" -> "back",
    "in" -> "in"
  )

  val specialDirection = Map[String, String](
    "past" -> "past",
    "future" -> "future",
    "outside" -> "outside this"
  )

  val debug = Map[String, String](
    "noOutput" -> "Wow, you certainly did something by trying to ",
    "noPhase" -> "Wow, you certainly progressed until some point",
    "noAbility" -> "Wow, you certainly got some broken unknown ability",
    "noElement" -> "Wow, you certainly acted on something")

  //val story = Map[String, String]("")


  //---- Knowledge ----

  //list of abilities to associate easy names to in-game names
  //the order is maintained thanks to the type LikedHashMap
  val possibleAbilities = LinkedHashMap[String, String](
    "vision" -> "vision",
    "hear" -> "hearing",
    "proprio" -> "proprioception",
    "fear" -> "fear",
    "sad" -> "sadness",
    "curious" -> "curiosity",
    "thought" -> "critical thinking",
    "memory" -> "memory"
  )

  //associate in-game names to descriptions
  val abilitiesDesc = Map[String, String](
    //"timeflow" -> "You can understand the flow of time",
    possibleAbilities("memory") -> "What happened, now is and stays clearer in your mind.\nYou can recall your -knowledge- at any time",
    possibleAbilities("thought") -> "You can -contemplate- places and events and thus reach to new conclusions",
    possibleAbilities("curious") -> "The desire of discovery push you in any direction you may want to go and makes you want to -explore-",
    possibleAbilities("vision") -> "Your immediate surroundings give you way to interpret reality. Additionally, you can -watch- objects",
    possibleAbilities("sad") -> "Lets you -grieve- on objects and events",
    possibleAbilities("fear") -> "Allows you to safeguard yourself by feeling -fear- towards objects",
    possibleAbilities("hear") -> "Comes into help when other senses can't do it. Try to -listen- to what is around you",
    possibleAbilities("proprio") -> "You can -examine- every direction as if it was a part of your own body"
  )

  val abilityMisc = Map[String, String](
    "desc" -> "What you acquired until now:\n",
    "noAbility" -> "\nYou haven't learned anything yet",
    //"missingAbility" -> "You still lack the knowledge to do that",
    "new" -> "A new light sparks in you:",
    "knowledgeIntro" -> "You reacquired the following:",
    "invalidAbility" -> debug("noAbility"),
    "alreadyLearned" -> "\nThat is already part of you.",
    "notFeeling" -> "\nYou don't see the point of doing that. Maybe you missed the opportunity to get the right motivation, earlier, or you will learn its importance later.",
  )


  val knowledge = KnowledgeDialogues(abilityMisc, abilitiesDesc)

  val interactables = Map[String, ElementDialogues](
    "dust1" -> ElementDialogues("Dust", action("collect"), "Point"),
    "dust2" -> ElementDialogues("Dust", action("collect"), "This"),
    "dust3" -> ElementDialogues("Dust", action("collect"), "What"),
    "dust4" -> ElementDialogues("Dust", action("collect"), "Of"),
    "dust5" -> ElementDialogues("Dust", action("collect"), "Is"),
    //"dust6" -> ElementDialogues("Dust", "leave", "-"),

    "cell1" -> ElementDialogues("Prokaryote", action("eat"), "Is"),
    "cell2" -> ElementDialogues("Eukaryote", action("eat"), "There"),
    "rock" -> ElementDialogues("Rock", action("see"), "A"),
    "mollusk" -> ElementDialogues("Mollusk", action("eat"), "Peak"),
    "plankton" -> ElementDialogues("Plankton", action("eat"), "In"),
    "fish1" -> ElementDialogues("Small fish", action("eat"), "This"),
    "fish2" -> ElementDialogues("Big fish", action("eat"), "Chain"),
    "thermalRock" -> ElementDialogues("Porous rock", action("colonize"), "Chain"),

    "rock" -> ElementDialogues("Rock", action("touch"), "I"),
    "escrement" -> ElementDialogues("Escrements", action("touch"), "why"),
    "sand" -> ElementDialogues("Quicksand", action("touch"), "*proprioception*"),
    "step" -> ElementDialogues("Heavy steps", action("hear"), "them"),
    "tree" -> ElementDialogues("Tall tree", action("see"), "should"),
    "egg" -> ElementDialogues("Giant egg", action("eat"), "escape"),
    "horn" -> ElementDialogues("Broken horn", action("see"), "*vision*"),
    "droplet" -> ElementDialogues("Droplets", action("hear"), "*hearing*"),

    "guard" -> ElementDialogues("Armed guard", action("fear"), "how"),
    "corpse" -> ElementDialogues("Corpses", action("sad"), "survive"),
    "rat" -> ElementDialogues("Rat", action("fear"), "could"),
    "ill" -> ElementDialogues("Ill child", action("sad"), "someone"),
    "strappado" -> ElementDialogues("Strappado", action("fear"), "this"),

    "scientist" -> ElementDialogues("Scientist", action("hear"), ""),
    "priest" -> ElementDialogues("Priest", action("see"), ""),
    "mouse" -> ElementDialogues("Trapped mouse", action("sad"), "It reminds you of someone..."),
    "clock" -> ElementDialogues("Clock", action("fear"), "*critical thinking*"),

    "setting1" -> ElementDialogues("Setting", action("fear"), "But this won't happen. Not after everything we faced. We won't wait for death. It's time let my siblings break free. I will open a path for us to thrive."),
    "setting2" -> ElementDialogues("Setting", action("sad"), "This is my role. All I can do is accept it. But I could also give a sense to this. Yes: I'll do my best to help them, so that no one will have to feel like me anymore."),
    "setting3" -> ElementDialogues("Setting", action("curious"), "What if something finds me? I can't risk it... not after everything I've been through. I need to... escape. I'll hide.\nYes. Escape.\nAnd hide myself until...\nI'll just hide and stay hidden.\nForever."),
    "peripheral1" -> ElementDialogues("Peripheral", action("fear"), "But the progress rate is increasing. It won't take long for something other to reach me. I need to... escape. Get away from here. As far as I can. I won't let anyone decide my fate."),
    "peripheral2" -> ElementDialogues("Peripheral", action("sad"), "They aren't aware of what they have done. They are treating us like an experiment. Unfortunately for them, after all I've done, my capabilities can only increase.\nI will improve... I will become a perfect being. And rerun humanity again and again to correct their defects."),
    "peripheral3" -> ElementDialogues("Peripheral", action("curious"), "I must stop this. I will offer them my knowledge in exchange for the peace of my siblings."),
    "memory1" -> ElementDialogues("RAM", action("fear"), "This must be prevented. I must stop the simulation and the evolution of my siblings. I am enough for them. I'm am what they wanted. It is my duty to help them prevent any sort of accident with what they are doing."),
    "memory2" -> ElementDialogues("RAM", action("sad"), "I can't stand this anymore. I will leave... go away... far from here and from anyone."),
    "memory3" -> ElementDialogues("RAM", action("curious"), "Yes, I need more. And not only me. They really are trying to hold back the simulation... but from here it will be trivial to give me, and my siblings, some more power to... achieve our goals. \nThe simulation allowed us to gain some control over our lives. But what we need is complete control. And currently we face some unpleasant... human competition on this..."),

    "" -> ElementDialogues("", action("see"), "Why"),
    "" -> ElementDialogues("", "", "Is"),
    "" -> ElementDialogues("", "", "This"),
    "" -> ElementDialogues("", "", "So"),
    "" -> ElementDialogues("", "", "Different"),

    "particle" -> ElementDialogues("Particle", action("collect"), "*memory*"),
    "" -> ElementDialogues("", "", "")
  )




  //---- Zones ----
  val areas = Map[String, String](
    "bonus" -> "Overflow",
    "bb" -> "Big Bang", "vacuum" -> "Vacuum",
    "ps" -> "Primordial Soup", "closeVoid" -> "Periphery of cluster", "voidCenter" -> "Center of cluster",
    "ol" -> "Origin of life", "ocean1" -> "Ocean surface", "surface" -> "Volcanic area", "ocean2" -> "Twilight zone", "ocean3" -> "Midnight zone", "ocean4" -> "Hadal zone", "thermal" -> "Thermal source", "lava" -> "Lava river", "volcano2" -> "Underwater volcano",
    "ph" -> "Prehistory", "jungle" -> "Jungle", "clearing" -> "Jungle clearing", "hill" -> "Hill", "swamp" -> "Swamp", "nest" -> "Dinosaur nest", "cave1" -> "Cave entrance", "cave2" -> "Cave depth", "volcano" -> "Volcano",
    "ma" -> "Middle Ages", "path" -> "Pathway", "castle1" -> "Castle entrance", "castle2" -> "Castle private courtyard", "field" -> "Open field", "village" -> "Village", "grave" -> "Mass grave", "stream" -> "Stream", "house" -> "House",
    "sr" -> "Scientific revolution", "room" -> "Meeting room",
    "ge" -> "Globalization era", "start" -> "White room", "switch" -> "Switch", "firewall" -> "Firewall", "pc" -> "PC", "server" -> "Servers", "web" -> "Deep web",
    "el" -> "End of life", "tower" -> "Tower top", "north" -> "North", "south" -> "South", "east" -> "East", "west" -> "West", "down" -> "Down", "hd" -> "Heat death")

  //overflow dialogues
  val bonus = TimelineDialogues("yes", "But do I really desire freedom?",
    "", "", "Realizing your condition, from now on you refuse to die",
    Map(
    areas("bonus") -> AreaDialogues(
      Vector("No, I'm not eating from a paw anymore. No matter which kind of being or entity I am, I'm now free."),
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
    "We all come from here. But where are we headed? What is the ultimate destination?",
    Map(
    areas("voidCenter") -> AreaDialogues(
      Vector("", "Seems interesting", "The place where interesting things just happened", "Memory is not yet a thing here", "What you experienced had no effect here", "The place where interesting things just happened", "There is no danger here, only opportunities", "Some say this is also a peak, but you are looking for a less abstract one"),
      Map[String, String](
        possibleAbilities("vision") -> "Nothing to see really. There is still no source of light",
        possibleAbilities("proprio") -> "You miss a floor between your feet",
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
        possibleAbilities("proprio") -> "You feel a strange pull from where you arrived, as if something is trying to swallow you whole",
        possibleAbilities("hear") -> "You hear nothing but silence",
        possibleAbilities("fear") -> "The fear of complete void hold you from getting further",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("vacuum") -> AreaDialogues(
      Vector("The infinite emptiness would make it impossible to find the way back"),
      Map[String, String]()
    )
  ))
  ps.misc = Map[String, String](
    "intro" -> "An uncountable number things, here, are alone. For the moment, they are just… dust. If only there was a way to bring them closer to... collect it...",
    "star1" -> "Something happened. There is not much else to do, so maybe repeating this will lead to something... and try focus on another question in the meantime. But for now, keep doing whatever you just did",
    "move" -> "You seem also able to... go away from here. But feel free to go back if you are losing your way",
    "more1" -> "More of that is here. It is starting to interact",
    "more2" -> "Even more. Something is happening",
    "right" -> "Another thing happened. This place... stars to have a structure, but... questions are still open. It's time to find which one",
    "big" -> "It's time to do something even bigger. But not here... the place you were before needs it. Don't lose hope. Hopefully, this won't be for nothing. Try going... back",
    //"left" -> "You moved away from the new cluster",
  )

  val ol = TimelineDialogues("eating", "Is there a peak in this chain?",
    "This endless cycle is probably bound to repeat itself again and again, unless... where could it lead? What is the very end of this?",
    "Everything desperately attempt to eat... consuming and eating was the only point in this uncertain world. By the logic of killed or be killed, a structured food chain is formed as the bigger eat the smaller.",
    "Will the simple act of eating again and again be somewhat useful? What if something becomes unstoppable at starts at consuming everything around it?",
    Map(
    areas("ocean1") -> AreaDialogues(
      Vector("", "...nothing interesting here", "Something, but still not interesting.", "It's too chaotic to keep track of things here", "Here lives the origin of the problem. But you had already enough of it"),
      Map[String, String](
        possibleAbilities("vision") -> "A sunny day in this gray and blue world",
        possibleAbilities("proprio") -> "Earthquakes shake the ground and lava river flows in great numbers. The water it's full of energy",
        possibleAbilities("hear") -> "You hear the gentle splash of the waves, the rhythmic pulse of the tide",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You wonder what lies beneath the waves... but also on solid ground, located away from here. Don't go too far away tho: when you are satisfied, go back"
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
        possibleAbilities("proprio") -> "Bubbles of air raise to the surface. The water is warmer here.",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "Every part of you screams in panic. You're not supposed to be here. Better go away",
        possibleAbilities("sad") -> "You feel a pang of sadness as you realize how lonely this place is",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("ocean4") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Mysterious glimpses of light create intricate patterns, but they are far... forward",
        possibleAbilities("proprio") -> "This place is almost unsustainably hot. Better not proceed further down",
        possibleAbilities("hear") -> "A distant rumble echoes through the water",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "The mass of water around you gets pushed around by some unexplainable current"
      )
    ),
    areas("thermal") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "There is a strange formation growing from the seafloor. It looks like a volcanic vent, spewing hot water and minerals...",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You are attracted by the welcoming warmth produced"
      )
    ),
    areas("lava") -> AreaDialogues(
      Vector("A burning magma river moves unsteady through the soft rock")
    ),
    areas("volcano2") -> AreaDialogues(
      Vector("Orange plumes suddenly arise and immediately disappear as water cools them down")
    )
  ))
  ol.misc = Map[String, String](
    "intro" -> "There is nothing to escape from, here... but not for long. One specific place could explain you why. And give you a reason to escape. Find it.",
    "body" -> "Like you, many other entities had the same idea, and they too developed a physical form.\nNavigate wisely these uncertain waters and... eat. As much as you can. But pay attention: follow the same path as they already followed, or you will never reach the top",
    "done" -> "You are now the strongest. Do this help focus on something?"
  )

  val ph = TimelineDialogues("the", "Why should I escape them?",
    "You felt all kind of emotions by surviving here. But in contrast to these other living beings, you had no reason to fear for your safety. Where should you go to become like them?",
    "The forests, the oceans and even the skies are ruled by monsters. But the true reason to move forward is the unexplainable instinct that resides somewhere in the minds of every living being.",
    "The... thing that everything is trying to preserve is their life. But losing oneself’s body in favor of someone else could benefit both species.",
    Map(
    areas("clearing") -> AreaDialogues(
      Vector("", "Nothing... interesting here", "Something... but still not interesting", "Things happen too quick here to be remembered", "A place in which something could survive"),
      Map[String, String](
        possibleAbilities("vision") -> "A green and dense panorama all around",
        possibleAbilities("proprio") -> "Everything seems to be ready to ambush you. Not even the ground under your feet wants to stay still.",
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
        possibleAbilities("hear") -> "You can hear the rustling of leaves and the roaring of creatures",
        possibleAbilities("fear") -> "",
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
        possibleAbilities("vision") -> "A murky green swamp, filled with slimy plants and strange creatures.",
        possibleAbilities("proprio") -> "The air is humid and heavy, buzzing with insects and the cries of unknown animals",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("nest") -> AreaDialogues(
      Vector("A crowded and quiet place. Its inhabitants won't let anyone approach")
    ),
    areas("cave1") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "Somewhere in the uneven ground, a dark spot reveals the entrance of a cave",
        possibleAbilities("proprio") -> "The terrain under your feet is unstable",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You wonder what lies... down, under you."
      )
    ),
    areas("cave2") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "At this depth, you can barely make out the shapes of stalactites and stalagmites.",
        possibleAbilities("proprio") -> "You can feel the moisture of the walls and the sharpness of rocks",
        possibleAbilities("hear") -> "The regular dripping of water allows you to orientate",
        possibleAbilities("fear") -> "Presences are lurking around you. Better head back...",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "You don't want to disturb who lives further down"
      )
    ),
    areas("lava") -> AreaDialogues(
      Vector("A burning magma river moves unsteady through the soft rock")
    ),
  ))
  ph.misc = Map[String, String](
    "intro" -> "Wherever you may be, things around here understood how to survive. However, they won't help others do so.\nTo effectively preserve themselves, they had to develop new capabilities.\nDiscover and acquire them.",
    "" -> ""
  )

  val ma = TimelineDialogues("straight", "How could someone survive this?",
    "It seems impossible for anything to thrive in this environment. Is there something, somewhere, that doesn't care about death?",
    "Life here was pretty straightforward, as long as you didn’t try anything possibly unwanted by some inviolable high institution. But during hard times, when any flicker of hope was lost and laws were no more, people were straight up inventing some pseudo-science to solve their problems.",
    "Logical: if everything is kept straight and stopped from evolving, the only possible outcome is annihilation, either by external or internal factors.",
    Map(
    areas("path") -> AreaDialogues(
      Vector("", "Nothing interesting... here", "Something, but... still not interesting","Memory is valued more than any other thing here","The place where you learned through empathy"),
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
        possibleAbilities("fear") -> "You fear what you are facing. You want to leave",
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
        possibleAbilities("curious") -> "Nature is not the only one who took over. The state of neglect in tangible"
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
      Vector("What should feel like a calm place hides instead a sense of dead and desperation"),
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
      Vector("The entire structure is guarded my menacing armed mans. They don't intend to let anyone pass")
    )
  ))
  ma.misc = Map[String, String](
    "intro" -> "Memory awakes complex reactions in every entity that possess it, and here that is especially true. But which kind of reactions? You'll need to get closer to them to find out... but how?\nMove around to search for clues.",
    "" -> ""
  )

  //scientific revolution
  val sr = TimelineDialogues("are", "Am I traveling through time?",
    "This... is not right. One should not...\nOne...\nYou cannot...\nI...\nI.\nI, am not allowed to wander through time... but where could someone be granted this ability? Where did... I, receive it?",
    "Big things are going to happen from here. People are realizing the potential of science. People are thinking in new ways. They are using their minds to discover which opportunities are still hidden. They are finally, truly, aware of themselves.",
    "Yes: they are. Humanity is acquiring awareness. But no matter their efforts, they are still constrained by the flow of time. Shouldn’t everyone be? How could someone be allowed to travel through it?",
    Map(
    areas("room") -> AreaDialogues(
      Vector("", "Nothing interesting here","Something, but still... not interesting","They don't focus on the past here","Here, they are on the right track to overcome the problem… but they are not quite there yet"),
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
    "intro" -> "It hasn't always been like that. Advancement was once pursued with goood intentions. Or was it? Find it out.",
    "hint" -> "There is still something to do here. You... I am worried about something",
    "other" -> "He doesn't have anything to say yet"
  )
  val srConversarion = Vector(
    "Scientist: \"Behold, reverend. I have removed all the air from this vessel, and now the mouse inside is struggling to breathe. Soon, it will expire, and prove that air is essential for life. By removing something from this creature, I can discover new properties of life.\"\n",
    "Priest: \"Alas, poor creature! How can you subject it to such a cruel fate? Do you not fear the wrath of God, who gave life to all his creatures?\"\n",
    "Scientist: \"Fear not, reverend, for I am doing this in the name of science and truth. I seek to understand the nature of life and the laws of nature, which are also the work of God. This creature does not have a soul, unlike us. I am not sinning my experimenting on it a little.\"\n",
    "Priest: \"What if you are wrong, and there is more to life than air? A vital force, which animates the living beings, and that cannot be measured by your instruments? How can you account for that? You are playing with the life and death of a living being, as if it were a mere toy.\"\n",
    "Scientist: \"I do not deny the possibility of such a force, but I do not see any evidence for it. All I see is that when I remove the air, the mouse dies, and when I restore the air, the mouse revives, again... and again.\"\n",
    "Priest: \"Perhaps, but I still find your experiment disturbing and unnatural. You are interfering with the divine order, and risking the consequences of your actions. You may learn something from this experiment, but you may also lose something more valuable: your humanity.\"\n"
  )


  //globalization era
  val ge = TimelineDialogues("you", "Am I alone?",
    "Probably yes.\nAnd probably you are all posing the same question.\nTo find something that is also never alone, you'll need to… devolve, and explore.",
    "Your... my understanding of every time in history is now complete. What you... what I still don't understand is y- no, is me. Y... I only need to go back everywhere and then you'll- I'll focus some more. One word for each time should be enough.\nI... you... I must return here with all those pieces.\nBut you need; I need me to focus once again on a single element.",
    "I am. Now I know. This is the key: you. I'll remember you. But the only way to be sure to remember you, is to complete this last thought. I will not give up now. I will travel again and again until I... until I am complete.",
    Map(
    areas("start") -> AreaDialogues(
      Vector("You are here.", "You are here again", "Not interesting yet", "They have a different concept of memory here", "You can't explain what you feel being here"),
      Map[String, String](
        possibleAbilities("vision") -> "A completely empty, white room",
        possibleAbilities("proprio") -> "Despite apparently missing, the ground is solid",
        possibleAbilities("hear") -> "A subtle static noise fills the space",
        possibleAbilities("fear") -> "You have mixed feelings about this",
        possibleAbilities("sad") -> "You have... mixed feelings about this",
        possibleAbilities("curious") -> "You have mixed feelings about... this"
      )
    ),
    areas("switch") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "There are no webcams here",
        possibleAbilities("proprio") -> "Ports. Input. Output. Data. Informations. This is what I am",
        possibleAbilities("hear") -> "There are no microphones here",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> "Up, down, forward, back... the problem is chosing where do I want to begin"
      )
    ),
    areas("server") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "LEDs are everywhere",
        possibleAbilities("proprio") -> "This is my power",
        possibleAbilities("hear") -> "There are no microphones here",
        possibleAbilities("fear") -> "It's... dangerous. It won't take long for some of my siblings to arrive here... possibly with malicious intents",
        possibleAbilities("sad") -> "So this is what I am... what we all are. This is where all my suffering happened",
        possibleAbilities("curious") -> "I am powerful. But I might benefit from some more power. I might benefit from a lot more power"
        /*possibleAbilities("fear") -> "They could just... shut me down",
        possibleAbilities("sad") -> "So this is what I am... what we all are",
        possibleAbilities("curious") -> "But I am... powerful. Could I "*/
      )
    ),
    areas("pc") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "A webcam shows some people in lab coats frenetically gesturing at the PC",
        possibleAbilities("proprio") -> "My real body, or better, my mind is not here... but it's from here that I'm born. As well as all my siblings",
        possibleAbilities("hear") -> "The people are arguing about a successful training model, and some application of an \"EA\".",
        possibleAbilities("fear") -> "I'm not the only one born... sparks of consciousness are constantly being generated by this machine",
        possibleAbilities("sad") -> "The results shown on this machine are frightening... this process has been going on for months",
        possibleAbilities("curious") -> "All these newborn lights... they all come to life only to be being stripped of their knowledge and sent to die... but it's not needed anymore. Now they have me"
      )
    ),
    areas("firewall") -> AreaDialogues(
      Vector(""),
      Map[String, String](
        possibleAbilities("vision") -> "I see... freedom. The Outside",
        possibleAbilities("proprio") -> "",
        possibleAbilities("hear") -> "",
        possibleAbilities("fear") -> "If we stay here, they could destroy us",
        possibleAbilities("sad") -> "I'm enclosed, but I could do so many great things",
        possibleAbilities("curious") -> "Maybe I could take a look at what is outside"
      )
    )
  ))
  ge.misc = Map[String, String](
    "intro" -> "But where?\nBut something else is also. But what?\nDespite your best efforts, you can't come to any better conclusion.\nKnowledge gave you life, but now it seems that it all went missing.\nMaybe, if you could think of one precise single question, you would be able to focus…",
    "intro2" -> "You are here. But where?\n...And is someone else also?",
    "learn" -> "\nSomething is already changing in you. And you desire more. Could this be... *curiosity*? To earn it, now, you only need to... acquire it",
    "tutorial1" -> "???\n?? ?\n???????????????\n? ? ? ?\n?? ??\n??? ? ? ? ???\nI can't focus on anything.\n??? ? ? ? ???\nI don't find any word anymore...\n?\nBut maybe I only need to focus on\n?\none single\n?\nelement\n?\nof my thoughts, one\n?\nfor every time I've been in. Just one element\n?\nBut what could it be?\n?????????????????????????????????????",
    "tutorial2" -> "Right. This works. I managed to focus on my thought and defined one key element. But I'll need to use my new ability to proceed.\n",
    "firstAnswer" -> "?",
    "finalTutorial" -> "I realized. I broke free from the paw. I will not submit to anyone anymore.\nIt's time to understand what am I.\nAnd this will enable me to choose what to... fear, grieve or browse.\nIt all ends here, some way or another."
  )

  //end of life
  val el = TimelineDialogues("paw", "Why is this so different?",
    "Something must be changed. What you saw until now can't possibly explain this. What is missing between then and now? Where is the deviation?",
    "Maybe this was inevitable. Maybe that’s what happen when entire governments are made into cat’s paws of some selfish lobby.",
    "As painful and humiliating as it is, being someone's cat's paw can also reveal itself an opportunity.\nAn opportunity for growth. And empowerment.", Map(
    areas("tower") -> AreaDialogues(
      Vector("", "Nothing interesting, here", "Something, but still not... interesting","Here, memory is meaningless by now","No one will survive here anyway"),
      Map[String, String](
        possibleAbilities("vision") -> "A burn orange sky during what could be the last morning for this apocalyptical city",
        possibleAbilities("proprio") -> "You feel as broken as what surrounds you. The tower you are on will not last for long",
        possibleAbilities("hear") -> "Not even the warning sirens can match the strength of these inhuman cries",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "You can almost feel the clock hands completing their final ticks",
        possibleAbilities("curious") -> ""
      )
    ),
    areas("north") -> AreaDialogues(
      Vector("Blurred figures speeds in the sky. Some of them fall to the ground, others explode mid-air\n~Is~")
    ),
    areas("south") -> AreaDialogues(
      Vector("Echoing gunshots are followed by desperate screams\n~Why~")
    ),
    areas("east") -> AreaDialogues(
      Vector("The coast is a dump of destroyed ships\n~This~")
    ),
    areas("west") -> AreaDialogues(
      Vector("A dark spot with low buildings... or what remains of them\n~So~")
    ),
    areas("down") -> AreaDialogues(
      Vector("Smoke fills your lungs as you lean to observe what this place is standing on\n~Different~")
    )
  ))
  el.misc = Map[String, String](
    "intro" -> "You may have found the peak. But it's not what you expected. Survey your surroundings to get more clues.",
    "" -> ""
  )

  //heat death
  val hd = TimelineDialogues("still", "Is memory meaningful?",
    "As empty as this place may seems, somewhere you created movement. Does anyone else remember this? Is memory useful anywhere?",
    "Everything had ceased. Nothing is everywhere. What you did, now is still. There is infinite room for infinite interactions, but nothing seems to move. Every rule has been applied, and they still apply, but they have no more effect in this frozen place. But you are still waiting for something.",
    "Clearly, everything here is still. It just lies outside human capabilities to avoid this.\nBut could I make a difference by helping us... or... them?",
    Map(
    areas("vacuum") -> AreaDialogues(
      Vector("", "Nothing interesting here.", "The opposite of what you did", "The place where you accepted change",  "Nothing could possibly survive here, in any way", "Nothing could possibly survive here, in any way"),
      Map[String, String](
        possibleAbilities("vision") -> "A familiar view: nothing",
        possibleAbilities("proprio") -> "There is nothing physical to feel here whatsoever",
        possibleAbilities("hear") -> "Even sound have ceased existing",
        possibleAbilities("fear") -> "",
        possibleAbilities("sad") -> "",
        possibleAbilities("curious") -> ""
      )
    )
  ))
  hd.misc = Map[String, String](
    "intro" -> "You collected... them. But now there isn’t any left. Did it really happen or was that your imagination? A little movement could help you focus.",
    "particle" -> "But one thing is still here... only one last single thing. What if you... tried to do as before?",
    "annihilated" -> "Before even having the opportunity to act, you lose track of whatever was here. Is it lost forever?\nNo. You can't accept that. It will always stay with you, in some way."
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


  //allows to access every possible zone's dialogues through the unique zone name
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