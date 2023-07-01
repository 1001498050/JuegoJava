
package Modelo;

public class Usuario {
    
    private String id;

    private String Nombre;

    private String Apellido;

    private String correo;

    private String Contraseña;
    

    public String getId ()
    {
        return id;
    }

public void setId (String id)
    {
        this.id = id;
    }

    public String getcorreo ()
    {
        return correo;
    }

    public void setcorreo (String Correo)
    {
        this.correo = correo;
    }

    public String getNombre ()
    {
        return Nombre;
    }

    public void setNombre (String Nombre)
    {
        this.Nombre = Nombre;
    }

    public String getApellido ()
    {
        return Apellido;
    }

    public void setApellido (String Apellido)
    {
        this.Apellido = Apellido;
    }

    public String getContrseña ()
    {
        return Contraseña;
    }

    public void setContraseña (String Contraseña)
    {
        this.Contraseña = Contraseña;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", Correo = "+correo+", nombre = "+Nombre+", apellido = "+Apellido+", contraseña = "+Contraseña+"]";
    }
}
