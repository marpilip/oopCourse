package ru.marpilip.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersection(Range range1, Range range2) {
        if (range1.getTo() < range2.getFrom() || range1.getFrom() > range2.getTo()) { //пересечения нет
            return null;
        } else {
            return new Range(Math.max(range1.getFrom(), range2.getFrom()), Math.min(range1.getTo(), range2.getTo()));
        }
    }

    public Range[] getCombining(Range range1, Range range2) {
        Range[] ranges = new Range[2];

        if (range1.getTo() < range2.getFrom() || range1.getFrom() > range2.getTo()) {
            ranges[0] = range1;
            ranges[1] = range2;
        } else {
            Range range = new Range(Math.min(range1.getFrom(), range2.getFrom()), Math.max(range1.getTo(), range2.getTo()));
            ranges[0] = range;
        }

        return ranges;
    }

    public Range[] getDifference(Range range1, Range range2) {
        Range[] ranges = new Range[2];

        if (range1.getTo() < range2.getFrom() || range1.getFrom() > range2.getTo() || (range1.getFrom() > range2.getFrom() && range1.getTo() < range2.getTo())) {
            ranges = null;
        } else if (range1.getFrom() >= range2.getFrom() && range1.getTo() >= range2.getTo()) {
            ranges[0] = new Range(range2.getTo(), range1.getTo());
        } else if (range1.getFrom() <= range2.getFrom() && range1.getTo() <= range2.getTo()) {
            ranges[0] = new Range(range2.getFrom(), range1.getTo());
        } else if (range1.isInside(range2.getFrom()) && range1.isInside(range2.getTo())) {
            ranges[0] = new Range(range1.getFrom(), range2.getFrom());
            ranges[1] = new Range(range2.getTo(), range1.getTo());
        }

        return ranges;
    }
}