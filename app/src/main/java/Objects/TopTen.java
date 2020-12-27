package Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TopTen {

    private ArrayList<Records> records = new ArrayList<>();

    public TopTen() {
    }

    public TopTen(ArrayList<Records> records) {
        this.records = records;

    }

    public ArrayList<Records> getRecords() {
        return records;
    }
    public void setRecords(ArrayList<Records> records) {
        this.records = records;
    }

}
