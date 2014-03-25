package controller.utils.midi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import triton.model.Triton;
import triton.model.global.Global;
import triton.model.sound.Sound;
import triton.model.sound.SoundWithLocation;
import triton.model.sound.combination.Combination;
import triton.model.sound.program.Program;

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
            if (decodedItems != null) {
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
                midiSender.shouldSend(true); // the message is finished
                if (midiSender.shouldBeInterrupted()) {
                    t.interrupt();
                }
            } // end (!decodedItems.isEmpty())
            else {
                midiSender.shouldSend(false); // the message is to be continued...
            }
        }
    }
    
    public void sendMsg(MidiMessage msg) {
        boolean shouldBeInterrupted = midiSender.shouldBeInterrupted();
        midiSender.addMsg(msg);
        if (shouldBeInterrupted) {
            t.interrupt();
        }
    }
    
    public void reallySendMsg(MidiMessage msg) {
        super.sendMsg(msg);
    }
    
    public void close () {
        midiSender.die();
        t.interrupt();
        super.close();
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
            while (isRunning) {
                try {
                    while (isRunning && sendFlag && !msgToSend.isEmpty()) {
                        msg = msgToSend.remove();
                        midiIO.reallySendMsg(msg);
                        Thread.sleep(1000); // Give the hardware a little breathing room
                    }
                    Thread.sleep(30000);
                }
                catch (InterruptedException e) {
                    // Sometimes it's good to wake up and get to work!
                }
            }
        }
        
        /**
         * Add a message to the queue of messages to send to hardware.
         * 
         * @param msg
         */
        public void addMsg(MidiMessage msg) {
            msgToSend.add(msg);
        }
        
        /**
         * Set if the thread should be sending data to hardware.  It usually should, but if the
         * hardware is sending something back, then don't send anything to the hardware.
         * 
         * @param runFlag
         */
        public void shouldSend(boolean runFlag) {
            this.sendFlag = runFlag;
        }
        
        /**
         * The thread should be interrupted if it is allowed to send data, but doesn't have
         * any data to send.  Note that you have to call this before you add data.
         * 
         * @return  True if it should be interrupted.
         */
        public boolean shouldBeInterrupted () {
            return sendFlag && msgToSend.isEmpty();
        }
        
        /**
         * Stop this thread
         */
        public void die () {
            isRunning = false;
        }
        
        boolean sendFlag = true;
        boolean isRunning = true;
        Queue<MidiMessage> msgToSend = new ArrayDeque<MidiMessage>(); // messages to send to hardware
        TritonMidiIO midiIO;
    }
    
    MidiSender midiSender;
    Triton triton;
    Thread t;
}
