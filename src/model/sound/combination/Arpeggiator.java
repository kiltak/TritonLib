package model.sound.combination;

public class Arpeggiator {
    public int unpack (byte data[], int offset) {
        _pattern = data[offset++];
        
        _octave = data[offset] & 0x03;
        _resolution = (data[offset] & 0x0C) >> 2;
        ++offset;
        
        _gate = data[offset++];
        _velocity = data[offset++];
        _swing = data[offset++];
        
        _sort = data[offset] & 0x01;
        _latch = (data[offset] & 0x02) >> 2;
        _keySync = (data[offset] & 0x04) >> 3;
        _keyboard = (data[offset] & 0x08) >> 4;
        ++offset;
        
        _topKey = data[offset++];
        _bottomKey = data[offset++];
        _topVel = data[offset++];
        _bottomVel = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte) _pattern;
        
        data[offset]  = (byte)_octave;
        data[offset] |= (byte)_resolution << 2;
        ++offset;
        
        data[offset++] = (byte) _gate;
        data[offset++] = (byte) _velocity;
        data[offset++] = (byte) _swing;
        
        data[offset]  = (byte)_sort;
        data[offset] |= (byte)_latch << 2;
        data[offset] |= (byte)_keySync << 3;
        data[offset] |= (byte)_keyboard << 4;
        ++offset;
        
        data[offset++] = (byte)_topKey;
        data[offset++] = (byte)_bottomKey;
        data[offset++] = (byte)_topVel;
        data[offset++] = (byte)_bottomVel;
        
        return offset;
    }
    
    public String toString() {
        String retString = "";
        
        retString += "Pattern    " + _pattern + "\n";
        retString += "Octave     " + _octave + "\n";
        retString += "Resolution " + _resolution + "\n";
        retString += "Gate       " + _gate + "\n";
        retString += "Velocity   " + _velocity + "\n";
        retString += "Swing      " + _swing + "\n";
        retString += "Sort       " + _sort + "\n";
        retString += "Latch      " + _latch + "\n";
        retString += "KeySync    " + _keySync + "\n";
        retString += "Keyboard   " + _keyboard + "\n";
        retString += "Top Key    " + _topKey + "\n";
        retString += "Bottom Key " + _bottomKey + "\n";
        retString += "Top Vel    " + _topVel + "\n";
        retString += "Bottom Vel " + _bottomVel + "\n";
        
        return retString;
    }
    
    private int _pattern;    // byte 0
    private int _octave;     // byte 1, bits 0~~1  valid values 0~~3, transport to 1~~4
    private int _resolution; // byte 1, bits 2~~4   0:16T, 1:16, 2:8T, 3:8, 4:4T, 5:4
    private int _gate;       // byte 2    0x00~~0x64, 0x65:Step
    private int _velocity;   // byte 3   0x01~~0x7F, 0x80:Key, 0x81:Step
    private int _swing;      // byte 4   0x9C~~0x64 = -100~~100
    private int _sort;       // byte 5, bit 0
    private int _latch;      // byte 5, bit 1
    private int _keySync;    // byte 5, bit 2
    private int _keyboard;   // byte 5, bit 3
    private int _topKey;     // byte 6   0x00~~0x7F= C-1~~G9
    private int _bottomKey;  // byte 7   0x00~~0x7F= C-1~~G9
    private int _topVel;     // byte 8   01~~7F: 1~~127
    private int _bottomVel;  // byte 9   01~~7F: 1~~127
    
    public static int BYTE_SIZE = 10;
}
