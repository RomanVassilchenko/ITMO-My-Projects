package Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Organizations extends Stack<Organization> {
    private final LocalDate initDate;

    public Organizations() {
        initDate = LocalDate.now();
    }

    public void update(List<Organization> list){
        List<Organization> space= Collections.synchronizedList(list);
        synchronized (space){
            this.clear();
            this.addAll(space);
        }
    }

    @Override
    public String toString() {
        return  "Collection type: Stack\n" +
                "Initialization date: " + initDate.toString() + "\n" +
                "Number of elements: " + this.size();
    }
}
