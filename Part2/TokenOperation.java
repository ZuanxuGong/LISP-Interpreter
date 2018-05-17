import java.util.*;

public class TokenOperation{
    private static StringTokenizer tokenizer;
    private static String token;

    public TokenOperation(){
        tokenizer = null;
        token = null;
    }

    public void Tokenize(String line){
        tokenizer = new StringTokenizer(line, "() . \t \n", true);
        token = null;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        if(token == null){
            if(tokenizer.hasMoreTokens()){
                token = tokenizer.nextToken();
                while(Character.isWhitespace(token.charAt(0)))
                    token = tokenizer.nextToken();
            }else
                token = null;
        }
        return token;
    }
}