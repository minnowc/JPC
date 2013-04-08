package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class arpl_Ew_Gw_mem extends Executable
{
    final Pointer op1;
    final int op2Index;

    public arpl_Ew_Gw_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
        op2Index = Modrm.Gw(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op2 = cpu.regs[op2Index];
        int sel1 = op1.get16(cpu);
        int sel2 = op2.get16();
        if ((sel1 & 3) < (sel2 & 3))
        {
            cpu.zf(true);
            op1.set16(cpu,  (short)(sel1 | (sel2 & 3)));
        } else
        {
            cpu.zf(false);
        }
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