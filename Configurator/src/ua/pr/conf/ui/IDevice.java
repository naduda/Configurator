package ua.pr.conf.ui;

public interface IDevice {
	String getType();
	String getAddress();
	String getSerialNumber();
	String getSpeed();
	
	void setType(String type);
	void setAddress(String address);
	void setSerialNumber(String serialNumber);
	void setSpeed(String speed);
}
