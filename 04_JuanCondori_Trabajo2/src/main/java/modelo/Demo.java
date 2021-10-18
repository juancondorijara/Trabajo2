package modelo;

import lombok.Data;

@Data

public class Demo {
    
    private String CAMPO1;
    private String CAMPO2;
    private String CAMPO3;

    public Demo() {
    }

    public Demo(String CAMPO1, String CAMPO2, String CAMPO3) {
        this.CAMPO1 = CAMPO1;
        this.CAMPO2 = CAMPO2;
        this.CAMPO3 = CAMPO3;
    }
    
}
