package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * represents a camera.
 */
@Getter
@EqualsAndHashCode
public class Camera {

    private final String name;
    private final String color;
    private final boolean hasLivePicture;

    /**
     * overrides default constructor.
     * @param name The camera's name.
     * @param color The camera's color.
     * @param hasLivePicture If camera has live picture or not.
     */
    public Camera(String name, String color, boolean hasLivePicture) {
        this.name = name;
        this.color = color;
        this.hasLivePicture = hasLivePicture;
    }

    /**
     * overrides default toString().
     * @return summarized String with all info about camera (name, livePicture yes or no).
     */
    @Override
    public String toString() {
        String livePicture = hasLivePicture ? "with live picture" : "without live picture";
        return "Camera: " + name + ", " + livePicture;
    }
}