package BL;

/**
 * Created by Chaos on 20.6.2017..
 */
public class Diagnosis {
    private String title;
    private String text;

    @Override
    public String toString() {
        return title;
    }
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Diagnosis(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Diagnosis() {
    }
}
