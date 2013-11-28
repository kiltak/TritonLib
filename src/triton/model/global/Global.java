package triton.model.global;

public class Global {
    public Global () {
        for (int i = 0; i < _userScaleOctave.length; ++i) {
            _userScaleOctave[i] = new UserScaleOctave();
        }
    }
    
    public int unpack (byte[] data, int offset) {
        _masterTune = data[offset++];
        _keyTranspose = data[offset++];
        _velocityCurve = data[offset++];
        _afterTouchCurve = data[offset++];
        
        _footSwPolarity = data[offset] & 0x01;
        _damperPolarity = (data[offset] & 0x02) >> 1;
        _convertPosition = (data[offset] & 0x04) >> 2;
        _progAutoArp = (data[offset] & 0x08) >> 3;
        _combiAutoArp = (data[offset] & 0x10) >> 4;
        ++offset;
        
        _footSwAssign = data[offset++];
        _footPedalAssign = data[offset++];
        _reserved = data[offset++];
        
        for (int i = 0; i < _userScaleOctave.length; ++i) {
            offset = _userScaleOctave[i].unpack(data, offset);
        }
        
        offset = _userScaleAllNotes.unpack(data, offset);
        
        for (int i = 0; i < _progCategoryName.length; ++i) {
            _progCategoryName[i] = "";
            for (int j = 0; j < 16; ++j) {
                data[offset] &= (byte)0x7F; // Not really sure why this is needed...
                _progCategoryName[i] += (char)data[offset++];
            }
        }
        
        for (int i = 0; i < _combiCategoryName.length; ++i) {
            _combiCategoryName[i] = "";
            for (int j = 0; j < 16; ++j) {
                data[offset] &= (byte)0x7F; // Not really sure why this is needed...
                _combiCategoryName[i] += (char)data[offset++];
            }
        }
        
        offset = _audioInput1.unpack(data, offset);
        offset = _audioInput2.unpack(data, offset);
        
        return offset;
    }
    
    public int pack (byte[] data, int offset) {
        data[offset++] = (byte)_masterTune;
        data[offset++] = (byte)_keyTranspose;
        data[offset++] = (byte)_velocityCurve;
        data[offset++] = (byte)_afterTouchCurve;
        
        data[offset]  = (byte)_footSwPolarity;
        data[offset] |= (byte)(_damperPolarity << 1);
        data[offset] |= (byte)(_convertPosition << 2);
        data[offset] |= (byte)(_progAutoArp << 3);
        data[offset] |= (byte)(_combiAutoArp << 4);
        ++offset;
        
        data[offset++] = (byte)_footSwAssign;
        data[offset++] = (byte)_footPedalAssign;
        data[offset++] = (byte)_reserved;
        
        for (int i = 0; i < _userScaleOctave.length; ++i) {
            offset = _userScaleOctave[i].pack(data, offset);
        }
        
        offset = _userScaleAllNotes.pack(data, offset);
        
        for (int i = 0; i < _progCategoryName.length; ++i) {
            byte[] stringBytes;
            if (_progCategoryName[i] != null) {
                stringBytes = _progCategoryName[i].getBytes();
            }
            else {
                stringBytes = "--- null ---    ".getBytes();
            }
            System.arraycopy(stringBytes, 0, data, offset, 16);
            offset += 16;
        }
        
        for (int i = 0; i < _combiCategoryName.length; ++i) {
            byte[] stringBytes;
            if (_combiCategoryName[i] != null) {
                stringBytes = _combiCategoryName[i].getBytes();
            }
            else {
                stringBytes = "--- null ---    ".getBytes();
            }
            System.arraycopy(stringBytes, 0, data, offset, 16);
            offset += 16;
        }
        
        offset = _audioInput1.pack(data, offset);
        offset = _audioInput2.pack(data, offset);
        
        return offset;
    }

    public byte[] pack () {
        byte[] retArr = new byte[BYTE_SIZE];
        pack (retArr, 0);
        return retArr;
    }
    
    public String toString () {
        String retString = "";
        for (int i = 0; i < 16; ++i) {
            retString += _progCategoryName[i] + "\n";
        }
        for (int i = 0; i < 16; ++i) {
            retString += _combiCategoryName[i] + "\n";
        }
        
        return retString;
    }
    
    private int _masterTune;      // byte 0
    private int _keyTranspose;    // byte 1
    private int _velocityCurve;   // byte 2
    private int _afterTouchCurve; // byte 3
    private int _footSwPolarity;  // byte 4, bit 0
    private int _damperPolarity;  // byte 4, bit 1
    private int _convertPosition; // byte 4, bit 2
    private int _progAutoArp;     // byte 4, bit 3
    private int _combiAutoArp;    // byte 4, bit 4
    private int _footSwAssign;    // byte 5
    private int _footPedalAssign; // byte 6
    private int _reserved;        // byte 7
    private UserScaleOctave[] _userScaleOctave = new UserScaleOctave[12];
    private UserScaleAllNotes _userScaleAllNotes = new UserScaleAllNotes();
    private String[] _progCategoryName = new String[16];
    private String[] _combiCategoryName = new String[16];
    private AudioInput _audioInput1 = new AudioInput();
    private AudioInput _audioInput2 = new AudioInput();
    
    public static final int BYTE_SIZE = 850;
}
