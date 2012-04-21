package com.nullprogram.joystick;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;

@Log
@ToString(exclude="actions")
@EqualsAndHashCode(exclude={"keycodes"})
public class Combo {

    @Getter
    private final Set<Action> actions = new HashSet<Action>();

    public void add(Action a) {
        actions.add(a);
    }

    @Getter
    private final List<Integer> keycodes = new ArrayList<Integer>();

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.severe("Could not create java.awt.Robot: " + e);
            throw new RuntimeException(e);
        }
    }

    public Combo press(int code) {
        keycodes.add(code);
        return this;
    }

    public void execute() {
        for (int keycode : keycodes) {
            robot.keyPress(keycode);
        }
        for (int keycode : keycodes) {
            robot.keyRelease(keycode);
        }
    }
}
