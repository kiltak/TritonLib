package view;

import model.sound.Bank;
import model.sound.combination.Combination;
import model.sound.program.Program;

public interface View {
    public abstract void displaySound (Program p);
    public abstract void displaySound (Combination c);
    public abstract void displayBank  (Bank b);
    public abstract void displayBanks (Bank[] b);
}
