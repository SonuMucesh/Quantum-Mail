package com.QM;

public class UsernameClass {
    public String name; //this is a field
    public boolean LogicName;

    public  UsernameClass(String newName) {
            name = newName; //assigns the value of a field from within a method
            if(name.contains("@"))
            {
                LogicName = true;
                return;
            }
            else {
                LogicName = false;
                return;
            }

        }
}