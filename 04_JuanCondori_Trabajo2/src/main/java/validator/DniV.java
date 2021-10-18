package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import modelo.Cliente;
import dao.ClienteImpl;
import controlador.ClienteC;
import java.util.ArrayList;
import java.util.List;
//https://www.youtube.com/watch?v=UEbgJ2CrYJE
import lombok.Data;
import org.primefaces.PrimeFaces;

@Data

@FacesValidator(value = "dniV")
public class DniV implements Validator {

    private Cliente cliente;
    private ClienteImpl dao;
    private List<Cliente> listadoCliente;

    public DniV() {
        cliente = new Cliente();
        dao = new ClienteImpl();
        listadoCliente = new ArrayList<>();
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String numero = value.toString().trim();
        if (numero.length() != 0 && numero.length() < 8) {
            String formato = "^d\\d\\d\\d\\d\\d\\d\\d$";
            boolean val = Pattern.matches(formato, numero);
            if (!val) {
                throw new ValidatorException(new FacesMessage("El formato es ########"));
            }
        }
        
//        if (dao.duplicadoDNI(cliente, listadoCliente) == true) {
//            throw new ValidatorException(new FacesMessage("Duplicado, el DNI ya existe"));


//            PrimeFaces.current().ajax().update("mensajes");
//            PrimeFaces.current().ajax().update("msgDni");
//            throw new ValidatorException(new FacesMessage("Duplicado, el DNI ya existe"));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
//        } else {
//            throw new ValidatorException(new FacesMessage("Duplicado, el DNI ya existe"));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "DUPLICADO", "El DNI ya Existe"));
        
        
//        }
    }

}
