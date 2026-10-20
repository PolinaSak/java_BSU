package mycomp;

public class Point {
    private final RationalFraction xCoord;
    private final RationalFraction yCoord;
    private final RationalFraction zCoord;

    public Point(RationalFraction x, RationalFraction y, RationalFraction z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public double distanceTo(Point b) {
        double dx = xCoord.toDouble() - b.xCoord.toDouble();
        double dy = yCoord.toDouble() - b.yCoord.toDouble();
        double dz = zCoord.toDouble() - b.zCoord.toDouble();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    };

    public double distanceToO() {
        double x = xCoord.toDouble() * xCoord.toDouble();
        double y = yCoord.toDouble() * yCoord.toDouble();
        double z = zCoord.toDouble() * zCoord.toDouble();

        return Math.sqrt(x + y + z);
    }

    public static boolean areCollinear(Point a, Point b, Point c) {

        RationalFraction abx = b.xCoord.subtract(a.xCoord);
        RationalFraction aby = b.yCoord.subtract(a.yCoord);
        RationalFraction abz = b.zCoord.subtract(a.zCoord);

        RationalFraction acx = c.xCoord.subtract(a.xCoord);
        RationalFraction acy = c.yCoord.subtract(a.yCoord);
        RationalFraction acz = c.zCoord.subtract(a.zCoord);
    
        RationalFraction cx = aby.multiply(acz).subtract(abz.multiply(acy));
        RationalFraction cy = abz.multiply(acx).subtract(abx.multiply(acz));
        RationalFraction cz = abx.multiply(acy).subtract(aby.multiply(acx));
    
        return cx.isZero() && cy.isZero() && cz.isZero();
    }
}
