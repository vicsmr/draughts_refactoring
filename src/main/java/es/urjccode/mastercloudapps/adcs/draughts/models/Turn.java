package es.urjccode.mastercloudapps.adcs.draughts.models;

class Turn {

    private Color color;

    Turn() {
        this.color = Color.WHITE;
    }

    private int obtainOrdinalColorValue() {
        return (this.color.ordinal() + 1) % 2;
    }

    void change() {
        this.color = Color.values()[this.obtainOrdinalColorValue()];
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.color.name();
    }

}