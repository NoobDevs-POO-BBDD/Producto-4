package com.tiendaonline.dao.factory;

import com.tiendaonline.dao.impl.ClienteDAOImpl;
import com.tiendaonline.dao.interfaces.ArticuloDAO;
import com.tiendaonline.dao.interfaces.ClienteDAO;
import com.tiendaonline.dao.interfaces.PedidoDAO;
import com.tiendaonline.dao.jpa.ArticuloDAOJpaImpl;
import com.tiendaonline.dao.jpa.ClienteDAOJpaImpl;
import com.tiendaonline.dao.jpa.PedidoDAOJpaImpl;
import com.tiendaonline.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class JpaDAOFactory implements  DAOFactory {

    /**
     * @return se pasa em el entity Manager
     */
    @Override
    public ArticuloDAO getArticuloDAO(){
        EntityManager em = JPAUtil.getEntityManager();
        return new ArticuloDAOJpaImpl(em);
    }

    /**
     * @return una instancia de tu nuevo DAO inyectándole el EntityManager
     */
    @Override
    public ClienteDAO getClienteDAO() {
        EntityManager em = JPAUtil.getEntityManager();
        // Usamos ClienteDAOJpaImpl (no ClienteDAOImpl, que era la vieja)
        return new ClienteDAOJpaImpl(em);
    }

    /**
     * @return se pasa em el entity Manager
     */
    @Override
    public PedidoDAO getPedidoDAO() {
        EntityManager em = JPAUtil.getEntityManager();
        return new PedidoDAOJpaImpl(em);
    }
}
