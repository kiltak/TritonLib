package model.sound;

public class Location {
    public Location (int bank, int offset) {
        _bank = bank;
        _offset = offset;
    }
    
    public int getBank ()   { return _bank; }
    public int getOffset () { return _offset; }
    
    public String toString () {
        return "Location: " + Bank.BANK_NAMES[_bank] + "-" + _offset;
    }
    
    private final int _bank; // 0 - 10 ???
    private final int _offset; // 0 - 127
}
