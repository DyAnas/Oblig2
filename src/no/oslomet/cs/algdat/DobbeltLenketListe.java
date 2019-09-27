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
        this (); // alle variablene er nullet
        if (a == null) {
            throw new NullPointerException ("Tabellen " + a + " er tom");
        }

        int i = 0;
        // finn den første i som ikke er null
        for (; i < a.length && a[i] != null; i++) ;

        if (i < a.length) {
            hale = hode = new Node<> (a[i]);
            Node<T> p = hode; // lage ny node p
            p.verdi = a[i]; // sette verdi for første node som ikke er null  i array
            antall = 1;                                 // vi har minst en node
            for (; i < a.length; i++) {
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
    public static void fratilKontroll(int tablengde, int fra, int til)
    {
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

    // fjerner den midlertidige noden
    public Liste<T> subliste(int fra, int til) {
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<T>();
                fratilKontroll(liste.antall(), fra,til);
         Node<T> p= hode;
         int i = 0;
         while (p != null){
             if (i> fra && i < til){
                 liste.leggInn (p.verdi);

             }
             p=p.neste;
             i++;

         }
         return liste;
    }

    private Node<T> finnNode(int indeks) {

        if (indeks < antall / 2) {
            Node<T> p = hode;
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
                return p;
            }
        }
        Node<T> q = hale;
        for (int i = 0; i < indeks; i++) q = q.forrige;
        return q;

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
            hale = hode = q;
        } else {
            hale = hale.neste = p;
        }
        endringer++;
        antall++;
        return true;

    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new NotImplementedException ();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new NotImplementedException ();
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
         return finnNode (indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new NotImplementedException ();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull (nyverdi,"Ikke tillat med null-verdier");
      indeksKontroll (indeks, false);
      Node<T> p = finnNode (indeks);
      T gamleverdi= p.verdi;
      p.verdi= nyverdi;
      endringer++; // oppdatere en endring
        return gamleverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException ();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException ();
    }

    @Override
    public void nullstill() {
        throw new NotImplementedException ();
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

                if (p.verdi != null) s.append (',').append (p.verdi);
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


