package org.vinuvicho.entity;

import java.util.ArrayList;
import java.util.List;

public class Kanon {
    private final StringBuilder limitations = new StringBuilder();
    private final StringBuilder equation = new StringBuilder();
    private final List<StringBuilder> conditions = new ArrayList<>();
    private final List<Integer> matrixEquation = new ArrayList<>(); //for matrix   непотріб
    private final StringBuilder matrixHtml = new StringBuilder();
    private final List<ArrayList<Integer>> columns = new ArrayList<ArrayList<Integer>>(new ArrayList<>());
    private StringBuilder stringMatrix = new StringBuilder();
    private final List<Integer> columnEnding = new ArrayList<>();

    public Kanon(NeKanon neKanon) {
        int zIndex = 0;
        //limitations
        String neLimitation = neKanon.getLimitation();
        int maxZIndex = neLimitation.length();
        for (int i = 0; i < neLimitation.length(); i++) {
            if (i != 0) limitations.append(", ");
            limitations.append("X<sub>").append(i + 1).append("</sub>=");
            if (neLimitation.charAt(i) == '=') {
                maxZIndex++;
                limitations.append("Z<sub>").append(++zIndex).append("</sub>-Z<sub>").append(++zIndex).append("</sub>");
            }
            if (neLimitation.charAt(i) == '<') limitations.append("-Z<sub>").append(++zIndex).append("</sub>");
            if (neLimitation.charAt(i) == '>') limitations.append("Z<sub>").append(++zIndex).append("</sub>");
        }
        //equation

        {
            StringBuilder notEquation = new StringBuilder(neKanon.getEquation());
            zIndex = 0;
            int limitationIndex = 0;
            boolean max = neKanon.getMeta();
            if (notEquation.charAt(0) != '-') notEquation.insert(0, "+");
            StringBuilder smallEquation = new StringBuilder();
            for (int i = 0; i < notEquation.length(); i++) {
                smallEquation.append(notEquation.charAt(i));
                if (notEquation.charAt(i) == 'x' || notEquation.charAt(i) == 'X') {
                    int equationIndex = equation.length();
                    if (neLimitation.charAt(limitationIndex) == '=') {
                        equation.append(znakChanger(smallEquation.charAt(0), max));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            equation.append(smallEquation.charAt(s));
                        }
                        matrixEquation.add(Integer.valueOf(equation.substring(equationIndex)));
                        equation.append("Z<sub>").append(++zIndex).append("</sub>").append(max ? znakChanger(smallEquation.charAt(0), false) : smallEquation.charAt(0));
                        equationIndex = equation.length();
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            equation.append(smallEquation.charAt(s));
                        }
                        matrixEquation.add(-(Integer.parseInt(equation.substring(equationIndex))));
                        equation.append("Z<sub>").append(++zIndex).append("</sub>");
                    } else if (neLimitation.charAt(limitationIndex) == '<') {
                        equation.append((max ? znakChanger(smallEquation.charAt(0), false) : smallEquation.charAt(0)));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            equation.append(smallEquation.charAt(s));
                        }
                        matrixEquation.add(Integer.valueOf(equation.substring(equationIndex)));
                        equation.append("Z<sub>").append(++zIndex).append("</sub>");
                    } else if (neLimitation.charAt(limitationIndex) == '>') {
                        equation.append(znakChanger(smallEquation.charAt(0), max));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            equation.append(smallEquation.charAt(s));
                        }
                        matrixEquation.add(Integer.valueOf(equation.substring(equationIndex)));
                        equation.append("Z<sub>").append(++zIndex).append("</sub>");
                    }
                    limitationIndex++;
                    smallEquation = new StringBuilder();
                }
            }
            if (equation.charAt(0) == '+') equation.deleteCharAt(0);
            equation.append(" → max");
            equation.insert(0,"F(x)= ");
        }
        //conditions
        for (int q = 0; q < neKanon.getConditionList().size(); q++) {
//        for (Condition baseCondition : neKanon.getConditionList()) {
            Condition baseCondition = neKanon.getConditionList().get(q);
            String stringCondition = baseCondition.getCondition();
            if (stringCondition.equals("")) continue;
            StringBuilder edit = new StringBuilder();
            zIndex = 0;
            int limitationIndex = 0;
            StringBuilder smallEquation = new StringBuilder();
//            int columnNumber = 1;
            for (int i = 0; i < stringCondition.length(); i++) {
                if (stringCondition.charAt(i) == '=') {
                    edit.append(stringCondition.substring(i));
                    columnEnding.add(Integer.parseInt(stringCondition.substring(i + 1)));
                    break;
                }
                if (stringCondition.charAt(i) == '<') {
                    edit.append("+Z<sub>").append(++maxZIndex).append("</sub>=").append(stringCondition.substring(i + 1));
                    columnEnding.add(Integer.parseInt(stringCondition.substring(i + 1)));
                    addToLastColumn(1, q, neKanon.getConditionList().size());
                    break;
                }
                if (stringCondition.charAt(i) == '>') {
                    edit.append("-Z<sub>").append(++maxZIndex).append("</sub>=").append(stringCondition.substring(i + 1));
                    columnEnding.add(Integer.parseInt(stringCondition.substring(i + 1)));
                    addToLastColumn(-1, q, neKanon.getConditionList().size());
                    break;
                }
                smallEquation.append(stringCondition.charAt(i));
                if (stringCondition.charAt(i) == 'x' || stringCondition.charAt(i) == 'X') {
                    int equationIndex = edit.length();
                    if (neLimitation.charAt(limitationIndex) == '=') {
                        edit.append(smallEquation.charAt(0));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            edit.append(smallEquation.charAt(s));
                        }
                        addToColumn(Integer.parseInt(edit.substring(equationIndex)), zIndex);
                        edit.append("Z<sub>").append(++zIndex).append("</sub>")
                                .append(znakChanger(smallEquation.charAt(0), false));
                        equationIndex = edit.length();
                        for (int s = 0; s < smallEquation.length() - 1; s++) {
                            edit.append(smallEquation.charAt(s));
                        }
                        addToColumn(-Integer.parseInt(edit.substring(equationIndex)), zIndex);
                        edit.append("Z<sub>").append(++zIndex).append("</sub>");
                    } else if (neLimitation.charAt(limitationIndex) == '<') {
                        edit.append((znakChanger(smallEquation.charAt(0), false)));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            edit.append(smallEquation.charAt(s));
                        }
                        addToColumn(Integer.parseInt(edit.substring(equationIndex)), zIndex);
                        edit.append("Z<sub>").append(++zIndex).append("</sub>");
                    } else if (neLimitation.charAt(limitationIndex) == '>') {
                        edit.append(smallEquation.charAt(0));
                        for (int s = 1; s < smallEquation.length() - 1; s++) {
                            edit.append(smallEquation.charAt(s));
                        }
                        addToColumn(Integer.parseInt(edit.substring(equationIndex)), zIndex);
                        edit.append("Z<sub>").append(++zIndex).append("</sub>");
                    }
                    limitationIndex++;
                    smallEquation = new StringBuilder();
                }
            }
            if (edit.charAt(0) == '+') edit.deleteCharAt(0);
            conditions.add(edit);
        }
        makingMatrix(neKanon.getConditionList().size());
        //fill
        for (int i = matrixEquation.size(); i < maxZIndex; i++) matrixEquation.add(0);      //не даю дупля для чого
    }

    private void addToColumn(int number, int columnNumber) {
        if (columns.size() <= columnNumber) {
            columns.add(new ArrayList<>());
        }
        columns.get(columnNumber).add(number);
    }
    private void addToLastColumn(int num, int row, int max) {
        ArrayList<Integer> lastColumn = new ArrayList<>();
        while (lastColumn.size() < row-1) {
            lastColumn.add(0);
        }
        lastColumn.add(num);
        while (lastColumn.size() < max-1) {
            lastColumn.add(0);
        }
        columns.add(lastColumn);
    }

    private void makingMatrix(int max) {
        stringMatrix.append("<table><tr><td><table style=\"border-top: 1px solid; border-left:  1px solid; border-bottom: 1px solid;\"><tr><td>");
        for (int i = 0; i < max; i++) {
            stringMatrix.append("<br>");
        }
        stringMatrix.append("</td></tr></table></td>");     //закриваюча дужка
        stringMatrix.append("<td><table>");
        for (int i = 0; i < columns.get(0).size(); i++) {
            stringMatrix.append("<tr align=\"center\">");
            for (ArrayList<Integer> column : columns) {
                stringMatrix.append("<td align=\"center\"> ").append(column.get(i)).append(" </td>");
            }
            stringMatrix.append("</tr>");
        }
        stringMatrix.append("</table></td><td><table style=\"border-top: 1px solid; border-right:  1px solid; border-bottom: 1px solid;\"><tr><td>");
        for (int i = 0; i < max; i++) {
            stringMatrix.append("<br>");
        }
        stringMatrix.append("</td></tr></table></td><td><table><tr><td>=</td></tr></table></td><td>"
                + "<table style=\"border-top: 1px solid; border-left:  1px solid; border-bottom: 1px solid;\"><tr><td>");
        for (int i = 0; i < max; i++) {
            stringMatrix.append("<br>");
        }
        stringMatrix.append("</td></tr></table></td><td><table>");
        for (int i = 0; i < columns.get(0).size(); i++) {
            stringMatrix.append("<tr align=\"center\"><td align=\"center\"> ").append(columnEnding.get(i)).append(" </td></tr>");
        }
        stringMatrix.append("</table><td><table style=\"border-top: 1px solid; border-right:  1px solid; border-bottom: 1px solid;\"><tr><td>");
        for (int i = 0; i < max; i++) {
            stringMatrix.append("<br>");
        }
        stringMatrix.append("</td></tr></table></td></td></tr></table>");
    }

    public List<StringBuilder> getConditions() {
        return conditions;
    }

    public char znakChanger(char znak, boolean max) {
        return max ? znak : (znak == '-') ? '+' : '-';
    }

    public StringBuilder getLimitations() {
        return limitations;
    }

    public StringBuilder getEquation() {
        return equation;
    }

    public List<Integer> getMatrixEquation() {
        return matrixEquation;
    }

    public StringBuilder getMatrixHtml() {
        return matrixHtml;
    }

    public List<ArrayList<Integer>> getColumns() {
        return columns;
    }

    public StringBuilder getStringMatrix() {
        return stringMatrix;
    }

    public void setStringMatrix(StringBuilder stringMatrix) {
        this.stringMatrix = stringMatrix;
    }
}
