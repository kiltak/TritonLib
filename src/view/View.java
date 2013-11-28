package view;

import triton.model.sound.Bank;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;

public interface View {
    public abstract void displaySound (Program p);
    public abstract void displaySound (Combination c);
    public abstract void displayBank  (Bank b);
    public abstract void displayBanks (Bank[] b);
}
