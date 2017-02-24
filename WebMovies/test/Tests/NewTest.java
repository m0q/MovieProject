/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mqul
 */
public class NewTest {
    private boolean idCheck(String id){
       String s = id.chars()
                    .filter(c -> Character.isDigit(c))
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
       
       if(s.length() == 7){
        return true;
       }
       return false;    
    }
    
    public static void main (String[] args){
        NewTest nt = new NewTest();
        
        boolean b = nt.idCheck("0394828");
        
        ArrayList<Integer> al = new ArrayList<>();
        
        while(al.size() < 10){
            if(al.size() < 2){
                al.add(1);
                al.add(1);
            }
            int tmp = al.get(al.size()-1) + al.get(al.size()-2);
            al.add(tmp);
        }
        
        al.forEach(s -> System.out.print(s+", "));
    }
}
