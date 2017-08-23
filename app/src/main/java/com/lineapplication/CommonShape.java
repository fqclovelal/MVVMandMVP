package com.lineapplication;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class CommonShape {
    private int horizontalLevel;
    private int verticalLevel;
    private String ProcessName;
    private String shapeColor;
    private String textColor;

    public int getHorizontalLevel() {
        return horizontalLevel;
    }

    public void setHorizontalLevel(int horizontalLevel) {
        this.horizontalLevel = horizontalLevel;
    }

    public int getVerticalLevel() {
        return verticalLevel;
    }

    public void setVerticalLevel(int verticalLevel) {
        this.verticalLevel = verticalLevel;
    }

    public String getProcessName() {
        return ProcessName;
    }

    public void setProcessName(String processName) {
        ProcessName = processName;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
