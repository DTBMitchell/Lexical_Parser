package parserPackage;

//Imports the functions from my Lexical Analyzer
//The 'main' function was placed in there
import static parserPackage.LexicalAnalyzer.*;

public class Parse {

    /*Entrance to Grammar*/
    //<assign> -> id = <expr>
    public static void assign(){
        System.out.println("Enter <assign>");

        //Checks the next token for an identifier and the assignment operator
        if (getNextToken() == IDENT ) {
            lex();
            if (getNextToken() == ASSIGN_OP) {
                lex();
                //Will follow this Token and Lexeme until factor
                expr();
            }
        }
        else{
            System.out.println("This is not a valid ASSIGN statement");
        }

        System.out.println("Exit <assign>");
    }

    /*Expression Grammar*/
    //<expr> -> <term> {(+|-) <term>}
    public static void expr(){
        System.out.println("Enter <expr>");

        term();

        //Confirms next token is addition or subtraction operators
        while (getNextToken() == ADD_OP || getNextToken() == SUB_OP){
            lex();
            term();
        }
        System.out.println("Exit <expr>");
    }

    /*Term Grammar*/
    //<term> -> <factor>{(+|-) <factor>}
    public static void term(){
        System.out.println("Enter <term>");

        factor();

        //Confirms next Token is not multiplication or division operators
        while (getNextToken() == MULT_OP || getNextToken() == DIV_OP){
            lex();
            factor();
        }
        System.out.println("Exit <term>");
    }

    /*Factor Grammar*/
    //<factor> -> id | int_constant | (<expr>)
    public static void factor(){
        System.out.println("Enter <factor>");

        //If the token is IDENT or INT_LIT prints it out and exits factor
        if (getNextToken() == IDENT || getNextToken() == INT_LIT){
            lex();
        }
        //If the token is a LEFT_PAREN, print the token
        //Then user Parse recursively to confirm the RIGHT_PAREN
        else{
            if (getNextToken() == LEFT_PAREN){
                lex();
                expr();
                if(getNextToken() == RIGHT_PAREN){
                    lex();
                }
                else{
                    System.out.println("Next token is: " + getNextToken() + " but Parse is expecting RIGHT_PAREN");
                }
            }
        }
        System.out.println("Exit <factor>");
    }
}
