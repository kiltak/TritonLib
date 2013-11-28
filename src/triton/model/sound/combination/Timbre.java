package triton.model.sound.combination;

import triton.model.sound.Location;

public class Timbre {
    public int unpack (byte data[], int offset) {
        _prog = data[offset++];
        _bank = data[offset++];
        
        _midiChannel = data[offset] & 0x1F;
        _status = (data[offset] & 0xE0) >> 5;
        ++offset;
        
        _bankSelMSB = data[offset++];
        _bankSelLSB = data[offset++];
        _vol = data[offset++];
        _pitchBendRange = data[offset++];
        _transpose = data[offset++];
        _detuneMSB = data[offset++];
        _detuneLSB = data[offset++];
        _delayStart = data[offset++];
        _pan = data[offset++];
        _send1Level = data[offset++];
        _send2Level = data[offset++];
        
        _dkitIfx4Patch = data[offset] & 0x07;
        _dkitIfx5Patch = (data[offset] & 0x38) >> 3;
        ++offset;
        
        _dkitIfx1Patch = data[offset] & 0x07;
        _dkitIfx2Patch = (data[offset] & 0x38) >> 3;
        _dkitIfx3Patch = (data[offset] & 0x38) >> 3;  // Documentation has 9 bits in this byte...
        ++offset;
        
        _busSelect = data[offset++];
        
        _progChangeFilt = data[offset] & 0x01;
        _afterTouchFilter = (data[offset] & 0x02) >> 1;
        _damperFilter = (data[offset] & 0x04) >> 2;
        _portamentoFilter = (data[offset] & 0x08) >> 3;
        _jsXasAmsFilter = (data[offset] & 0x10) >> 4;
        _jsYposFilter = (data[offset] & 0x20) >> 5;
        _jsYnegFilter = (data[offset] & 0x40) >> 6;
        _ribonFilter = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _knob1Filter = data[offset] & 0x01;
        _knob2Filter = (data[offset] & 0x02) >> 1;
        _knob3Filter = (data[offset] & 0x04) >> 2;
        _knob4Filter = (data[offset] & 0x08) >> 3;
        _sw1Filter = (data[offset] & 0x10) >> 4;
        _sw2Filter = (data[offset] & 0x20) >> 5;
        _footPedalFilter = (data[offset] & 0x40) >> 6;
        _otherFilter = (data[offset] & 0x80) >> 7;
        ++offset;
        
        _forceOscMode = data[offset] & 0x03;
        _oscSelect = (data[offset] & 0x0C) >> 2;
        _arpAssign = (data[offset] & 0x30) >> 4;
        _useProgScale = (data[offset] & 0x40) >> 6;
        ++offset;
        
        _portamentTime = data[offset++];
        _keyZTop = data[offset++];
        _keyZBottom = data[offset++];
        
        _keyZTopSlope = data[offset] & 0x0F;
        _keyZBottomSlope = (data[offset] & 0xF0) >> 4;
        ++offset;
        
        _velZTop = data[offset++];
        _velZBottom = data[offset++];
        
        _velZTopSlope = data[offset] & 0x0F;
        _velZBottomSlope = (data[offset] & 0xF0) >> 4;
        ++offset;
        
        _mossVoice = data[offset++];
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        data[offset++] = (byte)_prog;
        data[offset++] = (byte)_bank;
        
        data[offset]  = (byte)_midiChannel;
        data[offset] |= (byte)(_status << 5);
        ++offset;
        
        data[offset++] = (byte)_bankSelMSB;
        data[offset++] = (byte)_bankSelLSB;
        data[offset++] = (byte)_vol;
        data[offset++] = (byte)_pitchBendRange;
        data[offset++] = (byte)_transpose;
        data[offset++] = (byte)_detuneMSB;
        data[offset++] = (byte)_detuneLSB;
        data[offset++] = (byte)_delayStart;
        data[offset++] = (byte)_pan;
        data[offset++] = (byte)_send1Level;
        data[offset++] = (byte)_send2Level;
        
        data[offset]  = (byte)_dkitIfx4Patch;
        data[offset] |= (byte)(_dkitIfx5Patch << 3);
        ++offset;
        
        data[offset]  = (byte)_dkitIfx1Patch;
        data[offset] |= (byte)(_dkitIfx2Patch << 3);
        data[offset] |= (byte)(_dkitIfx3Patch << 3);  // Documentation has 9 bits in this byte...
        ++offset;
        
        data[offset++] = (byte)_busSelect;
        
        data[offset]  = (byte)_progChangeFilt;
        data[offset] |= (byte)(_afterTouchFilter << 1);
        data[offset] |= (byte)(_damperFilter << 2);
        data[offset] |= (byte)(_portamentoFilter << 3);
        data[offset] |= (byte)(_jsXasAmsFilter << 4);
        data[offset] |= (byte)(_jsYposFilter << 5);
        data[offset] |= (byte)(_jsYnegFilter << 6);
        data[offset] |= (byte)(_ribonFilter << 7);
        ++offset;
        
        data[offset]  = (byte)_knob1Filter;
        data[offset] |= (byte)(_knob2Filter << 1);
        data[offset] |= (byte)(_knob3Filter << 2);
        data[offset] |= (byte)(_knob4Filter << 3);
        data[offset] |= (byte)(_sw1Filter << 4);
        data[offset] |= (byte)(_sw2Filter << 5);
        data[offset] |= (byte)(_footPedalFilter << 6);
        data[offset] |= (byte)(_otherFilter << 7);
        ++offset;
        
        data[offset]  = (byte)_forceOscMode;
        data[offset] |= (byte)(_oscSelect << 2);
        data[offset] |= (byte)(_arpAssign << 4);
        data[offset] |= (byte)(_useProgScale << 6);
        ++offset;
        
        data[offset++] = (byte)_portamentTime;
        data[offset++] = (byte)_keyZTop;
        data[offset++] = (byte)_keyZBottom;
        
        data[offset]  = (byte)_keyZTopSlope;
        data[offset] |= (byte)(_keyZBottomSlope << 4);
        ++offset;
        
        data[offset++] = (byte)_velZTop;
        data[offset++] = (byte)_velZBottom;
        
        data[offset]  = (byte)_velZTopSlope;
        data[offset] |= (byte)(_velZBottomSlope << 4);
        ++offset;
        
        data[offset++] = (byte)_mossVoice;
        
        return offset;
    }
    
