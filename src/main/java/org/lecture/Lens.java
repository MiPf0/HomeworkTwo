/**
 * represents a lens.
 */
package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Lens {

    private final String manufacturer;
    private final Double aperture;

    /**
     * overrides default constructor.
     * @param manufacturer The lens's manufacturer.
     * @param aperture The lens's aperture.
     */
    public Lens (String manufacturer, Double aperture) {
        this.manufacturer = manufacturer;
        this.aperture = aperture;
    }

    /**
     * overrides default toString().
     * @return summarized String with all info about lens (manufacturer, aperture).
     */
    @Override
    public String toString() {
        return "Lens: " + manufacturer + "; Aperture: " + aperture;
    }
}
