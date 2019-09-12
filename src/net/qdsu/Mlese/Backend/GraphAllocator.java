package net.qdsu.Mlese.Backend;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.*;

import java.io.PrintStream;
import java.util.*;


/*
* This Graph allocator is based on Chapter 11 of Modern Compiler Implementation
* */
public class GraphAllocator {
    public static class Graph {
        public static class Renamer implements IRVisitor{
            private Function fun;
            private Hashtable<Register, Register> alias;
            private Hashtable<Register, PhysicalRegister> color;

            private Register getAlias(Register r){
                Register a = alias.get(r);
                //if (a == null) return r;
                //else
                if (a == r) return r;
                else return getAlias(a);
            }

            public Renamer(Function fun, Hashtable<Register, Register> alias, Hashtable<Register, PhysicalRegister> color) {
                this.fun = fun;
                this.alias = alias;
                this.color = color;
            }

            public void run(){
//                System.out.println(fun);
//                System.out.println(color);
//                System.out.println(alias);
                for (BasicBlock bb : fun.getBbList())
                    for (IRInstruction ins =  bb.getIrStart() ; ins != null ; ins = ins.getNext()){
                        ins.accept(this);
                    }
            }

            private Operand getOp(Operand o){
                if (o instanceof Immediate || o instanceof StaticString || o instanceof GlobalVar) return o;
                if (o instanceof PhysicalRegister) return o;
                //System.out.println(o);
                return getColor((Register)o);
            }

            private Register getColor(Register r){
                Register u = getAlias(r);
                if (u == null) return r;
                if (u instanceof PhysicalRegister) return u;
                return color.get(u);
            }

            @Override
            public void visit(IRInstruction inst) {
            }

