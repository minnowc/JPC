package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class cmp_Ew_Ib_mem extends Executable
{
    final Pointer op1;
    final int immb;

    public cmp_Ew_Ib_mem(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1 = new Pointer(parent.operand[0], parent.adr_mode);
        immb = (byte)parent.operand[1].lval;
    }

    public Branch execute(Processor cpu)
    {
        cpu.flagOp1 = (short)op1.get16(cpu);
        cpu.flagOp2 = (short)immb;
        cpu.flagResult = (short)(cpu.flagOp1 - cpu.flagOp2);
        cpu.flagIns = UCodes.SUB16;
        cpu.flagStatus = OSZAPC;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}