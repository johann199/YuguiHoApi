package Duelo;

public class Monster {
    private String name;
    private int atk;
    private int def;
    private String  urlImagen;

    public Monster(String name, int atk, int def, String urlImagen) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.urlImagen = urlImagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}