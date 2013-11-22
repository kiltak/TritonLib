package model.sound.combination;

public class Common {
    public Common () {
        for (int i = 0; i < _sw.length; ++i) {
            _sw[i] = new sw();
        }
    }
    
    public int unpack (byte data[], int offset) {
        _category = data[offset] & 0x0F;
        _mosBusSelect = (data[offset] & 0xF0) >> 4;
        ++offset;
        
        _scaleType = data[offset++];
        _scaleKey = data[offset++];
        _randomIntensity = data[offset++];
        for (int i = 0; i < _sw.length; ++i) {
            offset = _sw[i].unpack(data, offset);
        }

        _knob1AssignType = data[offset] & 0x7F;
        _realtimeControls = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _knob2AssignType = data[offset++];
        _knob3AssignType = data[offset++];
        _knob4AssignType = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset] = (byte)_category;
        data[offset] = (byte)(_mosBusSelect << 4);
        ++offset;
        
        data[offset++] = (byte)_scaleType;
        data[offset++] = (byte)_scaleKey;
        data[offset++] = (byte)_randomIntensity;
        for (int i = 0; i < _sw.length; ++i) {
            offset = _sw[i].pack(data, offset);
        }

        data[offset] = (byte)_knob1AssignType;
        data[offset] = (byte)(_realtimeControls << 7);
        ++offset;
        
        data[offset++] = (byte)_knob2AssignType;
        data[offset++] = (byte)_knob3AssignType;
        data[offset++] = (byte)_knob4AssignType;
        
        return offset;
    }
    
    private class sw {
        public int unpack (byte data[], int offset) {
            _assignType = data[offset] & 0x3F;
            _toggleMomentary = (data[offset] & 0x40) >> 6;
            _onOff = (data[offset] & 0x80) >> 7;
            ++offset;
            
            return offset;
        }
        
        public int pack (byte data[], int offset) {
            data[offset]  = (byte)_assignType;
            data[offset] |= (byte)(_toggleMomentary << 6);
            data[offset] |= (byte)(_onOff << 7);
            ++offset;
            
            return offset;
        }
        
        private int _assignType;       // byte 0, bits 0-5
        private int _toggleMomentary;  // byte 0, bit  6  false:Toggle, true:Momentary
        private int _onOff;            // byte 0, bit  7
    }
    
    private int _category;         // byte 0, bits 0-3
    private int _mosBusSelect;     // byte 0, bit 4-7
    private int _scaleType;        // byte 1
    private int _scaleKey;         // byte 2
    private int _randomIntensity;  // byte3 
    private sw[] _sw = new sw[2];
    private int _knob1AssignType;  // byte 6, bits 0-6
    private int _realtimeControls; // byte 6, bit 7
    private int _knob2AssignType;  // byte 7
    private int _knob3AssignType;  // byte 8
    private int _knob4AssignType;  // byte 9
    
    public static int BYTE_SIZE = 10;
}
