/**
 * The basic building block for the programs and combinations.
 * 
 * This takes care of effects and the name.
 */

package triton.model.sound;

import triton.model.effects.*;

public class Sound {
    public Sound () {
        for (int i = 0; i < _insertFx.length; ++i) {
            _insertFx[i] = new InsertEffect();
        }
        for (int i = 0; i < _masterFx.length; ++i) {
            _masterFx[i] = new MasterEffect();
        }
    }
    
    /**
     * 
     * @param data
     * @param offset
     * @return
     */
    public int unpack(byte data[], int offset) {
        _name = "";  // Make sure it's cleared out in case of reuse of object
        for (int i = 0; i < 16; ++i) {
            data[offset] &= (byte)0x7F; // Not really sure why this is needed...
            _name += (char)data[offset++];
        }
        
        // Unpack the effects
        for (int i = 0; i < _insertFx.length; ++i) {
            offset = _insertFx[i].unpack(data, offset);
        }
        for (int i = 0; i < _masterFx.length; ++i) {
            offset = _masterFx[i].unpack(data, offset);
        }
        offset = _masterFxStuff.unpack(data, offset);
        
        return offset;
    }
    
    public int pack (byte data[], int offset) {
        if (_name != null) {
            byte[] name = _name.getBytes();
            System.arraycopy(name, 0, data, offset, 16);
        }
        else {
            byte[] name = "--- null ---    ".getBytes();
            System.arraycopy(name, 0, data, offset, 16);
        }
        offset += 16;
        
        for (int i = 0; i < _insertFx.length; ++i) {
            offset = _insertFx[i].pack(data, offset);
        }
        for (int i = 0; i < _masterFx.length; ++i) {
            offset = _masterFx[i].pack(data, offset);
        }
        offset = _masterFxStuff.pack(data, offset);
        
        return offset;
    }
    
    /**
     * 
     */
    public String toString () {
        return getName();
    }
    
    public void setName (String newName) {
        if (newName != null) {
            _name = newName.substring(0, 15);
        }
    }
    
    public String getName() {
        return _name;
    }

    protected String _name = null;
    protected InsertEffect[] _insertFx = new InsertEffect[5];
    protected MasterEffect[] _masterFx = new MasterEffect[2];
    protected MasterEffectStuff _masterFxStuff = new MasterEffectStuff();
}