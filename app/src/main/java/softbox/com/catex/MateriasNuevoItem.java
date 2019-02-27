package softbox.com.catex;

public class MateriasNuevoItem {
    private boolean uno;
    private boolean dos;
    private boolean tres;
    private String horaInicio1;
    private String horaFin1;
    private String horaInicio2;
    private String horaFin2;
    private String horaInicio3;
    private String horaFin3;
    private boolean dia;
    private String diaN;

    public MateriasNuevoItem(String diaN) {
        this.uno = false;
        this.dos = false;
        this.tres = false;
        this.horaInicio1 = null;
        this.horaFin1 = null;
        this.horaInicio2 = null;
        this.horaFin2 = null;
        this.horaInicio3 = null;
        this.horaFin3 = null;
        this.dia = false;
        this.diaN = diaN;
    }

    public boolean isUno() {
        return uno;
    }

    public void setUno(boolean uno) {
        this.uno = uno;
    }

    public boolean isDos() {
        return dos;
    }

    public void setDos(boolean dos) {
        this.dos = dos;
    }

    public boolean isTres() {
        return tres;
    }

    public void setTres(boolean tres) {
        this.tres = tres;
    }

    public String getHoraInicio1() {
        return horaInicio1;
    }

    public void setHoraInicio1(String horaInicio1) {
        this.horaInicio1 = horaInicio1;
    }

    public String getHoraFin1() {
        return horaFin1;
    }

    public void setHoraFin1(String horaFin1) {
        this.horaFin1 = horaFin1;
    }

    public String getHoraInicio2() {
        return horaInicio2;
    }

    public void setHoraInicio2(String horaInicio2) {
        this.horaInicio2 = horaInicio2;
    }

    public String getHoraFin2() {
        return horaFin2;
    }

    public void setHoraFin2(String horaFin2) {
        this.horaFin2 = horaFin2;
    }

    public String getHoraInicio3() {
        return horaInicio3;
    }

    public void setHoraInicio3(String horaInicio3) {
        this.horaInicio3 = horaInicio3;
    }

    public String getHoraFin3() {
        return horaFin3;
    }

    public void setHoraFin3(String horaFin3) {
        this.horaFin3 = horaFin3;
    }

    public boolean isDia() {
        return dia;
    }

    public void setDia(boolean dia) {
        this.dia = dia;
    }

    public String getDiaN() {
        return diaN;
    }

    public void setDiaN(String diaN) {
        this.diaN = diaN;
    }
}
