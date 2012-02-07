                                Readme
                                ======

Author: Jonathan Arkell
Date: 2012-02-06 20:18:59 MST


Table of Contents
=================
1 Bunny Doors 
    1.1 Features
2 Requirements 
    2.1 Protection Plugins
    2.2 Vault:  http://dev.bukkit.org/server-mods/vault/
    2.3 Spout: http://dev.bukkit.org/server-mods/spout/
3 Open source!
4 Changelog


1 Bunny Doors 
--------------

  This plugin lets you lock specific doors on your server, with a particular key.  Only users with that key can open the door.  

  - *Quick Start* : [https://github.com/jonnay/BunnyDoors/wiki/Quickstart]
  - *Documentation* : [https://github.com/jonnay/BunnyDoors/wiki/]
  - *Source Code* : [https://github.com/jonnay/BunnyDoors/]

  There are plenty of plugins that give you specific custom permissions for doors but this plugin is different.  The point of this plugin is
  to emulate old-school JRPG doors (Think Dragon Quest).  It aims to be easy for you to create doors that require keys.  I've been wanting
  doors-with-permissions for a long time now, and decided to roll my own.

  Make Citizens NPCs give your players special keys for completing quests!  Give your theif Hero class the ability to pick locks!   Give
  VIPs special access! So many possibilities!

1.1 Features
============
   - Create an unlimited number of doors, and an unlimited number of keys to unlock them!
   - Use with any permissions system.
   - Simple Spout integration, with more coming!
   - Keys are implemented as permissions, so you can use it with a variety of other plugins to enhance your experience.

2 Requirements 
---------------
  
  Bunny Doors will work, as is, out of the box, without any other plugin.  It is HIGHLY recommended that you have a region protection plugin
  like Worldguard.  (If you know of any other region protection plugins, then I'd like to hear about them, so I can update this document)

  The only requirements around a protection plugin, is they must block a user from placing or removing blocks in the area where your door is.

  Beyond the protection plugin of your choice, if you have Spout or Vault installed, then BunnyDoors will give you extra functionality.
  It's called progressive enhancement baby, and it rules. 

2.1 Protection Plugins
======================
  WorldGuard: [http://dev.bukkit.org/server-mods/worldguard/]  

2.2 Vault:  [http://dev.bukkit.org/server-mods/vault/]
======================================================

   If you have vault installed, you will be able to use the ~/bunnykey give~ command, which will let you give keys to users. If you want
   your users to be able to take keys from chests, you will also need Vault for this.

2.3 Spout: [http://dev.bukkit.org/server-mods/spout/]
=====================================================

   If you have spout installed, and your players are using spoutcraft, then when they open the inventory a little message pops up telling
   the user what keys they currently have.


3 Open source!
--------------

   I am leaning plugin development by looking at other peoples source, and basically standing upon the shoulders of bukkit giants. So NOT
   making this plugin open source would be insane, and kinda lame.

   Source code:  [https://github.com/jonnay/BunnyDoors]

   Instead of large grand plans, I have a set of very small discreet releases planned.  If anyone wants to help, they are more then welcome
   to fork the repo, and I will pull the changes (assuming they work).  Please feel free down download an contribute.  

   finally, there is a better then even chance that at some point, I will stop developing this plugin (due to time constraints, hatred of
   writing java, or a case of hey-look-at-that-shiny-thing-over-there).  When that inevitability happens, I want to make sure that you, as
   a server admin, can update the plugin for whatever new version of Bukkit comes out.  It also means 
   

4 Changelog
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
   0.5:
     - Added very basic spout support through the notification interface.  Note that this is only a "soft dependency".  You don't need spout
       to make it go! 


