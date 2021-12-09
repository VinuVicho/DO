package org.vinuvicho.entity;

import java.util.ArrayList;
import java.util.List;

public class NeKanon {
    private String equation;
    private boolean meta;
    private String stringMeta = "max";
    private List<Condition> conditionList = new ArrayList<>();

    private String limitation;

    public boolean validate() {
        if (stringMeta.equals("min")) meta = false;
        else if (stringMeta.equals("max")) meta = true;
        else return false;
        if (equation == null || limitation == null) return false;
        return true;
    }

    public void clearConditionList() {
        conditionList = new ArrayList<>();
    }

    public NeKanon() {
        conditionList.add(new Condition(0));
    }

    public void addCondition(Condition condition) {
        this.conditionList.add(condition);
    }

    public Condition getCondition(int id) {
        for (Condition value : conditionList) {
            if (value.getId() == id) {
                return value;
            }
        }
        return new Condition();
    }

    public void removeCondition(int id) {
        conditionList.remove(getCondition(id));
    }

    public String getStringMeta() {
        return stringMeta;
    }

    public void setStringMeta(String stringMeta) {
        this.stringMeta = stringMeta;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public Boolean getMeta() {
        return meta;
    }

    public void setMeta(Boolean meta) {
        this.meta = meta;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }
}
