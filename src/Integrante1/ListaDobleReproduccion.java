
package Integrante1;

public class ListaDobleReproduccion {

    // Punteros principales
    private NodoDoble head;
    private NodoDoble tail;
    private NodoDoble cancionActual;

    // Control del reproductor
    private boolean modoBucle;
    private int cantidad;

    // Constructor
    public ListaDobleReproduccion() {
        head = null;
        tail = null;
        cancionActual = null;
        modoBucle = false;
        cantidad = 0;
    }

    // Agregar canción al final en O(1)
    public void agregarAlFinal(Cancion cancion) {

        NodoDoble nuevo = new NodoDoble(cancion);

        // Caso 1: lista vacía
        if (head == null) {
            head = nuevo;
            tail = nuevo;
            cancionActual = nuevo;

        } else {

            // Conectar el nuevo nodo con el último
            nuevo.setPrev(tail);
            tail.setNext(nuevo);

            // Actualizar la cola
            tail = nuevo;

            // Si está activo el modo bucle,
            // mantener la conexión circular
            if (modoBucle) {
                tail.setNext(head);
                head.setPrev(tail);
            }
        }

        cantidad++;

        System.out.println("Canción agregada: "
                + cancion.getTitulo());
    }

    // Pasar a la siguiente canción en O(1)
    public void pasarSiguiente() {

        if (cancionActual == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        if (cancionActual.getNext() == null) {
            System.out.println("Ya estás en la última canción.");
            return;
        }

        cancionActual = cancionActual.getNext();

        mostrarCancionActual();
    }

    // Volver a la canción anterior en O(1)
    public void volverAnterior() {

        if (cancionActual == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        if (cancionActual.getPrev() == null) {
            System.out.println("Ya estás en la primera canción.");
            return;
        }

        cancionActual = cancionActual.getPrev();

        mostrarCancionActual();
    }

    // Activar modo bucle en O(1)
    public void activarModoBucle() {

        if (head == null) {
            System.out.println("No hay canciones para activar el bucle.");
            return;
        }

        modoBucle = true;

        tail.setNext(head);
        head.setPrev(tail);

        System.out.println("Modo bucle activado.");
    }

    // Desactivar modo bucle en O(1)
    public void desactivarModoBucle() {

        if (!modoBucle) {
            System.out.println("El modo bucle ya está desactivado.");
            return;
        }

        modoBucle = false;

        tail.setNext(null);
        head.setPrev(null);

        System.out.println("Modo bucle desactivado.");
    }

    // Cambiar estado del modo bucle
    public void cambiarModoBucle(boolean activar) {

        if (activar) {
            activarModoBucle();
        } else {
            desactivarModoBucle();
        }
    }

    // Mostrar canción actual
    public void mostrarCancionActual() {

        if (cancionActual == null) {
            System.out.println("No hay canción reproduciéndose.");
            return;
        }

        System.out.println("\nReproduciendo:");
        System.out.println(cancionActual.getDato());
    }

    // Mostrar toda la lista
    public void mostrarLista() {

        if (head == null) {
            System.out.println("La lista de reproducción está vacía.");
            return;
        }

        NodoDoble auxiliar = head;

        System.out.println("\n===== LISTA DE REPRODUCCIÓN =====");

        // Recorrido circular
        if (modoBucle) {

            do {
                mostrarNodo(auxiliar);
                auxiliar = auxiliar.getNext();

            } while (auxiliar != head);

        } else {

            // Recorrido normal
            while (auxiliar != null) {
                mostrarNodo(auxiliar);
                auxiliar = auxiliar.getNext();
            }
        }

        System.out.println("Total de canciones: " + cantidad);
        System.out.println("Modo bucle: "
                + (modoBucle ? "Activado" : "Desactivado"));
    }

    // Mostrar información de un nodo
    private void mostrarNodo(NodoDoble nodo) {

        if (nodo == cancionActual) {
            System.out.print(">> ");
        } else {
            System.out.print("   ");
        }

        System.out.println(nodo.getDato());
    }

    // Getters de los punteros principales
    public NodoDoble getHead() {
        return head;
    }

    public NodoDoble getTail() {
        return tail;
    }

    public NodoDoble getCancionActual() {
        return cancionActual;
    }

    public boolean isModoBucle() {
        return modoBucle;
    }

    public int getCantidad() {
        return cantidad;
    }
}
