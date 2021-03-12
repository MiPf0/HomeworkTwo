/**
 * represents a motif.
 */
package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Motif {

    private final String type;
    private final Double aperture;
    private final Double exposureTime;

    /**
     * overrides default constructor.
     * @param type The motif's type.
     * @param aperture The suggested aperture for this motif.
     * @param exposureTime The suggested exposure time for this motif.
     */
    public Motif(String type, Double aperture, Double exposureTime) {
        this.type = type;
        this.aperture = aperture;
        this.exposureTime = exposureTime;
    }

    /**
     * overrides default toString().
     * @return summarized String with all info about motif (type, suggested exposure time, suggested aperture).
     */
    @Override
    public String toString() {
        return "Motif: " + type + "; Exposure time: " + exposureTime + "; Aperture: " + aperture;
    }
}