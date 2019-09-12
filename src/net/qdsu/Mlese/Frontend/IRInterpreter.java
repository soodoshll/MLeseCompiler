        package net.qdsu.Mlese.Frontend;

        import net.qdsu.Mlese.IR.BasicBlock;
        import net.qdsu.Mlese.IR.BuiltinFunction;
        import net.qdsu.Mlese.IR.IRRoot;
        import net.qdsu.Mlese.IR.Instruction.*;
        import net.qdsu.Mlese.IR.Operand.*;

        import java.util.*;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import static net.qdsu.Mlese.IR.Operand.PhysicalRegister.rax;

public class IRInterpreter implements IRVisitor {
//    Configurations
    public boolean PRINT_INSTRUCTION = false;
    public boolean PRINT_BASICBLOCK  = false;
    public boolean PRINT_STORE       = false;
    public boolean PRINT_LOAD        = false;

    private IRRoot root;
    private IRInstruction curIns;
    private Scope curScope;
    private int return_value;
    private Scanner sc = new Scanner(System.in);
    private static class Scope{
        public Hashtable<Register, Integer> symbol_table = new Hashtable<>();
        public Scope father;
        public IRInstruction next;
        public Register dest;
        public Scope(Scope father, IRInstruction next, Register dest){
            this.father = father;
            this.next = next;
            this.dest = dest;
        }
        private List<String> moniter = Arrays.asList();
        public void put(Register reg, int v){
            for (String s : moniter){
                if (s.equals(reg.toString()))
                    System.out.println("set " + reg + " to be " + v);
            }
            symbol_table.put(reg, v);
        }
        public Integer find(Register reg){
            Integer result = symbol_table.get(reg);
            if (result == null && father != null) return father.find(reg);
            else return result == null ? 0 : result;
        }
        public void print(){
            System.out.println("==== SCOPE ====");
            for (Register r : symbol_table.keySet()){
                System.out.printf("%s : %d\n", r, symbol_table.get(r));
            }
            System.out.println("===============");
        }
    }
    private ArrayList<Integer> heap = new ArrayList<>();
    private ArrayList<String> stringPool = new ArrayList<>();
    private Hashtable<GlobalVar, Integer> global = new Hashtable<>();
    public IRInterpreter(IRRoot root){
        this.root = root;
    }

    private static Pattern intpattern = Pattern.compile("-?[0-9]+");
    private int StrToInt(String str){
        Matcher m = intpattern.matcher(str);
        if (m.find()){
            return Integer.parseInt(str.substring(m.start(), m.end()));
        }else{
            return 0;
        }

    }

    private IRInstruction jump_from;

    private int getHeap(int index){
        return heap.get(index / 8);
    }

    private String getString(int index){
        return stringPool.get(index / 8);
    }

    private int strPoolPtr(){
        return (stringPool.size() - 1) * 8;
    }

    private int heapPtr(){
        return (heap.size() - 1) * 8;
    }

    public void run(){
        curIns = root.getMainFun().getBBStart().getIrStart();
        curScope = new Scope(null, null, null);
        heap.add(0);
        stringPool.add("");
        initGlobalVar();
        BasicBlock last_bb=null;
        while (curIns != null ) {

            if (PRINT_INSTRUCTION)
                System.out.println(curIns);

            if (PRINT_BASICBLOCK)
                if (curIns.getBb()!=last_bb){
                    last_bb  = curIns.getBb();
                    System.out.println(last_bb);
                }

            curIns.accept(this);
        }
        System.out.printf("*** Return Value: %d ***\n", return_value);
    }

    public void initGlobalVar(){
        for (GlobalVar var : root.getGlobal_var()){
            global.put(var, heap.size() * 8);
            heap.add(0);
        }
    }

    private Hashtable<Register, Integer> phi_table;

    public int getValue(Operand op){
        if (op instanceof GlobalVar)
            return global.get(op);
        else if (op instanceof Immediate) {
            return ((Immediate) op).getValue();
        }
        else if (op instanceof Register)
            return curScope.find((Register)op);
        else if (op instanceof StaticString){
            stringPool.add(((StaticString) op).getValue());
            return strPoolPtr(); //return the pointer of newly-added string
        }
        throw new RuntimeException("Can't get value");
    }

    @Override
    public void visit(IRInstruction inst) {

    }

