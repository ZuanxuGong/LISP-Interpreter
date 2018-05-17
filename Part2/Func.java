import java.util.*;

public class Func{
    private static boolean failure;
    private static SExp dList;
    public static Hashtable<String, Integer> paramsNum = new Hashtable<String, Integer>();

    public Func(){
        dList = new SExp("NIL");
        failure = false;
    }

    public boolean isFailure(){
        return failure;
    }

    public SExp evalSExp(SExp root) {
        failure = false;
        return eval(root, new SExp("NIL"), dList);
    }

    //------EVAL
    private static SExp eval(SExp root, SExp aList, SExp dList){
        if(failure == false){
            if(root.getType() == 1)
                return root;
            else if(root.getType() == 2){
                if(root.getName().equals("T"))
                    return new SExp("T");
                else if(root.getName().equals("NIL"))
                    return new SExp("NIL");
                else if(inAlist(root, aList))
                    return getVal(root, aList);
                System.out.println("> ** Error: unbound atom: " + root.getName() + " **");
                failure = true;
                return new SExp("NIL");
            }else if(root.getLeft().getType() == 2){
                String funcName = root.getLeft().getName();
                if(funcName.equals("QUOTE"))
                    return quote(root);
                else if(funcName.equals("COND"))
                    return evcon(cdr(root), aList, dList);
                else if(funcName.equals("DEFUN"))
                    return defun(root);
                else{
                    int num = getParamNum(root.getRight());
                    if(defFunc(root.getLeft(), dList)){
                        if(num != paramsNum.get(funcName))
                            checkParNum(funcName, num, paramsNum.get(funcName));
                        else
                            return apply(root.getLeft(), evlist(root.getRight(), aList, dList), aList, dList);
                    }else if(root.getRight().getType() == 2)
                        System.out.println("> ** Error: trying to evaluate (" + root.getLeft().getName() + "." + root.getRight().getName() + ") **");
                    else if(root.getRight().getType() == 1)
                        System.out.println("> ** Error: trying to evaluate (" + root.getLeft().getName() + "." + root.getRight().getVal() + ") **");
                    else
                        return apply(root.getLeft(), evlist(root.getRight(), aList, dList), aList, dList);
                    failure = true;
                    return new SExp("NIL");
                }
            }else if(root.getLeft().getType() == 1)
                System.out.println("> ** Error: cannot apply integer atom " + root.getLeft().getVal() + " as a function **");
            else
                System.out.println("> ** Error: cannot apply a non-atom as a function **");
            failure = true;
            return new SExp("NIL");
        }
        return new SExp("NIL");
    }

