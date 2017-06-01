/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankescomaester.dao;

import banco.util.NewHibernateUtil;
import com.bankescomaester.entities.Cuenta;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author isaac_stark
 */
public class BankDAO {
    
    public void consultar( Cuenta c ) {
        Session sesion = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        String hql = "FROM Cuenta as c WHERE c.noTarjetaCredito =:tarjeta";
        List<Cuenta> cuentas;
        
        try {
            Query q = sesion.createQuery( hql );
            q.setParameter("tarjeta", c.getNoTarjetaCredito() );
            cuentas = q.list();
            sesion.flush();
            trans.commit();
        } catch( HibernateException e ) {
            e.printStackTrace();
        }
    }
    
}