    /**
     * Return the locatoin of the program.
     * 
     * @return
     */
    public Location getProgLocation () {
        if (_status == 0) {
            return new Location (_bank, _prog);
        }
        else {
            return null;
        }
    }
    
    /**
     * Update the location to the new location.
     * 
     * @param l
     */
    public void setLocation (Location l) {
        _bank = l.getBank();
        _prog = l.getOffset();
    }
    
    private int _prog;             // byte 0
    private int _bank;             // byte 1
    private int _midiChannel;      // byte 2, bits 0-4
    private int _status;           // byte 2, bits 5-7   0:INT, 1:Off, 2:EXT, 3:EXT2
    private int _bankSelMSB;       // byte 3    only available when status is EXT2
    private int _bankSelLSB;       // byte 4    only available when status is EXT2
    private int _vol;              // byte 5
    private int _pitchBendRange;   // byte 6   E7:PROG, E8~~18:-24~~24
    private int _transpose;        // byte 7  E8~~18:-24~~24
    private int _detuneMSB;        // byte 8  FB50~~4B0:-1200~~1200
    private int _detuneLSB;        // byte 9  ^^^^^^^^^^^^^^^^^^^^^
    private int _delayStart;       // byte 10
    private int _pan;              // byte 11
    private int _send1Level;       // byte 12
    private int _send2Level;       // byte 13
    private int _dkitIfx4Patch;    // byte 14, bits 0-2
    private int _dkitIfx5Patch;    // byte 14, bits 3-5
    private int _dkitIfx1Patch;    // byte 15, bits 0-2
    private int _dkitIfx2Patch;    // byte 15, bits 3-5
    private int _dkitIfx3Patch;    // byte 15, bits 6-8
    private int _busSelect;        // byte 16
    private int _progChangeFilt;   // byte 17, bit 0
    private int _afterTouchFilter; // byte 17, bit 1
    private int _damperFilter;     // byte 17, bit 2
    private int _portamentoFilter; // byte 17, bit 3
    private int _jsXasAmsFilter;   // byte 17, bit 4
    private int _jsYposFilter;     // byte 17, bit 5
    private int _jsYnegFilter;     // byte 17, bit 6
    private int _ribonFilter;      // byte 18, bit 7
    private int _knob1Filter;      // byte 18, bit 0
    private int _knob2Filter;      // byte 18, bit 1
    private int _knob3Filter;      // byte 18, bit 2
    private int _knob4Filter;      // byte 18, bit 3
    private int _sw1Filter;        // byte 18, bit 4
    private int _sw2Filter;        // byte 18, bit 5
    private int _footPedalFilter;  // byte 18, bit 6
    private int _otherFilter;      // byte 18, bit 7
    private int _forceOscMode;     // byte 19, bits 0-1   0:PROGRAM, 1:Poly, 2:Mono, 3:Mono Legate
    private int _oscSelect;        // byte 19, bits 2-3    0:BOTH, 1:OSC1, 2:OSC2
    private int _arpAssign;        // byte 19, bits 4-5   0:Off, 1:A, 2:B
    private int _useProgScale;     // byte 19, bit 6
    private int _portamentTime;    // byte 20   FF:PRG, 00:Off
    private int _keyZTop;          // byte 21
    private int _keyZBottom;       // byte 22
    private int _keyZTopSlope;     // byte 23, bits 0-3
    private int _keyZBottomSlope;  // byte 23, bits 4-7
    private int _velZTop;          // byte 24
    private int _velZBottom;       // byte 25
    private int _velZTopSlope;     // byte 26, bits 0-3
    private int _velZBottomSlope;  // byte 26, bits 4-7
    private int _mossVoice;        // byte 27
    
    public static int BYTE_SIZE = 28;
}
