package com.filters.gui.Model;

import java.util.Iterator;
import java.util.LinkedList;

public class History implements Iterable<Action> {

    private LinkedList<Action> actions = new LinkedList<>();
    public void add(Action a) {
        actions.add(a);
    }
    public void undo() {
        actions.removeLast();
        actions.forEach(action -> action.doAction());
    }

    @Override
    public Iterator<Action> iterator() {
        return actions.iterator();
    }
}
