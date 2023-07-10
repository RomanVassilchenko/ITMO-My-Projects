package lab1.models;

import java.util.ArrayList;

public class ResultSet {
    ArrayList<ArrayList<Double>> iteraz = new ArrayList<>();
    ArrayList<Double> result = new ArrayList<>();
    ArrayList<Double> residuals = new ArrayList<>();
    ArrayList<ArrayList<Double>> e = new ArrayList<>();
    public void addIter(double[] iter){
        ArrayList<Double> arrayList = new ArrayList<>();
        for(double it : iter){ arrayList.add(it); }
        iteraz.add(arrayList);
    }
    public String getTable(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("№   |");
        for(int i = 0; i < iteraz.get(0).size(); i++) {
            stringBuilder.append("x").append(i+1).append("         |");
        }
        stringBuilder.append(" ###|  ");
        for(int i = 0; i < iteraz.get(0).size(); i++) {
            stringBuilder.append("eps").append(i+1).append("   |");
        }
        stringBuilder.append("\n").append("0   |");
        for(int i = 0; i < iteraz.get(0).size(); i++) {
            stringBuilder.append("0").append("          |");
        }
        stringBuilder.append("\n");
        for(int i = 0; i < iteraz.size(); i++){
            stringBuilder.append(i+1).append("   |");
            for(Double it : iteraz.get(i)){
                stringBuilder.append(String.format("%.6f",it)).append("   |");
            }
            stringBuilder.append(" ###| ");
            try {
                if (!e.isEmpty() && !e.get(i).isEmpty()) {
                    for (Double it : e.get(i)) {
                        stringBuilder.append(String.format("%.6f", it)).append("   |");
                    }
                }
            }catch (Exception ignored){}
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void setResult(double[] res){
        for(double it : res){
            result.add(it);
        }
    }

    public ArrayList<Double> getResult() {
        return result;
    }

    public void addE(ArrayList<Double> e) {
        this.e.add(e);
    }

    public ArrayList<Double> getResiduals() {
        return residuals;
    }

    public void setResiduals(ArrayList<Double> residuals) {
        this.residuals = residuals;
    }
}
