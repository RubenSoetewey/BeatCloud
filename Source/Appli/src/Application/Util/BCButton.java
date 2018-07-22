package Application.Util;

import javafx.scene.control.Button;

public class BCButton extends Button {
    public String associatedFile;
    public String associatedFileURL;
    public BCButton(){
        super();
    }

    public BCButton(String file){
        super();
        this.associatedFile = file;
    }
    public BCButton(String file,String text){
        super(text);
        this.associatedFile = file;
    }
    public BCButton(String file,String text,String url){
        super(text);
        this.associatedFile = file;
        this.associatedFileURL = url;
    }
}