    @Override
    public void visit(BinaryOp inst) {
        int lhs = getValue(inst.getLhs());
        int rhs = getValue(inst.getRhs());
        switch (inst.getOp()){
            case ADD:
                curScope.put(inst.getDest(), lhs + rhs);
                break;
            case SUB:
                curScope.put(inst.getDest(), lhs - rhs);
                break;
            case MUL:
                curScope.put(inst.getDest(), lhs * rhs);
                break;
            case DIV:
                curScope.put(inst.getDest(), lhs / rhs);
                break;
            case MOD:
                curScope.put(inst.getDest(), lhs % rhs);
                break;
            case AND:

                curScope.put(inst.getDest(), lhs & rhs);
                break;
            case OR:
                curScope.put(inst.getDest(), lhs | rhs);
                break;
            case XOR:
                curScope.put(inst.getDest(), lhs ^ rhs);
                break;
            case SHL:
                curScope.put(inst.getDest(), lhs << rhs);
                break;
            case SHR:
                curScope.put(inst.getDest(), lhs >> rhs);
                break;
        }
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Branch inst) {
        int cond;
        if (inst.getCmp() != null){
            cond = getIntCmpValue(inst.getCmp());
        }else {
            cond = getValue(inst.getCond());
        }
        if (cond == 1) curIns = inst.getIfTrue().getIrStart();
        else curIns = inst.getIfFalse().getIrStart();
        jump_from = inst;
    }



    @Override
    public void visit(Call inst) {
        if (inst.getCallee() == BuiltinFunction.print) {

            System.out.print(getString(getValue(inst.getArguments().get(0)) ));
            curIns = inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.println) {

            System.out.println(getString(getValue(inst.getArguments().get(0))));
            curIns = inst.getNext();

        }
        else if (inst.getCallee() == BuiltinFunction.printlnInt) {
            System.out.println(getValue(inst.getArguments().get(0)));
            curIns = inst.getNext();
        }
        else if (inst.getCallee() == BuiltinFunction.toString){

            Integer value = getValue(inst.getArguments().get(0));
            stringPool.add(value.toString());
            curScope.put(inst.getDest(), strPoolPtr());

            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.stringEQ){

            String a = getString(getValue(inst.getArguments().get(0)));
            String b = getString(getValue(inst.getArguments().get(1)));
            curScope.put(inst.getDest(), a.equals(b)?1:0);
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.stringLT) {

            String a = getString(getValue(inst.getArguments().get(0)));
            String b = getString(getValue(inst.getArguments().get(1)));
            curScope.put(inst.getDest(), a.compareTo(b) < 0 ? 1 : 0);
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.stringConcat) {

            String a = getString(getValue(inst.getArguments().get(0)));
            String b = getString(getValue(inst.getArguments().get(1)));
            String c = a + b;
            stringPool.add(c);
            curScope.put(inst.getDest(), strPoolPtr());
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.stringLen) {

            String str = getString(getValue(inst.getArguments().get(0)));
            curScope.put(inst.getDest(), str.length());
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.parseInt) {

            String str = getString(getValue(inst.getArguments().get(0)));
            curScope.put(inst.getDest(), StrToInt(str));
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.subString) {

            int start = getValue(inst.getArguments().get(0));
            int end = getValue(inst.getArguments().get(1));
            String str = getString(getValue(inst.getArguments().get(2)));
            stringPool.add(str.substring(start, end + 1));
            curScope.put(inst.getDest(), strPoolPtr());
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.ord){

            int index = getValue(inst.getArguments().get(0));
            String str = getString(getValue(inst.getArguments().get(1)));
            char [] chararray = str.toCharArray();
            curScope.put(inst.getDest(), chararray[index]);
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.getInt){

            int result = sc.nextInt();
            curScope.put(inst.getDest(), result);
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.getString) {

            String result = sc.next();
            while (result.equals("")) result = sc.next();
            stringPool.add(result);
            curScope.put(inst.getDest(), strPoolPtr());
            curIns =  inst.getNext();

        }else if (inst.getCallee() == BuiltinFunction.arraySize) {

            int addr = getValue(inst.getArguments().get(0));
            curScope.put(inst.getDest(), getHeap(addr));
            curIns = inst.getNext();
        }else if (inst.getCallee() == BuiltinFunction.malloc) {
            curScope.put(inst.getDest(), heap.size() * 8);
            for (int i = 0 ; i < getValue(inst.getArguments().get(0)) / 8 ; i++) heap.add(0);
            curIns =  inst.getNext();
        }else{

            Scope new_scope = new Scope(curScope, inst.getNext(), inst.getDest());
            for (int i = 0 ; i < inst.getArguments().size() ; ++i){
                new_scope.put(inst.getCallee().getParameters().get(i),
                        getValue(inst.getArguments().get(i)));
            }
            curScope = new_scope;
            curIns =  inst.getCallee().getBBStart().getIrStart();

        }

    }

