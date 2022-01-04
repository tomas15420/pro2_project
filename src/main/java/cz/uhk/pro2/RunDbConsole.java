package cz.uhk.pro2;

import org.apache.derby.tools.ij;

public class RunDbConsole {
    public static void main(String[] args){
        try{
            //connect 'jdbc:derby:ChatClientDb;create=true';
            //connect 'jdbc:derby:ChatClientDb';
            ij.main(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
