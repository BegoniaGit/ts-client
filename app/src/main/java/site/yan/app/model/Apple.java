package site.yan.app.model;

public class Apple {
    private String color;
    private double size;
    private double price;

    public Apple(String color, double size, double price) {
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
