package ua.pr.conf.ui;

import javafx.beans.property.SimpleStringProperty;

public class TableDevice implements IDevice {
	private final SimpleStringProperty typeProperty = new SimpleStringProperty("");
	private final SimpleStringProperty addressProperty = new SimpleStringProperty("");
	private final SimpleStringProperty serialNumberProperty = new SimpleStringProperty("");
	private final SimpleStringProperty speedProperty = new SimpleStringProperty("");
	
	public TableDevice () {
		this("", "", "", "");
	}
	
	public TableDevice (String type, String address, String serialNumber, String speed) {
		setType(type);
		setAddress(address);
		setSerialNumber(serialNumber);
		setSpeed(speed);
	}

	@Override
	public String getType() {
		return typeProperty.get();
	}

	@Override
	public String getAddress() {
		return addressProperty.get();
	}

	@Override
	public String getSerialNumber() {
		return serialNumberProperty.get();
	}

	@Override
	public String getSpeed() {
		return speedProperty.get();
	}

	@Override
	public void setType(String type) {
		typeProperty.set(type);
	}

	@Override
	public void setAddress(String address) {
		addressProperty.set(address);
	}

	@Override
	public void setSerialNumber(String serialNumber) {
		serialNumberProperty.set(serialNumber);
	}

	@Override
	public void setSpeed(String speed) {
		speedProperty.set(speed);
	}

}
