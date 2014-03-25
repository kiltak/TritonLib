package triton.model.sound.program;

import triton.util.Limits;

class Common {
    public Common () {
        for (int i = 0; i < _sw.length; ++i) {
            _sw[i] = new sw();
        }
    }
    
    public int unpack (byte data[], int offset) {
        _oscMode = data[offset] & 0x03;
        _keyAssign = (data[offset] & 0x04) >> 2;
        _legato = (data[offset] & 0x08) >> 3;
        _priority = (data[offset] & 0x30) >> 4;
        _singletrigger = (data[offset] & 0x40) >> 6;
        _hold = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _busSelect = data[offset] & 0x7F;
        _useDkSetting = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _category = data[offset++];
        _scaleType = data[offset++];
        _scaleKey = data[offset++];
        _randomIntensity = data[offset++];
        
        for (int i = 0; i < _sw.length; ++i) {
            offset = _sw[i].unpack(data, offset);
        }
        
        _knob1AssignType = data[offset] & 0x7F;
        _realtimeControls = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _knob2Assign = data[offset++];
        _knob3Assign = data[offset++];
        _knob4Assign = data[offset++];
        
        validate();
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset]  = (byte)_oscMode;
        data[offset] |= (byte)(_keyAssign << 2);
        data[offset] |= (byte)( _legato << 3);
        data[offset] |= (byte)( _priority << 4);
        data[offset] |= (byte)(_singletrigger << 6);
        data[offset] |= (byte)(_hold << 7);
        ++offset;
        
        data[offset]  = (byte)_busSelect;
        data[offset] |= (byte)(_useDkSetting << 7);
        ++offset;
        
        data[offset++] = (byte)_category;
        data[offset++] = (byte)_scaleType;
        data[offset++] = (byte)_scaleKey;
        data[offset++] = (byte)_randomIntensity;
        
        for (int i = 0; i < _sw.length; ++i) {
            offset = _sw[i].pack(data, offset);
        }
        
        data[offset]  = (byte)_knob1AssignType;
        data[offset] |= (byte)(_realtimeControls << 7);
        ++offset;
        
        data[offset++] = (byte)_knob2Assign;
        data[offset++] = (byte)_knob3Assign;
        data[offset++] = (byte)_knob4Assign;
        
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
        
        public boolean validate () {
            try {
                Limits.checkLimits ("_assignType", _assignType, 0, 0x0C);
                Limits.checkLimits ("_toggleMomentary", _toggleMomentary, 0, 1);
                Limits.checkLimits ("_onOff", _onOff, 0, 1);
            }
            catch (IllegalArgumentException e) {
                System.out.println (e);
                return false;
            }
            
            return true;
        }
        
        public int _assignType;       // byte 0, bits 0-5
        public int _toggleMomentary;  // byte 0, bit  6
        public int _onOff;            // byte 0, bit  7
    }
    
    private boolean validate () {
        boolean retVal = true;

        try {
            Limits.checkLimits ("_oscMode", _oscMode, 0, 2);
            Limits.checkLimits ("_keyAssign", _keyAssign, 0, 1);
            Limits.checkLimits ("_legato", _legato, 0, 1);
            Limits.checkLimits ("_priority", _priority, 0, 2);
            Limits.checkLimits ("_singletrigger", _singletrigger, 0, 1);
            Limits.checkLimits ("_hold", _hold, 0, 1);
            Limits.checkLimits ("_busSelect", _busSelect, 0, 0x0C);
            Limits.checkLimits ("_useDkSetting", _useDkSetting, 0, 1);
            Limits.checkLimits ("_category", _category, 0, 15);
            Limits.checkLimits ("_scaleType", _scaleType, 0, 0x1A);
            Limits.checkLimits ("_scaleKey", _scaleKey, 0, 0x0C);
            Limits.checkLimits ("_randomIntensity", _randomIntensity, 0, 7);
            Limits.checkLimits ("_knob1AssignType", _knob1AssignType, 0, 0x7C);
            Limits.checkLimits ("_realtimeControls", _realtimeControls, 0, 1);
            Limits.checkLimits ("_knob2Assign", _knob2Assign, 0, 0x7C);
            Limits.checkLimits ("_realtimeControlsMSB", _realtimeControlsMSB, 0, 1);
            Limits.checkLimits ("_knob3Assign", _knob3Assign, 0, 0x7C);
            Limits.checkLimits ("_knob4Assign", _knob4Assign, 0, 0x7C);
        }
        catch (IllegalArgumentException e) {
            System.out.println (e);
            retVal = false;
        }
        
        for (sw s : _sw) {
            if (!s.validate()) {
                retVal = false;
            }
        }
        
        return retVal;
    }
    
    private int _oscMode;          // byte 0, bits 0-1
    private int _keyAssign;        // byte 0, bit  2
    private int _legato;           // byte 0, bit  3
    private int _priority;         // byte 0, bits 4-5
    private int _singletrigger;    // byte 0, bit  6
    private int _hold;             // byte 0, bit  7
    private int _busSelect;        // byte 1, bits 0-6
    private int _useDkSetting;     // byte 1, bit  7
    private int _category;         // byte 2
    private int _scaleType;        // byte 3
    private int _scaleKey;         // byte 4
    private int _randomIntensity;  // byte 5
    private sw  _sw[] = new sw[2]; // bytes 6-7
    private int _knob1AssignType;  // byte 8, bits 0-6
    private int _realtimeControls; // byte 8, bit  7
    private int _knob2Assign;      // byte 9, bits 0-6
    private int _realtimeControlsMSB; // byte 9, bit 7
    private int _knob3Assign;      // byte 10
    private int _knob4Assign;      // byte 11
    
    public static int BYTE_SIZE = 12;
}