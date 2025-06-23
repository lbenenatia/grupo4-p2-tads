package um.edu.uy.converter;

import com.opencsv.bean.AbstractBeanField;

import java.util.Date;

public class FechaConversor extends AbstractBeanField<Integer, String> {

    @Override
    protected Integer convert(String fecha) {
        Date date = new Date(Long.parseLong(fecha)*1000);
        return date.getMonth();
    }
}
