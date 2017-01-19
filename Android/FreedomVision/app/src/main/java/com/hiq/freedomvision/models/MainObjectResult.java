package com.hiq.freedomvision.models;

public class MainObjectResult {
    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }

    public class Data {
        private String pincode;

        private String city_id;

        private String state_id;

        private String city_name;

        private String area_id;

        private String hotel_id;

        private String address;

        private String hotel_display_name;

        private String longitude;

        private String latitude;

        private String hotel_name;

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getHotel_id() {
            return hotel_id;
        }

        public void setHotel_id(String hotel_id) {
            this.hotel_id = hotel_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHotel_display_name() {
            return hotel_display_name;
        }

        public void setHotel_display_name(String hotel_display_name) {
            this.hotel_display_name = hotel_display_name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        @Override
        public String toString() {
            return "ClassPojo [pincode = " + pincode + ", city_id = " + city_id + ", state_id = " + state_id + ", city_name = " + city_name + ", area_id = " + area_id + ", hotel_id = " + hotel_id + ", address = " + address + ", hotel_display_name = " + hotel_display_name + ", longitude = " + longitude + ", latitude = " + latitude + ", hotel_name = " + hotel_name + "]";
        }
    }
}