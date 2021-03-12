/**
 * represents a selection of Objects Camera, Lens and Motif.
 */
package org.lecture;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Selection {

    private Camera selectedCamera;
    private Lens selectedLens;
    private Motif selectedMotif;
    private boolean continueSelection;

    /**
     * overrides default toString().
     * @return summarized String with all info (camera, lens, motif, boolean about continue selection or not).
     */
    @Override
    public String toString() {
        return "Selection{" +
                "selectedCamera=" + selectedCamera +
                ", selectedLens=" + selectedLens +
                ", selectedMotif=" + selectedMotif +
                ", continueConfiguration=" + continueSelection +
                "}";
    }

    /**
     * tests, if lens and motive have the same aperture or not.
     * @return boolean true if same, false if not same.
     */
    public boolean testLensMotifSameOrNot() {
        return Double.compare(selectedLens.getAperture(),selectedMotif.getAperture()) == 0;
    }

    /**
     * tests, if taking a picture is possible or not.
     * @return boolean true, if taking a picture is possible and false if not.
     */
    public boolean picPossible() {
        return selectedCamera != null && selectedLens != null && selectedMotif != null;
    }

    /**
     * tests, if camera is selected.
     * @return boolean
     */
    public boolean hasSelectedCamera() {
        return selectedCamera != null;
    }

    /**
     * tests, if lens is selected.
     * @return boolean
     */
    public boolean hasSelectedLens() {
        return selectedLens != null;
    }

    /**
     * tests, if motif is selected.
     * @return boolean
     */
    public boolean hasSelectedMotif() {
        return selectedMotif != null;
    }

    /**
     * returns the color of the selected Camera Object.
     * @return String Color of Camera.
     */
    public String getCamColor() {
        return selectedCamera.getColor();
    }

    /**
     * returns the name of the selected Camera Object.
     * @return String Name of Camera.
     */
    public String getCamName() {
        return selectedCamera.getName();
    }

    /**
     * returns the aperture of the selected Lens Object.
     * @return Double Aperture of Lens.
     */
    public Double getLensAperture() {
        return selectedLens.getAperture();
    }

    /**
     * returns the type of the selected Motif Object.
     * @return String Type of Motif.
     */
    public String getMotifType() {
        return selectedMotif.getType();
    }

    /**
     * returns the exposure time of the selected Motif Object.
     * @return Double Exposure Time of Motif.
     */
    public Double getMotifExposureTime() {
        return selectedMotif.getExposureTime();
    }

    /**
     * returns the aperture of the selected Motif Object.
     * @return Double Aperture of Motif.
     */
    public Double getMotifAperture() {
        return selectedMotif.getAperture();
    }

}