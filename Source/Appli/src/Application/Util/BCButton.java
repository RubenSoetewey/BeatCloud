package Application.Util;

import javafx.scene.control.Button;

public class BCButton extends Button {
    public String associatedFile;

    BCButton(String file){
        super();
        this.associatedFile = file;
    }
}
