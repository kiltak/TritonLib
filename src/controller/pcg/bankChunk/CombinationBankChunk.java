package controller.pcg.bankChunk;

import controller.pcg.BadDataException;
import controller.pcg.ChunkBody;
import triton.model.sound.Bank;
import triton.model.sound.combination.Combination;

public class CombinationBankChunk extends ChunkBody {
    public int unpack(byte data[], int offset) throws BadDataException {
        offset = super.getHeader (data, offset);

        _bank = new Bank (Bank.COMBI, _bankId);
        
        for (int i = 0; i < _count; ++i) {
            Combination c = new Combination();
            offset = c.unpack(data, offset);
            _bank.set(c, i);
        }
        
        return offset;
    }
    
    public int pack(byte data[], int offset) {
        offset = super.putHeader (data, offset);
        offset = _bank.pack(data, offset);
        
        return offset;
    }
    
    public Bank getBank() {
        return _bank;
    }
    
    public String toString() {
        return _bank.toString();
    }
    
    private Bank _bank;
}