    //------APPLY
    private static SExp apply(SExp func, SExp param, SExp aList, SExp dList){
        if(failure == false){
            int num = getParamNum(param);
            String name = func.getName();
            if(func.getType() != 2){
                System.out.println("> ** Error: function name should be a symbolic atom. **");
                failure = true;
                return new SExp("NIL");
            }
            if(name.equals("CAR")){
                if(num == 1)
                    return car(car(param));
                checkParNum(name, num, 1);
            }else if(name.equals("CDR")){
                if(num == 1)
                    return cdr(car(param));
                checkParNum(name, num, 1);
            }else if(name.equals("CONS")){
                if(num == 2)
                    return cons(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("ATOM")){
                if(num == 1)
                    return atom(car(param));
                checkParNum(name, num, 1);
            }else if(name.equals("EQ")){
                if(num == 2)
                    return eq(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("NULL")){
                if(num == 1)
                    return fNull(car(param));
                checkParNum(name, num, 1);
            }else if(name.equals("INT")){
                if(num == 1)
                    return fInt(car(param));
                checkParNum(name, num, 1);
            }else if(name.equals("PLUS")){
                if(num == 2)
                    return plus(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("MINUS")){
                if(num == 2)
                    return minus(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("TIMES")){
                if(num == 2)
                    return times(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("QUOTIENT")){
                if(num == 2)
                    return quotient(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("REMAINDER")){
                if(num == 2)
                    return remainder(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("LESS")){
                if(num == 2)
                    return less(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(name.equals("GREATER")){
                if(num == 2)
                    return greater(car(param), car(cdr(param)));
                checkParNum(name, num, 2);
            }else if(defFunc(func, dList)){
                if(num == paramsNum.get(name))
                    return eval(cdr(getVal(func, dList)), combLists(car(getVal(func, dList)), param, aList), dList);  
                checkParNum(name, num, paramsNum.get(name));
            }else
                System.out.println("> ** Error: function " + name + "() is not defined. **");
        }
        failure = true;
        return new SExp("NIL");
    }

    //------Check the number of parameter
    private static void checkParNum(String name, int num, int neededNum){
        if(num > neededNum)
            System.out.println("> ** Error: two many arguments for " + name + " **");
        else
            System.out.println("> ** Error: two few arguments for " + name + " **");
    }

    //------EVLIS
    private static SExp evlist(SExp param, SExp aList, SExp dlist){
        if(param.getName() != null && param.getName().equals("NIL"))
            return new SExp("NIL");
        else
            return cons(eval(car(param), aList, dlist), evlist(cdr(param), aList, dlist));
    }

    //------EVCON
    private static SExp evcon(SExp exp, SExp aList, SExp dlist){
        if(failure == true)
            return new SExp("NIL");
        else if(exp.getName() != null && exp.getName().equals("NIL")){
            System.out.println("> ** Error: all conditions are false. **");
            failure = true;
            return new SExp("NIL");
        }else{
            int num = getParamNum(cdr(car(exp)));
            if(num != 1){
                System.out.println("> ** All clauses of COND must be a list of size 2 **");
                failure = true;
                return new SExp("NIL");
            }
            SExp temp = eval(car(car(exp)), aList, dlist);
            if(temp.getType() == 2 && temp.getName().equals("NIL"))
                return evcon(cdr(exp), aList, dlist);
            else
                return eval(car(cdr(car(exp))), aList, dlist);
            /*if(temp.getType() == 2 && !temp.getName().equals("NIL") || (temp.getType() == 1 && temp.getVal() != 0))
                return eval(car(cdr(car(exp))), aList, dlist);
            else
                return evcon(cdr(exp), aList, dlist);*/
        }
    }

    //get the number of parameters
    private static int getParamNum(SExp exp){
        SExp temp = exp;
        int num = 0;
        while(temp != null){
            if(temp.getLeft() != null)
                num++;
            temp = temp.getRight();
        }
        return num;
    }

    //------In
    private static boolean inAlist(SExp exp, SExp aList){
        if(aList == null || exp == null || aList.getLeft() == null)
            return false;
        else if(car(aList) != null && car(car(aList)) != null){
            if(exp.getName() != null && exp.getName().equals((car(car(aList))).getName()))
                return true;
            else
                return inAlist(exp, cdr(aList));
        }
        return false;
    }

    //------getVal
    private static SExp getVal(SExp exp, SExp aList){
        if(aList == null)
            return new SExp("NIL");
        else if(eq(exp, car(car(aList))).getName().equals("T"))
            return (cdr(car(aList)));
        return getVal(exp, cdr(aList));
    }

    //------combine alists
    private static SExp combLists(SExp list1, SExp list2, SExp aList){
        if(list1.getName() != null && list1.getName().equals("NIL"))
            return aList;
        else
            return (cons(cons(car(list1), car(list2)), combLists(cdr(list1), cdr(list2), aList)));
    }

    //------whether the function is defined
    private static boolean defFunc(SExp exp, SExp list){
        if(list.getName() != null && (list.getName().equals("NIL") || exp.getName().equals("T")))
            return false;
        if(eq(exp, car(car(list))).getName().equals("T"))
            return true;
        return defFunc(exp, cdr(list));
    }

    //-----CAR
    private static SExp car(SExp exp){
        //if(exp.getName() != null && (exp.getName().equals("NIL") || exp.getName().equals("T")))
          //  return new SExp("NIL");
        if(exp.getType() == 3)
            return exp.getLeft();
        String name = exp.getType() == 2 ? exp.getName() : Integer.toString(exp.getVal());
        System.out.println("> ** Error: CAR cannot be applied to the atom " + name + ". **");
        failure = true;
        return new SExp("NIL");
    }

    //-----CDR
    private static SExp cdr(SExp exp){
        //if(exp.getName() != null && (exp.getName().equals("NIL") || exp.getName().equals("T")))
          //  return new SExp("NIL");
        if(exp.getType() == 3)
            return exp.getRight();
        String name = exp.getType() == 2 ? exp.getName() : Integer.toString(exp.getVal());
        System.out.println("> ** Error: CDR cannot be applied to the atom " + name + ". **");
        failure = true;
        return new SExp("NIL");
    }

    //-----CONS
    private static SExp cons(SExp exp1, SExp exp2){
        SExp consSExp = new SExp();
        consSExp.setType(3);
        consSExp.setLeft(exp1);
        consSExp.setRight(exp2);
        return consSExp;
    }

    //-----ATOM
    private static SExp atom(SExp exp){
        if(exp.getType() == 3 || exp.getRight() != null)
            return new SExp("NIL");
        return new SExp("T");
    }

    //-----EQ
    private static SExp eq(SExp exp1, SExp exp2){
        if(exp1.getType() != 3 && exp2.getType() != 3){
            if(exp1.getType() == 1 && exp2.getType() == 1 && exp1.getVal() == exp2.getVal())
                return new SExp("T");
            else if(exp1.getType() == 2 && exp2.getType() == 2 && exp1.getName().equals(exp2.getName()))
                return new SExp("T");
            return new SExp("NIL");
        }
        System.out.println("> ** Error: eq() must be applied to atoms. **");
        failure = true;
        return new SExp("NIL");
    }

    //-----NULL
    private static SExp fNull(SExp exp){
        if(exp == null || exp.getType() != 2 || !exp.getName().equals("NIL"))
            return new SExp("NIL");
        return new SExp("T");
    }
    
    //-----INT
    private static SExp fInt(SExp exp){
        if(exp.getType() == 1)
            return new SExp("T");
        return new SExp("NIL");
    }

    //-----PLUS
    private static SExp plus(SExp exp1, SExp exp2){
        if(exp1.getType() == 1 && exp2.getType() == 1){
            SExp plusSExp = new SExp();
            plusSExp.setType(1);
            plusSExp.setVal(exp1.getVal() + exp2.getVal());
            return plusSExp;
        }
        System.out.println("> ** Error: wrong argument type for PLUS(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----MINUS
    private static SExp minus(SExp exp1, SExp exp2){
        if(exp1.getType() == 1 && exp2.getType() == 1){
            SExp minusSExp = new SExp();
            minusSExp.setType(1);
            minusSExp.setVal(exp1.getVal() - exp2.getVal());
            return minusSExp;
        }
        System.out.println("> ** Error: wrong argument type for MINUS(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----TIMES
    private static SExp times(SExp exp1, SExp exp2){
        if(exp1.getType() == 1 && exp2.getType() == 1){
            SExp timesSExp = new SExp();
            timesSExp.setType(1);
            timesSExp.setVal(exp1.getVal() * exp2.getVal());
            return timesSExp;
        }
        System.out.println("> ** Error: wrong argument type for TIMES(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----QUOTIENT
    private static SExp quotient(SExp exp1, SExp exp2) {
        if(exp1.getType() == 1 && exp2.getType() == 1){
            SExp qtSExp = new SExp();
            qtSExp.setType(1);
            qtSExp.setVal(exp1.getVal() / exp2.getVal());
            return qtSExp;
        }
        System.out.println("> ** Error: wrong argument type for QUOTIENT(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----REMAINDER
    private static SExp remainder(SExp exp1, SExp exp2){
        if (exp1.getType() == 1 && exp2.getType() == 1){
            SExp rmdSExp = new SExp();
            rmdSExp.setType(1);
            rmdSExp.setVal(exp1.getVal() % exp2.getVal());
            return rmdSExp;
        }
        System.out.println("> ** Error: wrong argument type for REMAINDER(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----LESS
    private static SExp less(SExp exp1, SExp exp2){
        if(exp1.getType() == 1 && exp1.getType() == 1){
            if(exp1.getVal() < exp2.getVal())
                return new SExp("T");
            else
                return new SExp("NIL");
        }
        System.out.println("> ** Error: wrong argument type for LESS(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----GREATER
    private static SExp greater(SExp exp1, SExp exp2){
        if(exp1.getType() == 1 && exp2.getType() == 1){
            if(exp1.getVal() > exp2.getVal())
                return new SExp("T");
            else
                return new SExp("NIL");
        }
        System.out.println("> ** Error: wrong argument type for GREATER(). **");
        failure = true;
        return new SExp("NIL");
    }

    //-----COND
    //-----QUOTE
    private static SExp quote(SExp exp){
        //System.out.println("num: " + getParamNum(exp.getRight()));
        if(getParamNum(exp.getRight()) == 1)
            return car(cdr(exp));
        System.out.println("> ** Error: two many arguments for QUOTE(). **");
        failure = true;
        return new SExp("NIL");
    }
    //-----DEFUN
    private static SExp defun(SExp exp){
        int num = getParamNum(cdr(exp));
        if(num != 3){
            checkParNum("DEFUN", num, 3);
            failure = true;
            return new SExp("NIL");
        }
        SExp temp = exp.getRight().getRight().getLeft();
        if(temp.getType() != 3 && !(temp.getType() == 2 && temp.getName() == "NIL")){
            System.out.println("> ** Error: Parameters for a function definition must be a list. **");
            failure = true;
            return new SExp("NIL");
        }
        if(validFunc(exp)){
            updateDList(cdr(exp));
            return new SExp(car(cdr(exp)).getName());
        }
        return new SExp("NIL");
    }

    //------update dList with the new defined function
    private static void updateDList(SExp exp) {
        SExp temp = cons(car(exp), cons(car(cdr(exp)), car(cdr(cdr(exp)))));
        dList = cons(temp, dList);
    }

    //------validFunc
    private static boolean validFunc(SExp exp){
        String name = car(cdr(exp)).getName();
        boolean validName = true;
        if((car(cdr(exp))).getType() != 2 || (name.equals("T")) || (name.equals("NIL")) || (name.equals("CAR"))
            || (name.equals("CDR")) || (name.equals("CONS")) || (name.equals("ATOM")) || (name.equals("EQ"))
            || (name.equals("NULL")) || (name.equals("INT")) || (name.equals("PLUS")) || (name.equals("MINUS")) 
            || (name.equals("TIMES")) || (name.equals("QUOTIENT")) || (name.equals("REMAINDER")) || (name.equals("LESS")) 
            || (name.equals("GREATER")) || (name.equals("COND")) || (name.equals("QUOTE")) || (name.equals("DEFUN")))
            validName = false;
        if(validName){
            SExp temp = car(cdr(cdr(exp)));
            ArrayList<String> params = new ArrayList<String>();
            while(temp != null) {
                if(temp.getLeft() != null) {
                    if(temp.getLeft().getType() == 2){
                        String param = temp.getLeft().getName();
                        if(param.equals("T") || param.equals("NIL"))
                            System.out.println("> ** Error: illegal parameter name for function " + name + "(). **");
                        else
                            params.add(param);
                    }else
                        System.out.println("> ** Error: non-symbolic parameter name for function " + name + "(). **");
                }
                temp = temp.getRight();
            }
            if(params.size() != new HashSet<String>(params).size()) {
                System.out.println("> ** Error: duplicate parameter names for function " + name + "(). **");
                failure = true;
                return false;
            }else
                paramsNum.put(name, params.size());
            return true;
        }
        System.out.println("> ** Error: illegal function name " + name + "() **");
        failure = true;
        return false;
    }
}