public class SExp {
    private int type; 				/* 1: integer atom; 2: symbolic atom; 3: non-atom */
    private int val; 				/* if type is 1 */
    private String name; 			/* if type is 2 */
    private SExp left, right; 		/* if type is 3 */

    public static final int UNASSIGNED = 0;
    public static final int INTEGER = 1;
    public static final int SYMBOLIC = 2;
    public static final int NONATOM = 3;

    SExp(){
        setType(0);
        setVal(0);
        setName(null);
        setLeft(null);
        setRight(null);
    }
    public void setInteger(int num){
        setType(1);
        setVal(num);
    }

    public void setSymbolic(String exp){
        setType(2);
        setName(exp);
    }

    public void setNonAtom(SExp leftSexp, SExp rightSexp){
        setType(3);
        setLeft(leftSexp);
        setRight(rightSexp);
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getVal(){
        return val;
    }

    public void setVal(int val){
        this.val = val;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public SExp getLeft(){
        return left;
    }

    public void setLeft(SExp left){
        this.left = left;
    }

    public SExp getRight(){
        return right;
    }

    public void setRight(SExp right){
        this.right = right;
    }
}