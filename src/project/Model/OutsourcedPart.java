package project.Model;

public class OutsourcedPart extends Part {

    private String companyName;

    public OutsourcedPart(int id, String name, Double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
