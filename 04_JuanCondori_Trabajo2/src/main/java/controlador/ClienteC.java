package controlador;

import dao.ClienteImpl;
import modelo.Cliente;
import modelo.Ubigeo;
//import services.ReniecS;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.servlet.ServletContext;
//import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import com.google.gson.JsonSyntaxException;
import org.omnifaces.util.Messages;
import org.omnifaces.filter.FacesExceptionFilter;
//import services.EmailS;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "clienteC")
//@Dependent
@SessionScoped
public class ClienteC implements Serializable {

    private Cliente cliente;
    private ClienteImpl dao;
    private List<Cliente> listadoCliente;
    private Ubigeo ubigeo;
    private int tipo = 1;
    private ExcelOptions xls;
    private PDFOptions pdf;
    private boolean disable = true;
//    private boolean disable = ReniecS.disable;  //true

    public ClienteC() {
        cliente = new Cliente();
        dao = new ClienteImpl();
        listadoCliente = new ArrayList<>();
        ubigeo = new Ubigeo();
        customizationOptions();
    }

    public void buscarDNI() throws Exception {
        try {
//            ReniecS.buscarDNI(cliente);
//            if (ReniecS.disable == false) {
//                this.disable = false;
//            } else {
//                this.disable = true;
//            }
//            this.disable = true;
//            ReniecS.buscarDniReniec(cliente);
//            ReniecS.buscarRUC(cliente);
        } catch (NullPointerException e) {
            System.out.println("Error en BuscarDNI_C " + e.getMessage());
            this.disable = false;
        }
    }

    public void registrar() throws Exception {
        try {
            if (dao.duplicadoDNI(cliente, listadoCliente) == false) {
                dao.registrar(cliente);
//                EmailS.enviarEmail(cliente);
                PrimeFaces.current().ajax().update("form:dlgDatos");
                limpiar();
                listar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Registrado con Éxito"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "REVISE SU CORREO", "Se Envio su Contraseña"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Registrar"));
            System.out.println("Error en RegistrarC " + e.getMessage());
        }
        cliente = new Cliente();
        listar();
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(cliente);
            PrimeFaces.current().ajax().update("form3:dlgDatos");
            limpiar();
            listar();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Modificado con Éxito"));
            Messages.addFlashGlobalInfo("Modificado con Éxito");
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Al Modificar"));
            System.out.println("Error en ModificarC " + e.getMessage());
        }
        cliente = new Cliente();
        listar();
    }

    public void eliminarEstado() throws Exception {
        try {
            dao.eliminarEstado(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "CORRECTO", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    public void restaurarEstado() throws Exception {
        try {
            dao.restaurarEstado(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Restaurado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en restaurarEstadoC " + e.getMessage());
        }
    }

    public void limpiar() {
        cliente = new Cliente();
    }

    public void listar() {
        try {
            listadoCliente = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
        }
    }

    public void customizationOptions() {
        xls = new ExcelOptions();
        xls.setFacetBgColor("#19C7FF");
        xls.setFacetFontSize("10");
        xls.setFacetFontColor("#FFFFFF");
        xls.setFacetFontStyle("BOLD");
        xls.setCellFontColor("#000000");
        xls.setCellFontSize("8");
        xls.setFontName("Verdana");

        pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listar();
    }

}
