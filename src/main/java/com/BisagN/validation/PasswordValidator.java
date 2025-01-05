package com.BisagN.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class PasswordValidator {
    private static Pattern pattern;
    private static Matcher matcher;
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!^\\&_.~*]).{8,28})";
    public static boolean validate(final String password) {
    	pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
   /* public static void main(String[] args) {
    	System.out.println("Hello World");
    	pattern = Pattern.compile(PASSWORD_PATTERN);
		String pass = "Bisag@#$%&*1";
		matcher = pattern.matcher(pass);
		System.out.println("Hello World" + matcher);
		System.out.println(pass+"===" +  matcher.matches());
    }*/
}