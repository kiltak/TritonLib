/**
 * This is a way to pass sounds with their locations.  I didn't want to put the location with the
 * sound because that just felt wrong.  A location doesn't really care where it is.
 */

package triton.model.sound;

public class SoundWithLocation {
    
    public SoundWithLocation(Sound s, Location l) {
        this.s = s;
        this.l = l;
    }
    
    public Sound getSound () {
        return s;
    }
    
    public Location getLocation () {
        return l;
    }
    
    private final Sound s;
    private final Location l;
}
