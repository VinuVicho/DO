package org.vinuvicho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.vinuvicho.entity.Condition;
import org.vinuvicho.entity.Kanon;
import org.vinuvicho.entity.NeKanon;
import org.vinuvicho.repository.AllKanon;

import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
@Controller
public class FirstController {
    @Autowired
    private AllKanon allKanon;

    public String sendNew(Model model) {
        model.addAttribute("neKanon", allKanon.getNeKanon());
        model.addAttribute("kanon", allKanon.getKanon());
        return "new";
    }

    @PostMapping("/update/{id}")
    public String updateCondition(@PathVariable("id") int id, @ModelAttribute("condition") Condition condition, Model model) {
        if (condition.validate()) {
            allKanon.getNeKanon().removeCondition(id);
            allKanon.getNeKanon().addCondition(condition);
            return sendNew(model);
        }
        return "new-condition";
    }

    @GetMapping("/clear")
    public String clearNeKanon(Model model) {
        allKanon.setNeKanon(new NeKanon());
        allKanon.getNeKanon().clearConditionList();
        return sendNew(model);
    }

    @GetMapping("/add")
    public String addCondition(Model model, @ModelAttribute("neKanon") NeKanon neKanon) {
        model.addAttribute("newCondition", new Condition());
        allKanon.getNeKanon().setStringMeta(neKanon.getStringMeta());
        allKanon.getNeKanon().setEquation(neKanon.getEquation());
        allKanon.getNeKanon().setLimitation(neKanon.getLimitation());
        return "new-condition";
    }

    @PostMapping("/condition")
    public String createCondition(Model model, @ModelAttribute("newCondition") Condition newCondition) {
        if (newCondition.validate()) {
            newCondition.setId(AllKanon.getId());
            allKanon.getNeKanon().addCondition(newCondition);
            return sendNew(model);
        }
        return "new-condition";
    }

    @GetMapping()
    public String start(Model model) {
        return sendNew(model);
    }

    @PostMapping("/create")
    public String createKanon(@ModelAttribute("neKanon") NeKanon neKanon, Model model) {
        if (!neKanon.validate()) return "error";
        allKanon.setNeKanon(neKanon);
        Kanon kanon = new Kanon(allKanon.getNeKanon());
        allKanon.setKanon(kanon);
        return sendNew(model);
    }

    @GetMapping("/{id}")
    public String updateCondition(@PathVariable int id, Model model) {
        model.addAttribute("condition", allKanon.getNeKanon().getCondition(id));
        return "update-condition";
    }

    @GetMapping ("/delete/{id}")
    public String deleteCondition(Model model, @PathVariable("id") int id) {
        allKanon.getNeKanon().removeCondition(id);
        return sendNew(model);
    }
}
