package softuni.meal_plan.model.binding;

import softuni.meal_plan.model.service.SupplierServiceModel;
import softuni.meal_plan.model.service.UserServiceModel;

import java.math.BigDecimal;
import java.util.Date;

public class OrderCreateBindingModel {

    private Date orderDate;
    private Date deliveryDate;
    private BigDecimal price;
    private UserServiceModel user;
    private SupplierServiceModel supplier;

    public OrderCreateBindingModel(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public SupplierServiceModel getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierServiceModel supplier) {
        this.supplier = supplier;
    }
}
