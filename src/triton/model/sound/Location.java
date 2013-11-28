/**
 * This is just the location of the sound.  Bank and offset within the bank.
 */

package triton.model.sound;

public class Location {
    public Location (int bank, int offset) {
        _bank = bank;
        _offset = offset;
    }
    
    public boolean equals (Location l) {
        return ((_bank == l.getBank()) && (_offset == l.getOffset()));
    }
    
    public int getBank ()   { return _bank; }
    public int getOffset () { return _offset; }
    
    public String toString () {
        return "Location: " + _bank + "-" + _offset;
    }
    
    private final int _bank; // 0 - 10 ???
    private final int _offset; // 0 - 127
}
