/**
 * ### DRIVER CLASS ###
 *
 * a program with a command line menu interface.
 * a user can choose camera, lens and motive.
 * if all three attributes are chosen, taking a picture is possible.
 * if aperture of lens and motif match, picture will look good - if not, picture will look odd.
 */

package org.lecture;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class DriverClass {

    /**
     * ### DRIVER METHOD ###
     *
     * driver method of driver class.
     * short description of main logic in class javadoc.
     */
    public static void main(String[] args) {

        Path input = Paths.get("src/main/resources/config.json");

        String jsonString = null;

        try {
            jsonString = Files.readString(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonOj = new JSONObject(jsonString);

        JSONObject jsonCamOj = jsonOj.getJSONObject("cameras");
        ArrayList<Camera> cameras = generateCameras(jsonCamOj);
        JSONObject jsonLensOj = jsonOj.getJSONObject("lenses");
        ArrayList<Lens> lenses = generateLenses(jsonLensOj);
        JSONObject jsonMotifOj = jsonOj.getJSONObject("motives");
        ArrayList<Motif> motives = generateMotives(jsonMotifOj);

        Selection selection = new Selection();
        selection.setContinueSelection(true);

        HashMap<String,ArrayList> options = new HashMap<>();
        options.put("cameras", cameras);
        options.put("lenses", lenses);
        options.put("motives", motives);

        do {
            String output = """
                    1 - Choose camera
                    2 - Choose lens
                    3 - Choose motive
                    4 - Take picture
                    5 - Exit program
                    """;
            System.out.println(output);
            decideForCase(options, selection);
        } while (selection.isContinueSelection());

    }

    /**
     * gets the user's decision for a case of a presented menu.
     * @param hashmap Created Hashmap of cameras, lenses and motives.
     * @param selectedChoice User selection.
     */
    public static void decideForCase(HashMap<String, ArrayList> hashmap, Selection selectedChoice) {
        Integer caseSelection = getCaseSelectionFromUser();
        if (caseSelection >= 1 && caseSelection <= 5) {
            switch (caseSelection) {
                case 1 -> selectedChoice.setSelectedCamera(selectCamera(hashmap.get("cameras")));
                case 2 -> selectedChoice.setSelectedLens(selectLens(hashmap.get("lenses")));
                case 3 -> selectedChoice.setSelectedMotif(selectMotif(hashmap.get("motives")));
                case 4 -> takePic(selectedChoice);
                case 5 -> System.exit(0);
            }
        }
    }

    /**
     * gets case selection from the user.
     * @return Integer Integer user typed into the console.
     */
    public static Integer getCaseSelectionFromUser() {
        Scanner sc = new Scanner(System.in);
        int userInput = 0;
        userInput = sc.nextInt();
        return userInput;
    }

    /**
     * get's a yes or no from a user which will be typed into the console by the user.
     * @param printOutQuestion  The question one wants to ask the user.
     * @return boolean true boolean if yes, false boolean if no.
     */
    public static boolean getYesOrNoFromUser(String printOutQuestion) {
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        boolean bool = false;

        do {
            if(Objects.equals(userInput, "y")) {
                break;
            } else if (Objects.equals(userInput, "n")) {
                break;
            }
            System.out.println(printOutQuestion);
            userInput = sc.nextLine();
        } while (true);

        if(Objects.equals(userInput, "y"))
            bool = true;
        return bool;
    }

    /**
     * takes the picture for the user, if all attributes are selected.
     * depending on the combination of attributes and final selection, information will be printed out to the user.
     * after that, selection will be nullified and can be started again.
     * @param sel current selection of the user.
     */
    public static void takePic(Selection sel) {

        if(!sel.picPossible()) {
            if(!sel.hasSelectedCamera()) {
                System.out.println("Not possible. You have not chosen any camera - please do.");
            } else if (!sel.hasSelectedLens()) {
                System.out.println("Not possible. You have not chosen any lens - please do.");
            } else if (!sel.hasSelectedMotif()) {
                System.out.println("Not possible. You have not chosen any motif - please do");
            }
        } else {
            System.out.println("You selected the " + sel.getCamColor() + " " + sel.getCamName() +
                    " with a lens aperture of " + sel.getLensAperture() + ". Your chosen motif is " +
                    sel.getMotifType() + " with a suggested aperture of " + sel.getMotifAperture() + " and an exposure time of " + sel.getMotifExposureTime() + ".");
            boolean answer1 = getYesOrNoFromUser("Checking optical viewfinder...\nDo you want to continue and take a picture? y/n");
            if (!answer1) {
                System.exit(0);
            } else {
                if(!sel.testLensMotifSameOrNot()) {
                    System.out.println("You selected the " + sel.getCamColor() + " " + sel.getCamName() +
                            " with a lens aperture of " + sel.getLensAperture() + ". Your chosen motif is " +
                            sel.getMotifType() + " with a suggested aperture of " + sel.getMotifAperture() + " and an exposure time of " + sel.getMotifExposureTime() + ".");
                    boolean answer2 = getYesOrNoFromUser("Checking optical viewfinder...\nAperture not matching. Continue? y/n");
                    if (!answer2) {
                        System.exit(0);
                    } else {
                        System.out.println("Taking picture... looks odd...\n");
                        sel.setSelectedCamera(null);
                        sel.setSelectedLens(null);
                        sel.setSelectedMotif(null);
                    }
                } else {
                    System.out.println("Taking picture... looks good!\n");
                    sel.setSelectedCamera(null);
                    sel.setSelectedLens(null);
                    sel.setSelectedMotif(null);
                }
            }
        }
    }

    /**
     * generates a new Camera Object from a JSONObject.
     * @param camera JSONObject.
     * @return Camera Object.
     */
    private static Camera generateCamera(JSONObject camera) {
        return new Camera(camera.getString("name"), camera.getString("colour"), camera.getBoolean("hasLivePicture"));
    }

    /**
     * generates an ArrayList of type "Camera" from 3 JSONObjects named "cam1", "cam2" and "cam3".
     * @param cameras JSONObject.
     * @return ArrayList of type Camera.
     */
    public static ArrayList<Camera> generateCameras(JSONObject cameras) {
        Camera camOne = generateCamera(cameras.getJSONObject("cam1"));
        Camera camTwo = generateCamera(cameras.getJSONObject("cam2"));
        Camera camThree = generateCamera(cameras.getJSONObject("cam3"));
        ArrayList<Camera> cameraList = new ArrayList<>();
        cameraList.add(camOne);
        cameraList.add(camTwo);
        cameraList.add(camThree);
        return cameraList;
    }

    /**
     * makes the user select from a list of cameras.
     * @param cameras An ArrayList of cameras.
     * @return Camera An Object of the selected camera.
     */
    public static Camera selectCamera(ArrayList<Camera> cameras) {
        System.out.println("You can choose from the following cameras:");
        for (int i = 0; i < cameras.size(); i++) {
            System.out.println(i + 1 + " - " + cameras.get(i).toString());
        }
        Integer choice = getCaseSelectionFromUser();
        if (choice <= 3 && choice > 0) {
            return cameras.get(choice - 1);
        } else {
            System.out.println("Not possible");
            return null;
        }
    }

    /**
     * generates a new Lens Object from a JSONObject.
     * @param lens JSONObject.
     * @return Lens Object.
     */
    private static Lens generateLens(JSONObject lens) {
        return new Lens(lens.getString("manufacturer"), lens.getDouble("aperture"));
    }

    /**
     * generates an ArrayList of type "Lens" from 3 JSONObjects named "lens1", "lens2" and "lens3".
     * @param lenses JSONObject.
     * @return ArrayList of type Lens.
     */
    public static ArrayList<Lens> generateLenses(JSONObject lenses) {
        Lens lensOne = generateLens(lenses.getJSONObject("lens1"));
        Lens lensTwo = generateLens(lenses.getJSONObject("lens2"));
        Lens lensThree = generateLens(lenses.getJSONObject("lens3"));
        ArrayList<Lens> lensList = new ArrayList<>();
        lensList.add(lensOne);
        lensList.add(lensTwo);
        lensList.add(lensThree);
        return lensList;
    }

    /**
     * makes the user select from a list of lenses.
     * @param lenses An ArrayList of lenses.
     * @return Lens An Object of the selected lens.
     */
    public static Lens selectLens(ArrayList<Lens> lenses) {
        System.out.println("You can choose from the following lenses:");
        for (int i = 0; i < lenses.size(); i++) {
            System.out.println(i + 1 + " - " + lenses.get(i).toString());
        }
        Integer choice = getCaseSelectionFromUser();
        if (choice <= 3 && choice > 0) {
            return lenses.get(choice - 1);
        } else {
            System.out.println("Not possible");
            return null;
        }
    }

    /**
     * generates a new Motif Object from a JSONObject.
     * @param motif JSONObject.
     * @return Motif Object.
     */
    private static Motif generateMotif(JSONObject motif) {
        return new Motif(motif.getString("type"), motif.getDouble("aperture"), motif.getDouble("exposureTime"));
    }

    /**
     * generates an ArrayList of type "Motif" from 3 JSONObjects named "motif1", "motif2" and "motif3".
     * @param motives JSONObject.
     * @return ArrayList of type Motif.
     */
    public static ArrayList<Motif> generateMotives(JSONObject motives) {
        Motif motifOne = generateMotif(motives.getJSONObject("motif1"));
        Motif motifTwo = generateMotif(motives.getJSONObject("motif2"));
        Motif motifThree = generateMotif(motives.getJSONObject("motif3"));
        ArrayList<Motif> motifList = new ArrayList<>();
        motifList.add(motifOne);
        motifList.add(motifTwo);
        motifList.add(motifThree);
        return motifList;
    }

    /**
     * makes the user select from a list of motives.
     * @param motives An ArrayList of motives.
     * @return Motif An Object of the selected motif.
     */
    public static Motif selectMotif(ArrayList<Motif> motives) {
        System.out.println("You can choose from the following motives:");
        for (int i = 0; i < motives.size(); i++) {
            System.out.println(i + 1 + " - " + motives.get(i).toString());
        }
        Integer choice = getCaseSelectionFromUser();
        if (choice <= 3 && choice > 0) {
            return motives.get(choice - 1);
        } else {
            System.out.println("Not possible");
            return null;
        }
    }

}