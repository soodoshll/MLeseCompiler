package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.BuiltinFunction;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.Call;
import net.qdsu.Mlese.IR.Instruction.IRInstruction;
import net.qdsu.Mlese.IR.Instruction.Phi;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;

public class NaiveDCE {
    private IRRoot root;

    public NaiveDCE(IRRoot root) {
        this.root = root;
    }

    public void run(){
        System.err.println("NAIVE DCE");
        boolean changed;
        for (Function func: root.getFunctions()){
            changed = true;
            while (changed) {
                changed = false;
                for (BasicBlock bb : func.getBbList()) {
                    IRInstruction ins = bb.getIrStart();
//                    System.out.println(bb);
                    while (ins != null) {
                        //System.out.println(ins);
                        if (!(ins instanceof Call) && ins.ssadef() != null && ins.ssadef().usedIns.isEmpty()) {
                            //if (ins instanceof Phi) System.out.println("remove phi");
//                        System.out.println("remove "+ins);
                            IRInstruction old_ins = ins;
                            for (Register r : ins.ssause()) r.usedIns.remove(ins);
                            ins = ins.getNext();
                            old_ins.remove();
                            changed = true;
                        } else {
                            ins = ins.getNext();
                        }
                    }
                }
            }
        }
    }

    public void delPar(Function fun, int index){
        System.err.println("del "+fun+" "+index);
        for (Function f : root.getFunctions())
            for (BasicBlock bb : f.getBbList())
                for (IRInstruction ins = bb.getIrStart() ; ins != null ; ins=ins.getNext()){
                    if (ins instanceof Call && ((Call) ins).getCallee() == fun){
                        System.err.println(ins+" ; "+fun);
                        Operand arg = ((Call) ins).getArguments().get(index);
                        if (arg instanceof Register) ((Register) arg).usedIns.remove(ins);
                        ((Call) ins).getArguments().remove(index);

                    }
                }
    }

    public boolean all_recursion(Function func, Register par){
        System.err.println(par);
        for (IRInstruction ins : par.usedIns){
            System.err.println(ins);
            if (!(ins instanceof Call)) return false;
//            Call call = (Call)ins;
            if (((Call) ins).getCallee() != func) return false;
        }
        return true;
    }

    private HashSet<Call> calls;

    public void uselessPar(){
        boolean changed = true;
        while (changed){
            changed = false;
            for (Function func : root.getFunctions())
                if (! (func instanceof BuiltinFunction)) {
                    HashSet<Register> useless = new HashSet<>();
                    for (int i = func.getParameters().size()-1 ; i >=0 ; --i) {
                        Register par = func.getParameters().get(i);
                        if (par.usedIns.isEmpty() || all_recursion(func, par)) {
                            changed = true;
                            delPar(func, func.getParameters().indexOf(par));
                            useless.add(par);
                        }
                    }
                    func.getParameters().removeAll(useless);
                }
        }
    }
}
