package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultList implements Iterable<Result>{
    private final List<Result> results;

    public ResultList() {
        this.results = new ArrayList<Result>();
    }

    public void addResult(Result newResult) {
        results.add(newResult);
    }

    public void clear() {
        results.clear();
    }

    public Iterator<Result> iterator() {
        return results.iterator();
    }

    public int getSize() {return results.size();}
    public Result getResult(int i) {return results.get(i);}
}
