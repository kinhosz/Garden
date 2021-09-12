package event;
import constants.KeyboardConst;
import constants.MouseConst;
import constants.SourceConst;

public abstract class Event implements SourceConst, KeyboardConst, MouseConst{
    private int source;

    public int getSource(){
        return this.source;
    }

    public void setSource(int source){
        this.source = source;
    }
}