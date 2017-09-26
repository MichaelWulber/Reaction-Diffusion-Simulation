package Simulation;

import States.GrowingState;
import States.StableState;
import States.State;

import java.awt.*;

public class ReactionDiffusionSim {

    private int count = 0;
    private int width;
    private int height;
    private State state;
    private double[][][] model;
    private double[][][] prev;
    private double feedRate;
    private double killRate;
    private double dA;
    private double dB;

    public ReactionDiffusionSim(int width, int height){
        this.width = width;
        this.height = height;
        this.feedRate = 0.055;
        this.killRate = 0.062;
        this.dA = 1.0;
        this.dB = 0.5;
        this.state = new GrowingState();
        this.model = new double[width][height][2];
        init();
    }

    public void init(){
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                model[i][j][0] = 1.0;
                model[i][j][1] = 0.0;
            }
        }

        for (int i = width/2 - width/10; i < width/2 + width/10; i++){
            for (int j = height/2 - width/10; j < height/2 + height/10; j++){
                model[i][j][0] = 0.0;
                model[i][j][1] = 1.0;
            }
        }
        this.prev = model;

    }

    public void tick(){
        state.tick(this);
        if(this.isStable()){
            setState(new StableState());
        }

    }

    public void render(Graphics g){
        state.render(g, this);
    }

    private boolean isStable(){
        return false;
    }

    public double dA(int x, int y){
        double A = getA(x, y);
        double B = getB(x, y);

        return (dA * laplaceA(x, y) - A*B*B + feedRate * (1 - A));
    }

    public double dB(int x, int y){
        double A = getA(x, y);
        double B = getB(x, y);

        return (dB * laplaceB(x, y) + A*B*B - (killRate + feedRate) * B);
    }

    private double laplaceA(int x, int y){
        double val = -getA(x, y);

        val += getA(x + 1, y) * 0.2;
        val += getA(x - 1, y) * 0.2;
        val += getA(x, y + 1) * 0.2;
        val += getA(x, y - 1) * 0.2;

        val += getA(x + 1, y + 1) * 0.05;
        val += getA(x - 1, y + 1) * 0.05;
        val += getA(x + 1, y - 1) * 0.05;
        val += getA(x - 1, y - 1) * 0.05;
        return val;
    }

    private double laplaceB(int x, int y){
        double val = -getB(x, y);
        val += getB(x + 1, y) * 0.2;
        val += getB(x - 1, y) * 0.2;
        val += getB(x, y + 1) * 0.2;
        val += getB(x, y - 1) * 0.2;

        val += getB(x + 1, y + 1) * 0.05;
        val += getB(x - 1, y + 1) * 0.05;
        val += getB(x + 1, y - 1) * 0.05;
        val += getB(x - 1, y - 1) * 0.05;
        return val;
    }

    public Color extractColor(int x, int y){
        double A = model[x][y][0];
        double B = model[x][y][1];
        int scale = (int) (A * 255.0 + B * 0.0);

        if (scale > 255){
            scale = 255;
        } else if (scale < 0){
            scale = 0;
        }

        return new Color(scale, scale, scale);
    }


    // GETTERS AND SETTERS

    public double getPrevA(int x, int y){
        return prev[x][y][0];
    }

    public void setPrevA(int x, int y, double c ){
        prev[x][y][0] = c;
    }

    public double getPrevB(int x, int y){
        return prev[x][y][1];
    }

    public void setPrevB(int x, int y, double c ){
        prev[x][y][1] = c;
    }

    public double getA(int x, int y){
        return model[x][y][0];
    }

    public void setA(int x, int y, double c ){
        model[x][y][0] = c;
    }

    public double getB(int x, int y){
        return model[x][y][1];
    }

    public void setB(int x, int y, double c ){
        model[x][y][1] = c;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double[][][] getModel(){
        return model;
    }

    public double[][][] getPrev(){
        return prev;
    }
    public void setPrev(double[][][] current){
        prev = current;
    }
}
