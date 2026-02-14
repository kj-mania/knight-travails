package edu.westga.dsdm.knightstravails.solver;

import edu.westga.dsdm.knightstravails.model.Position;
import javafx.geometry.Pos;

import java.util.*;

public class Solver {

    public LinkedList<Position> getSolutionPath(Position start, Position end) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.equals(end)) {
                return reconstructPath(parent, end);
            }

            Position[] neighbors = this.generateNeighbors(current);

            for (Position neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return new LinkedList<>();
    }

    private LinkedList<Position> reconstructPath(Map<Position, Position> parent, Position end) {
        LinkedList<Position> path = new LinkedList<>();
        Position current = end;

        while (current != null) {
            path.addFirst(current);
            current = parent.get(current);
        }

        return path;
    }

    private Position[] generateNeighbors(Position node) {
        List<Position> validNeighbors = new ArrayList<>();

        int[][] moves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : moves) {
            int newRow = node.row() + move[0];
            int newCol = node.col() + move[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                validNeighbors.add(new Position(newRow, newCol));
            }
        }

        return validNeighbors.toArray(new Position[0]);
    }
}