package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import java.util.function.Predicate;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this (verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        endringer = 0;
        antall = 0;
    }

    public DobbeltLenketListe(T[] a) {
        this ();   // alle variablene er nullet


        if (a == null) {
            throw new NullPointerException ("Tabellen " + a + " er tom");
        }

        int i = 0;
        // finn den første i som ikke er null
        for (; i < a.length; i++) {
            if (a[i] != null) {
                break;
            }
        }

        if (i < a.length) {
            hale = hode = new Node<> (a[i]);
            Node<T> p = hode; // lage ny node p

            p.verdi = a[i]; // sette verdi for første node som ikke er null  i array
            antall++;                                 // vi har minst en node

            for (i++; i < a.length; i++) {
                if (a.length == 1) {
                    break;
                }

                if (a[i] != null) {
                    Node<T> q = new Node<> (a[i]); // lage nye node q

                    q.forrige = p;
                    p.neste = q;
                    p = q;
                    hale = q;
                    antall++;
                }
            }
        }
    }

    public static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0)                             // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > tablengde)                     // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > tablengde(" + tablengde + ")");

        if (fra > til)                           // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    public Liste<T> subliste(int fra, int til) {

        fratilKontroll (antall, fra, til);
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<T> ();
        Node<T> q = hode;
        int i = fra;


        for (; i < til; i++) {
            q = finnNode (i);
            if (q != null) {
                liste.leggInn (q.verdi);
                q = q.neste;
            }
        }
        return liste;

    }

    private Node<T> finnNode(int indeks) {
        Node<T> p;

        if (indeks < antall / 2) {
            p = hode;
            for (int i = 0; i < indeks; i++) p = p.neste;
        } else {
            p = hale;
            for (int i = antall - 1; i > indeks; i--) p = p.forrige;
        }

        return p;
    }


    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if (antall == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull (verdi, "Ikke tillatt med null-verdier!");
        Node<T> q = new Node<> (verdi, null, null);
        Node<T> p = new Node<> (verdi, hale, null);
        if (tom ()) {
            hode = hale = q;
        } else {
            hale = hale.neste = p;
        }
        endringer++;
        antall++;
        return true;

    }

    @Override
    public void leggInn(int indeks, T verdi) {
        if (indeks < 0 || indeks > antall)
            throw new IndexOutOfBoundsException ();

        if (verdi == null) throw new NullPointerException ();
        if (indeks == 0) {
            Node<T> p = new Node<> (verdi);
            p.neste = hode;
            hode.forrige = p;
            hode = p;
        } else if (indeks == antall ()) {
            Node<T> q = new Node<T> (verdi);
            hale.neste = q;
            q.forrige = hale;
            hale = q;
        } else {
            Node<T> p = hode;
            for (int i = 0; i < indeks; i++) p = p.neste;
            Node<T> q = new Node<T> (verdi, p.forrige, p);
            p = q;
            q.forrige.neste = p.neste.forrige = p;
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil (verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll (indeks, false);
        return finnNode (indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;
        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            if (p.verdi.equals (verdi)) return i;
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull (nyverdi, "Ikke tillat med null-verdier");
        indeksKontroll (indeks, false);
        Node<T> p = finnNode (indeks);
        T gamleverdi = p.verdi;
        p.verdi = nyverdi;
        endringer++; // oppdatere en endring
        return gamleverdi;
    }

    @Override
    public boolean fjern(T verdi) {
       if (verdi==null){
           return false;
       }
       Node<T> p= hode;

    if (p==null) return false; //  return false hvis node er lik null


        while (p !=null){
            if (p.verdi.equals (verdi)){
                break;
            }
            p=p.neste;
        }
        // fjerne node hvis den ligger i første
          if (p==hode){
              hode = hode.neste;
              if (hode != null){
                  hode.forrige=null;
              }else{
                  hale=null;
              }
          }
          // hvis node ligger bakerst
          else if (hode ==null){
              hale = hale.forrige;
              hale.neste=null;
          } else {
              p.forrige.neste=p.neste;
              p.neste.forrige=p.forrige;
          }
          p.verdi=null;
          p.forrige=p.neste=null;
          antall--;
          endringer++;
          return true;
    }

    @Override
    public T fjern(int indeks) {
     indeksKontroll (indeks, false);
        Node<T> p ;
        if (indeks==0){
            p = hode;
            hode= hode.neste;
            hode.forrige= null;
        }
        else if (indeks == antall-1){
            p=hale;
            hale=hale.forrige;
            hale.neste=null;
        }
        else{
            Node <T>q=finnNode (indeks-1);
            p= q.neste;
            q.neste= q.neste.neste;
            q.neste.forrige=q;
        }
      antall--;
        endringer++;
        return p.verdi;
    }

    @Override
    public void nullstill() {
        Node<T> p = hode, q = null;

        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer++;

    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder ();

        s.append ('[');

        if (!tom ()) {
            Node<T> p = hode;

            if (p.verdi != null)
                s.append (p.verdi);

            p = p.neste;


            while (p != null)  // tar med resten hvis det er noe mer
            {

                if (p.verdi != null) s.append (',').append (' ').append (p.verdi);
                p = p.neste;
            }
        }

        s.append (']');

        return s.toString ();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder ();

        s.append ('[');

        if (!tom ()) {
            Node<T> p = hale;
            if (p.verdi != null)
                s.append (p.verdi);

            p = p.forrige;

            while (p != null)  // tar med resten hvis det er noe mer
            {

                if (p.verdi != null)
                    s.append (',').append (' ').append (p.verdi);
                p = p.forrige;
            }
        }

        s.append (']');

        return s.toString ();
    }

    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException ();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException ();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            throw new NotImplementedException ();
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new NotImplementedException ();
        }

        @Override
        public boolean hasNext() {
            throw new NotImplementedException ();
        }

        @Override
        public T next() {
            throw new NotImplementedException ();
        }

        @Override
        public void remove() {
            throw new NotImplementedException ();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException ();
    }

} // class DobbeltLenketListe


