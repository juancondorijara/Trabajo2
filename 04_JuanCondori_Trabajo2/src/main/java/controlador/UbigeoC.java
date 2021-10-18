package controlador;

import dao.UbigeoImpl;
import modelo.Ubigeo;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
//import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "ubigeoC")
//@Dependent
@SessionScoped
public class UbigeoC implements Serializable {

    private Ubigeo ubigeo;
    private UbigeoImpl dao;
    private List<Ubigeo> listadoUbigeo;

    public UbigeoC() {
        ubigeo = new Ubigeo();
        dao = new UbigeoImpl();
        listadoUbigeo = new ArrayList<>();
    }
    
    public void listar() {
        try {
            listadoUbigeo = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }
    
    @PostConstruct
    public void construir() {
        listar();
    }
    
}
