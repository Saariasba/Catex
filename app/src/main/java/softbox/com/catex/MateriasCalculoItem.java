package softbox.com.catex;

public class MateriasCalculoItem {
    private String valor;
    private String porcentaje;
    private String titulo;

    public MateriasCalculoItem() {
        this.valor = null;
        this.porcentaje = null;
        this.titulo = null;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
