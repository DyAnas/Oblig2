package no.oslomet.cs.algdat;

import sun.plugin.dom.DOMObject;

import java.util.Arrays;

public class main {
    public static void main(String[]args){
        // skjekke for første oppgave
        Liste<String> l = new DobbeltLenketListe<> ();
        System.out.println ("---------------oppgave 1 a---------------");
        System.out.println (l.antall ()+ " "+l.tom ());
        System.out.println ("---------------oppgave 1 b---------------");
      String[] s = {"Ole",null,"Per","Kari",null};
      Liste<String> liste = new DobbeltLenketListe<> (s);
        System.out.println (liste.antall ()+ " "+liste.tom ());
        // skjekke for andre oppgave 2a
        System.out.println ("---------------oppgave 2a---------------");
        String[]s1 ={}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1= new DobbeltLenketListe<> (s1);
        DobbeltLenketListe<String> l2= new DobbeltLenketListe<> (s2);
        DobbeltLenketListe<String> l3= new DobbeltLenketListe<> (s3);
        System.out.println (l1.toString ()+" "+l2.toString ()+l2.antall ()+
                l3.toString ()+l3.antall ()+ "  "+l1.omvendtString ()+
                l2.omvendtString ()+" "+l3.omvendtString ());

        // sjekke for oppgave 2b
        System.out.println ("---------------oppgave 2b---------------");
        DobbeltLenketListe <Integer> liste1=new DobbeltLenketListe<> ();
        System.out.println (liste1.toString ()+" "+ liste1.omvendtString ());

        for (int i = 1; i< 4; i++){
            liste1.leggInn (i);
            System.out.println (liste1.toString ()+" "+liste1.omvendtString ());
        }


        // sjekke oppgave 3
        System.out.println ("---------------oppgave 3---------------");
        Character[] c = {'A','B','c','D','e','f','g','h','i','j',};

      DobbeltLenketListe <Character> list=new DobbeltLenketListe<> (c);
      //list.leggInn ('a');
       System.out.println (list.subliste (3,8));
        System.out.println (list.subliste (5,5));
        System.out.println (list.subliste (8,list.antall ()));
      // System.out.println (list.subliste (0,11));



     // skejkke oppgave 4
        System.out.println ("---------------oppgave 4---------------");
        System.out.println ("Indeks til 1: "+ liste1.indeksTil (1));
        System.out.println ("tall 4 innholder i liste{1,2,3}: "+ liste1.inneholder (5));
        // skejkke oppgave 5
        System.out.println ("---------------oppgave 5---------------");
        liste1.leggInn (0,0);
        System.out.println ("legg nummer 0 til liste {1,2,3} i foran"+liste1.toString ());
      liste1.leggInn (4,5);
     System.out.println ("legg nummer 4 til liste {4,1,2,3} i bakerst"+liste1.toString ());
        liste1.leggInn (4,4);
        System.out.println ("legg nummer 23 til liste {4,1,2,3,4} i midten "+liste1.toString ());

    }
}
