package org.vinuvicho.repository;

import org.springframework.stereotype.Repository;
import org.vinuvicho.entity.Condition;
import org.vinuvicho.entity.Kanon;
import org.vinuvicho.entity.NeKanon;

import java.util.List;

@Repository
public class AllKanon {
    private NeKanon neKanon = new NeKanon();
    private Kanon kanon = null;
    private static int id = 0;

    public static int getId() {
        return ++id;
    }

    public NeKanon getNeKanon() {
        return neKanon;
    }

    public void setNeKanon(NeKanon neKanon) {
        List<Condition> a = this.neKanon.getConditionList();
        this.neKanon = neKanon;
        this.neKanon.setConditionList(a);
    }

    public Kanon getKanon() {
        return kanon;
    }

    public void setKanon(Kanon kanon) {
        this.kanon = kanon;
    }
}
