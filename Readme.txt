                                Readme
                                ======

Author: Jonathan Arkell
Date: 2012-01-29 12:17:56 MST


Table of Contents
=================
1 Jonnays First Ever Plugin Foray!
    1.1 Yay!
    1.2 Open source!
    1.3 It's only the beginning.
    1.4 Commands
    1.5 Configuration
    1.6 Permissions
    1.7 No Wife.  No Horse.  No Mustache.
    1.8 Roadmap:
    1.9 Development


1 Jonnays First Ever Plugin Foray!
----------------------------------

1.1 Yay!
========
   This plugin is my first ever (not-modifying-someone-elses) plugin.

   While I am not the worlds greatest Java developer, I have been writing code in some form or another for decades.  So hopefully it won't suck!

   I've been wanting doors-with-permissions for long.  There is a plugin that supposedly allows keys and doors, and... effectively SOUNDS
   awesome, but the developers are busy and wont release it.  So it is time to write my own!

   So here is an open source plugin that adds permissions to specific doors.  So say you wanted to make a standard RPG-esque door that is
   only open-able by a key, you would set a door with a permission like doors.key.ironkey.  To give a player an iron key, you would grant
   them the permission doors.key.ironkey.

   With a plugin like citizens, you could grant them that permission after completing a quest!

1.2 Open source!
================
   I am leaning plugin development by looking at other peoples source, and basically standing upon the shoulders of bukkit giants. So NOT
   making this plugin open source would be insane, and kinda lame.

   Source code, such as it is:  [https://github.com/jonnay/BunnyDoors]

1.3 It's only the beginning.
============================
   No code yet. :(

   Instead of large grand plans, I have a set of very small discreet releases planned.  If anyone wants to help, they are more then welcome
   to fork the repo, and I will pull the changes (assuming they work).  Please feel free down download an contribute.  

1.4 Commands
============

   No Code? No Commands.

1.5 Configuration
=================
   
   No Code? No Config.

1.6 Permissions
===============

   No Code? No Permission.

1.7 No Wife.  No Horse.  No Mustache.
=====================================

1.8 Roadmap:
============
   v0.1:
     - Internal release only!
     - Outputs data to the console when a user uses a door 
       - Some kind of door identifier 
       - location of door (if needed)
     - Set up a command ~/bunnydoor info~ Outputs to the console the door that the user is looking at 
   v0.2: 
     - 3 Basic permission nodes: (With a nod to the Dragon Quest series)  
       - ~door.key.thiefkey~
       - ~door.key.magickey~
       - ~door.key.finalkey~
     - Storage of door properties inside of a yml file 
     - ~/bunnydoor lock <key>~ locks the door with the given key (i.e. thiefkey, magickey, finalkey)
     - Actually prevent users from using a door when they don't have a key, and give them a message.
   v0.3: 
     - Optimization release 
     - Keep a queue of most recently used doors in memory (to avoid costly db lookups?)
       - when a door is modified/created it should throw event
     - Explain to user difference between yaml door persistence (stored in memory) and DB persistence (not in memory, but takes CPU to
       retrieve)
     - Don't try an optimize DB side!  Let DB and server-admin do that!
   Future Versions:
     - Custom lock messages!
     - Non-indentifiable locks! ("This door is locked" vs "This door is locked, you need the iron key.")
     - Add custom permission nodes right within the game!
     - Lock chests with keys!
     - Allow opening of iron doors with use if the key is held!
     - ~/bunnydoor list~ to list keys!
     - optional spoutcraft integration, to show keys in the inventory screen on the side!
     - selectable persistence!  Yaml, sqlite or MySQL!
     - herocraft lockpicking skill!

1.9 Development
===============
   The repository is in git.  Go Nuts!  My to-do items and projects are inside of a text-file called Dev.org.  Pick one and go with it!  Or
   do something else!  If you use Emacs, it is even easier to edit that file.  If you do plan on working on something, shoot me a note so we
   don't invent eachohters wheel.  (that sounds dirty).

