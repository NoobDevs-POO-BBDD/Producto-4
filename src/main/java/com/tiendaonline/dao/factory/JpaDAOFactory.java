package com.tiendaonline.dao.factory;

import com.tiendaonline.dao.interfaces.ArticuloDAO;
import com.tiendaonline.dao.interfaces.ClienteDAO;
import com.tiendaonline.dao.interfaces.PedidoDAO;
// Importamos TU nueva implementación JPA
import com.tiendaonline.dao.jpa.ClienteDAOJpaImpl;
// Importamos la utilidad para obtener el EntityManager (Es NECESARIO)
import com.tiendaonline.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class JpaDAOFactory implements DAOFactory {

    /**
     * @return null por ahora, para evitar errores si tus compañeros no han acabado.
     */
    @Override
    public ArticuloDAO getArticuloDAO() {
        // Devolvemos null para que no falle si no tienes el archivo ArticuloDAOJpaImpl
        return null;
    }

    /**
     * ESTA ES TU PARTE (CLIENTES)
     * @return una instancia de tu nuevo DAO inyectándole el EntityManager
     */
    @Override
    public ClienteDAO getClienteDAO() {
        // 1. Obtenemos el EntityManager desde tu clase JPAUtil
        EntityManager em = JPAUtil.getEntityManager();

        // 2. Creamos tu DAO pasando ese EntityManager
        // (Esto cumple con el constructor que pusimos en ClienteDAOJpaImpl)
        return new ClienteDAOJpaImpl(em);
    }

    /**
     * @return null por ahora, para evitar errores.
     */
    @Override
    public PedidoDAO getPedidoDAO() {
        // Devolvemos null para que no falle si no tienes el archivo PedidoDAOJpaImpl
        return null;
    }
}