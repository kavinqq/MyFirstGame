package company.gametest9th.utils;

/**
 *
 * @author LSYu
 */

public class Vector {

    private double x; // 水平分量
    private double y; // 垂直分量

    // 三種建構子 1. 沒參數 2. 2個double 3.一個向量
    // 呼叫建構字沒帶參數 => 零向量
    public Vector() {
        this(0, 0);
    }

    // 設定兩個分量
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // 給一個向量 直接copy 他的兩個分量
    public Vector(Vector vector) {
        this(vector.x, vector.y);
    }

    // 弧度
    public final double getRadian() {
        return Math.atan2(y, x);
    }

    // 角度
    public final double getAngle() {
        return getRadian() / Math.PI * 180;
    }

    // 三角形法得到 XY向量 開根號 => 長度
    public final double length() {
        return Math.sqrt(lengthSquare());
    }

    // 任何一個非 0 向量皆可分解成 水平分量(X) && 垂直分量(Y)
    // 算出斜的那條 => 三角形法
    public final double lengthSquare() {
        return x * x + y * y;
    }

    // 向量歸零
    public final Vector zero() {
        this.x = this.y = 0;
        return this;
    }

    // 是否為非零向量
    public final boolean isZero() {
        return x == 0 && y == 0;
    }

    // ???
    public final Vector setLength(double value) {
        double angle = getRadian();
        x = Math.cos(angle) * value;
        y = Math.sin(angle) * value;
        return this;
    }


    // 把每次要移動的 X Y量 訂出來
    public final Vector normalize() {
        double length = length();
        x = x / length;
        y = y / length;
        return this;
    }

    // 是否為單位向量
    public final boolean isNormalized() {
        return length() == 1.0;
    }

    // 向量反轉
    public Vector reverse() {
        x = -x;
        y = -y;
        return this;
    }

    // 求兩向量的dot product
    public double dotP(Vector v) {
        return x * v.x + y * v.y;
    }

    // 求兩向量的cross product
    public double crossP(Vector v) {
        return x * v.y - y * v.x;
    }

    // 求兩向量間夾角
    public static double radianBetween(Vector v1, Vector v2) {
        if (!v1.isNormalized()) {
            v1 = new Vector(v1).normalize(); // |v1| = 1
        }
        if (!v2.isNormalized()) {
            v2 = new Vector(v2).normalize(); // |v2| = 1
        }
        return Math.acos(v1.dotP(v2));
    }

    public double vx() {
        return x;
    }

    public double vy() {
        return y;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y);
    }

    public Vector multiply(double value) {
        return new Vector(x * value, y * value);
    }

    public Vector divide(double value) {
        return new Vector(x / value, y / value);
    }
}

