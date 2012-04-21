package com.nullprogram.joystick;

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
        new Handler(controllers[0]).run();
    }
}
