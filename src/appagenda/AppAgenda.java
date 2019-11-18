/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appagenda;

import entidades.Provincia;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Usuario
 */
public class AppAgenda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Establece la conexion
        Map<String,String> emfProperties = new HashMap<String,String>();   
    emfProperties.put("javax.persistence.schemageneration.database.action","create");
    
    //crea una variable con la bd
    EntityManagerFactory emf=
    Persistence.createEntityManagerFactory("AppAgendaPU",emfProperties);
    EntityManager em = emf.createEntityManager();
    
    //crea los nuevos objetos
        Provincia provinciaCadiz=new Provincia(1,"Cadiz");
        Provincia provinciaSevilla=new Provincia();
        provinciaSevilla.setNombre("Sevilla");  
        
    //inicias la trasaccion
    em.getTransaction().begin();
    
    //introduces la serie de cambios
        em.persist(provinciaSevilla);
        em.persist(provinciaCadiz);
    
    //vuelcas los cambios con un commit
    em.getTransaction().commit();
    
    //Si se quiere deshacer esos cambios antes de volcarlos 
    //em.getTransaction().rollback();
    

    //cierra la conexion
    em.close();
    emf.close();
        try{
            DriverManager.getConnection("jdbc:derby:BDAgenda;shutdown=true");
        } catch (SQLException ex){
        }


    }
    
}
