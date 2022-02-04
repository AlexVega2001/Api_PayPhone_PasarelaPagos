package com.example.api_payphone_pasarelapagos.Model;

public class EquiposElectronicos {
    private double precio;
    private int iva, imgEquipo;
    private String name;

    public EquiposElectronicos() {
    }

    public EquiposElectronicos(String Name, double Precio, int Iva, int ImgEquipo) {
        this.name = Name;
        this.precio = Precio;
        this.iva = Iva;
        this.imgEquipo = ImgEquipo;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getImgEquipo() {
        return imgEquipo;
    }

    public void setImgEquipo(int imgEquipo) {
        this.imgEquipo = imgEquipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
