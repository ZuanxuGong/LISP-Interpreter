import java.util.*;

public class Parser {
    public static SExp root;
    private static boolean failure;
    private static TokenOperation TokenOp = new TokenOperation();

    public Parser(String line){
        root = null;
        failure = false;
        TokenOp.Tokenize(line);
    }

    public void doParsing(String line){
        failure = false;
        if(existIllegalChar(line)){
            failure = true;
            return;
        }
        root = new SExp();
        parseSExp(root);
        String curToken = TokenOp.getToken();
        if (!failure && curToken != null){
            System.out.println("> ** Error: unexpected dot. **");
            failure = true;
            root = null;
        }
    }

    private void parseSExp(SExp node){
        String curToken = TokenOp.getToken();
         // <E>::=(<L>
        if(curToken.equals("(")){
            TokenOp.setToken(null);
            // ( + ")" or "<L><R>"
            curToken = TokenOp.getToken();
            if(curToken.equals(")")){
                TokenOp.setToken(null);
                node.setSymbolic("NIL");
            }else{
                TokenOp.setToken(curToken);
                SExp leftExp = new SExp(), rightExp = new SExp();
                parseSExp(leftExp);
                curToken = TokenOp.getToken();
                // <L> + ".<dotR>" or "<ListR>"
                if(curToken.equals(".")){
                    TokenOp.setToken(null);
                    String nxtToken = TokenOp.getToken();
                    if(nxtToken.equals(")")){
                        System.out.println("> ** Error: unexpected dot. **");
                        failure = true; 
                        return;   
                    }
                    parseSExp(rightExp);
                    nxtToken = TokenOp.getToken();
                    if(nxtToken.equals(")"))
                        TokenOp.setToken(null);
                }else{
                    TokenOp.setToken(curToken);
                    parseListR(rightExp);
                    String nxtToken = TokenOp.getToken();
                    if(nxtToken.equals(")"))
                        TokenOp.setToken(null);
                    else
                        TokenOp.setToken(nxtToken);
                }
                node.setNonAtom(leftExp, rightExp);
            }
        }else{
            if(isSymbol(curToken)){
                node.setSymbolic(curToken);
            }else if(isInteger(curToken)){
                if(curToken.charAt(0) == '+')
                    curToken = curToken.substring(1);
                node.setInteger(Integer.parseInt(curToken));
            }else{
                System.out.println("> ** Error: unexpected atom: " + curToken + " **");
                failure = true;
            }
            TokenOp.setToken(null);
        }
    }

    private void parseListR(SExp node){
        String curToken = TokenOp.getToken();
        //<L> + "<E><ListR>"" or "NIL"
        if(curToken != null && !curToken.equals(")")){
            SExp leftExp = new SExp(), rightExp = new SExp();
            parseSExp(leftExp);
            parseListR(rightExp);
            node.setNonAtom(leftExp, rightExp);
        }else
            node.setSymbolic("NIL");
    }

    //Check whether exists illegal character
    private static boolean existIllegalChar(String line){
        int i = -1 , n = line.length();
        while(++i < n){
            char c = line.charAt(i);
            if((c == '+' || c == '-') && (i == n - 1 || line.charAt(i + 1) < 48 || line.charAt(i + 1) > 57)){
                System.out.println("> ** Error: sign + and - should be followed by an integer immediately. **");
                return true;
            }else if(c == '$'){
                System.out.println("> ** Error: unexpected dollar. **");
                return true;
            }else if(!((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c == '(')
                    || (c == ')') || (c == '.') || (c == '+') || (c == '-')
                    || (c == ' ') || (c == '\n') || (c == '\t'))){
                System.out.println("> ** Error: exists Illegal character: " + c + " **");
                return true;
            }
        }
        return false;
    }

    // Check whether it is a valid integer atom
    private static boolean isInteger(String token){
        int i = 0;
        if((token.charAt(i) >= 48 && token.charAt(i) <= 57) || token.charAt(i) == '+' || token.charAt(i) == '-'){
            while(++i < token.length()){
                if (token.charAt(i) < 48 || token.charAt(i) > 57)
                    return false;
            }
        }else
            return false;
        return true;
    }

    //Check whether it is a valid symbolic atom
    private static boolean isSymbol(String token){
        int i = 0;
        if(token.charAt(i) >= 65 && token.charAt(i) <= 90){
            while(++i < token.length()){
                if(token.charAt(i) < 48 || (token.charAt(i) > 57 && token.charAt(i) < 65) || token.charAt(i) > 90)
                    return false;
            }
        }else
            return false;
        return true;
    }

    public boolean isfailure(){
        return failure;
    }
}