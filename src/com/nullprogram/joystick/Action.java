package com.nullprogram.joystick;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(exclude={"time"})
public class Action {
    @Getter
    private final int component;

    @Getter
    private final int direction;

    @Getter
    private final long time;
}
