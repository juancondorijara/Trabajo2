package controlador;

import modelo.Demo;
import dao.DemoImpl;
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
@Named(value = "demoC")
//@Dependent
@SessionScoped
public class DemoC implements Serializable {

    private Demo demo;
    private DemoImpl dao;
    private List<Demo> listadoDemo;
    private int tipo = 1;

    public DemoC() {
        demo = new Demo();
        dao = new DemoImpl();
        listadoDemo = new ArrayList<>();
    }
    
    public void registrar() throws Exception {
        try {
            dao.registrar(demo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }
    
    public void limpiar() {
        demo = new Demo();
    }
    
    public void listar() {
        try {
            listadoDemo = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }
    
    @PostConstruct
    public void construir() {
        listar();
    }
    
}
