package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import static org.jpc.emulator.processor.Processor.*;

public class ror_Eb_CL_mem extends Executable
{
    final Pointer op1;

    public ror_Eb_CL_mem(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1 = new Pointer(parent.operand[0], parent.adr_mode);
    }

    public Branch execute(Processor cpu)
    {
            int shift = cpu.r_cl.get8() & 0xf;
            int reg0 = 0xFF&op1.get8(cpu);
            int res = (reg0 >>> shift) | (reg0 << (8 - shift));
            op1.set8(cpu, (byte)res);
            boolean bit30  = (res & (1 << (8-2))) != 0;
            boolean bit31 = (res & (1 << (8-1))) != 0;
            if (cpu.r_cl.get8() > 0)
            {
                cpu.cf = bit31;
                if (cpu.r_cl.get8() == 1)
                {
                    cpu.of = bit30 ^ bit31;
                    cpu.flagStatus &= NOFCF;
                }
                else
                    cpu.flagStatus &= NCF;
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