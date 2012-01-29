
//http://wiki.bukkit.org/Introduction_to_the_New_Event_System

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class BunnyDoorModifiedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String message;
 
    public BunnyDoorModifiedEvent(String example) {
        message = example;
    }
 
    public String getMessage() {
        return message;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
