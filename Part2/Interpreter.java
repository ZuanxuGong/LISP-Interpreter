import java.util.*;
import java.io.*;

public class Interpreter{
    static String tokenSet = "";
    static int leftParenNum = 0, rightParenNum = 0, tokenNum = 0;
    static boolean validList = false;

    public static void main(String[] args){
 		try{
            System.out.println("/* Wecome to use LISP Interpreter -- Zuanxu Gong(gong.366) */");
            System.out.println("/* Exit method: type $$ on a single line  */");
            System.out.println("/* Using existing test file: java LispInterpreter < l2input.txt */");
            System.out.println("/* Using customized test case: SExp followed by the $ sign on a single line */");

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String inputLine = "";
            StringTokenizer tokenizer;
            Func func = new Func();
            while((inputLine = input.readLine()) != null){
                //System.out.println(inputLine);
                inputLine = inputLine.toUpperCase();
                tokenizer = new StringTokenizer(inputLine, "() . \t", true);
                while(tokenizer.hasMoreTokens()){
                	String token = tokenizer.nextToken();
                	if(token.charAt(0) == '(')
                    	leftParenNum++;
                    else if(token.charAt(0) == ')')
                        rightParenNum++;
                    else if(token.charAt(0) == ' ' || token.charAt(0) == '\t')
                        continue;
                    if((inputLine.equals("$") || inputLine.equals("$$"))){
                        if(leftParenNum != rightParenNum)
                            System.out.println("> ** Error: unequal parentheses **");
                        else if(leftParenNum == 0 && rightParenNum == 0 && tokenNum > 1)
                            System.out.println("> ** Error: input SExp should be a list or an atom **");
                        else if(tokenNum == 0)
                            System.out.println("> ** empty input **");
                        else{
                            Parser parser = new Parser(tokenSet);
            				parser.doParsing(tokenSet);
                            if(parser.root != null){
                                validList = false;
                                checkValidList(parser.root);
        				        if((parser.root.getType() != 3 || validList) && !parser.isFailure()){
                                    SExp evalRes = func.evalSExp(parser.root);
                                    if(!func.isFailure()){
                                        System.out.print("> ");
                                        if(evalRes.getType() == 1)
                                            System.out.println(Integer.toString(evalRes.getVal()));
                                        else if(evalRes.getType() == 2)
                                            System.out.println(evalRes.getName());
                                        else{
                                            printSExpTree(evalRes);
                                            System.out.println();
                                        }
                                    }
                                    System.out.print("dot notation: ");
                                    printSExpTree(parser.root);
                                    System.out.println();
        				        }
                                if(parser.root.getType() == 3 && (!validList) && !parser.isFailure()){
                                    System.out.println("> ** Error: Invalid list since the final atom we reach by applying cdr repeatedly should be NIL. **");
                                    System.out.print("dot notation: ");
                                    printSExpTree(parser.root);
                                    System.out.println();
                                }
                            }
                        }
                        tokenSet = "";
    			        leftParenNum = 0;
    			        rightParenNum = 0;
    			        tokenNum = 0;
                    }else{
                        tokenSet += (" " + token);
                        tokenNum++;
                    }
                }
                if (inputLine.equals("$$")){
                    System.out.println("Thank you for using! Have a good day. :)");
                    break;
                }
            }
        }catch(Exception exMessage){
            System.out.println("> ** Error: " + exMessage.getMessage() + " **");
        }
    }

    private static void printSExpTree(SExp node){
        if(node == null || node.getType() == 0)
            return;
        else if(node.getType() == 1){
            System.out.print(node.getVal());
            return;
        }else if(node.getType() == 2){
            System.out.print(node.getName());
            return;
        }
        System.out.print("(");
        printSExpTree(node.getLeft());
        System.out.print(" . ");
        printSExpTree(node.getRight());
        System.out.print(")");
    }

    private static void checkValidList(SExp rootSExp){
        if(!validList && rootSExp != null){
            if(rootSExp.getType() == 2 && rootSExp.getName().equals("NIL"))
                validList = true;
            else if(rootSExp.getRight() != null)
                checkValidList(rootSExp.getRight());
        }
    }
}