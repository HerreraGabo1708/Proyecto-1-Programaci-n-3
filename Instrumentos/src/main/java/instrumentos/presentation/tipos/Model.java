package instrumentos.presentation.tipos;

import instrumentos.Application;
import instrumentos.logic.TipoInstrumento;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<TipoInstrumento> list;
    TipoInstrumento current;

    int changedProps = NONE;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public void init(List<TipoInstrumento> list){
        mode = Application.MODE_CREATE;
        setList(list);
        setCurrent(new TipoInstrumento());
    }

    public List<TipoInstrumento> getList() {
        return list;
    }
    public void setList(List<TipoInstrumento> list){
        this.list = list;
        changedProps +=LIST;
    }

    public TipoInstrumento getCurrent() {
        return current;
    }
    public void setCurrent(TipoInstrumento current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;
    public int mode;

    public int getMode() {
        return mode;
    }
}
