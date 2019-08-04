package parserPackage;

import java.io.*;
import java.lang.*;
import parserPackage.Parse;

public class LexicalAnalyzer{

    /*Global Declarations*/
    /*Constants*/
    private static final int LEXLEN = 100;
    private static final int LINELENGTH = 80;
    private static final String FILEPATH = "src\\parserPackage\\statements.txt";



    /*Variables*/
    private static String charClass;

    private static String nextToken;
    public static String getNextToken() {
        return nextToken;
    }
    private static String line;

    private static File f;

    private static int cIndex = 0;
    private static int lexLen;

    private static char[] lexeme = new char[LEXLEN];
    private static char nextChar;

    //Character Classes
    private static final String LETTER = "LETTER";
    private static final String DIGIT = "DIGIT";
    private static final String UNKNOWN = "";

    //Token Codes
    public static final String INT_LIT = "INT_LIT";
    public static final String IDENT = "IDENT";
    public static final String ASSIGN_OP = "ASSIGN_OP";
    public static final String ADD_OP = "ADD_OP";
    public static final String SUB_OP = "SUB_OP";
    public static final String MULT_OP = "MULT_OP";
    public static final String DIV_OP = "DIV_OP";
    public static final String LEFT_PAREN = "LEFT_PAREN";
    public static final String RIGHT_PAREN = "RIGHT_PAREN";
    public static final String EOL = "END_OF_LINE";
    public static final String EOF = "END_OF_FILE";





    /*Entrance Method
    * Reads file and runs the analyzer*/
    public static void main(String[] args) throws IOException {
        try {
            //Check File
            if((f = new File(FILEPATH)) == null)
                System.out.println("Error - cannot open file 'lexInput.txt'");
            else {
                BufferedReader b = new BufferedReader(new FileReader(f));
                //Begin looping Lines
                while ((line = b.readLine()) != null) {
                    printNewLine();
                    System.out.println("Parsing the Statement: " + line + "\n");
                    resetLine();
                    getChar();
                    do {
                        lex();
                        Parse.assign();
                    } while (nextToken != EOL);
                }
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                printNewLine();
                outputLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }




    /*Begin Controllers*/

    //Manages output and lookup dispatching
    public static int lex(){
        lexLen=0;
        getNonBlank();
        switch (charClass){
            case LETTER:
                addChar();
                getChar();
                while(charClass == LETTER || charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            case UNKNOWN:
                lookupToken(nextChar);
                getChar();
                break;
            case EOL:
                nextToken = EOL;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'L';
                lexeme[3] = 0;
                break;
        }
        outputLine();
        lexeme = new char[LINELENGTH];
        return 0;
    }




    /*Begin Models*/

    //adds next character to lexeme
    private static void addChar(){
        if (lexLen <= 98){
            lexeme[lexLen++]=nextChar;
            lexeme[lexLen]=0;
        }
        else{
            System.out.println("Error - lexeme is too long");
        }
    }
    //grabs next character and assigns character class
    private static void getChar(){
        if (checkExist()){
            if (isAlpha(nextChar)){
                charClass = LETTER;
            }
            else if (isDigit(nextChar)){
                charClass = DIGIT;
            }
            else{
                charClass = UNKNOWN;
            }
        }
        else {
            charClass = EOL;
        }
    }
    //Returns the index of the next non-blank character in array
    private static void getNonBlank() {
        while (isWhitespace(nextChar))
        {getChar();}
    }

    //Fetches and assigns token identifier
    private static String lookupToken(char ch){
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            default:
                addChar();
                nextToken = EOL;
        }
        return nextToken;
    }

    //checks if Alpha, Digit, or whitespace respectively
    private static boolean isAlpha(char ch){
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            return true;
        }
        else{
            return false;
        }
    }
    private static boolean isDigit(char ch){
        if (ch >= '0' && ch <= '9'){
            return true;
        }
        else {
            return false;
        }
    }
    private static boolean isWhitespace(char ch){
        if ((ch == ' ') || (ch =='\n') || (ch == '\t')){
            return true;
        }
        else{
            return false;
        }
    }

    //Checks the existence of the character at the next index.
    private static boolean checkExist() throws java.lang.StringIndexOutOfBoundsException{
        try {
            nextChar = line.charAt(cIndex++);
            return true;
        }
        catch(java.lang.StringIndexOutOfBoundsException e){
            return false;
        }
    }

    //Used to reset line variables in between reads
    private static void resetLine(){
        cIndex = 0;
        lexeme = new char[LEXLEN];
    }

    /*Begin Views*/

    //Just prints a line of * with length equal to LINELENGTH
    private static void printNewLine(){
        String rtn ="";
        for (int i=0;i<LINELENGTH; i++)
            rtn += "*";
        System.out.println(rtn);
    }

    //outputs a formatted line
    private static void outputLine(){
        if (nextToken == EOL){
            /* Uncomment this block to add an end of line Token
            String output = "Next token is: " + nextToken;
            output += padRight(output);
            output += "Next lexeme is ";
            for (char t :
                    lexeme) {
                output += t;
            }
            System.out.println(output);*/
        }


        else {
            String output = "Next token is: " + nextToken;
            output += padRight(output);
            output += "Next lexeme is ";
            for (char t :
                    lexeme) {
                output += t;
            }
            System.out.println(output);
        }
    }

    //provides padding to the outputed lines
    private static String padRight(String line){
        String rtn = "";
        while ((line.length()+rtn.length()) <= LINELENGTH/2){
            rtn+=" ";
        }
        return rtn;
    }
}