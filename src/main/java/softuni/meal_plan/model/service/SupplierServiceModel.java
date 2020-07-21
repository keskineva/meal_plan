package softuni.meal_plan.model.service;

public class SupplierServiceModel extends BaseServiceModel{
    private String name;
    private String logoUrl;

    public SupplierServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
