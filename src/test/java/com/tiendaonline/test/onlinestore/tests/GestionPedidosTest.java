package com.tiendaonline.test.onlinestore.tests;

import com.tiendaonline.model.Pedido;
import com.tiendaonline.model.TiendaOnline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionPedidosTest {

    TiendaOnline tienda;

    @BeforeEach
    void setUp()  throws Exception{
        tienda = new TiendaOnline();
        try{
            tienda.cargarDatosDePrueba(); // Carga los artículos iniciales
        }catch(IllegalArgumentException e){
            System.out.println("Aviso: Datos de prueba ya cargados. Continuando...");
        }
    }

    @Test
    void testAnadirPedidoValido()throws Exception {
        tienda.anadirPedido("P006", "anna@mail.com", "A001", 2);
        Pedido pedido = tienda.buscarPedido("P006");
        assertNotNull(pedido);
        assertEquals(2, pedido.getCantidad());
        assertEquals("anna@mail.com", pedido.getCliente().getEmail());
        assertFalse(pedido.isEstado()); // Pendiente por defecto
    }

    @Test
    void testAnadirPedidoArticuloNoExiste() throws Exception{
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tienda.anadirPedido("P007", "anna@mail.com", "A999", 1);
        });
        assertEquals("No existe el artículo con código: A999", exception.getMessage());
    }

    @Test
    void testAnadirPedidoClienteNoExiste() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tienda.anadirPedido("P008", "no.existe@mail.com", "A001", 1);
        });
        assertTrue(exception.getMessage().contains("No existe el cliente con email: no.existe@mail.com"));
    }

    @Test
    void testAnadirPedidoCantidadInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tienda.anadirPedido("P009", "anna@mail.com", "A001", 0);
        });
        assertEquals("La cantidad debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testEliminarPedidoPendienteCancelable() throws Exception{
        boolean eliminado = tienda.eliminarPedido("P001"); // Pedido reciente y pendiente
        assertTrue(eliminado);
        assertNull(tienda.buscarPedido("P001"));
    }

    @Test
    void testEliminarPedidoNoCancelable() throws Exception{
        boolean eliminado = tienda.eliminarPedido("P003"); // Pedido antiguo
        assertFalse(eliminado);
    }

    @Test
    void testEliminarPedidoEnviado() throws Exception{
        boolean eliminado = tienda.eliminarPedido("P004"); // Pedido enviado
        assertFalse(eliminado);
    }

    @Test
    void testMarcarPedidoComoEnviado() throws Exception{
        tienda.marcarPedidoComoEnviado("P002");
        Pedido pedido = tienda.buscarPedido("P002");
        assertTrue(pedido.isEstado());
    }

    @Test
    void testMostrarPedidosPendientesYEnviados()throws Exception {
        List<Pedido> pendientes = tienda.mostrarPedidosPendientes();
        List<Pedido> enviados = tienda.mostrarPedidosEnviados();

        assertTrue(pendientes.stream().allMatch(p -> !p.isEstado()));
        assertTrue(enviados.stream().allMatch(Pedido::isEstado));
    }

    @Test
    void testMostrarPedidosFiltradosPorCliente() throws Exception{
        List<Pedido> pendientesAnna = tienda.mostrarPedidosPendientes("ana@mail.com");
        List<Pedido> enviadosKevin = tienda.mostrarPedidosEnviados("kevin@mail.com");

        assertTrue(pendientesAnna.stream().allMatch(p -> p.getCliente().getEmail().equals("anna@mail.com") && !p.isEstado()));
        assertTrue(enviadosKevin.stream().allMatch(p -> p.getCliente().getEmail().equals("kevin@mail.com") && p.isEstado()));
    }
}