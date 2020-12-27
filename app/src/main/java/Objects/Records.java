package Objects;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyLocation;
import com.google.android.gms.maps.model.LatLng;

public class Records implements Comparable <Records>  {

    private String name="";
    private int score;
    private MyLocation location;




    public Records() {
    }

    public Records(String name, int score, MyLocation location) {
        this.name = name;
        this.score = score;
        this.location = location;
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Records{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", location=" + location +
                '}';
    }

    @Override
    public int compareTo(Records record) {
        return  record.score-this.score;
    }
}
