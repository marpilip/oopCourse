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

    public Range getIntersection(Range range) {
        if (to <= range.getFrom() || from >= range.getTo()) { // пересечения нет
            return null;
        }

        return new Range(Math.max(from, range.getFrom()), Math.min(to, range.getTo()));
    }

    public Range[] getUnion(Range range2) {
        if (to < range2.getFrom() || from > range2.getTo()) {
            return new Range[]{new Range(from, to), range2};
        }

        return new Range[]{new Range(Math.min(from, range2.getFrom()), Math.max(to, range2.getTo()))};
    }

    public Range[] getDifference(Range range) {
        if (to < range.getFrom() || from > range.getTo() || (from > range.getFrom() && to < range.getTo())) {
            return new Range[]{};
        }

        if (from >= range.getFrom() && to >= range.getTo()) {
            return new Range[]{new Range(range.getTo(), to)};
        }

        if (from <= range.getFrom() && to <= range.getTo()) {
            return new Range[]{new Range(from, range.getFrom())};
        }

        if (this.isInside(range.getFrom()) && this.isInside(range.getTo())) {
            return new Range[]{
                    new Range(from, range.getFrom()),
                    new Range(range.getTo(), to)
            };
        }

        return new Range[]{};
    }

    @Override
    public String toString() {
        return "(" + from + "; " + to + ")";
    }
}
