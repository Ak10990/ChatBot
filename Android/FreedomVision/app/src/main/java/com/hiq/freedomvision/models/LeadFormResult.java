package com.hiq.freedomvision.models;

public class LeadFormResult {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }

    public class Data {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ClassPojo [status = " + status + "]";
        }
    }
}