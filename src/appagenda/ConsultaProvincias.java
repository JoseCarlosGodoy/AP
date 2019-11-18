package appagenda;


import entidades.Provincia;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class ConsultaProvincias {

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
    
    
    //Obtiene la lista completa de la base de datos
       Query queryProvincias = em.createNamedQuery("Provincia.findAll");
       List<Provincia> listProvincias = queryProvincias.getResultList();
        for(Provincia provincia : listProvincias){
            System.out.println(provincia.getNombre());
        }
    
    //Obtiene los resultados con nombre:Cadiz
        Query queryProvinciaCadiz = em.createNamedQuery("Provincia.findByNombre");
    queryProvinciaCadiz.setParameter("nombre", "Cadiz");
    List<Provincia> listProvinciasCadiz =queryProvinciaCadiz.getResultList();
    for(Provincia provinciaCadiz:listProvinciasCadiz){
    System.out.print(provinciaCadiz.getId()+":");
    System.out.println(provinciaCadiz.getNombre());
    }
    
    //Obtiene los datos para el id 2
    Provincia provinciaId2=em.find(Provincia.class,2);
    if (provinciaId2 != null){
    System.out.print(provinciaId2.getId() + ":");
    System.out.println(provinciaId2.getNombre());
    } else {
    System.out.println("No hay ninguna provincia con ID=2");
    }
    
    //Modifica un dato y hace el commit
    em.getTransaction().begin();
    for(Provincia provinciaCadiz : listProvinciasCadiz){
    provinciaCadiz.setCodigo("CA");
    em.merge(provinciaCadiz);
    }
    em.getTransaction().commit();
    
    //Para eliminar algun registro EJ:Elimina el registro cuyo id sea 15
    Provincia provinciaId15 = em.find(Provincia.class, 15);
    em.getTransaction().begin();
        if (provinciaId15 != null){
            em.remove(provinciaId15);
        }else{
    System.out.println("No hay ninguna provincia con ID=15");
    }
    em.getTransaction().commit();
    
    
    
        //cierra la conexion
    em.close();
    emf.close();
        try{
            DriverManager.getConnection("jdbc:derby:BDAgenda;shutdown=true");
        } catch (SQLException ex){
        }
    }
   
}
