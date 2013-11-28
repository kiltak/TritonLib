package triton.model.sound.program;

public class Arpeggiator {
    public int unpack (byte data[], int offset) {
        _tempo = data[offset++];
        _switch = data[offset++];
        _patternNo = data[offset++];
        
        _octave = data[offset] & 0x03;
        _resolution = (data[offset] & 0x1C) >> 2;
        ++offset;
        
        _gate = data[offset++];
        _velocity = data[offset++];
        _swing = data[offset++];
        
        _sort = data[offset] & 0x01;
        _latch = (data[offset] & 0x02) >> 1;
        _keySync = (data[offset] & 0x04) >> 2;
        _keyboard = (data[offset] & 0x08) >> 3;
        ++offset;
        
        _topKey = data[offset++];
        _bottomKey = data[offset++];
        _topVelocity = data[offset++];
        _bottomVelocity = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_tempo;
        data[offset++] = (byte)_switch;
        data[offset++] = (byte)_patternNo;
        
        data[offset]  = (byte)_octave;
        data[offset] |= (byte)(_resolution << 2);
        ++offset;
        
        data[offset++] = (byte)_gate;
        data[offset++] = (byte)_velocity;
        data[offset++] = (byte)_swing;
        
        data[offset]  = (byte)_sort;
        data[offset] |= (byte)(_latch << 1);
        data[offset] |= (byte)(_keySync << 2);
        data[offset] |= (byte)(_keyboard << 3);
        ++offset;
        
        data[offset++] = (byte)_topKey;
        data[offset++] = (byte)_bottomKey;
        data[offset++] = (byte)_topVelocity;
        data[offset++] = (byte)_bottomVelocity;
        
        return offset;
    }
	
	private int _tempo;          // byte  0
	private int _switch;         // byte  1
	private int _patternNo;      // byte  2
	private int _octave;         // byte  3, bits 0-1
	private int _resolution;     // byte  3, bits 2-4
	private int _gate;           // byte  4
	private int _velocity;       // byte  5
	private int _swing;          // byte  6
	private int _sort;           // byte  7, bit 0
	private int _latch;          // byte  7, bit 1
	private int _keySync;        // byte  7, bit 2
	private int _keyboard;       // byte  7, bit 3
	private int _topKey;         // byte  8
	private int _bottomKey;      // byte  9
	private int _topVelocity;    // byte 10
	private int _bottomVelocity; // byte 11
	
    public static int BYTE_SIZE = 12;
}