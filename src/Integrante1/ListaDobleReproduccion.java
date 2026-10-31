
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

    // Desconecta tail y head si el bucle esta activo, para operar como lista lineal 20
    private void romperCirculo() {

        if (modoBucle && head != null) {
            tail.setNext(null);
            head.setPrev(null);
        }
    }

    // Vuelve a conectar tail con head si el bucle esta activo 20
    private void cerrarCirculo() {

        if (modoBucle && head != null) {
            tail.setNext(head);
            head.setPrev(tail);
        }
    }

    // Saca el nodo de la lista uniendo a sus vecinos y actualiza head y tail 20
    private void desconectar(NodoDoble nodo) {

        NodoDoble ant;
        NodoDoble sig;

        ant = nodo.getPrev();
        sig = nodo.getNext();

        if (ant != null) {
            ant.setNext(sig);
        } else {
            head = sig;
        }

        if (sig != null) {
            sig.setPrev(ant);
        } else {
            tail = ant;
        }
    }

    // Mete el nodo despues de ant (si ant es null lo deja de primero) 20
    private void conectarDespuesDe(NodoDoble nodo, NodoDoble ant) {

        NodoDoble sig;

        if (ant == null) {
            nodo.setPrev(null);
            nodo.setNext(head);

            if (head != null) {
                head.setPrev(nodo);
            } else {
                tail = nodo;
            }

            head = nodo;
        } else {
            sig = ant.getNext();

            nodo.setPrev(ant);
            nodo.setNext(sig);
            ant.setNext(nodo);

            if (sig != null) {
                sig.setPrev(nodo);
            } else {
                tail = nodo;
            }
        }
    }

    // Inserta un nodo ya creado despues de ant, tambien sirve para el undo 20
    public int insertarNodoDespuesDe(NodoDoble nodo, NodoDoble ant) {

        if (nodo == null) {
            return -1;
        }

        romperCirculo();

        conectarDespuesDe(nodo, ant);
        cantidad++;

        if (cancionActual == null) {
            cancionActual = nodo;
        }

        cerrarCirculo();

        return 1;
    }

    // Play Next: crea una cancion nueva justo despues de la actual 20
    public int insertarDespuesDeActual(Cancion cancion) {

        if (cancion == null) {
            return -1;
        }

        return insertarNodoDespuesDe(new NodoDoble(cancion), cancionActual);
    }

    // Quita el nodo dado y lo devuelve; si era el actual, mueve cancionActual 20
    public NodoDoble eliminarNodoEspecifico(NodoDoble nodo) {

        NodoDoble ant;
        NodoDoble sig;

        if (nodo == null || head == null) {
            return null;
        }

        romperCirculo();

        ant = nodo.getPrev();
        sig = nodo.getNext();

        desconectar(nodo);
        cantidad--;

        if (nodo == cancionActual) {
            if (sig != null) {
                cancionActual = sig;
            } else if (modoBucle && head != null) {
                cancionActual = head;
            } else {
                cancionActual = ant;
            }
        }

        if (cantidad == 0) {
            modoBucle = false;
        }

        cerrarCirculo();

        return nodo;
    }

    // Elimina la cancion que esta sonando y devuelve su nodo 20
    public NodoDoble eliminarCancionActual() {

        return eliminarNodoEspecifico(cancionActual);
    }

    // Cambia el nodo de posicion, dejandolo despues de destino 20
    public int moverCancion(NodoDoble nodo, NodoDoble destino) {

        if (nodo == null || head == null || nodo == destino) {
            return -1;
        }

        romperCirculo();

        desconectar(nodo);
        conectarDespuesDe(nodo, destino);

        cerrarCirculo();

        return 1;
    }

    // Pone cancionActual directamente en el nodo elegido 20
    public int saltarACancion(NodoDoble nodo) {

        if (nodo == null || head == null) {
            return -1;
        }

        cancionActual = nodo;

        return 1;
    }

    // Busca un nodo por el id de su cancion, devuelve null si no existe 20
    public NodoDoble buscarNodoPorId(int id) {

        NodoDoble auxiliar;
        int cont;

        auxiliar = head;
        cont = 0;

        while (auxiliar != null && cont < cantidad) {
            if (auxiliar.getDato().getId() == id) {
                return auxiliar;
            }

            auxiliar = auxiliar.getNext();
            cont++;
        }

        return null;
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
