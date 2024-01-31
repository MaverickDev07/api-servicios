package org.allivia.api.alliviaapi.security;

/**
 * Created by yeri_ on 3/12/2021.
 */
public class MiExcepcion extends Exception{
    private int codigoError;
    private String mensaje;

    public MiExcepcion(int codigoError, String mensaje){
        super();
        this.codigoError=codigoError;
        this.mensaje=mensaje;
    }

    @Override
    public String getMessage(){

        String mensaje="";

        switch(codigoError){
            case 111:
                mensaje="Error, el numero esta entre 0 y 10";
                break;
            case 222:
                mensaje="Error, el numero esta entre 11 y 20";
                break;
            case 333:
                mensaje="Error, el numero esta entre 21 y 30";
                break;
        }

        return mensaje;

    }
}
