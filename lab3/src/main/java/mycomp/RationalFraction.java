package mycomp;

public class RationalFraction {
    private long numerator;
    private long denominator;

    public RationalFraction(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("знаменатель не может быть нулем!");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        long commonDiv = gcd(Math.abs(numerator), denominator);

        this.numerator = numerator / commonDiv;
        this.denominator = denominator / commonDiv;
    }


    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }

    private static long gcd(long a, long b) {
        while (a != 0 && b != 0) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }
        return (Math.max(a, b));
    }

    public double toDouble(){
        return (double)numerator/denominator;
    }

    public RationalFraction subtract(RationalFraction other) {
        return new RationalFraction(
            this.numerator * other.denominator - other.numerator * this.denominator,
            this.denominator * other.denominator);
    }
    
    public RationalFraction multiply(RationalFraction other) {
        return new RationalFraction(
            this.numerator * other.numerator,
            this.denominator * other.denominator);
    }
    
    public boolean isZero() {
        return numerator == 0;
    }
}
