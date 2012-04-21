package com.nullprogram.joystick;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import lombok.extern.java.Log;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

@Log
@SuppressWarnings("serial")
public class Handler extends JComponent implements Runnable {

    private static final long POLL_PERIOD = 10; // millis
    private static final float THRESHOLD = 0.1f;
    private static final long WINDOW = 150 * 1000000L; // nanos

    private final Controller controller;
    private final Map<Component, Integer> map =
        new HashMap<Component, Integer>();

    private final Deque<Action> actions = new LinkedList<Action>();

    private final List<Combo> combos = new ArrayList<Combo>();

    public Handler(Controller input) {
        controller = input;
        int counter = 0;
        for (Component c : controller.getComponents()) {
            map.put(c, counter++);
        }

        //Combo diag = new Combo(9);
        //diag.add(new Action(12, 1, 0));
        //diag.add(new Action(13, 1, 0));
        //combos.add(diag);

        //Combo down = new Combo(1);
        //down.add(new Action(13, 1, 0));
        //combos.add(down);

        //Combo right = new Combo(2);
        //right.add(new Action(12, 1, 0));
        //combos.add(right);
    }

    @Override
    public void run() {
        while (true) {
            /* Read in new events. */
            controller.poll();
            EventQueue eq = controller.getEventQueue();
            Event e = new Event();
            while (eq.getNextEvent(e)) {
                int component = map.get(e.getComponent());
                int direction;
                if (e.getValue() > THRESHOLD) {
                    direction = 1;
                } else if (e.getValue() < -THRESHOLD) {
                    direction = -1;
                } else {
                    continue;
                }
                Action a = new Action(component, direction, System.nanoTime());
                actions.offerLast(a);
            }

            /* Check for combinations. */
            check();

            /* Wait awhile. */
            try {
                Thread.sleep(POLL_PERIOD);
            } catch (InterruptedException exception) {
                log.warning("Interrupted during polling.");
                return;
            }
        }
    }

    private void check() {
        Combo hit = null;

        /* Check for comboes starting with each old event. */
        long now = System.nanoTime();
        while (!actions.isEmpty() &&   // No actions to analyze
               hit == null &&          // Stop on a match
               now - actions.peekFirst().getTime() > WINDOW) {
            Action init = actions.pollFirst();
            long start = init.getTime();
            Combo combo = new Combo(0);
            combo.add(init);
            hit = find(combo, null);
            for (Action a : actions) {
                if (a.getTime() - start <= WINDOW) {
                    combo.add(a);
                    hit = find(combo, null);
                } else {
                    break;
                }
            }
        }

        /* Remove claimed actions. */
        if (hit != null) {
            actions.removeAll(hit.getActions());
        }
    }

    private Combo find(Combo c, Combo def) {
        for (Combo e : combos) {
            if (e.equals(c)) {
                return e;
            }
        }
        return def;
    }

    public Deque<Action> read() {
        return new LinkedList<Action>(actions);
    }
}
