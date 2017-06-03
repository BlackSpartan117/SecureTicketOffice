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
    
    public Cuenta getCuentaById( String tarjetaCredito ) {
        Session sesion = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        String hql = "FROM Cuenta as c WHERE c.noTarjetaCredito =:tarjeta";
        List<Cuenta> cuentas = null;
        
        try {
            Query q = sesion.createQuery( hql );
            q.setParameter("tarjeta", tarjetaCredito );
            cuentas = q.list();
            sesion.flush();
            trans.commit();
        } catch( HibernateException e ) {
            e.printStackTrace();
        }
        
        if( cuentas != null && cuentas.size() > 0 )
            return cuentas.get(0);
        
        return null;
    }
    
    public void updateCuenta( Cuenta c ) {
        Session sesion = NewHibernateUtil.getSessionFactory().openSession();
        Transaction trans = sesion.beginTransaction();
        
        try {
            sesion.saveOrUpdate( c );
            sesion.flush();
            trans.commit();
        } catch( HibernateException e ) {
            e.printStackTrace();
        }
    }
}
