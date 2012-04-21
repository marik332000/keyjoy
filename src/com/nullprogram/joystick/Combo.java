package com.nullprogram.joystick;

import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(exclude="actions")
@RequiredArgsConstructor
@EqualsAndHashCode(exclude={"keycode"})
public class Combo {

    @Getter
    private final Set<Action> actions = new HashSet<Action>();

    public void add(Action a) {
        actions.add(a);
    }

    @Getter
    private final int keycode;
}
