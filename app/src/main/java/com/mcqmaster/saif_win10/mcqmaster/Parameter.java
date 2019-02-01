package com.mcqmaster.saif_win10.mcqmaster;

public class Parameter {
    private String source;
    private String sum;
    private String topic;
    private String total;


    public Parameter(String source, String sum, String topic, String total) {
        this.source = source;
        this.sum = sum;
        this.topic = topic;
        this.total = total;

    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Parameter()
    {

    }
}
