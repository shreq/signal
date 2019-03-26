package serialization;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;

public class SerializationModel implements Serializable {
    public TreeMap<BigDecimal, Double> data = new TreeMap<>();
    public double t0;
    public double fs;

    public SerializationModel(double _t0, double _fs, TreeMap<BigDecimal, Double> _data){
        t0 = _t0;
        fs = _fs;
        BigDecimal ts = new BigDecimal(1).divide(new BigDecimal(fs));
        for (BigDecimal t = ts; t.compareTo(_data.lastKey()) <= 0; t = t.add(ts)){
            data.put(t, _data.get(t));
        }
    }

}