    private int getIntCmpValue(IntCmp inst){
        int lhs = getValue(inst.getLhs());
        int rhs = getValue(inst.getRhs());
        switch (inst.getOp()){
            case LT:
                return lhs < rhs ? 1:0;
            case GT:
                return lhs > rhs ? 1:0;
            case LE:
                return lhs <= rhs ? 1:0;
            case GE:
                return lhs >= rhs ? 1:0;
            case EQ:
                return lhs == rhs ? 1:0;
            case NE:
                return lhs != rhs ? 1:0;
        }
        return 0;
    }

    @Override
    public void visit(IntCmp inst) {
        curScope.put(inst.getDest(), getIntCmpValue(inst));
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Jump inst) {
        curIns = inst.getDest().getIrStart();
        jump_from = inst;
    }

    @Override
    public void visit(Load inst) {
        if (PRINT_LOAD) {
            System.out.println(inst);
            System.out.printf("%s = LOAD %s %d\n",
                    inst.getDest(),
                    getValue(inst.getSrc1()),
                    heap.get(getValue(inst.getSrc1()) / 8)
            );
        }
        curScope.put(inst.getDest(), heap.get(getValue(inst.getSrc1()) / 8));
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Malloc inst) {
        curScope.put(inst.getDest(), heap.size() * 8);
        for (int i = 0 ; i < getValue(inst.getSize()) / 8 ; i++) heap.add(0);
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Move inst) {

        curScope.put(inst.getDest(), getValue(inst.getSrc()));
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Store inst) {
        if (PRINT_STORE) {
            System.out.println(inst);
            System.out.println("STORE " + getValue(inst.getDest1()) + " " + getValue(inst.getSrc()));
        }
        heap.set(getValue(inst.getDest1()) / 8, getValue(inst.getSrc()));
        curIns =  inst.getNext();
    }

    @Override
    public void visit(UnaryOp inst) {
        switch (inst.getOp()){
            case LINC: case RINC:
                curScope.put(inst.getDest(), getValue(inst.getOperand()) + 1);
                break;
            case LDEC: case RDEC:
                curScope.put(inst.getDest(), getValue(inst.getOperand()) - 1);
                break;
            case POS:
                curScope.put(inst.getDest(), getValue(inst.getOperand()));
                break;
            case NEG:
                curScope.put(inst.getDest(), -getValue(inst.getOperand()));
                break;
            case NOT:
                curScope.put(inst.getDest(), ~getValue(inst.getOperand()));
                break;
        }
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Return inst) {
        return_value = inst.getValue() == null ? 0 : getValue(inst.getValue());
        Register ret_reg = curScope.dest;
        curIns  = curScope.next;
        curScope = curScope.father;
        if (ret_reg != null) curScope.put(ret_reg, return_value);
    }

    @Override
    public void visit(Pop inst) {
        //TODO
        curIns = inst.getNext();
    }

    @Override
    public void visit(Push inst) {
        //TODO
        curIns  = inst.getNext();
    }

    @Override
    public void visit(Phi inst) {
        int index = inst.getBb().getFrom().indexOf(jump_from);
        if (inst.getPrev() == null) phi_table = new Hashtable<>();
        phi_table.put(inst.getDest(), getValue(inst.getArg(index)));

        boolean last_phi = true;
        for (IRInstruction i = inst.getNext(); i!=null; i=i.getNext())
            if (i instanceof Phi){
                last_phi = false;
                break;
            }

        if (last_phi){
            for (Register reg : phi_table.keySet()){
                curScope.put(reg, phi_table.get(reg));
            }
        }
        curIns =  inst.getNext();
    }

    @Override
    public void visit(Lea inst) {
        int base = getValue(inst.getBase());
        int index = getValue(inst.getIndex());
        int size = inst.getSize();
        int offset = inst.getOffset();

        //System.out.printf("### %d %d %d %d\n", base, index, size, offset);

        curScope.put(inst.getDest(), base + offset + index * size);
        curIns  = inst.getNext();
    }
}