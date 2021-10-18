package dao;

//import com.mysql.jdbc.PreparedStatement;
import modelo.Cliente;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ClienteImpl extends Conexion implements ICRUD<Cliente> {
    
    @Override
    public void registrar(Cliente cliente) throws Exception {
        String sql = "insert into CLIENTE (NOMCLI, APECLI, DIRCLI, EMACLI, DNICLI, CELCLI, CODUBI, ESTCLI) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNOMCLI());
            ps.setString(2, cliente.getAPECLI());
            ps.setString(3, cliente.getDIRCLI());
            ps.setString(4, cliente.getEMACLI());
            ps.setString(5, cliente.getDNICLI());
            ps.setString(6, cliente.getCELCLI());
            ps.setString(7, cliente.getCODUBI());
            ps.setString(8, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RegistrarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Cliente cliente) throws Exception {
        String sql = "update CLIENTE set NOMCLI=?, APECLI=?, DIRCLI=?, EMACLI=?, DNICLI=?, CELCLI=?, CODUBI=?, ESTCLI=? where IDCLI=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNOMCLI());
            ps.setString(2, cliente.getAPECLI());
            ps.setString(3, cliente.getDIRCLI());
            ps.setString(4, cliente.getEMACLI());
            ps.setString(5, cliente.getDNICLI());
            ps.setString(6, cliente.getCELCLI());
            ps.setString(7, cliente.getCODUBI());
            ps.setString(8, "A");
            ps.setInt(9, cliente.getIDCLI());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ModificarD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Cliente> listado = null;
        Cliente cliente;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vClienteA";
                break;
            case 2:
                sql = "SELECT * FROM vClienteI";
                break;
            case 3:
                sql = "SELECT * FROM vClienteT";
                break;
        }
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setIDCLI(rs.getInt("IDCLI"));
                cliente.setNOMCLI(rs.getString("NOMCLI"));
                cliente.setAPECLI(rs.getString("APECLI"));
                cliente.setDIRCLI(rs.getString("DIRCLI"));
                cliente.setEMACLI(rs.getString("EMACLI"));
                cliente.setDNICLI(rs.getString("DNICLI"));
                cliente.setCELCLI(rs.getString("CELCLI"));
                cliente.setCODUBI(rs.getString("DISUBI"));
                listado.add(cliente);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en ListarTodosD" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    @Override
    public void eliminarEstado(Cliente cliente) throws Exception {
        String sql = "update CLIENTE set ESTCLI='I' where IDCLI=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
//            ps.setString(1, "I");
            ps.setInt(1, cliente.getIDCLI());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void restaurarEstado(Cliente cliente) throws Exception {
        String sql = "update CLIENTE set ESTCLI='A' where IDCLI=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
//            ps.setString(1, "A");
            ps.setInt(1, cliente.getIDCLI());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en RestaurarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public boolean duplicadoDNI(Cliente cliente, List<Cliente> listaCliente) {
        for (Cliente cli : listaCliente) {
            if (cliente.getDNICLI().equals(cli.getDNICLI())) {
                return true;
            }
        }
        return false;
    }

//    public boolean duplicadoDNI2(Cliente cliente, List<Cliente> listaCliente) {
//        for (Cliente cli : listaCliente) {
//            if (cliente.equals(cli)) {
//                return true;
//            }
//        }
//        return false;
//    }
    
    public void validarCliente(Cliente cliente) throws Exception {
        String NOMCLI = cliente.getNOMCLI();
        String APECLI = cliente.getAPECLI();
        String DIRCLI = cliente.getDIRCLI();
        String EMACLI = cliente.getEMACLI();
        String DNICLI = cliente.getDNICLI();
        String CELCLI = cliente.getCELCLI();
        String sql = "SELECT NOMCLI,APECLI,DIRCLI,EMACLI,DNICLI,CELCLI FROM CLIENTE WHERE NOMCLI='" + NOMCLI + "' and APECLI='" + APECLI + "' and DIRCLI='" + DIRCLI + "' and EMACLI='" + EMACLI + "' and DNICLI='" + DNICLI + "' and CELCLI='" + CELCLI + "'";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                cliente.setNOMCLI(rs.getString("NOMCLI"));
                cliente.setAPECLI(rs.getString("APECLI"));
                cliente.setDIRCLI(rs.getString("DIRCLI"));
                cliente.setEMACLI(rs.getString("EMACLI"));
                cliente.setDNICLI(rs.getString("DNICLI"));
                cliente.setCELCLI(rs.getString("CELCLI"));
            } else {
                cliente.setNOMCLI("");
                cliente.setAPECLI("");
                cliente.setDIRCLI("");
                cliente.setEMACLI("");
                cliente.setDNICLI("");
                cliente.setCELCLI("");
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en Validar ClienteD " + e.getMessage());
        }
    }
    
}
