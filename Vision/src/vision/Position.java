package vision;

import java.util.ArrayList;

/**
 * Represents the centre point of an object, for example the ball or a robot.
 * 
 * @author s0840449
 * @author Alex Adams (s1046358)
 */
public class Position {
        private int x;
        private int y;

        /**
         * Default constructor.
         * 
         * @param x
         *            The x-coordinate of the object.
         * @param y
         *            The y-coordinate of the object.
         */
        public Position(int x, int y) {
                this.x = x;
                this.y = y;
        }

        /**
         * Return the x-coordinate.
         * 
         * @return The x-coordinate of the object.
         */
        public int getX() {
                return x;
        }

        /**
         * Set the x-coordinate.
         * 
         * @param x
         *            The value to set as the x-coordinate.
         */
        public void setX(int x) {
                this.x = x;
        }

        /**
         * Return the y-coordinate.
         * 
         * @return The y-coordinate of the object.
         */
        public int getY() {
                return y;
        }

        /**
         * Set the y-coordinate.
         * 
         * @param y
         *            The value to set as the y-coordinate.
         */
        public void setY(int y) {
                this.y = y;
        }
        
        @Override
        public String toString() {
                return "(" + x + ", " + y + ")";
        }

        /**
         * Compares the current x and y co-ordinates to another set of co-ordinates
         * (usually the previous co-ordinates for the position), fixing the current
         * co-ordinates based on the previous ones.
         * 
         * @param oldX
         *            The old x-coordinate.
         * @param oldY
         *            The old y-coordinate.
         */
        public void fixValues(int oldX, int oldY) {
                // Use old values if nothing found
                if (this.getX() == 0) {
                        this.setX(oldX);
                }
                if (this.getY() == 0) {
                        this.setY(oldY);
                }

                // Use old values if not changed much
                if (sqrdEuclidDist(this, new Position(oldX, oldY)) < 9) {
                        this.setX(oldX);
                        this.setY(oldY);
                }
        }

        /**
         * Updates the centre point of the object, given a list of new points to
         * compare it to. Any points too far away from the current centre are
         * removed, then a new mean point is calculated and set as the centre point.
         * 
         * @param points
         *            The set of points
         */
        public void filterPoints(ArrayList<Position> points) {
                if (points.size() > 0) {
                        double stdev = standardDeviation(points, this);

                        int count = 0;
                        int newX = 0;
                        int newY = 0;

                        // Remove points further than standard deviation
                        for (int i = 0; i < points.size(); ++i) {
                                Position p = points.get(i);

                                if (Math.sqrt(sqrdEuclidDist(this, p)) < stdev) {
                                        newX += p.getX();
                                        newY += p.getY();
                                        ++count;
                                }
                        }

                        int oldX = this.getX();
                        int oldY = this.getY();

                        if (count > 0) {
                                this.setX(newX / count);
                                this.setY(newY / count);
                        }

                        this.fixValues(oldX, oldY);
                }
        }

        /**
         * TODO: find out why 1.17 was chosen by the previous year. Removes points
         * which are more than 1.17 standard deviations from the centroid and
         * returns the rest
         * 
         * @param points
         *            The set of points
         * @param centroid
         *            The centroid of the points
         * @return An ArrayList of points which are within 1.17 standard deviations
         *         of the centroid
         */
        public static ArrayList<Position> removeOutliers(
                        ArrayList<Position> points, Position centroid) {
                ArrayList<Position> goodPoints = new ArrayList<Position>();

                if (points.size() > 0) {
                        double stdDev = standardDeviation(points, centroid);
                        // Remove points further than 1.17 standard deviations
                        stdDev *= 1.17;
                        for (int i = 0; i < points.size(); ++i) {
                                Position p = points.get(i);
                                if (Math.sqrt(sqrdEuclidDist(centroid, p)) < stdDev)
                                        goodPoints.add(p);
                        }
                }

                return goodPoints;
        }

        /**
         * Calculates the standard deviation for a set of points in two dimensions
         * given the centroid
         * 
         * @param points
         *            The set of points
         * @param centroid
         *            The centroid of the points
         * @return The two-dimensional standard deviation of the points
         */
        public static double standardDeviation(ArrayList<Position> points,
                        Position centroid) {
                double variance = 0.0;

                // Standard deviation
                for (int i = 0; i < points.size(); ++i) {
                        variance += sqrdEuclidDist(points.get(i), centroid);
                }

                return Math.sqrt(variance / (double) (points.size()));
        }

        /**
         * Calculates the squared euclidean distance between two 2D points.
         * 
         * @param p1
         *            The first point.
         * @param p2
         *            The second point.
         * 
         * @return The squared euclidean distance between the two points.
         */
        public static int sqrdEuclidDist(Position p1, Position p2) {
                int xDiff = p2.getX() - p1.getX();
                int yDiff = p2.getY() - p1.getY();

                return xDiff * xDiff + yDiff * yDiff;
        }
}