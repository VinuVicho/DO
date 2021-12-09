package org.vinuvicho.entity;

public class Condition {
    private int id;
    private String condition = "";

    public Condition() {
    }

    public Condition(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean validate() {
        return true;        //TODO make validate + place '+' at beginning
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public int getId() {
        return id;
    }
}
