package instrumentos.presentation.tipos;

import com.sun.nio.file.ExtendedOpenOption;
import instrumentos.Application;
import instrumentos.logic.Service;
import instrumentos.logic.TipoInstrumento;

import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new TipoInstrumento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(TipoInstrumento filter) throws  Exception{
        List<TipoInstrumento> rows = Service.instance().search(filter);
        if (rows.isEmpty()){
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setList(rows);
        model.setCurrent(new TipoInstrumento());
        model.commit();
    }

    public void edit(int row){
        TipoInstrumento e = model.getList().get(row);
        try {
            model.setCurrent(Service.instance().read(e));
            model.setMode(Application.MODE_EDIT);
            model.commit();
        } catch (Exception ex) {}
    }

    public void delete(){
        try{
            Service.instance().delete(model.current);
            this.search(new TipoInstrumento());
        }catch(Exception e){}
    }

    public void clear(){
        view.setCodigoText(null);
        view.setUnidadText(null);
        view.setNombreText(null);
        view.getDelete().setEnabled(false);
        view.getCodigo().setEnabled(true);
    }

    public void save(TipoInstrumento tipo) throws Exception{
        Service.instance().create(tipo);
        model.setCurrent(new TipoInstrumento());
    }

    public void create(TipoInstrumento w){
        try {
            Service.instance().create(w);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setList(Service.instance().getTipos());
        model.setCurrent(new TipoInstrumento());
        model.commit();
    }

    public void update(TipoInstrumento instrumento){
        try {
            Service.instance().update(instrumento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setList(Service.instance().getTipos());
        model.setCurrent(instrumento);
        model.commit();
    }
}
