package dev.reymark.models;

import dev.sol.core.application.FXModel;
import dev.sol.core.properties.beans.FXStringProperty;

public class Department extends FXModel {
    private FXStringProperty emp_id;
    private FXStringProperty name;
    private FXStringProperty location;

    public Department(String dep_id, String name, String location) {
        this.emp_id = new FXStringProperty(dep_id);
        this.name = new FXStringProperty(name);
        this.location = new FXStringProperty(location);

        track_properties(this.emp_id, this.name, this.location);

    }

    public FXStringProperty depIDProperty() {
        return emp_id;
    }

    public String getDepId() {
        return depIDProperty().get();
    }

    public void setDepId(String emp_id) {
        depIDProperty().set(getDepId());

    }

    public FXStringProperty nameProperty() {
        return name;

    }

    public String getName() {
        return nameProperty().get();

    }

    public void setName(String name) {
        nameProperty().set(getDepId());

    }

    public FXStringProperty locationProperty() {
        return location;

    }

    public String getLocation() {
        return locationProperty().get();
    }

    public void setLocation(String location) {
        locationProperty().set(getLocation());
    }

    @Override
    public dev.sol.core.application.FXModel clone() {
        return new Department(getDepId(), getName(), getLocation());
    }

    @Override
    public void copy(dev.sol.core.application.FXModel arg0) {
        Department c = (Department) arg0;

        setDepId(c.getDepId());
        setName(c.getName());
        setLocation(c.getLocation());

    }

}