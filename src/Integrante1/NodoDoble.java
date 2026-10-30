
package Integrante1;

public class NodoDoble {

    // Atributos
    private Cancion dato;
    private NodoDoble next;
    private NodoDoble prev;

    // Constructor
    public NodoDoble(Cancion dato) {
        this.dato = dato;
        this.next = null;
        this.prev = null;
    }

    // Getter y Setter del dato
    public Cancion getDato() {
        return dato;
    }

    public void setDato(Cancion dato) {
        this.dato = dato;
    }

    // Getter y Setter del siguiente nodo
    public NodoDoble getNext() {
        return next;
    }

    public void setNext(NodoDoble next) {
        this.next = next;
    }

    // Getter y Setter del nodo anterior
    public NodoDoble getPrev() {
        return prev;
    }

    public void setPrev(NodoDoble prev) {
        this.prev = prev;
    }
}
