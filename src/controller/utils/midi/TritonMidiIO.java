package controller.utils.midi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import model.Triton;
import model.global.Global;
import model.sound.Location;
import model.sound.Sound;
import model.sound.SoundWithLocation;
import model.sound.combination.Combination;
import model.sound.program.Program;

public class TritonMidiIO extends MidiIO {
    
    private MidiParser _midiParser = new MidiParser();
    
    public TritonMidiIO(Triton triton) {
        super._inReceiver = this;
        this.triton = triton;
        midiSender = new MidiSender(this);
        t = new Thread(midiSender);
        t.start();
    }
    
    /**
     * This is really reading from the MIDI device. The message is getting sent
     * to the computer.
     */
    @Override
    public void send(MidiMessage message, long lTimeStamp) {
        if (message instanceof SysexMessage) {
            ArrayList<Object> decodedItems = _midiParser.decodeMessage((SysexMessage) message);
            if (decodedItems!= null) {
                // Start dealing with the stuff that just came down
                for (Object o : decodedItems) {
                    if (o instanceof SoundWithLocation) {
                        SoundWithLocation swl = (SoundWithLocation) o;
                        int bank = swl.getLocation().getBank();
                        int offset = swl.getLocation().getOffset();
                        Sound s = swl.getSound();
                        if (s instanceof Program) {
                            Program p = (Program)s;
                            triton.setSingleProg(p, bank, offset);
                        }
                        else if (s instanceof Combination) {
                            Combination c = (Combination)s;
                            triton.setSingleCombi(c, bank, offset);
                        }
                    }
                    else if (o instanceof Global) {
                        triton.setGlobal((Global)o);
                    }
                    else {
                        System.out.println ("don't know what TritonMidiIO got... " + o);
                    }
                } // end for decodedItems
                
                // Start sending commands to hardware again
                midiSender.shouldRun(true); // the message is finished
                t.interrupt();
            } // end (!decodedItems.isEmpty())
            else {
                midiSender.shouldRun(false); // the message is to be continued...
            }
        }
    }
    
    public void sendMsg(MidiMessage msg) {
        midiSender.addMsg(msg);
        t.interrupt();
    }
    
    public void reallySendMsg(MidiMessage msg) {
        super.sendMsg(msg);
    }
    
    /**
     * MidiSender throttles the requests being sent to the hardware. If a
     * command is sent to the hardware while the hardware is responding to a
     * previous request, the new command is dropped. This class will keep all
     * the commands to be sent to the hardware in a queue. The runFlag is true
     * when hardware is open for business. After sending a command, sleep for a
     * second to give the hardware a chance to start answering. The class using
     * this class has to set the runFlag to false when it is receiving a multi
     * part message, then back to true when the message is completed.
     */
    private class MidiSender implements Runnable {
        public MidiSender(TritonMidiIO m) {
            midiIO = m;
        }
        
        @Override
        public void run() {
            MidiMessage msg;
            while (true) {
                try {
                    if (runFlag && !msgToSend.isEmpty()) {
                        msg = msgToSend.remove();
                        midiIO.reallySendMsg(msg);
                        Thread.sleep(1000); // Give the hardware a little breathing room
                    }
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                    // Sometimes it's good to wake up and get to work!
                }
            }
        }
        
        public void addMsg(MidiMessage msg) {
            msgToSend.add(msg);
        }
        
        public void shouldRun(boolean runFlag) {
            this.runFlag = runFlag;
        }
        
        boolean runFlag = true;
        Queue<MidiMessage> msgToSend = new ArrayDeque<MidiMessage>(); // messages to send to hardware
        TritonMidiIO midiIO;
    }
    
    MidiSender midiSender;
    Triton triton;
    Thread t;
}
