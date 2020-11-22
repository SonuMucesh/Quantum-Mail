package com.QM;

public class PasswordClass {
    public String PasswordFromLoginScreen;
    public boolean PasswordLogic;
    public PasswordClass(String NewPassword) {
        PasswordFromLoginScreen = NewPassword; //assigns the value of a field from within a method
        if(PasswordFromLoginScreen.isEmpty())
        {
            PasswordLogic = false;
            return;
        }
        else {
            PasswordLogic = true;
            return;
        }
    }
}