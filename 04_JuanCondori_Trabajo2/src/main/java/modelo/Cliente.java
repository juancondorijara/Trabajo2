package modelo;

import lombok.Data;

@Data

public class Cliente {
    
    private Integer IDCLI;
    private String NOMCLI;
    private String APECLI;
    private String DIRCLI;
    private String EMACLI;
    private String DNICLI;
    private String CELCLI;
    private String CODUBI;
    private String ESTCLI;
    private Ubigeo ubigeo = new Ubigeo();

    public Cliente() {
    }

    public Cliente(Integer IDCLI, String NOMCLI, String APECLI, String DIRCLI, String EMACLI, String DNICLI, String CELCLI, String CODUBI, String ESTCLI, Ubigeo ubigeo) {
        this.IDCLI = IDCLI;
        this.NOMCLI = NOMCLI;
        this.APECLI = APECLI;
        this.DIRCLI = DIRCLI;
        this.EMACLI = EMACLI;
        this.DNICLI = DNICLI;
        this.CELCLI = CELCLI;
        this.CODUBI = CODUBI;
        this.ESTCLI = ESTCLI;
        this.ubigeo = ubigeo;
    }

}
