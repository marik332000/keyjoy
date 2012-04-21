package com.nullprogram.joystick;

import java.awt.event.KeyEvent;
import java.util.List;
import lombok.extern.java.Log;
import lombok.val;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

@Log
public class Launcher {
    public static void main(final String[] args) {
        try {
            com.nullprogram.lwjgl.Lwjgl.setup();
        } catch (java.io.IOException e) {
            log.severe("could not prepare libraries: " + e);
            System.exit(1);
        }
        val env = ControllerEnvironment.getDefaultEnvironment();
        Controller[] controllers = env.getControllers();
        if (controllers.length == 0) {
            log.severe("No joysticks found!");
            System.exit(-1);
        } else {
            log.info(controllers.length + " joysticks found.");
        }

        Handler handler = new Handler(controllers[0]);
        setBindings(handler);
        handler.run();
    }

    /* Hard code my keybindings. */
    private static void setBindings(Handler h) {
        List<Combo> combos = h.getCombos();

        /* Movement */

        Combo up = new Combo().press(KeyEvent.VK_K);
        up.add(new Action(13, -1, 0));
        combos.add(up);

        Combo left = new Combo().press(KeyEvent.VK_H);
        left.add(new Action(12, -1, 0));
        combos.add(left);

        Combo down = new Combo().press(KeyEvent.VK_J);
        down.add(new Action(13, 1, 0));
        combos.add(down);

        Combo right = new Combo().press(KeyEvent.VK_L);
        right.add(new Action(12, 1, 0));
        combos.add(right);

        Combo dr = new Combo().press(KeyEvent.VK_N);
        dr.add(new Action(12, 1, 0));
        dr.add(new Action(13, 1, 0));
        combos.add(dr);

        Combo dl = new Combo().press(KeyEvent.VK_B);
        dl.add(new Action(12, -1, 0));
        dl.add(new Action(13, 1, 0));
        combos.add(dl);

        Combo ur = new Combo().press(KeyEvent.VK_U);
        ur.add(new Action(12, 1, 0));
        ur.add(new Action(13, -1, 0));
        combos.add(ur);

        Combo ul = new Combo().press(KeyEvent.VK_Y);
        ul.add(new Action(12, -1, 0));
        ul.add(new Action(13, -1, 0));
        combos.add(ul);

        /* Buttons */

        Combo explore = new Combo().press(KeyEvent.VK_O);
        explore.add(new Action(0, 1, 0));
        combos.add(explore);

        Combo upstair = new Combo().press(KeyEvent.VK_LESS);
        upstair.add(new Action(5, 1, 0));
        combos.add(upstair);

        Combo downstair = new Combo();
        downstair.press(KeyEvent.VK_SHIFT).press(KeyEvent.VK_GREATER);
        downstair.add(new Action(7, 1, 0));
        combos.add(downstair);
    }
}