            @Override
            public void visit(BinaryOp inst) {
                inst.setLhs(getOp(inst.getLhs()));
                inst.setRhs(getOp(inst.getRhs()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Branch inst) {
                if (inst.getCmp() == null){
                    inst.setCond(getOp(inst.getCond()));
                }else{
                    IntCmp cmp = inst.getCmp();
                    cmp.setLhs(getOp(cmp.getLhs()));
                    cmp.setRhs(getOp(cmp.getRhs()));
                }
            }

            @Override
            public void visit(Call inst) {
                inst.setDest(getColor(inst.getDest()));
                for (int i = 0 ; i < inst.getArguments().size() ; i++){
                    Operand arg = inst.getArguments().get(i);
                    inst.getArguments().set(i, getOp(arg));
                }
            }

            @Override
            public void visit(IntCmp inst) {
                inst.setLhs(getOp(inst.getLhs()));
                inst.setRhs(getOp(inst.getRhs()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Jump inst) {

            }

            @Override
            public void visit(Load inst) {
                inst.setSrc1(getOp(inst.getSrc1()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Malloc inst) {
                inst.setSize(getOp(inst.getSize()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Move inst) {
                inst.setSrc(getOp(inst.getSrc()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Store inst) {
                inst.setSrc(getOp(inst.getSrc()));
                inst.setDest1(getOp(inst.getDest1()));
            }

            @Override
            public void visit(UnaryOp inst) {
                inst.setOperand(getOp(inst.getOperand()));
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Return inst) {

            }

            @Override
            public void visit(Pop inst) {
                inst.setDest(getColor(inst.getDest()));
            }

            @Override
            public void visit(Push inst) {
                inst.setSrc(getOp(inst.getSrc()));
            }

            @Override
            public void visit(Phi inst) {
                throw new RuntimeException("unreachable");
            }

            @Override
            public void visit(Lea inst) {
                //inst.setOffset(getOp(inst.getOffset()));
                inst.setBase(getOp(inst.getBase()));
                inst.setIndex(getOp(inst.getIndex()));
                inst.setDest(getColor(inst.getDest()));
            }
        }
        public static class Spiller implements IRVisitor{
            private Register r;
            private int offset;
            private Function fun;
            public Spiller(Register r, Function fun) {
                this.r = r;
                offset = fun.alloca();
                this.fun = fun;
            }

            @Override
            public void visit(IRInstruction inst) {
            }

            private Register spillRead(IRInstruction ins){
                VirtualRegister addr = new VirtualRegister();
                ins.insertBefore(new Lea(addr, PhysicalRegister.rbp, offset, new Immediate(0), 0));
                VirtualRegister result = new VirtualRegister();
                ins.insertBefore(new Load(result, addr));
                //System.out.printf("new vreg for spilling %s\n",result);

                fun.graph.regs.add(addr);
                fun.graph.regs.add(result);

                return result;
            }

            private Register spillWrite(IRInstruction ins){
                VirtualRegister addr = new VirtualRegister();
                VirtualRegister result = new VirtualRegister();
                ins.insertAfter(new Store(addr, result));
                ins.insertAfter(new Lea(addr, PhysicalRegister.rbp,offset, new Immediate(0), 0));
                //System.out.printf("new vreg for spilling %s\n",result);

//                System.out.println(fun.graph);

                fun.graph.regs.add(addr);
                fun.graph.regs.add(result);

                return result;
            }

            private void store(Register n, IRInstruction ins){
                VirtualRegister addr = new VirtualRegister();
                ins.insertAfter(new Store(addr, n));
                ins.insertAfter(new Lea(addr, PhysicalRegister.rbp, offset, new Immediate(0), 0));

                fun.graph.regs.add(addr);
            }


            @Override
            public void visit(BinaryOp inst) {
//                System.out.println(inst);

                switch (inst.getOp()){

                    case ADD: case SUB:
                        assert(inst.getDest() == inst.getLhs());
                        if (inst.getLhs() == r){
                            inst.setLhs(spillRead(inst));
                            inst.setDest((Register)inst.getLhs());
                            store(inst.getDest(), inst);
                        }
                        if (inst.getRhs() == r)
                            inst.setRhs(spillRead(inst));
                        break;
                    case MUL:
                        if (inst.getRhs() == r)
                            inst.setRhs(spillRead(inst));
                        if (inst.getLhs() == inst.getDest()){
                            if (inst.getLhs() == r){
                                inst.setLhs(spillRead(inst));
                                inst.setDest((Register)inst.getLhs());
                                store(inst.getDest(), inst);
                            }
                        }else{
                            assert(inst.getRhs() instanceof Immediate);
                            if (inst.getDest() == r) inst.setDest(spillWrite(inst));
                            if (inst.getLhs() == r) inst.setLhs(spillRead(inst));
                            //if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
                        }
                        break;
                    case DIV: case MOD:
                        if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
                        break;
                    case SHL: case SHR:
                        assert(inst.getLhs() == inst.getDest());
                        assert(inst.getRhs() == PhysicalRegister.rcx || inst.getRhs() instanceof Immediate && ((Immediate) inst.getRhs()).getValue() ==1);
                        if (inst.getLhs() == r){
                            inst.setLhs(spillRead(inst));
                            inst.setDest((Register)inst.getLhs());
                            store(inst.getDest(), inst);
                        }
//                        assert(inst.getLhs() == inst.getDest());
//                        if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
                        break;
                    case XOR:case OR:case AND:
                        assert(inst.getLhs() == inst.getDest());
                        if (inst.getLhs() == r){
                            inst.setLhs(spillRead(inst));
                            inst.setDest((Register)inst.getLhs());
                            store(inst.getDest(), inst);
                        }
                        if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
                        break;
                }
//                if (inst.getLhs() == r) inst.setLhs(spillRead(inst));
//                if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
//                if (inst.getDest() == r)inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Branch inst) {
                if (inst.getCmp() == null){
                    if (inst.getCond() == r) inst.setCond(spillRead(inst));
                }else{
                    IntCmp cmp = inst.getCmp();
                    if (cmp.getLhs() == r) cmp.setLhs(spillRead(inst));
                    if (cmp.getRhs() == r) cmp.setRhs(spillRead(inst));
                }
            }

            @Override
            public void visit(Call inst) {
                if (inst.getDest() == r) inst.setDest(spillWrite(inst));
//                for (int i = 0 ; i < inst.getArguments().size() ; i++){
//                    Operand arg = inst.getArguments().get(i);
//                    if (arg == r) inst.getArguments().set(i, spillRead(inst));
//                }
            }

            @Override
            public void visit(IntCmp inst) {
                if (inst.getLhs() == r) inst.setLhs(spillRead(inst));
                if (inst.getRhs() == r) inst.setRhs(spillRead(inst));
                if (inst.getDest() == r) inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Jump inst) {

            }

            @Override
            public void visit(Load inst) {
                if (inst.getSrc1() == r) inst.setSrc1(spillRead(inst));
                if (inst.getDest() == r) inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Malloc inst) {
//                if (inst.getSize() == r) inst.setSize(spillRead(inst));
//                if (inst.getDest() == r)inst.setDest(spillWrite(inst));
                throw new RuntimeException("unreachable");
            }

            @Override
            public void visit(Move inst) {
                if (inst.getSrc() == r) inst.setSrc(spillRead(inst));
                if (inst.getDest() == r) inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Store inst) {
                if (inst.getDest1() == r) inst.setDest1(spillRead(inst));
                if (inst.getSrc() == r) inst.setSrc(spillRead(inst));
            }


            @Override
            public void visit(UnaryOp inst) {
                assert(inst.getOperand() == inst.getDest());
                if (inst.getOperand() == r) {
                    inst.setOperand(spillRead(inst));
                    inst.setDest((Register)inst.getOperand());
                    store(inst.getDest(), inst);
                }
                //if (inst.getDest() == r)inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Return inst) {

            }

            @Override
            public void visit(Pop inst) {
                if (inst.getDest() == r)inst.setDest(spillWrite(inst));
            }

            @Override
            public void visit(Push inst) {
                if (inst.getSrc() == r) inst.setSrc(spillRead(inst));
            }

            @Override
            public void visit(Phi inst) {

            }

            @Override
            public void visit(Lea inst) {
                if (inst.getBase() == r) inst.setBase(spillRead(inst));
                //if (inst.getOffset() == r) inst.setOffset(spillRead(inst));
                if (inst.getIndex() == r) inst.setIndex(spillRead(inst));

                if (inst.getDest() == r)inst.setDest(spillWrite(inst));
            }
        }

        public HashSet<Register> regs = new HashSet<>();
        public Function fun;
        public IRRoot root;

        public Graph(IRRoot root, Function fun) {
            this.root = root;
            this.fun = fun;
        }

        public void addEdge(Register r1, Register r2){
            if (r1 != r2) {
                r1.edges.add(r2);
                r2.edges.add(r1);
            }
        }

        public void print(PrintStream out){
//            System.out.println(spilledRegs);
            for (Register r1 : regs) {
                if (r1 instanceof PhysicalRegister) continue;
                for (Register r2 : r1.edges)
                    if (r1.toString().compareTo(r2.toString()) > 0 || r2 instanceof PhysicalRegister) {
                        if (spilledRegs.contains(r1) || coalescedRegs.contains(r1)) continue;
                        if (spilledRegs.contains(r2) || coalescedRegs.contains(r2)) continue;
                        String rs1, rs2;
                        rs1 = (r1 instanceof PhysicalRegister) ? r1.toString() + "_" + fun.toString() : r1.toString();
                        rs2 = (r2 instanceof PhysicalRegister) ? r2.toString() + "_" + fun.toString() : r2.toString();
                        out.printf("\"<%s_%d>\" -> \"<%s_%d>\"[arrowType=\"none\"];\n", rs1,r1.getDegree(), rs2,r2.getDegree());
                    }
            }
        }



        private Hashtable<Register, Register> alias;

        private int round = 0;

        public void run(){
//            System.out.println("NEW coloring round "+fun);

            //Initialization
            simplifyList = new HashSet<>();
            freezeList = new HashSet<>();
            spillList  = new HashSet<>();
            spilledRegs = new HashSet<>();
            coalescedRegs = new HashSet<>();
            coloredRegs = new HashSet<>();
            selectStack = new Stack<>();

            coalescedMoves = new HashSet<>();
            constrainedMoves = new HashSet<>();
            frozenMoves = new HashSet<>();
            worklistMoves = new HashSet<>();
            activeMoves = new HashSet<>();

            remove_edges = new HashSet<>();

//            for (Register reg : PhysicalRegister.reg) {
//                reg.moves.clear();
//                reg.edges.clear();
//            }

            regs.clear();

            LivenessAnalysis liveness = new LivenessAnalysis(root);
            liveness.run(fun);

            build();
            //System.out.println(regs);
            initWorkList();

            //System.out.printf("REGS %s\n", regs);

            alias = new Hashtable<>();
            for (Register reg : regs) {
                alias.put(reg, reg);
            }

            updateUseAndDefOfRegs();

            while (!simplifyList.isEmpty() || !worklistMoves.isEmpty() || !freezeList.isEmpty() || !spillList.isEmpty()){
                if (!simplifyList.isEmpty()) Simplify();
                else if (!worklistMoves.isEmpty()) Coalesce();
                else if (!freezeList.isEmpty()) Freeze();
                else if (!spillList.isEmpty()) SelectSpill();
            }


            AssignColor();
//            System.out.println("SPILLED "+spilledRegs);
//            System.out.println("COALESCED "+coalescedRegs);
//            System.out.println("COLORED "+coloredRegs);
//            System.out.println("STACK "+selectStack);
//            System.out.println("------------------");
            for (Register reg : regs) {
                reg.moves.clear();
                reg.edges.clear();
            }
            if (!spilledRegs.isEmpty()){
                round ++ ;
                insertSpill();
                //if (round == 1) return;
                run();
            }


        }

        private void initWorkList(){
            for (Register reg : regs){
                if (reg instanceof VirtualRegister) {
                    if (reg.getDegree() >= PhysicalRegister.K)
                        spillList.add(reg);
                    else if (reg.moveRelated())
                        freezeList.add(reg);
                    else
                        simplifyList.add(reg);
                }
            }
        }

        private Register getAlias(Register r){
            if (coalescedRegs.contains(r)) return getAlias(alias.get(r));
            else return r;
        }

//        private HashSet<Register> adjOf(Register reg){
//            HashSet result = new HashSet(reg.edges);
//            result.removeAll(selectStack);
//            result.removeAll(coalescedRegs);
//            return result;
//        }

//        private HashSet<Register> movesOf(Register reg){
//            HashSet result = new HashSet(reg.moves);
//            result.removeAll(coalescedMoves);
//            result.removeAll(constrainedMoves);
//            result.removeAll(frozenMoves);
//            return result;
//        }
//
        private Object getElem(HashSet set){
            Object result = set.iterator().next();
            set.remove(result);
            return result;
        }

        private void Simplify(){
            Register cur = (Register)getElem(simplifyList);
            //System.out.printf("Simplify %s\n",cur);
            selectStack.push(cur);
            for (Register adj : cur.edges){
                //adj.edges.remove(cur);
                removeEdge(adj, cur);
            }
        }

        public class Edge{
            public Register from;
            public Register to;
            public Edge(Register from, Register to) {
                this.from = from;
                this.to = to;
            }
        }

        private HashSet<Edge> remove_edges;

        private void removeEdge(Register src, Register dest){
            remove_edges.add(new Edge(src, dest));
            src.edges.remove(dest);
            if (src.getDegree() == PhysicalRegister.K - 1){

                enableMoves(src);
                src.edges.forEach(x->enableMoves(x));

                spillList.remove(src);
                if (src.moveRelated()){
                    freezeList.add(src);
                }else{
                    simplifyList.add(src);
                }
            }
        }

        private void enableMoves(Register nodes){
            for (Move m : nodes.moves){
                if (activeMoves.contains(m)){
                    activeMoves.remove(m);
                    worklistMoves.add(m);
                }
            }
        }

        private void Coalesce(){
            Move m = (Move)getElem(worklistMoves);
            Register u,v;
            Register x = getAlias(m.getDest()), y = getAlias((Register)m.getSrc());
            if (y instanceof PhysicalRegister){
                u = y;
                v = x;
            }else{
                u = x;
                v = y;
            }
            if (u == v){
//System.out.printf("Coalesce %s and %s\n", u, v);

                coalescedMoves.add(m);
                u.moves.remove(m);

                addWorkList(u);
            }else if (v instanceof PhysicalRegister || u.edges.contains(v)){
//System.out.printf("Constrain %s and %s\n", u, v);

                constrainedMoves.add(m);
                u.moves.remove(m);
                v.moves.remove(m);

                addWorkList(u);
                addWorkList(v);
            }else if (u instanceof PhysicalRegister && George(u,v)
            || ! (u instanceof PhysicalRegister) && Briggs(u, v)){
//System.out.printf("Coalesce %s and %s\n", u, v);

                coalescedMoves.add(m);
                u.moves.remove(m);
                v.moves.remove(m);

                Combine(u,v);
                addWorkList(u);
            }else{
                activeMoves.add(m);
            }
        }

        private boolean Briggs(Register u, Register v){
            int tot = 0;
            for (Register adj : u.edges)
                if (adj.getDegree() >= PhysicalRegister.K)
                    tot++;

            for (Register adj : v.edges)
                if (adj.getDegree() >= PhysicalRegister.K)
                    tot++;

            return tot < PhysicalRegister.K;
        }

        private boolean George(Register u, Register v){
            for (Register t : v.edges) {
                if (!(t.getDegree() < PhysicalRegister.K
                    || t.edges.contains(u) || t instanceof PhysicalRegister && u instanceof PhysicalRegister)
                ) return false;
            }
            return true;
        }

        private void addWorkList(Register reg){
            if (!(reg instanceof PhysicalRegister) &&
                    !reg.moveRelated() &&
                    reg.getDegree() < PhysicalRegister.K){
                freezeList.remove(reg);
                simplifyList.add(reg);
            }
        }

        private void Combine(Register u, Register v){
            freezeList.remove(v);
            spillList.remove(v);
            coalescedRegs.add(v);
            alias.put(v,u);
            u.moves.addAll(v.moves);
            for (Register t : v.edges){
                addEdge(u, t);
                removeEdge(t, v);
            }
            if (u.getDegree() >= PhysicalRegister.K && freezeList.contains(u)) {
                freezeList.remove(u);
                spillList.add(u);
            }
        }

        private void Freeze(){
            Register u = (Register)getElem(freezeList);
            simplifyList.add(u);
            FreezeMoves(u);
        }

        private void FreezeMoves(Register u){
            for (Move m: u.moves){
                Register v;
                if (getAlias(m.getDest()) == u)
                    v = (Register)m.getSrc();
                else
                    v = m.getDest();

                //u.moves.remove(m);
                if (u != v) v.moves.remove(m);

                activeMoves.remove(m);
                frozenMoves.add(m);
                if (v.moves.isEmpty() && v.getDegree() < PhysicalRegister.K){
                    freezeList.remove(v);
                    simplifyList.add(v);
                }
            }
            u.moves.clear();
        }

        private void SelectSpill(){
            Register selected = null;
            double f = 100000;
            for (Register m : spillList)
                if (1.0 * (m.use.size() + m.def.size()) / m.getDegree() < f){
                    f = m.getDegree();
                    selected = m;
                }

            //Register m = (Register)getElem(spillList);
            spillList.remove(selected);
            simplifyList.add(selected);
//System.out.printf("Spilled %s\n", m);
            FreezeMoves(selected);

        }

        private Hashtable<Register, PhysicalRegister> color;

        private void AssignColor(){
            color = new Hashtable<>();
            for (PhysicalRegister r : PhysicalRegister.reg)
                color.put(r, r);

//            for (Register r : coalescedRegs)
//                r.edges.forEach(x->x.edges.add(r));
//
//            for (Register r : selectStack)
//                r.edges.forEach(x->x.edges.add(r));

            for (Edge e : remove_edges){
                //System.out.println(e.from + " " + e.to);
                e.from.edges.add(e.to);
            }

            //System.out.println(selectStack);
            while (!selectStack.isEmpty()) {
                Register r = selectStack.pop();

//                r.edges.forEach(x->x.edges.add(r));

                HashSet<PhysicalRegister> okColors = PhysicalRegister.getUsableRegs();
                for (Register w : r.edges){
                    Register a = getAlias(w);
                    if (a instanceof PhysicalRegister )
                        okColors.remove(a);
                    else if ( coloredRegs.contains(a))
                        okColors.remove(color.get(a));
                }
                if (okColors.isEmpty())
                    spilledRegs.add(r);
                else {
//                    for (Register adj : r.edges) adj.edges.add(r);
                    coloredRegs.add(r);
                    PhysicalRegister c = (PhysicalRegister)getElem(okColors);
                    color.put(r,c);
                }
            }

            for (Register r : coalescedRegs){
                //System.out.println(r + " " + getAlias(r));
                color.put(r, color.get(getAlias(r)));
            }
//            for (Register r: coloredRegs){
//                System.out.printf("COLOR %s -> %s\n", r, color.get(r));
//            }

        }

        private void updateUseAndDefOfRegs(){
            for (Register r : regs){
                r.def = new HashSet<>();
                r.use = new HashSet<>();
            }

            for (BasicBlock bb : fun.getBbList())
                for (IRInstruction ins = bb.getIrStart(); ins != null; ins=ins.getNext()){
                    for (Register r: ins.def) r.def.add(ins);
                    for (Register r: ins.use) r.use.add(ins);
                }
        }

        private void insertSpill(){
//System.out.printf("SPILL \n");
            updateUseAndDefOfRegs();
            for (Register r : spilledRegs){
//System.out.printf("REAL SPILL %s \n", r);
                Spiller spiller = new Spiller(r, fun);
                for (IRInstruction ins : r.def) ins.accept(spiller);
                for (IRInstruction ins : r.use) ins.accept(spiller);
            }
        }

        //private HashSet<Register> init;
        private HashSet<Register> simplifyList;
        private HashSet<Register> freezeList;
        private HashSet<Register> spillList;
        private HashSet<Register> spilledRegs;
        private HashSet<Register> coalescedRegs;
        private HashSet<Register> coloredRegs;
        private Stack<Register> selectStack;

        private HashSet<Move> coalescedMoves;
        private HashSet<Move> constrainedMoves;
        private HashSet<Move> frozenMoves;
        private HashSet<Move> worklistMoves;
        private HashSet<Move> activeMoves;

        public void build(){

            IRTransform trans= new IRTransform(root);
            trans.cleanBBList();
//            System.out.println(fun.getBbList());
            for (BasicBlock bb : fun.getBbList()){
                HashSet<Register> live = new HashSet<>(bb.liveout);
                for (IRInstruction ins = bb.getIrEnd() ; ins != null ; ins = ins.getPrev()) {
//                    System.out.println(ins);
                    live.addAll(ins.def);
                    regs.addAll(ins.def);
                    regs.addAll(ins.use);

                    if (ins instanceof Move && ((Move) ins).getSrc() instanceof Register){
                        live.removeAll(ins.use);
                        for (Register reg : ins.def)
                            reg.moves.add((Move)ins);
                        for (Register reg : ins.use)
                            reg.moves.add((Move)ins);
                        worklistMoves.add((Move)ins);
                    }
                    for (Register r:ins.def)
                        for (Register l : live)
                            fun.graph.addEdge(r,l);
                    live.removeAll(ins.def);
                    live.addAll(ins.use);
                }
            }
        }


        public void rename(){
            Renamer renamer = new Renamer(fun, alias, color);
            renamer.run();
        }
    }



    private IRRoot root;

    public GraphAllocator(IRRoot root) {
        this.root = root;
    }

    public void run(){
        for (Function fun : root.getFunctions()){
            fun.graph = new Graph(root, fun);
            fun.graph.run();
            fun.graph.rename();
            for (BasicBlock bb : fun.getBbList()){
                IRInstruction ins = bb.getIrStart();
                while (ins != null){
                    IRInstruction next = ins.getNext();
                    if (ins instanceof Move){
                        Operand dest = ((Move) ins).getDest();
                        Operand src = ((Move) ins).getSrc();
                        if (dest == src) ins.remove();
                    }
                    ins = next;
                }
            }
        }
        IRTransform after_alloc = new IRTransform(root);
        after_alloc.BBCombine();

        LivenessAnalysis liveness = new LivenessAnalysis(root);
//        liveness.deadCodeEliminate();

        after_alloc.proAndEpi();
    }

    public void print(PrintStream out){
        int fun_index = 1000;
        for (Function fun : root.getFunctions())
            if (fun.graph != null)
            {
                out.printf("subgraph cluster%d\n{\n", fun_index);
                fun_index++;
                fun.graph.print(out);
                out.printf("}\n");
            }
    }
}
