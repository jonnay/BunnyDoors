                                Readme
                                ======

Author: Jonathan Arkell
Date: 2012-02-01 22:55:40 MST


Table of Contents
=================
1 Bunny Doors 
2 Open source!
3 Changelog


1 Bunny Doors 
--------------

  - *Quick Start* : [https://github.com/jonnay/BunnyDoors/wiki/Quickstart]
  - *Documentation* : [https://github.com/jonnay/BunnyDoors/wiki/]
  - *Source Code* : [https://github.com/jonnay/BunnyDoors/]

  This plugin lets you lock specific doors on your server, with a particular key.  Only users with that key can open the door.  

  There are plenty of plugins that give you specific custom permissions for doors but this plugin is different.  The point of this plugin is
  to emulate old-school JRPG doors (Think Dragon Quest).  It aims to be easy for you to create doors that require keys.  I've been wanting
  doors-with-permissions for a long time now, and decided to roll my own.
  

2 Open source!
--------------

   I am leaning plugin development by looking at other peoples source, and basically standing upon the shoulders of bukkit giants. So NOT
   making this plugin open source would be insane, and kinda lame.

   Source code:  [https://github.com/jonnay/BunnyDoors]

   Instead of large grand plans, I have a set of very small discreet releases planned.  If anyone wants to help, they are more then welcome
   to fork the repo, and I will pull the changes (assuming they work).  Please feel free down download an contribute.  
   

3 Changelog
-----------
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
   0.4:
     - fixed /bunnykey permissions issue
     - implemented /bunnykey give command
     - added ability to turn off devilstats in the config. (this should have been in there earlier. Sorry)
     - Fixed a door bug where the bottom half of the door gets copied into the top
     - Door info now displays more info about doors (i.e. who locked it)  It's prettier too.


