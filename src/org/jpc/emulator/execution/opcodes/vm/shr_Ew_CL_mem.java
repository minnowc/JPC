package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class shr_Ew_CL_mem extends Executable
{
    final Pointer op1;

    public shr_Ew_CL_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
        if((0x1f & cpu.r_cl.get8()) != 0)
        {
            cpu.flagOp1 = 0xFFFF&op1.get16(cpu);
            cpu.flagOp2 = 0x1f & cpu.r_cl.get8();
            cpu.flagResult = (short)(cpu.flagOp1 >>> cpu.flagOp2);
            op1.set16(cpu, (short)cpu.flagResult);
            cpu.flagIns = UCodes.SHR16;
            cpu.flagStatus = OSZAPC;
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