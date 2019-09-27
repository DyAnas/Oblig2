package no.oslomet.cs.algdat;

import sun.plugin.dom.DOMObject;

import java.util.Arrays;

public class main {
    public static void main(String[]args){
        // skjekke for første oppgave
      String[] s = {"Ole",null,"Per","Kari",null};
      Liste<String> liste = new DobbeltLenketListe<> (s);
        System.out.println (liste.antall ()+ " "+liste.tom ());
        // skjekke for andre oppgave 2a
        String[]s1 ={}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        //DobbeltLenketListe<String> l1= new DobbeltLenketListe<> (s1);
        DobbeltLenketListe<String> l2= new DobbeltLenketListe<> (s2);
        DobbeltLenketListe<String> l3= new DobbeltLenketListe<> (s3);
        System.out.println (" "+l2.toString ()+l2.antall ()+
                l3.toString ()+l3.antall ()+ "  "+
                l2.omvendtString ()+" "+l3.omvendtString ());

        // sjekke for oppgave 2b
        DobbeltLenketListe <Integer> liste1=new DobbeltLenketListe<> ();
        System.out.println (liste1.toString ()+" "+ liste1.omvendtString ());

        for (int i = 1; i<=3; i++){
            liste1.leggInn (i);
            System.out.println (liste1.toString ()+" "+liste1.omvendtString ());
        }
    }
}
