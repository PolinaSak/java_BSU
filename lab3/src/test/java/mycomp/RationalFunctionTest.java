package mycomp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RationalFractionTest {

    @Test
    void reducesFraction() {
        RationalFraction f = new RationalFraction(2, 4);
        assertEquals(1, f.getNumerator());
        assertEquals(2, f.getDenominator());
    }

    @Test
    void movesSignToNumerator() {
        RationalFraction f = new RationalFraction(1, -2);
        assertEquals(-1, f.getNumerator());
        assertEquals(2, f.getDenominator());
    }

    @Test
    void zeroNormalizesToZeroOverOne() {
        RationalFraction f = new RationalFraction(0, 5);
        assertEquals(0, f.getNumerator());
        assertEquals(1, f.getDenominator());
        assertTrue(f.isZero());
    }

    @Test
    void zeroDenominatorThrows() {
        assertThrows(IllegalArgumentException.class, () -> new RationalFraction(1, 0));
    }

    @Test
    void subtractWorks() {
        RationalFraction r = new RationalFraction(1, 2).subtract(new RationalFraction(1, 3));
        assertEquals("1/6", r.toString());
    }

    @Test
    void multiplyWorks() {
        RationalFraction r = new RationalFraction(2, 3).multiply(new RationalFraction(3, 4));
        assertEquals("1/2", r.toString());
    }

    @Test
    void toDoubleWorks() {
        assertEquals(0.5, new RationalFraction(1, 2).toDouble(), 1e-9);
    }

    @Test
    void toStringOmitsDenominatorOne() {
        assertEquals("3", new RationalFraction(6, 2).toString());
    }
}