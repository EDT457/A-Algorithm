import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        Step head = new Step(start, end);
        HashMap<Point, Boolean> closedList = new HashMap<>();
        PriorityQueue<Step> openList = new PriorityQueue<>(Comparator.comparingDouble(Step::getF));
        HashMap<Point, Step> stepped = new HashMap<>();
        Step currentNode = head;
        openList.add(head);
        stepped.put(start, head);
        while(!openList.isEmpty()){
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getLocation())
                    .filter(canPassThrough)
                    .filter(pt -> !closedList.containsKey(pt))
                    .collect(Collectors.toList());
            for(Point pos: neighbors){
                Step step = new Step(pos, end, currentNode);
                if(!stepped.containsKey(pos)){
                    stepped.put(pos, step);
                    openList.add(step);
                }else if (stepped.get(pos).getG() < step.getG()){
                        openList.remove(stepped.get(pos));
                        openList.add(step);
                        stepped.put(pos, step);
                }
            }
            closedList.put(currentNode.getLocation(), true);
            openList.remove(currentNode);
            if(currentNode.getPrior() != null && withinReach.test(currentNode.getPrior().getLocation(), end) ){
                openList.clear();
            }
            if (!openList.isEmpty()){
                currentNode = openList.peek();
            }
        }
        while(currentNode.getPrior() != null){
            path.add(currentNode.getPrior().getLocation());
            currentNode = currentNode.getPrior();
        }
        return path;
    }
}