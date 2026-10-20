package mycomp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PointTest {

    private static RationalFraction fr(long n, long d) {
        return new RationalFraction(n, d);
    }

    private static Point p(long x, long y, long z) {
        return new Point(fr(x, 1), fr(y, 1), fr(z, 1));
    }

    @Test
    void distanceBetweenPoints() {
        Point a = p(0, 0, 0);
        Point b = new Point(fr(1, 2), fr(1, 2), fr(1, 2));
        assertEquals(Math.sqrt(0.75), a.distanceTo(b), 1e-9);
    }

    @Test
    void distanceIsSymmetric() {
        Point a = p(1, 2, 3);
        Point b = p(4, 6, 3);
        assertEquals(5.0, a.distanceTo(b), 1e-9);
        assertEquals(5.0, b.distanceTo(a), 1e-9);
    }

    @Test
    void distanceToItselfIsZero() {
        Point a = p(1, 2, 3);
        assertEquals(0.0, a.distanceTo(a), 1e-9);
    }

    @Test
    void distanceToOrigin() {
        assertEquals(3.0, p(1, 2, 2).distanceToO(), 1e-9);
        assertEquals(0.0, p(0, 0, 0).distanceToO(), 1e-9);
    }

    @Test
    void collinearPointsOnDiagonal() {
        Point a = p(0, 0, 0);
        Point b = new Point(fr(1, 2), fr(1, 2), fr(1, 2));
        Point c = p(1, 1, 1);
        assertTrue(Point.areCollinear(a, b, c));
    }

    @Test
    void collinearPointsNotThroughOrigin() {
        assertTrue(Point.areCollinear(p(1, 1, 0), p(2, 3, 1), p(3, 5, 2)));
    }

    @Test
    void nonCollinearPoints() {
        assertFalse(Point.areCollinear(p(0, 0, 0), p(1, 0, 0), p(0, 1, 0)));
    }

    @Test
    void coincidentPointsAreCollinear() {
        assertTrue(Point.areCollinear(p(1, 1, 1), p(1, 1, 1), p(5, 5, 5)));
    }
}