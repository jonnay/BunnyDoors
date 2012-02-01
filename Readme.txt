                                Readme
                                ======

Author: Jonathan Arkell
Date: 2012-01-31 23:48:03 MST


Table of Contents
=================
1 Bunny Doors 
    1.1 Usage
        1.1.1 Caveats
    1.2 Open source!
    1.3 It's only the beginning.
    1.4 Installation
    1.5 Assumptions
    1.6 Roadmap:
    1.7 Changelog
    1.8 Development


1 Bunny Doors 
--------------
   This plugin is my first ever (not-modifying-someone-elses) plugin.  It allows you to make custom keys, and lock doors so only people who
   hold the key can get in.

   There are plenty of plugins that give you specific custom permissions for doors (well kinda), but this plugin is different.  The point of
   this plugin is to emulate keys for doors.  So it aims to be easy for you to create doors that require keys. I've been wanting
   doors-with-permissions for long.  There is a plugin that supposedly allows keys and doors, and SOUNDS awesome, but the developers are
   busy and wont release it.  So it is time to write my own!

   So say you wanted to make a standard RPG-esque door that is only open-able by a key, you would set a door with a permission like
   doors.key.ironkey.  To give a player an iron key, you would grant them the permission doors.key.ironkey.

   With a plugin like citizens, you could grant them that permission after completing a quest!

   While I am not the worlds greatest Java developer, I have been writing code in some form or another for decades.  So hopefully it won't suck!

1.1 Usage
=========
   
   1. Figure out the name of your keys, and add them to the config file
   2. Protect an area with the protection plugin of your choice.  (Mine is Worldguard).  Make sure you allow people to use the doors, but
      don't allow block breaks, or block places. 
   3. To Lock a door, look at the door (currently top-half only due to a bug) and type /bunnydoors lock <keyname>
   4. Profit!  Now only users with the bunnydoors.key.<keyname> can open the door.

   You will want to give your players the permission bunnydoors.keycmd.list so they can see what keys they currently own

   You can use a plugin like citizens, where the player can complete a quest to get the apropriate key permission.

1.1.1 Caveats
~~~~~~~~~~~~~

    This plugin DOESN'T protect against power opening the door.  This is great because it allows for one-way locked doors.  But It also
    means you need to make sure people can't place redstone torches in front of your locked door.

    This plugin DOESN'T protect against block-break events.  Think about it.. if the door is set in an area without block break proteciton,
    why do you even need it in the first place?

1.2 Open source!
================

   I am leaning plugin development by looking at other peoples source, and basically standing upon the shoulders of bukkit giants. So NOT
   making this plugin open source would be insane, and kinda lame.

   Source code, such as it is:  [https://github.com/jonnay/BunnyDoors]

1.3 It's only the beginning.
============================
 
   Instead of large grand plans, I have a set of very small discreet releases planned.  If anyone wants to help, they are more then welcome
   to fork the repo, and I will pull the changes (assuming they work).  Please feel free down download an contribute.  

1.4 Installation
================

   Easy, put the plugin in /plugins.  Customize the name of the keys inside of the keys node of plugins/BunnyDoors/config.yml

1.5 Assumptions
===============
   There are 2 big assumptions right now:

   1. You don't have lots and lots of doors
   2. You don't have lots and lots of players opening doors.

   If you want to run this plugin on a server that flies in the face of either assumption, I'd like to hear about it!  Maybe I can optimize
   the code to not suck. 

   For lots of doors being placed, the optimization scheme is to move the door serialization into a DB and out of memory.
   For lots of players opening doors, the optimization scheme is to keep a map of doors in memory. 

   

1.6 Roadmap:
============
   v0.4: 
     - admins can give keys to users manually 
     - Let players grant specific keys with a bunnydoor.keymaster.<key> permission
     - Keep a queue of most recently used doors in memory (to avoid costly db lookups?)
       - when a door is modified/created it should throw event
     - Explain to user difference between yaml door persistence (stored in memory) and DB persistence (not in memory, but takes CPU to
       retrieve)


   Future Versions:
     - Custom lock messages!
     - Non-indentifiable locks! ("This door is locked" vs "This door is locked, you need the iron key.")
     - Lock chests with keys!
     - Allow opening of iron doors with use if the key is held!
     - ~/bunnydoor list~ to list keys!
     - optional spoutcraft integration, to show keys in the inventory screen on the side!
     - selectable persistence!  Yaml, sqlite or MySQL!
     - herocraft lockpicking skill!

1.7 Changelog
=============
   0.1: First version
   0.2: 
     - Added Serialization scheme
     - added config file, with ability to customize keys
     - added /bunnydoor lock command
     - added /bunnydoor unlock command
     - added /bunnydoor reload command
   0.3:
     - added /bunnykey command (with list, listall and add subcommands)
     - locked doors close after 10 seconds.
     - debugging is turned off (Sorry about the spam)

1.8 Development
===============
   The repository is in git.  Go Nuts!  My to-do items and projects are inside of a text-file called Dev.org.  Pick one and go with it!  Or
   do something else!  If you use Emacs, it is even easier to edit that file.  If you do plan on working on something, shoot me a note so we
   don't invent eachohters wheel.  (that sounds dirty).

